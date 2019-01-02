package com.revature.project_0.testing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.project_0.dao.*;
import com.revature.project_0.dao.UserOracle;
import com.revature.project_0.models.*;

public class OracleTests {

	private static Logger Log = LogManager.getLogger(OracleTests.class);
	private static UserDAO userOracle;
	private static AdminDAO adminOracle;
	
	@BeforeClass
	public static void begin() {
		Log.info("Begin UserOracle Testing");
		userOracle = UserOracle.getDAO();
		adminOracle = AdminOracle.getDAO();
	}
	
	/*
	 * UserOracle
	 * ==================================================================================================
	 * ==================================================================================================
	 */
	
	/*
	 * .getUser(String username)
	 * ----------------------------------------------------------------------------------------
	 */
	
	@Test
	public void lookUpExistingUser() {
		//Assumes all provided parameters exist and match in the database
		User userExpected = new User(1, "LeChiffre", "baccarat");
		Optional<User> userActual = userOracle.getUser("LeChiffre");
		assertEquals(userExpected, userActual.get());
	}
	
	@Test (expected = NoSuchElementException.class)
	public void lookUpNonexistantUser() {
		//Assumes current username doesn't exist in the database
		userOracle.getUser("SomeName").get();
	}
	
	/*
	 * .createUser(String username, String password)
	 * ----------------------------------------------------------------------------------------
	 */
	
	@Test
	public void createAlreadyExistingUser() {
		//Assumes current username already exists in database
		assertEquals(false, userOracle.createUser("LeChiffre", "baccarat"));
	}
	
	
	@Test 
	public void createNewUser() {
		//Assumes current username doesn't exist in database
		assertEquals(true, userOracle.createUser("Goldfinger", "BadArchitect"));
	}
	
	/*
	 * AdminOracle
	 * ==================================================================================================
	 * ==================================================================================================
	 */
	
	/*
	 * .getAllUsers()
	 * ----------------------------------------------------------------------------------------
	 */
	
	@Test
	public void getAllUsersFromTable() {
		//Assumes that the expected list matches the database
		List<User> expectedList = new ArrayList<User>();
		expectedList.add(new User(1, "LeChiffre", "baccarat"));
		assertEquals(expectedList,adminOracle.getAllUsers().get());
	}
	
	@Test (expected = NoSuchElementException.class)
	public void getAllUsersFromEmptyTable() {
		//Assumes that the database table is empty
		adminOracle.getAllUsers().get();
	}
	
	@AfterClass
	public static void end() {
		Log.info("End UserOracle Testing" + System.lineSeparator());
	}

}
