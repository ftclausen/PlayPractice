/**
 * Notes:
 *
 * * Hash passwords in real life e.g. with PBKDF2
 *
**/
package models;

import models.*;
import org.junit.*;
import static org.junit.Assert.*;
import play.test.WithApplication;
import static play.test.Helpers.*;
import java.util.*;

public class ModelsTest extends WithApplication {
    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));
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
        User fred = new User("fred@example.com", "Fred", "this is a secret");
        User jata = new User("jata@example.com", "Jata", "jata's secret");
        fred.save();
        jata.save();

        Project project = Project.create("Play 2", "play", "fred@example.com");
        Task t1 = new Task();
        t1.title = "Do tutorial";
        t1.project = project;
        t1.assignedTo = fred;
        t1.done = true;
        t1.save();

        Task t2 = new Task();
        t2.title = "Do next tutorial";
        t2.project = project;
        t2.assignedTo = jata;
        t2.done = true;
        t2.save();

        List<Task> results = Task.findTodoInvolving("fred@example.com");
        assertEquals(1, results.size());
        assertEquals("Do tutorial", results.get(0).title);
    }
}

