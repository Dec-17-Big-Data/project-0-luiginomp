package com.revature.project_0.testing;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.project_0.dao.UserDAO;
import com.revature.project_0.dao.UserOracle;
import com.revature.project_0.models.User;

public class UserOracleTest {

	private static Logger Log = LogManager.getLogger(UserOracleTest.class);
	private static final String Optional = null;
	private static UserDAO userOracle;
	
	@BeforeClass public static void begin() {
		Log.info("Begin UserOracle Testing");
		userOracle = UserOracle.getDAO();
	}
	
	/*
	 * ==================================================================================================
	 * Tests for .getUser(String username)
	 * ==================================================================================================
	 */
	@Test
	public void lookUpExistingUser() {
		//Assumes all provided parameters exist and match in the database
		User userExpected = new User(2, "LeChiffre", "baccarat", "Normal");
		Optional<User> userActual = userOracle.getUser("LeChiffre");
		assertEquals(userExpected, userActual.get());
	}
	
	@Test (expected = NoSuchElementException.class)
	public void lookUpNonexistantUser() {
		//Assumes current username doesn't exist in the database
		userOracle.getUser("SomeName").get();
	}
	
	/*
	 * ==================================================================================================
	 * Tests for .createUser(String username, String password)
	 * ==================================================================================================
	 */
	@Test
	public void createAlreadyExistingUser() {
		//Assumes current username already exists in database
		assertEquals(false, userOracle.createUser("LeChiffre", "baccarat", "Normal"));
	}
	
	@Test
	public void createNewAdminUser() {
		//Assumes current username doesn't exist in database
		assertEquals(true, userOracle.createUser("HugoDrax", "Moonraker", "Admin"));
	}
	
	@Test 
	public void createNewNormalUser() {
		//Assumes current username doesn't exist in database
		assertEquals(true, userOracle.createUser("RosaKlebb", "SPECTRE#3", "Normal"));
	}
	
	@Test
	public void createNewUserWithInvalidUserType() {
		//Assumes current username doesn't exist in database
		assertEquals(false, userOracle.createUser("Goldfinger", "BadArchitect", "Invalid"));
	}
	
	@AfterClass public static void end() {
		Log.info("End UserOracle Testing" + System.lineSeparator() + System.lineSeparator());
	}

}
