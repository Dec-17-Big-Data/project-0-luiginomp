package com.revature.project_0.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.project_0.models.User;
import com.revature.project_0.utils.ConnectionUtil;

public class UserOracle implements UserDAO{
	
	private static Logger Log = LogManager.getLogger(UserOracle.class);
	
	protected UserOracle() {
	}

	public Boolean createUser(String username, String password, String type) {
		Log.traceEntry("Create user with name " + username + " and password " + password);
		Connection conn = ConnectionUtil.getConnection();
		if(conn == null) {
			Log.traceExit("No connection to database found. Failed to create user");
			return false;
		}
		if((Optional) getUser(username) != Optional.empty()) {
			Log.traceExit("Username already exists. Failed to create user");
			return false;
		}
		Log.trace("Username is valid");
		String sql = "{call insert_user (?, ?)}";
		CallableStatement stmt = null;
		try {
			Log.trace("Preparing call to stored procedure");
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
					Log.traceExit("Closed connection to database");
				}
			}catch (SQLException e) {
				Log.error("SQL Exception occurred while attempting to close connection", e);
			}
		}
		return false;
	}

	public Optional<User> getUser(String username) {
		Log.traceEntry("Retrieving user named " + username);
		Log.traceExit("Functionality not yet implemented. Returning empty");
		// TODO Auto-generated method stub
		return Optional.empty();
	}
	
	public Boolean comparePassword (String password, User user) {
		//TODO Auto-generated method stub
		return null;
	}

	public Boolean logOut() {
		// TODO Auto-generated method stub
		return null;
	}
}
