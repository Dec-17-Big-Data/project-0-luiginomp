package com.revature.project_0.testing;

import static org.junit.Assert.*;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.project_0.dao.*;
import com.revature.project_0.dao.UserOracle;
import com.revature.project_0.models.Account;

public class OracleTests {

	private static Logger Log = LogManager.getLogger(OracleTests.class);
	private static UserDAO userOracle;
	private static AdminDAO adminOracle;
	private static AccountDAO accountOracle;
	
	@BeforeClass
	public static void begin() {
		Log.info("Begin Oracle Testing");
		userOracle = UserOracle.getDAO();
		adminOracle = AdminOracle.getDAO();
		accountOracle = AccountOracle.getDAO();
	}
	//==================================================================================================
	//==================================================================================================
	//==================================================================================================
	
	//UserOracle
//	@Test
//	public void lookUpExistingUser() {
//		//Assumes all provided parameters exist and match in the database
//		User userExpected = new User(1, "LeChiffre", "baccarat");
//		Optional<User> userActual = userOracle.getUser("LeChiffre");
//		assertEquals(userExpected, userActual.get());
//	}
//	
//	@Test (expected = NoSuchElementException.class)
//	public void lookUpNonexistantUser() {
//		//Assumes current username doesn't exist in the database
//		userOracle.getUser("SomeName").get();
//	}
//	
//	@Test
//	public void createAlreadyExistingUser() {
//		//Assumes current username already exists in database
//		assertEquals(false, userOracle.createUser("LeChiffre", "baccarat"));
//	}
//	
//	
//	@Test 
//	public void createNewUser() {
//		//Assumes current username doesn't exist in database
//		assertEquals(true, userOracle.createUser("Goldfinger", "BadArchitect"));
//	}
//
//	@Test
//	public void getAllUsersFromTable() {
//		//Assumes that the expected list matches the database
//		List<User> expectedList = new ArrayList<User>();
//		expectedList.add(new User(1, "LeChiffre", "baccarat"));
//		assertEquals(expectedList,adminOracle.getAllUsers().get());
//	}
//	
//	@Test (expected = NoSuchElementException.class)
//	public void getAllUsersFromEmptyTable() {
//		//Assumes that the database table is empty
//		adminOracle.getAllUsers().get();
//	}
//	
//	//AdminOracle
//	@Test
//	public void deleteExistingUser() {
//		assertEquals(true, adminOracle.deleteUser("LeChiffre"));
//	}
//	
//	@Test
//	public void deleteAllExistingUsers() {
//		assertEquals(true, adminOracle.deleteAllUsers());
//	}
//
//	//AccountOracle
//	@Test
//	public void createFirstAccount() {
//		//Assumes there are no existing accounts in the bank_account table
//		assertEquals(Optional.of(1), accountOracle.createAccount());
//	}
//	
//	@Test
//	public void getExistingAccount() {
//		//Assumes account exists with given account ID
//		Account expectedAccount = new Account (1, 0.0); 
//		assertEquals(Optional.of(expectedAccount), accountOracle.getAccount(1));
//	}
//	
//	@Test
//	public void deleteExistingAccount() {
//		//Assumes account exists with given account ID
//		assertEquals(true, accountOracle.deleteAccount(1));
//	}
//	
//	@Test
//	public void testCallDepositBalance() {
//		assertEquals(true, accountOracle.callDepositBalance(1, 25.0));
//	}
//	
//	@Test
//	public void testCallWithdrawBalance() {
//		assertEquals(true, accountOracle.callWithdrawBalance(1, 13.13));
//	}
	
	//==================================================================================================
	//==================================================================================================
	//==================================================================================================
	@AfterClass
	public static void end() {
		Log.info("End Oracle Testing" + System.lineSeparator());
	}

}
