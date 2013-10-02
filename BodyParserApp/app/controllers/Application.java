package controllers;

import play.*;
import play.mvc.*;
import play.mvc.Http.RequestBody;
import views.html.*;

public class Application extends Controller {

	@BodyParser.Of(BodyLengthParser.class)
    public static Result index() {
    	RequestBody body = request().body().as(BodyLength.class);
    	
        return ok("Got body: " + body.getLength());
    }

}
