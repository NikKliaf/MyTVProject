package api;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AdminTest {

	private Admin admin;

	@Before
	public void setUp() {
		AdminInfo adminInfo = new AdminInfo("John", "Doe", "admin1", "password1");
		admin = new Admin(adminInfo);
	}

	@After
	public void tearDown() {
		admin = null;
	}

	@Test
	public void getFirstName() {
		assertEquals("John", admin.getFirstName());
	}

	@Test
	public void getLastName() {
		assertEquals("Doe", admin.getLastName());
	}

	@Test
	public void getUsername() {
		assertEquals("admin1", admin.getUsername());
	}

	@Test
	public void getPassword() {
		assertEquals("password1", admin.getPassword());
	}

	@Test
	public void isAdmin() {
		assertTrue(admin.isAdmin());
	}

	@Test
	public void getFavorites() {
		assertNull(admin.getFavorites());
	}
}
