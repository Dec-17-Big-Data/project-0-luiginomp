package com.revature.project_0.dao;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.project_0.models.Account;
import com.revature.project_0.models.User;
import com.revature.project_0.utils.ConnectionUtil;

public class AdminOracle extends UserOracle implements AdminDAO {
	
	private static Logger Log = LogManager.getLogger(AdminOracle.class);

	private static AdminOracle instance;
	
	private AdminOracle() {
	}
	
	public static AdminDAO getDAO() {
		if(instance == null) {
			instance = new AdminOracle();
		}
		return instance;
	}

	public Optional<List<User>> getAllUsers() {
		Log.traceEntry("Get list of all users from database");
		Connection conn = ConnectionUtil.getConnection();
		if(conn == null) {
			Log.traceExit("Connection to database not found. Failed to retrieve list of users");
			return Optional.empty();
		}
		List<User> userList = new ArrayList<User>();
		User user = null;
		String sql = "select * from bank_user";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Log.trace("Preparing statement");
			stmt = conn.prepareStatement(sql);
			Log.trace("Executing query");
			rs = stmt.executeQuery();
			Log.trace("Running through results");
			while(rs.next()) {
				user = new User (rs.getInt("user_id"), rs.getString("user_name"), rs.getString("user_password"));
				Log.info("Found User: " + user.toString());
				userList.add(user);
			}
			if(userList.isEmpty()) {
				Log.traceExit("No users found in database");
				return Optional.empty();
			}else {
				Log.traceExit("Completed search for users in database");
				return Optional.of(userList);
			}
		}catch (SQLException e) {
			Log.error("SQL Exception Ocurred:", e);
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
		return Optional.empty();
	}

	public Boolean deleteUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean deleteAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	public Optional<List<Account>> getAllAccounts() {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean deleteAllAccounts() {
		// TODO Auto-generated method stub
		return null;
	}

}
