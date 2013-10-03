package controllers;

import play.*;
import play.cache.Cached;
import play.mvc.*;
import play.mvc.Http.RequestBody;
import views.html.*;

public class Application extends Controller {

	// Accept json of max of 1MB
	@BodyParser.Of(value = BodyParser.Json.class, maxLength = 1024000)
	@With(VerboseAction.class)
    public static Result index() {
		if (request().body().isMaxSizeExceeded()) {
			return badRequest("Submitted file too large\n");
		} else {
			RequestBody body = request().body();
			return ok("Got body: " + body.asJson() + "\n");
		}
    	
        
    }

}
