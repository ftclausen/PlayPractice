package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

	public static Result index() {
		return redirect(controllers.routes.Application.hello("Default Dave"));
	}
	
	public static Result hello(String name) {
		return ok("Hello there " + name);
	}

}
