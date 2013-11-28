/**
 * Notes:
 *
 * * Hash passwords in real life e.g. with PBKDF2
 *
**/
package models;

import models.*;

import org.junit.*;
import org.yaml.snakeyaml.error.YAMLException;

import static org.junit.Assert.*;
import play.libs.Yaml;
import play.test.WithApplication;
import static play.test.Helpers.*;

import java.io.IOException;
import java.util.*;

import play.Logger;

import com.avaje.ebean.*;

public class ModelsTest extends WithApplication {
    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase(), fakeGlobal()));
    }

    @Test
    public void createAndRetrieveUser() {
        new User("fred@example.com", "Fred", "this is a secret").save();
        User fred = User.find.where().eq("email", "fred@example.com").findUnique();
        assertNotNull(fred);
        assertEquals("Fred", fred.name);
    }

    @Test
    public void tryAuthenticateUser() {
        new User("fred@example.com", "Fred", "this is a secret").save();
        assertNotNull(User.authenticate("fred@example.com", "this is a secret"));
        assertNull(User.authenticate("invalid@gmail.com", "bla"));
        assertNull(User.authenticate("another_invalid_user@bla.com", "bla2"));
    }

    @Test
    public void findProjectsInvolving() {
        new User("fred@example.com", "Fred", "this is a secret").save();
        new User("jata@example.com", "Jata", "jata's secret").save();

        Project.create("Play 2", "play", "fred@example.com");
        Project.create("Play 1", "play", "jata@example.com");

        List<Project> results = Project.findInvolving("fred@example.com");
        assertEquals(1, results.size());
        assertEquals("Play 2", results.get(0).name);
    }

    @Test
    public void findTodoTasksInvolving() {
        // TODO: Add multi user todo test
        User fred = new User("fred@example.com", "Fred", "this is a secret");
        fred.save();

        Project project = Project.create("Play 2", "play", "fred@example.com");
        Task t1 = new Task();
        t1.title = "Do tutorial";
        t1.project = project;
        t1.assignedTo = fred;
        t1.save();

        List<Task> results = Task.findTodoInvolving("fred@example.com");
        for (Task result: results) {
            Logger.debug("Got task : " + result.title + " with owner " + result.assignedTo.email);
        }
        assertEquals(1, results.size());
        assertEquals("Do tutorial", results.get(0).title);
    }

    @Test
    public void fullTest() {
    	try {
    		Ebean.save((List) Yaml.load("test-data.yml"));
    	} catch (YAMLException e) {
    		Logger.error("Cannot prcess test data file : test-data.yml");
    	}
    	
    	// Count all the things
    	assertEquals(3, User.find.findRowCount());
    	assertEquals(7, Project.find.findRowCount());
    	assertEquals(5, Task.find.findRowCount());
    	
    	// Auth
    	assertNotNull(User.authenticate("bob@example.com", "secret"));
    	assertNotNull(User.authenticate("jane@example.com", "secret"));
    	assertNull(User.authenticate("jeff@example.com", "secret not correct"));
    	assertNull(User.authenticate("tom@example.com", "secret not correct"));
    	
    	// Find all Bob's projects
    	List<Project> bobsProjects = Project.findInvolving("bob@example.com");
    	assertEquals(5, bobsProjects.size());
    	
    	// Find all Bob's todos
    	List<Task> bobsTasks = Task.findTodoInvolving("bob@example.com");
    	assertEquals(4, bobsTasks.size());
    	
    }
}


