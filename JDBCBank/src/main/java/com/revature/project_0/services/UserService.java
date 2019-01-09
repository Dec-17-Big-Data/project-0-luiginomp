package com.revature.project_0.services;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.logging.log4j.*;

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
	
	//A registered user can login with their username and password
	public User userLogIn(String username, String password) {
		Log.traceEntry("Check Credentials for username " + username + " and password " + password);
		User user = null;
		try{
			user = userOracle.sendUserQuery(username).get();
		}catch (NoSuchElementException e) {
			Log.info("User doesn't exist");
		}
		if(user == null) {
			Log.traceExit("User doesn't exist");
			return null;
		}
		if(!password.equals(user.getPassword())) {
			Log.traceExit("Invalid password");
			
			return null;
		}
		Log.traceExit("Successfully logged in");
		return user;
	}
	
	//An unregistered user can register by creating a username and password
	public User createUser(String username, String password){
		Log.traceEntry(username + ", " + password);
		User user = null;
		try{
			user = userOracle.sendUserQuery(username).get();
			Log.traceExit("Username already exists");
			System.out.println("Username already exists");
			return null;
		}catch (NoSuchElementException e) {
		}
		userOracle.callInsertUser(username, password);
		try{
			user = userOracle.sendUserQuery(username).get();
			Log.traceExit("Successfully created: " + user.toString());
			return user;
		}catch (NoSuchElementException e) {
		}
		Log.traceExit("Something went wrong. Please try again.");
		return null;
	}
}
