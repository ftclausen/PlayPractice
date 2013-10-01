package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Fred extends Controller {

    public static String index() {
    	return redirect(controllers.Application.hello("Fred"));
    }

}