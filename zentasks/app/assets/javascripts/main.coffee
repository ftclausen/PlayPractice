$(".options dt, .users dt").live "click", (e) ->
    e.preventDefault()
    if $(e.target).parent().hasClass("opened")
        $(e.target).parent().removeClass("opened")
    else
        $(e.target).parent().addClass("opened")
        $(e.document).one "click", ->
            $(e.target).parent().removeClass("opened")
    false

$.fn.editInPlace = (method, options...) ->
    this.each ->
        methods =
            # public methods
            init: (options) ->
                valid = (e) =>
                    newValue = @input.val()
                    options.onChange.call(options.context, newValue)
                cancel = (e) =>
                    @el.show()
                    @input.hide()
                @el = $(this).dblclick(methods.edit)
                @input = $(this).dblclick(methods.edit)
                    .insertBefore(@el)
                    .keyup(e) ->
                        switch(e.keyCode)
                            # Enter key
                            when 13 then $(this).blur()
                            # Escape key
                            when 27 then cancel(e)
                    .blur(valid)
                    .hide()
            edit: ->
                @input
                    .val(@el.text())
                    .show()
                    .focus()
                    .select()
                @el.hide()
            close: (newName) ->
                @el.text(newName).show()
                @input.hide()
    # Apparently the below is the jQuery approach - http://docs.jquery.com/Plugins/Authoring
    if ( methods[method] )
        return methods[method].apply(this, options)
    else if (typeof method == 'object')
        return methods.init.call(this, method)
    else
        $.error("Method " + method + " does not exist.")

class Drawer extends Backbone.View
    initialize: ->
        @el.children("li").each (i, group) ->
            new Group
                el: $(group)
            $("li", group).each (i, project) ->
                new Project
                    el: (project)

class Group extends Backbone.View
    events:
        "click  .toggle"     : "toggle"
        "click  .newProject" : "newProject"
    newProject: (e) ->
        @el.removeClass("closed")
        r = jsRoutes.controllers.Projects.add()
        $.ajax
            url: r.url
            type: r.type
            context: this
            data:
                group: @el.attr("data-group")
            success: (tpl) ->
                _list = $("ul", @el)
                _view = new Project
                    el: $(tpl).appendTo(_list)
                _view.el.find(".name").editInPlace("edit")
            error: (err) ->
                $.error("Error: " + err)
    toggle: (e) ->
        e.preventDefault()
        @el.toggleClass("closed")
        false

class Project extends Backbone.View

    $ ->
        drawer = new Drawer el: $("#projects")
