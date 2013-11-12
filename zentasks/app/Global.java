import java.util.List;

import models.User;

import com.avaje.ebean.Ebean;

import play.Application;
import play.GlobalSettings;
import play.libs.Yaml;


public class Global extends GlobalSettings {
	@Override
	public void onStart(Application app) {
		// Check if we have an empty DB - if so then populate with some test data
		if (User.find.findRowCount() == 0) {
			Ebean.save((List) Yaml.load("initial-data.yml"));
		}
	}
}
