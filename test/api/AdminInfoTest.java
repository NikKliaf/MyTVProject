package api;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AdminInfoTest {

    private AdminInfo adminInfo;

    @Before
    public void setUp() {
        adminInfo = new AdminInfo("John", "Doe", "admin1", "password1");
    }

    @After
    public void tearDown() {
        adminInfo = null;
    }

    @Test
    public void getFirstName() {
        assertEquals("John", adminInfo.getFirstName());
    }

    @Test
    public void getLastName() {
        assertEquals("Doe", adminInfo.getLastName());
    }

    @Test
    public void getUsername() {
        assertEquals("admin1", adminInfo.getUsername());
    }

    @Test
    public void getPassword() {
        assertEquals("password1", adminInfo.getPassword());
    }
}
