package com.revature.project_0.testing.Oracles;

import static org.junit.Assert.*;

import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;

import com.revature.project_0.dao.*;
import com.revature.project_0.models.*;

public class UserOracleTests {

	private static Logger Log = LogManager.getLogger(UserOracleTests.class);
	private static UserDAO userOracle;
	
	@BeforeClass
	public static void begin() {
		Log.info("BEGIN USER ORACLE TESTING");
		userOracle = UserOracle.getDAO();
	}
	
	@Test
	public void callInsertUserWithValidName() {
		Log.info("Begin Unit Test");
		assertEquals(true, userOracle.callInsertUser("NewName", "NewPassword"));
		Log.info("End Unit Test" + System.lineSeparator());
	}
	
	@Test
	public void callInsertUserWithInvalidName() {
		Log.info("Begin Unit Test");
		assertEquals(false, userOracle.callInsertUser("SeanConnery", "Irrelevant"));
		Log.info("End Unit Test" + System.lineSeparator());
	}
	
	@Test
	public void sendUserQueryWithValidName() {
		Log.info("Begin Unit Test");
		User expected = new User (1, "SeanConnery", "DrNo");
		assertEquals(Optional.of(expected), userOracle.sendUserQuery("SeanConnery"));
		Log.info("End Unit Test" + System.lineSeparator());
	}
	
	@Test
	public void sendUserQueryWithInvalidName() {
		Log.info("Begin Unit Test");
		assertEquals(Optional.empty(), userOracle.sendUserQuery("InvalidName"));
		Log.info("End Unit Test" + System.lineSeparator());
	}
	
	@Test
	public void userExistsWithValidName() {
		Log.info("Begin Unit Test");
		assertEquals(true, userOracle.userExists("SeanConnery"));
		Log.info("End Unit Test" + System.lineSeparator());
	}
	
	@Test
	public void userExistsWithInvalidName() {
		Log.info("Begin Unit Test");
		assertEquals(false, userOracle.userExists("InvalidName"));
		Log.info("End Unit Test" + System.lineSeparator());
	}

	@AfterClass
	public static void end() {
		Log.info("END USER ORACLE TESTING"
				+ System.lineSeparator() + "==========================================================================================="
				+ System.lineSeparator() + "===========================================================================================");
	}

}
