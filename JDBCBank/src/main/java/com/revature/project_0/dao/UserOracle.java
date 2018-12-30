package com.revature.project_0.dao;

import java.sql.*;
import java.util.*;

import org.apache.logging.log4j.*;

import com.revature.project_0.models.Account;
import com.revature.project_0.models.User;
import com.revature.project_0.utils.ConnectionUtil;

public class UserOracle implements UserDAO{
	
	private static Logger Log = LogManager.getLogger(UserOracle.class);
	
	public UserOracle() {
		
	}
	
	public static UserOracle userOracleInstance;
	
	public static UserDAO getDAO() {
		if(userOracleInstance == null) {
			userOracleInstance = new UserOracle();
		}
		return userOracleInstance;
	}

	
	public Optional<List<User>> getAllUsers() {
		Log.traceEntry();
		Connection connection = ConnectionUtil.getConnection();
		if(connection == null) {
			Log.traceExit("No connection - could not search database");
			return Optional.empty();
		}
		List <User> userList = new ArrayList <User>();
		try {
			Log.info("Creating command string");
			String command = "select * from bank_user";
			Log.info("Converting command");
			PreparedStatement preparedStatement = connection.prepareStatement(command);
			Log.info("Creating result set");
			ResultSet result = preparedStatement.executeQuery();
			while(result.next()) {
				Log.traceExit("Found user: ID " + result.getInt("user_id") + ", Username " + result.getString("username") + ", Password " + result.getString("password") + ", User Type " + result.getString("user_type") + ", Account ID " + result.getInt("account_id"));
				userList.add(new User(result.getInt("user_id"), result.getString("username"), result.getString("password"), result.getString("user_type")));	
			}
			if(userList.isEmpty()) {
				Log.traceExit("No users found in bank_user table.");
				return Optional.empty();
			}
			Log.traceExit("Completed search through current commit");
			return Optional.of(userList);
		}catch(SQLException e) {
			Log.error("SQL Exception Occurred: ", e);
		}
		Log.traceExit(Optional.of(userList));
		return Optional.of(userList);
	}
	
	public Optional <User> getUserByUsername(String name){
		Log.traceEntry("Search for username " + name);
		Connection connection = ConnectionUtil.getConnection();
		if(connection == null) {
			Log.traceExit("No connection - could not search database");
			return Optional.empty();
		}
		User user = null;
		try {
			Log.info("Creating command string");
			String command = "select * from bank_user where username = '" + name + "'";
			Log.info("Converting command");
			PreparedStatement preparedStatement = connection.prepareStatement(command);
			Log.info("Creating result set");
			ResultSet result = preparedStatement.executeQuery();
			Log.info("Iterating through result set");
			while(result.next()) {
				user = new User(result.getInt("user_id"), result.getString("username"), result.getString("password"), result.getString("user_type"));
				Log.traceExit("Found user: ID " + result.getInt("user_id") + ", Username " + result.getString("username") + ", Password " + result.getString("password") + ", User Type " + result.getString("user_type") + ", Account ID " + result.getInt("account_id"));
				return Optional.of(user);
			}
			
			if(user == null) {
				Log.traceExit("Current commit does not contain " + name);
				return Optional.empty();
			}
		}catch(SQLException e) {
			
		}
		return Optional.of(user);
	}


	public Boolean createUser(String username, String password, String type) {
		Log.traceEntry("Attempting to create user with username " + username + " password " + password + " type " + type);
		Connection connection = ConnectionUtil.getConnection();
		if(connection == null) {
			Log.traceExit("No connection - could not create new user");
			return false;
		}
		Integer userCount = 0;
		List <User> userList = new ArrayList <User>();
		try {
			Log.info("Creating statement");
			Statement statement = connection.createStatement();
			Log.info("Requesting number of users");
			String command = "select * from bank_user";
			ResultSet result = statement.executeQuery(command);
			while(result.next()) {
				userList.add(new User(result.getInt("user_id"), result.getString("username"), result.getString("password"), result.getString("user_type")));
			}
			for(User user : userList) {
				if(user.getUserName().equals(username)) {
					Log.traceExit("Username already exists. Please use another");
					return false;
				}
			}
			userCount = userList.size();
			Log.info("Adding command strings to batch");
			command = "insert into bank_user values (" + userCount + ", '" + username + "', '" + password + "', '" + type + "', null)";
			statement.addBatch(command);
			command = "commit";
			statement.addBatch(command);
			Log.info("Sending commands to database");
			statement.executeBatch();
			Log.traceExit("Successfully created new user");
			return true;
		}catch (SQLException e) {
			Log.error("SQL Exception Occurred: ", e);
		}
		Log.traceExit("Unable to create new user");
		return false;
	}
	
	public Optional <User> logIn(String username, String password) {
		Log.traceEntry("Attempting login using username '" + username + " and password " + password);
		Optional<User> user = getUserByUsername(username);
		if(user == null) {
			Log.traceExit("Username doesn't exist. Login attempt failed");
			return Optional.empty();
		}
		if(password.equals(user.get().getPassword())) {
			Log.traceExit("Username and password match. Login attempt succeeded");
			return user;
		}else {
			Log.traceExit("Password doesn't match. Login attempt failed");
			return Optional.empty();
		}
	}


	public Boolean createAccount() {
		// TODO Auto-generated method stub
		return null;
	}


	public Boolean deleteAccount() {
		//Check if balance is empty
		// TODO Auto-generated method stub
		return null;
	}


	public Optional<List<Account>> getAllAccounts() {
		// TODO Auto-generated method stub
		return null;
	}


	public Optional<Account> getAccountByAccountId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}


	public Boolean addToAccount() {
		// TODO Auto-generated method stub
		return null;
	}


	public Boolean withdrawFromAccount() {
		// TODO Auto-generated method stub
		return null;
	}


	public Boolean deleteUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}


	public Boolean deleteAllUsers(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
