package com.revature.project_0.dao;

import java.sql.*;
import java.util.*;

import org.apache.logging.log4j.*;

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
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Log.info("Found user: ID " + resultSet.getInt("user_id") + ", Username " + resultSet.getString("username") + ", Password " + resultSet.getString("password") + ", Account ID " + resultSet.getInt("account_id"));
				userList.add(new User(resultSet.getInt("user_id"), resultSet.getString("username"), resultSet.getString("password")));	
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

	public Optional<User> getUserById(Integer id) {
		Log.traceEntry("Getting user by ID {}", id);
		User user = null;
		Log.traceExit("No user found");
		return Optional.of(user);
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
			ResultSet resultSet = preparedStatement.executeQuery();
			Log.info("Iterating through result set");
			while(resultSet.next()) {
				user = new User(resultSet.getInt("user_id"), resultSet.getString("username"), resultSet.getString("password"));
				Log.traceExit("Found user: ID " + resultSet.getInt("user_id") + ", Username " + resultSet.getString("username") + ", Password " + resultSet.getString("password") + ", Account ID " + resultSet.getInt("account_id"));
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


	public Boolean createUser(String username, String password) {
		Log.traceEntry("Attempting to create user with username " + username + " and password " + password);
		Connection connection = ConnectionUtil.getConnection();
		if(connection == null) {
			Log.traceExit("No connection - could not create new user");
			return false;
		}
		try {
			Log.info("Creating statement");
			Statement statement = connection.createStatement();
			Log.info("Adding command strings to batch");
			String command = "insert into bank_user values (101, '" + username + "', '" + password + "', null)";
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
}
