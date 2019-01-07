package com.revature.project_0.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.NoSuchElementException;
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
	
	public Boolean loggedIn = false;
	
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
	
	public Boolean logIn(String username, String password) {
		Log.traceEntry("Service logging in using " + username + " and " + password);
		InputStream in = null;
		Properties props = new Properties();
		String adminUsername = null;
		String adminPassword = null;
		try {
            in = new FileInputStream("src\\main\\resources\\Admin.properties");
            props.load(in);
            adminUsername = props.getProperty("admin.username");
            adminPassword = props.getProperty("admin.password");	
		}catch (FileNotFoundException e) {
			Log.error("Service failed locate specified file");
			return false;
		}catch (IOException e) {
			Log.error("Service failed to load properties");
			return false;
		}catch (NoSuchElementException e) {
			Log.error("Service failed to find username or password in properties file");
			return false;
		}finally {
			try {
				in.close();
			}catch (IOException e) {
				Log.error("Service failed to close input stream");
			}
		}
		if(adminUsername == null || adminPassword == null) {
			Log.traceExit("Service failed to retrieve either the username or password");
			return false;
		}
		if(!username.equals(adminUsername) || !password.equals(adminPassword)) {
			Log.traceExit("Service failed to log in because the username or password is incorrect");
			return false;
		}
		Log.traceExit("Service logged in to Admin");
		return true;
	}
	
	public Boolean deleteUser(String username) {
		Log.traceEntry("Service deleting user " + username);
		User user = null;
		try {
			user = adminOracle.sendUserQuery(username).get();
		}catch(NoSuchElementException e) {
			Log.traceExit("Service failed to locate user");
			return false;
		}
		List<Account> accountList = accountService.retrieveAccountsForUser(user);
		if(!accountList.isEmpty()) {
			Log.traceExit("Service can't delete users with accounts");
			return false;
		}
		Log.traceExit("Service passing username " + username + " to Oracle to delete user");
		return adminOracle.callDeleteUser(username);
	}
}
