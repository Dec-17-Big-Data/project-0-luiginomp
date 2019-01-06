package com.revature.project_0.services;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.project_0.dao.UserDAO;
import com.revature.project_0.dao.UserOracle;
import com.revature.project_0.models.User;

public class UserService {
	
	private static Logger Log = LogManager.getLogger(UserService.class);
	private static UserService instance;
	private static UserDAO userOracle;
	
	private UserService() {
	}
	
	public static UserService getService() {
		userOracle = UserOracle.getDAO();
		if(instance == null) {
			instance = new UserService();
		}
		return instance;
	}
	
	public Optional<User> logIn(String username, String password) {
		Log.traceEntry("Check Credentials for username " + username + " and password " + password);
		User user = null;
		try{
			user = userOracle.getUser(username).get();
		}catch (NoSuchElementException e) {
			Log.info("User doesn't exist");
		}
		if(user == null) {
			Log.traceExit("User doesn't exist");
			return Optional.empty();
		}
		if(password.equals(user.getPassword())) {
			Log.traceExit("Successfully logged in");
			return Optional.of(user);
		}
		Log.traceExit("Invalid password");
		return Optional.empty();
	}
}
