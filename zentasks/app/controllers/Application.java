package controllers;

import models.Project;
import models.Task;
import play.*;
import play.mvc.*;
import views.html.*;
import static play.data.Form.*;

public class Application extends Controller {

    public static Result index() {
        // return ok(index.render("ZenTasks will be here."));
    	return ok(index.render(
    				Project.find.all(),
    				Task.find.all()
    			));
    }

}
