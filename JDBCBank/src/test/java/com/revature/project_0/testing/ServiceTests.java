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
	private static AdminService adminService;
	private static AccountService accountService;
	
	@BeforeClass
	public static void Begin() {
		Log.info("Begin Service Testing");
		userService = UserService.getService();
		adminService = AdminService.getService();
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
//	@Test
//	public void testAdminLogIn() {
//		assertEquals(true, adminService.logIn("Overseer", "Vault#101"));
//	}
//	
//	@Test
//	public void testIncorrectLogIn() {
//		assertEquals(false, adminService.logIn("RandomName", "RandomPassword"));
//	}
//	
//	@Test
//	public void testDeleteExistingUser() {
//		assertEquals(true, adminService.deleteUser("LeChiffre"));
//	}
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
//		Integer expectedId = 1;
//		Transaction transaction = accountService.makeDeposit(1, 20.00);
//		Integer actualId = transaction.getId();
//		assertEquals(expectedId, actualId);
//	}
//	
//	@Test
//	public void testMakeDepositToNonexistentAccount() {
//		assertEquals(null, accountService.makeDeposit(100, 10.55));
//	}
//	
//	@Test
//	public void testMakeWithdrawalToExistingAccountWithAvailableBalance() {
//		Integer expectedId = 2;
//		Transaction transaction = accountService.makeWithdrawal(1, 10.55);
//		Integer actualId = transaction.getId();
//		assertEquals(expectedId, actualId);
//	}
	
	@AfterClass
	public static void End() {
		Log.info("End Service Testing" + System.lineSeparator());
	}

}
