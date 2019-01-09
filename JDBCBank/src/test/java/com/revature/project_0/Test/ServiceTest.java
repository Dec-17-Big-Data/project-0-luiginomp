package com.revature.project_0.Test;

import static org.junit.Assert.*;

import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;


import com.revature.project_0.models.*;
import com.revature.project_0.services.*;

public class ServiceTest {
	
	private static Logger Log = LogManager.getLogger(ServiceTest.class);
	private static UserService userService;
	private static AdminService adminService;
	private static AccountService accountService;
	private static TransactionService transactionService;
	
	@BeforeClass
	public static void begin() {
		Log.info("BEGIN SERVICE TESTING");
		userService = UserService.getService();
		adminService = AdminService.getService();
		accountService = AccountService.getService();
		transactionService = TransactionService.getService();
	}
	
	@Test
	public void userLogInWithValidCredentials() {
		logBegin();
		User expected = new User (1, "LeChiffre", "gambler");
		assertEquals(expected, userService.userLogIn("LeChiffre",  "gambler"));
		logEnd();
	}
	
	@Test
	public void userLogInWithInvalidUsername() {
		logBegin();
		assertEquals(null, userService.userLogIn("Invalid",  "Irrelevant"));
		logEnd();
	}
	
	@Test
	public void userLogInWithInvalidPassword() {
		logBegin();
		assertEquals(null, userService.userLogIn("LeChiffre", "Invalid"));
		logEnd();
	}
	
	@Test
	public void createUserWithTakenName() {
		logBegin();
		assertEquals(null, userService.createUser("LeChiffre",  "Irrelevant"));
		logEnd();
	}
	
	@Test
	public void adminLogInWithValidCredentials() {
		logBegin();
		assertEquals(true, adminService.adminLogIn("JamesBond", "007"));
		logEnd();
	}
	
	@Test
	public void adminLogInWithInvalidUsername() {
		logBegin();
		assertEquals(false, adminService.adminLogIn("007",  "irrelevant"));
		logEnd();
	}
	
	@Test
	public void adminLogInWithInvalidPassword() {
		logBegin();
		assertEquals(false, adminService.adminLogIn("JamesBond", "invalid"));
		logEnd();
	}
	
	@Test
	public void viewUserWithValidUsername() {
		logBegin();
		assertEquals(true, adminService.viewUser("LeChiffre"));
		logEnd();
	}
	
	@Test
	public void viewUserWithInvalidUsername() {
		logBegin();
		assertEquals(false, adminService.viewUser("Invalid"));
		logEnd();
	}
	
	@Test
	public void updateUsernameWithValidNames() {
		logBegin();
		assertEquals(true, adminService.updateUsername("Updatable2", "Updated2"));
		logEnd();
	}
	
	@Test
	public void updateUsernameWithNonexistentUser() {
		logBegin();
		assertEquals(false, adminService.updateUsername("invalid", "irrelevant"));
		logEnd();
	}
	
	@Test
	public void updateusernameWithTakenName() {
		logBegin();
		assertEquals(false, adminService.updateUsername("Updatable2", "LeChiffre"));
		logEnd();
	}
	
	@Test
	public void deleteExistingUser() {
		logBegin();
		assertEquals(true, adminService.deleteUser("Deletable2"));
		logEnd();
	}
	
	@Test
	public void deleteNonexistentUser() {
		logBegin();
		assertEquals(false, adminService.deleteUser("invalid"));
		logEnd();
	}
	
	@Test
	public void deleteUserWithActiveAccounts() {
		logBegin();
		assertEquals(false, adminService.deleteUser("LeChiffre"));
		logEnd();
	}
	
	@Test
	public void retrieveExistingAccount() {
		logBegin();
		Account expected = new Account (2, 0.0);
		assertEquals(expected, accountService.retrieveAccount(2));
		logEnd();
	}
	
	@Test
	public void retrieveNonexistentAccount() {
		logBegin();
		assertEquals(null, accountService.retrieveAccount(100));
		logEnd();
	}
	
	@Test
	public void retrieveUserAccounts() {
		logBegin();
		List<Account> expected = new ArrayList<Account>();
		expected.add(new Account (6, 0.0));
		assertEquals(expected, accountService.retrieveUserAccounts(5));
		logEnd();
	}
	
	@Test
	public void createAccountForNonexistentUser() {
		logBegin();
		assertEquals(null, accountService.createAccount(100));
		logEnd();
	}
	
	@Test
	public void deleteAccountWithBalance() {
		logBegin();
		assertEquals(false, accountService.deleteAccount(1, 1));
		logEnd();
	}
	
	@Test
	public void deleteAccountWithWrongOwner() {
		logBegin();
		assertEquals(false, accountService.deleteAccount(1, 2));
		logEnd();
	}
	
	@Test
	public void deleteNonexistentAccount() {
		logBegin();
		assertEquals(false, accountService.deleteAccount(100, 100));
		logEnd();
	}
	
	@Test
	public void makeDepositOfZero() {
		logBegin();
		assertEquals(null, accountService.makeDeposit(1, 0.0, 1));
		logEnd();
	}
	
	@Test
	public void makeDepositToNonexistentAccount() {
		logBegin();
		assertEquals(null, accountService.makeDeposit(100, 1.00, 100));
		logEnd();
	}
	
	@Test
	public void makeDepositToAccountFromWrongOwner() {
		logBegin();
		assertEquals(null, accountService.makeDeposit(1, 1.00, 3));
		logEnd();
	}
	
	@Test
	public void makeWithdrawalOfZero() {
		logBegin();
		assertEquals(null, accountService.makeWithdrawal(1, 0.0, 1));
		logEnd();
	}
	
	@Test
	public void makeWithdrawalFromNonexistentAccount() {
		logBegin();
		assertEquals(null, accountService.makeWithdrawal(100, 1.0, 1));
		logEnd();
	}
	
	@Test
	public void retrieveNonexistentTransaction() {
		logBegin();
		assertEquals(null, transactionService.retrieveTransaction(100));
		logEnd();
	}
	
	private void logBegin() {
		Log.info("Begin Unit Test");
	}
	
	private void logEnd() {
		Log.info("End Unit Test" + System.lineSeparator());
	}

	@AfterClass
	public static void end() {
		Log.info("END SERVICE TESTING"
				+ System.lineSeparator() + "==========================================================================================="
				+ System.lineSeparator() + "===========================================================================================");
	}

}
