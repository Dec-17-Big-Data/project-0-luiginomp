package com.revature.project_0.Test;

import static org.junit.Assert.*;

import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;

import com.revature.project_0.dao.*;
import com.revature.project_0.models.*;

public class OracleTest {

	private static Logger Log = LogManager.getLogger(OracleTest.class);
	private static UserDAO userOracle;
	private static AdminDAO adminOracle;
	private static AccountDAO accountOracle;
	
	@BeforeClass
	public static void begin() {
		Log.info("BEGIN ORACLE TESTING");
		userOracle = UserOracle.getDAO();
		adminOracle = AdminOracle.getDAO();
		accountOracle = AccountOracle.getDAO();
	}
	
	@Test
	public void callInsertUserWithValidName() {
		logBegin();
		assertEquals(true, userOracle.callInsertUser("NewName", "NewPassword"));
		logEnd();
	}
	
	@Test
	public void callInsertUserWithTakenName() {
		logBegin();
		assertEquals(false, userOracle.callInsertUser("LeChiffre", "Irrelevant"));
		logEnd();
	}
	
	@Test
	public void sendUserQueryWithValidName() {
		logBegin();
		User expected = new User (1, "LeChiffre", "gambler");
		assertEquals(Optional.of(expected), userOracle.sendUserQuery("LeChiffre"));
		logEnd();
	}
	
	@Test
	public void sendUserQueryWithInvalidName() {
		logBegin();
		assertEquals(Optional.empty(), userOracle.sendUserQuery("InvalidName"));
		logEnd();
	}
	
	@Test
	public void userExistsWithValidName() {
		logBegin();
		assertEquals(true, userOracle.userExists("LeChiffre"));
		logEnd();
	}
	
	@Test
	public void userExistsWithInvalidName() {
		logBegin();
		assertEquals(false, userOracle.userExists("InvalidName"));
		logEnd();
	}
	
	@Test
	public void callDeleteUserOnExistingUser() {
		logBegin();
		assertEquals(true, adminOracle.callDeleteUser("Deletable"));
		logEnd();
	}
	
	@Test
	public void callUpdateUsernameOnExistingUser() {
		logBegin();
		assertEquals(true, adminOracle.callUpdateUsername("Updatable",  "Updated"));
		logEnd();
	}
	
	@Test
	public void callUpdatePasswordOnExistingUser() {
		logBegin();
		assertEquals(true, adminOracle.callUpdatePassword("LockedOut", "Updated"));
		logEnd();
	}
	
	@Test
	public void callInsertAccountOnExistingUser() {
		logBegin();
		assertEquals(Optional.of(7), accountOracle.callInsertAccount(1));
		logEnd();
	}
	
	@Test
	public void callInsertAccountOnNonexistentUser() {
		logBegin();
		assertEquals(Optional.empty(), accountOracle.callInsertAccount(100));
		logEnd();
	}
	
	@Test
	public void sendAccountQueryForExistingAccount() {
		logBegin();
		Account expected = new Account (2, 0.0);
		assertEquals(Optional.of(expected), accountOracle.sendAccountQuery(2));
		logEnd();
	}
	
	@Test
	public void callDeleteAccountOnExistingAccount() {
		logBegin();
		assertEquals(true, accountOracle.callDeleteAccount(1));
		logEnd();
	}
	
	@Test
	public void callDepositBalanceOnExistingAccount() {
		logBegin();
		assertEquals(Optional.of(7), accountOracle.callDepositBalance(3, 1.0));
		logEnd();
	}
	
	@Test
	public void sendAccountOwnerQueryForExistingUser() {
		logBegin();
		assertEquals(Optional.of(1), accountOracle.sendAccountOwnerQuery(2));
		logEnd();
	}
	
	@Test
	public void sendAccountOwnerQueryForNonexistentUser() {
		logBegin();
		assertEquals(Optional.empty(), accountOracle.sendAccountOwnerQuery(100));
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
		Log.info("END ORACLE TESTING"
				+ System.lineSeparator() + "==========================================================================================="
				+ System.lineSeparator() + "===========================================================================================");
	}

}
