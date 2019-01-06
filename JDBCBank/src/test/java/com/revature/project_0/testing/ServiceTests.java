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
	private static AccountService accountService;
	
	@BeforeClass
	public static void Begin() {
		Log.info("Begin Service Testing");
		userService = UserService.getService();
		accountService = AccountService.getService();
	}
//	//UserServices
//	//===========================================================================================
//	@Test
//	public void testLogInToExistingUser() {
//		User expected = new User (1, "LeChiffre", "baccarat");
//		assertEquals(expected, userService.logIn("LeChiffre", "baccarat").get());
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
//	
//	@Test
//	public void testRegisterNewUser() {
//		User expected = new User (2, "Goldfinger", "badArchitect");
//		assertEquals(expected, userService.register("Goldfinger", "badArchitect"));
//	}
//	
//	@Test
//	public void testRegisterWithExistingUser() {
//		assertEquals(false, userService.register("LeChiffre", "Irrelevant"));
//	}
//
//	//AdminServices
//	//===========================================================================================
//
//	//AccountServices
//	//===========================================================================================
//	@Test
//	public void testViewExistingAccount() {
//		Account expected = new Account(1, 0.0);
//		assertEquals(expected, accountService.viewAccount(1));
//	}
//	
//	@Test
//	public void testViewNonexistentAccount() {
//		assertEquals(null, accountService.viewAccount(100));
//	}
//	
//	@Test
//	public void testcreateNewAccount() {
//		Account expected = new Account (1,0.0);
//		assertEquals(expected, accountService.createAccount(1));
//	}
//	
//	@Test
//	public void testdeleteExistingAccount() {
//		assertEquals(true, accountService.deleteAccount(1));
//	}
//	
//	@Test
//	public void testMakeDepositToExistingAccount() {
//		Integer expected = 1;
//		Transaction transaction = accountService.makeDeposit(1, 10.55);
//		Integer actual = transaction.getId();
//		assertEquals(expected, actual);
//	}
	
	@AfterClass
	public static void End() {
		Log.info("End Service Testing" + System.lineSeparator());
	}

}
