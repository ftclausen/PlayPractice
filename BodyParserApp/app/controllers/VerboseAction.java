package controllers;

import play.Logger;
import play.libs.F.Promise;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.SimpleResult;


public class VerboseAction extends Action.Simple {
	public Promise<SimpleResult> call(Http.Context ctx) throws Throwable {
		Logger.info("Calling action for : " + ctx);
		return delegate.call(ctx);
	}

}
