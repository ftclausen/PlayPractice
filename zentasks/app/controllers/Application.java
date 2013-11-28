package controllers;

import models.Project;
import models.Task;
import models.User;
import play.*;
import play.mvc.*;
import views.html.*;
import static play.data.Form.*;
import play.data.*;

public class Application extends Controller {

    @Security.Authenticated(Secured.class)
    public static Result index() {
        String username = request().username();
    	return ok(index.render(
    				Project.findInvolving(username),
                    Task.findTodoInvolving(username),
                    User.find.byId(username)
    			));
    }


    public static Result login() {
        return ok(
                login.render(form(Login.class))
        );
    }

    public static Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(
                routes.Application.login()
        );
    }

    public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session().clear();
            session("email", loginForm.get().email);
            return redirect(
                    routes.Application.index()
            );
        }
    }


    public static class Login {
        public String email;
        public String password;
        public String validate() {
            if (User.authenticate(email, password) == null) {
                return "Invalid user name or password.";
            }
            return null;
        }
    }

}
