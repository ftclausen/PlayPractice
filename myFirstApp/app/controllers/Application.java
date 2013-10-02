package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

	public static Result signInTheWorld(String user) {
		session("connected", user);
		flash("success", "You signed in successfully");
		return ok("Welcome citizen " + user);
	}
	
	public static Result signOut() {
		session().remove("connected");
		// or session().clear();
		return ok("Bye");
	}
	
	public static Result index() {
		return redirect(controllers.routes.Application.hello("Default Dave"));
	}
	
	public static Result hello(String name) {
		response().setContentType("text/html; charset=utf-8");
		response().setHeader("the-answer", "42");
		response().setCookie("theme", "ultragreen");
		String message = flash("success");
		String user = session("connected");
		if (user != null) {
			if (message == null) {
				return ok("Welcome back " + user + " who requested " + name);
			} else { 
				return ok("Welcome back " + user + " who requested " + name + " and flash value is " + message);
			}
			
		} else {
			return unauthorized("Oops, you have not yet connected");
		}

	}

}
