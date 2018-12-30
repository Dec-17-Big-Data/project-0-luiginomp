package com.revature.project_0.services;

import java.util.*;

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
		Log.traceEntry("Logging in with " + username + ", " + password);
		User user = userDAO.getUserByUsername(username).get();
		if(user == null) {
			Log.traceExit("Username not found");
			return false;
		}else {
			if(password.equals(user.getPassword())) {
				currentUser = user;
				Log.traceExit("Log in successful");
				return true;
			}else {
				Log.traceExit("Password doesn't match");
				return false;
			}
		}
	}
	
	
	public static Boolean createUser(String username, String password) {
		return userDAO.createUser(username, password);
	}
}

