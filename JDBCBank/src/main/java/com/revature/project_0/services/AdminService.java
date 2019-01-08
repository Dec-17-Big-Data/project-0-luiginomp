package com.revature.project_0.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.project_0.dao.AdminDAO;
import com.revature.project_0.dao.AdminOracle;
import com.revature.project_0.models.Account;
import com.revature.project_0.models.User;

public class AdminService {
	
	private static Logger Log = LogManager.getLogger(AdminService.class);
	private static AdminDAO adminOracle;
	private static AdminService instance;
	private static AccountService accountService;
	private static User viewedUser = null;
	
	private AdminService() {
	}
	
	public static AdminService getService() {
		adminOracle = AdminOracle.getDAO();
		accountService = AccountService.getService();
		if(instance == null) {
			instance = new AdminService();
		}
		return instance;
	}
	
	public Boolean adminLogIn(String username, String password) {
		Log.info("adminLogIn called and passed username " + username + " and password [Hidden]");
		InputStream in = null;
		Properties props = new Properties();
		String adminUsername = null;
		String adminPassword = null;
		try {
			Log.info("adminLogIn assigning input stream");
            in = new FileInputStream("src\\main\\resources\\Admin.properties");
            Log.info("adminLogIn loading properties");
            props.load(in);
            Log.info("adminLogIn retrieving adminUsername");
            adminUsername = props.getProperty("admin.username");
            Log.info("adminLogIn retrieving adminPassword");
            adminPassword = props.getProperty("admin.password");	
		}catch (FileNotFoundException e) {
			Log.error("adminLogIn failed locate specified file");
			return false;
		}catch (IOException e) {
			Log.error("adminLogIn failed to load properties");
			return false;
		}catch (NoSuchElementException e) {
			Log.error("adminLogIn failed to find username or password in properties file");
			return false;
		}finally {
			try {
				in.close();
			}catch (IOException e) {
				Log.error("adminLogIn failed to close input stream");
			}
		}
		if(adminUsername == null || adminPassword == null) {
			Log.info("adminLogIn failed to retrieve either the username or password");
			return false;
		}
		if(!username.equals(adminUsername) || !password.equals(adminPassword)) {
			Log.info("adminLogIn failed to log in because the username or password is incorrect");
			return false;
		}
		Log.info("adminLogIn logged in to Admin");
		return true;
	}
	
	public Boolean viewUser(String username) {
		Log.info("viewUser called and passed username " + username);
		try {
			Log.info("viewUser calling sendUserQuery and passing username to try and get user object");
			viewedUser = adminOracle.sendUserQuery(username).get();
		}catch(NoSuchElementException e) {
			Log.info("viewUser returned false - user object was null");
			System.out.println("Username doesn't exist");
			return false;
		}
		Log.info("viewUser returned true - successfully retrieved " + viewedUser);
		System.out.println("Viewing " + viewedUser.toString());
		return true;
	}
	
	public List<User> retrieveAllUsers(){
		Log.info("retrieveUsers called");
		List<User> userList = null;
		try {
			Log.info("retrieveUsers calling sendUsersQuery to try and get list of user objects");
			userList = adminOracle.sendUsersQuery().get();
		}catch(NoSuchElementException e) {
			Log.info("retrieveUsers returned null - sendUsersQuery didn't return a list");
			return null;
		}
		Log.info("retrieveUsers returned userList");
		return userList;
	}
	
	public Boolean updateUsername(String username, String newName) {
		Log.info("updateUsername called and passed " + username + " and " + newName);
		Log.info("updateUsername calling userExists and passing username to see if user exists");
		if(adminOracle.userExists(username) == false) {
			Log.info("updateUsername returning false - user doesn't exist");
			System.out.println("User doesn't exist - failed to update username");
			return false;
		}
		Log.info("updateUsername calling userExists and passing newName to see if name is taken");
		
		if(adminOracle.userExists(newName) == true) {
			Log.info("updateUsername returning false - new username already taken");
			System.out.println("New username already taken - failed to update username");
			return false;
		}
		Log.info("updateUsername calling callUpdateUsername and passing" + username + " and " + newName);
		if(adminOracle.callUpdateUsername(username, newName) == false) {
			Log.info("updateUsername returning false - callUpdateUsername failed");
			System.out.println("Failed to update username. Please try again");
			return false;
		}
		Log.info("updateUsername calling userExists and passing newName to see if name now exists");
		if(adminOracle.userExists(newName) == false) {
			Log.info("updateUsername returning false - username still doesn't exist");
			System.out.println("Failed to update name. Please try again");
			return false;
		}
		Log.info("updateUsername calling sendUserQuery and passing username to see if name still exists");
		if(adminOracle.userExists(username) == true) {
			Log.info("updateUsername returning false - old username still exists");
			System.out.println("Failed to change name. Please try again");
			return false;
		}
		Log.info("updateUsername returning true - username successfully changed");
		System.out.println("Successfully changed name from " + username + " to " + newName);
		return true;
	}
	
	public Boolean deleteUser(String username) {
		Log.info("deleteUser called and passed username " + username);
		User user = null;
		try {
			Log.info("deleteUser calling sendUserQuery and passing username to try and get user object to check if it exists");
			user = adminOracle.sendUserQuery(username).get();
		}catch(NoSuchElementException e) {
			Log.info("deleteUser returned false - failed to delete user");
			System.out.println("Couldn't find user. Please make sure username is correct");
			return false;
		}
		Log.info("deleteUser calling retrieveUserAccounts and passing user to check if user has any active accounts");
		List<Account> accountList = accountService.retrieveUserAccounts(user.getUserId());
		if(!accountList.isEmpty()) {
			Log.info("deleteUser returned false - user has active accounts");
			System.out.println("Can't delete user with active accounts");
			return false;
		}
		Log.traceExit("Service passing username " + username + " to Oracle to delete user");
		return adminOracle.callDeleteUser(username);
	}
}
