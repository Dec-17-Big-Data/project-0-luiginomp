package com.revature.project_0.utils;

import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.project_0.dao.UserDAO;
import com.revature.project_0.dao.UserOracle;
import com.revature.project_0.models.User;
import com.revature.project_0.services.*;

public class BankApplication {
	
	private static Logger Log = LogManager.getLogger(BankApplication.class);
	final static UserDAO userDAO = UserOracle.getDAO();
	private static String username = "Overseer";
	private static String password = "Vault#101";
	
	public static void main (String[] args) {
		Log.traceEntry("Application starting");
		try {
			DatabaseManager.setupSchema();
			UserService.createUser(username, password);
			UserService.createUser("LuiginoMP", "C0d3L1k34B0$$");
			UserService.createUser("JohnDoe", "Wh04m!?");
			UserService.createUser("CarlSagan", "Space4Us!");
			userDAO.getAllUsers();
			UserService.logIn(username, password);
			DatabaseManager.tearDownSchema();
		}catch(NoSuchElementException e) {
			Log.error("Unable to find user");
		}
		Log.traceExit("Application completed");
	}
}
