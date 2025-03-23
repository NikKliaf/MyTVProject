package api;

import gui.LOGINWINDOW;
import gui.MAINWINDOW;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import javax.swing.*;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.*;



public class LoginHandlerTest {
    private static final String TEST_FILE_NAME = "test_login.ser";
    private final UserData userData = new UserData(TEST_FILE_NAME);

    @BeforeEach
    public void setUp() {
        File file = new File(TEST_FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
    }
    @After
    public void tearDown() {
        File file = new File(TEST_FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
    }
    @Test
    public void testAdminLogin() {
        userData.addUser(new Admin(new AdminInfo("John","Doe","admin1", "password1")));

        LoginHandler loginHandler = new LoginHandler("admin1", "password1",userData);
        assertEquals(loginHandler.handleLogin(), "admin");

        LoginHandler loginHandler1 = new LoginHandler("wrongadmin", "password1",userData);
        assertEquals(loginHandler1.handleLogin(), "failed");
    }

    @Test
    public void testSubscriberLogin() {
        userData.addUser(new Subscriber("John", "Doe", "subscriber","password",new ArrayList<VideoContent>()));

        LoginHandler loginHandler = new LoginHandler("subscriber", "password", userData);
        assertEquals(loginHandler.handleLogin(), "subscriber");

        LoginHandler loginHandler1 = new LoginHandler("wrongsubscriber", "password", userData);
        assertEquals(loginHandler1.handleLogin() ,"failed");

    }
}
