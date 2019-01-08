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
	
	@BeforeClass
	public static void begin() {
		Log.info("BEGIN ORACLE TESTING");
		userOracle = UserOracle.getDAO();
		adminOracle = AdminOracle.getDAO();
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
	
	@Test
	public void sendUsersQueryWithExistingUsers() {
		logBegin();
		List<User> userList = new ArrayList<User>();
		userList.add(new User(1, "SeanConnery", "DrNo"));
		userList.add(new User(2, "DavidNiven", "CasinoRoyale"));
		userList.add(new User(3, "RogerMoore", "GoldenGun"));
		userList.add(new User(4, "TimothyDalton", "LivingDaylights"));
		userList.add(new User(5, "PierceBrosnan", "GoldenEye"));
		userList.add(new User(6, "DanielCraig", "SkyFall"));
		assertEquals(Optional.of(userList), adminOracle.sendUsersQuery());
		logEnd();
	}
	
	private void logBegin() {
		Log.info("BeginUnitTest");
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
