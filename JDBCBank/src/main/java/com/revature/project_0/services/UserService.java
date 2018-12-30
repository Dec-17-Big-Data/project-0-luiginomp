package com.revature.project_0.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.project_0.dao.*;
import com.revature.project_0.models.*;
import com.revature.project_0.utils.BankApplication;

public class UserService {
	private static Logger Log = LogManager.getLogger(BankApplication.class);
	private static UserService userServiceInstance;
	
	final static UserDAO userDAO = UserOracle.getDAO();
	private static User currentUser;
	
	public UserService() {
		
	}
	
	
	public static UserService getUserService() {
		if(userServiceInstance == null) {
			userServiceInstance = new UserService();
		}
		return userServiceInstance;
	}
	
	public static Boolean logIn(String username, String password) {
		Log.traceEntry("Sending login attempt request to DAO with " + username + ", " + password);
		currentUser = userDAO.logIn(username, password).get();
		if(currentUser == null) {
			Log.traceExit("Incorrect username or password entered. Login attempt failed");
			return false;
		}else {
			Log.traceExit("Log in successful");
			return true;
		}
	}
	
	public static Boolean logOut() {
		Log.traceEntry("Logging out");
		currentUser = null;
		Log.traceExit("Successfully logged off");
		return false;
	}
	
	public static Boolean register(String username, String password) {
		return userDAO.createUser(username, password, "user");
	}
	
	public static Boolean createSuperUser(String username, String password) {
		Log.traceEntry("Create super user requested using username " + username + " and password " + password);
		if(currentUser == null) {
			Log.traceExit("Please log in to admin account to perform action");
			return false;
		}
		if(currentUser.getUserType().equals("super")) {
			Log.traceExit("Admin credentials approved");
			return userDAO.createUser(username, password, "super");
		}else {
			Log.traceExit("Please log in to admin account to perform action");
			return false;
		}
	}
}

