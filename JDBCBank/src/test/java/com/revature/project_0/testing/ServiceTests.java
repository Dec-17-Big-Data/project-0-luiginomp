package com.revature.project_0.testing;

import static org.junit.Assert.*;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;

import com.revature.project_0.models.*;
import com.revature.project_0.services.*;

public class ServiceTests {
	
	private static Logger Log = LogManager.getLogger(ServiceTests.class);
	private static UserService userService;
	
	@BeforeClass
	public static void Begin() {
		Log.info("Begin Service Testing");
		userService = UserService.getService();
	}
	
//	@Test
//	public void testLogInToExistingUser() {
//		User expected = new User (1, "LeChiffre", "baccarat");
//		User actual = userService.logIn("LeChiffre", "baccarat").get();
//		assertEquals(expected, actual);
//	}
//	
//	@Test
//	public void testLogInWithNonexistentUsername() {
//		assertEquals(Optional.empty(), userService.logIn("SomeRandomName", "Irrelevant"));
//	}
//	
//	@Test
//	public void testLogInWithIncorrectPassword() {
//		assertEquals(Optional.empty(), userService.logIn("LeChiffre", "WrongPassword"));
//	}
	
	@AfterClass
	public static void End() {
		Log.info("End Service Testing");
	}

}
