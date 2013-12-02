package controllers;

import java.util.*;

import static play.data.Form.form;
import play.mvc.*;
import views.html.projects.*;
import models.*;
import play.Logger;

@Security.Authenticated(Secured.class)
public class Projects extends Controller {
    public static Result add() {
        Project newProject = Project.create(
                "New project",
                form().bindFromRequest().get("group"),
                request().username()
        );
        return ok(item.render(newProject));
    }

    public static Result rename(Long project) {
        if (Secured.isMemberOf(project)) {
            return ok(
                    Project.rename(
                        project,
                        form().bindFromRequest().get("name")
                    )
            );
        } else {
            Logger.warn("Attempt by non-member, " + request().username() + 
                        " to rename project " + project);
            return forbidden();
        }
    }

    public static Result delete(Long project) {
        if (Secured.isMemberOf(project)) {
            Project.find.ref(project).delete();
            return ok();
        } else {
            return forbidden();
        }
    }

    public static Result addGroup() {
        return ok(
            group.render("New group", new ArrayList<Project>())
        );
    }
}

