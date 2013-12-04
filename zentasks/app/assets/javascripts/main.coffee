$(".options dt, .users dt").live "click", (e) ->
    e.preventDefault()
    if $(e.target).parent().hasClass("opened")
        $(e.target).parent().removeClass("opened")
    else
        $(e.target.parent().addClass("opened")
        $(e.document).one "click", ->
            $(e.target).parent().removeClass("opened")
    false

$.fn.editInPlace = (method, options...) ->
    this.each ->
        methods =
            # public methods
            init: (options) ->
            edit: ->
            close: (newName) ->
    if ( methods[method] )
        return methods[method].apply(this, options)
    else if (typeof method == 'object')
        return methods.init.call(this, method)
    else
        $.error("Method " + method + " does not exist.")
