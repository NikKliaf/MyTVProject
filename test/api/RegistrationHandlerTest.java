package api;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class RegistrationHandlerTest {
    private UserData userData;

    @Before
    public void setUp() throws Exception {
        userData = new UserData("src/test/api/test_users.ser");
    }

    @After
    public void tearDown() throws Exception {
        File file = new File("src/test/api/test_users.ser");
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void existsTest() {
        RegistrationHandler registrationHandler = new RegistrationHandler("Text", "Text", "Text", "Text".toCharArray(), "Text".toCharArray(),userData);
        RegistrationHandler registrationHandler1 = new RegistrationHandler("Text", "Text", "Text", "Text".toCharArray(), "Text".toCharArray(),userData);
        registrationHandler.registerUser();
        assertEquals("Username already exists", registrationHandler1.registerUser());
    }
    @Test
    public void usernameTest() {
        RegistrationHandler registrationHandler = new RegistrationHandler("Text", "Text", "", "Text".toCharArray(), "Text".toCharArray(),userData);
        assertEquals("Please enter a Username", registrationHandler.registerUser());
    }

    @Test
    public void passwordTest() {
        RegistrationHandler registrationHandler = new RegistrationHandler("Text", "Text", "Text", "".toCharArray(), "".toCharArray(),userData);
        assertEquals("Please enter a Password", registrationHandler.registerUser());

    }
    @Test
    public void notMatchTest() {
        String password = "<PASSWORD>";
        String confirmPassword = "<PASSWOR>";
        RegistrationHandler registrationHandler = new RegistrationHandler("Text", "Text", "Text", password.toCharArray(), confirmPassword.toCharArray(),userData);
        assertEquals("Passwords Do Not Match", registrationHandler.registerUser());
    }
}