package com.revature.project_0.testing.Oracles;

import static org.junit.Assert.*;

import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;

import com.revature.project_0.dao.*;
import com.revature.project_0.models.*;

public class AdminOracleTests {

	private static Logger Log = LogManager.getLogger(AdminOracleTests.class);
	private static AdminDAO adminOracle;
	@BeforeClass
	public static void begin() {
		Log.info("BEGIN ADMIN ORACLE TESTING");
		adminOracle = AdminOracle.getDAO();
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
		Log.info("END ADMIN ORACLE TESTING"
				+ System.lineSeparator() + "==========================================================================================="
				+ System.lineSeparator() + "===========================================================================================");
	}

}
