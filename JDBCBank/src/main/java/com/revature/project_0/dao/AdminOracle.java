package com.revature.project_0.dao;

import java.sql.CallableStatement;
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
			Log.traceExit("Connection to database not found. Failed to perform request");
			return Optional.empty();
		}
		List<User> userList = new ArrayList<User>();
		User user = null;
		String sql = "select * from bank_user";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()) {
				user = new User (
						rs.getInt("user_id"),
						rs.getString("user_name"),
						rs.getString("user_password"));
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
			ConnectionUtil.tryToClose(conn);
		}
		return Optional.empty();
	}

	public Boolean deleteUser(String username) {
		Log.traceEntry("Attempt to call delete_user with username " + username);
		Connection conn = ConnectionUtil.getConnection();
		String sql = "{call delete_user (?)}";
		CallableStatement stmt = null;
		try {
			stmt = conn.prepareCall(sql);
			stmt.setString(1, username);
			stmt.execute();
			Log.traceExit("Call to delete_user successfully sent");
			return true;
		}catch (SQLException e) {
			Log.error("SQL Exception Ocurred:", e);
		}catch (Exception e) {
			Log.error("Exception Occurred: ", e);
		}finally {
			ConnectionUtil.tryToClose(conn);
		}
		return false;
	}

	public Boolean deleteAllUsers() {
		Log.traceEntry("Attempting to call delete_all_users");
		Connection conn = ConnectionUtil.getConnection();
		String sql = "{call delete_all_users()}";
		CallableStatement stmt = null;
		try {
			stmt = conn.prepareCall(sql);
			stmt.execute();
			Log.traceExit("Call to delete_all_users successfully sent");
			return true;
		}catch (SQLException e) {
			Log.error("SQL Exception Ocurred:", e);
		}catch (Exception e) {
			Log.error("Exception Occurred: ", e);
		}finally {
			ConnectionUtil.tryToClose(conn);
		}
		Log.traceExit("Failed to call delete_all_users");
		return false;
	}

	public Boolean callUpdateUsername(String curName, String newName) {
		Log.traceEntry("Calling update_username");
		Connection conn = ConnectionUtil.getConnection();
		String sql = "{call update_username (?, ?)}";
		try {
			CallableStatement stmt = conn.prepareCall(sql);
			stmt.setString(1, curName);
			stmt.setString(2, newName);
			stmt.execute();
			Log.traceExit("Completed call to update_username");
			return true;
		}catch (SQLException e) {
			Log.error("SQL Exception occurred: ", e);
		}finally {
			ConnectionUtil.tryToClose(conn);
		}
		Log.traceExit("Failed to call update_username");
		return false;
	}
	
	public Boolean callUpdatePassword(String username, String newPassword) {
		Log.traceEntry("Calling update_password");
		Connection conn = ConnectionUtil.getConnection();
		String sql = "{call update_password (?, ?)}";
		try {
			CallableStatement stmt = conn.prepareCall(sql);
			stmt.setString(1, username);
			stmt.setString(2, newPassword);
			stmt.execute();
			Log.traceExit("Completed call to update_password");
			return true;
		}catch (SQLException e) {
			Log.error("SQL Exception occurred: ", e);
		}finally {
			ConnectionUtil.tryToClose(conn);
		}
		Log.traceExit("Failed to call update_password");
		return false;
	}
}
