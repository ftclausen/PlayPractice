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
}


