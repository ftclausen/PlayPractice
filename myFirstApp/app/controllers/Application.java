package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

	public static Result hello(String name) {
		return ok("Hello from root " + name);
	}
    public static Result index(String name) {
    	return ok("Hello world! Your name is " + name);
    	// return notFound("No page here bitch. Yo.");
    	//return internalServerError("The server made a boo boo");
    	// return redirect("/tmp");
    }

}
