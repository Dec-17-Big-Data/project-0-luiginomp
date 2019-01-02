package com.revature.project_0.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.project_0.models.User;
import com.revature.project_0.utils.ConnectionUtil;

public class UserOracle implements UserDAO{
	
	private static Logger Log = LogManager.getLogger(UserOracle.class);
	
	private static UserOracle instance;
	
	protected UserOracle() {
	}
	
	public static UserDAO getDAO() {
		if(instance == null) {
			instance = new UserOracle();
		}
		return instance;
	}

	public Boolean createUser(String username, String password) {
		Log.traceEntry("Create user with name " + username + ", password " + password);
		if((Optional) getUser(username) != Optional.empty()) {
			Log.traceExit("Username already exists. Failed to create user");
			return false;
		}
		Connection conn = ConnectionUtil.getConnection();
		if(conn == null) {
			Log.traceExit("No connection to database found. Failed to create user");
			return false;
		}
		Log.trace("Username is valid");
		String sql = "{call insert_user (?, ?)}";
		CallableStatement stmt = null;
		try {
			stmt = conn.prepareCall(sql);
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.execute();
			Log.trace("Call successful");
			return true;
		}catch (SQLException e) {
			Log.error("SQL Exception occurred while attempting to prepare statement", e);
		}catch (Exception e) {
			Log.error("Exception Occurred: ", e);
		}finally {
			try {
				if(stmt != null) {
					conn.close();
				}
			}catch (SQLException e) {
				Log.error("SQL Exception occurred while attempting to close connection", e);
			}
			try {
				if(conn != null) {
					conn.close();
					Log.info("Closed connection to database");
				}
			}catch (SQLException e) {
				Log.error("SQL Exception occurred while attempting to close connection", e);
			}
		}
		return false;
	}

	public Optional<User> getUser(String username) {
		Log.traceEntry("Retrieving user named " + username);
		Connection conn = ConnectionUtil.getConnection();
		if(conn == null) {
			Log.traceExit("No connection to database found. Failed to search for user");
		}
		User user = null;
		String sql = "select * from bank_user where user_name = ?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, username);
		rs = stmt.executeQuery();
		if(rs.next() == true) {
			user = new User(rs.getInt("user_id"), rs.getString("user_name"), rs.getString("user_password"));
			Log.info("Username found on file");
			return Optional.of(user);
		}
		if(user == null) {
			Log.info("Username not found");
			return Optional.empty();
		}
		}catch (SQLException e) {
			Log.error("SQL Exception occurred while attempting to get result set", e);
		}catch (Exception e) {
			Log.error("Exception Occurred: ", e);
		}finally {
			try {
				if(stmt != null) {
					conn.close();
				}
			}catch (SQLException e) {
				Log.error("SQL Exception occurred while attempting to close connection", e);
			}
			try {
				if(conn != null) {
					conn.close();
					Log.traceExit("Closed connection to database");
				}
			}catch (SQLException e) {
				Log.error("SQL Exception occurred while attempting to close connection", e);
			}
		}
		Log.traceExit("Username not found");
		return Optional.empty();
	}
}
