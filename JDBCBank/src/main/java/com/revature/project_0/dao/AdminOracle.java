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

	public Optional<List<User>> sendUsersQuery() {
		Connection conn = ConnectionUtil.getConnection();
		if(conn == null) {
			Log.info("sendUsersQuery returned optional empty - failed to establish connection");
			return Optional.empty();
		}
		List<User> userList = new ArrayList<User>();
		String sql = "SELECT * FROM bank_user";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				User user = new User (
						rs.getInt("user_id"),
						rs.getString("user_name"),
						rs.getString("user_password"));
				userList.add(user);
			}
		}catch (SQLException e) {
			Log.error("sendUsersQuery returned optional empty - encountered SQL Exception: ", e);
			return Optional.empty();
		}finally {
			ConnectionUtil.tryToClose(conn);
		}
		if(userList.isEmpty()) {
			Log.info("sendUsersQuery returned optional empty - no users found");
			return Optional.empty();
		}
		Log.info("sendUsersQuery returning optional of user list");
		return Optional.of(userList);
	}

	public Boolean callDeleteUser(String username) {
		Log.info("callDeleteUser called and passed username " + username);
		Log.info("callDeleteUser calling getConnection to establish connection");
		Connection conn = ConnectionUtil.getConnection();
		if(conn == null) {
			Log.info("callDeleteUser returning false - failed to establish connection");
			return false;
		}
		String sql = "{call delete_user(?)}";
		try {
			Log.info("callDeleteUser preparing callable statement for string " +sql);
			CallableStatement stmt = conn.prepareCall(sql);
			Log.info("callDeleteUser setting ? as " + username);
			stmt.setString(1, username);
			Log.info("callDeleteUser executing call");
			stmt.execute();
		}catch(SQLException e) {
			Log.info("callDeleteUser returning false - encountered SQL Exception: ", e);
			return false;
		}finally {
			ConnectionUtil.tryToClose(conn);
		}
		Log.info("callDeleteUser returned true - successfully called stored procedure");
		return true;
	}

	public Boolean deleteAllUsers() {
		Log.traceEntry("Attempting to call delete_all_users");
		Connection conn = ConnectionUtil.getConnection();
		if(conn == null) {
			return false;
		}
		String sql = "{call delete_all_users()}";
		try {
			CallableStatement stmt = conn.prepareCall(sql);
			stmt.execute();
			Log.traceExit("Call to delete_all_users successfully sent");
			return true;
		}catch (SQLException e) {
			Log.error("deleteAllUsers SQL Exception Ocurred:", e);
		}catch (Exception e) {
			Log.error("Exception Occurred: ", e);
		}finally {
			ConnectionUtil.tryToClose(conn);
		}
		Log.traceExit("Failed to call delete_all_users");
		return false;
	}
	
	public Boolean callUpdateUsername(String username, String newName) {
		Log.info("callUpdateUsername called and passed " + username + " and " + newName);
		Log.info("callUpdateUsername calling getConnection to establish connection");
		Connection conn = ConnectionUtil.getConnection();
		if(conn == null) {
			Log.info("callUpdateUsername returning false - failed to establish connection");
			return false;
		}
		String sql = "{CALL update_username (?, ?)}";
		try {
			Log.info("callUpdateUsername preparing callable statment for string " + sql);
			CallableStatement stmt = conn.prepareCall(sql);
			Log.info("callUpdateUsername setting first ? to " + username);
			stmt.setString(1, username);
			Log.info("callUpdateUsername setting second ? to " + newName);
			stmt.setString(2, newName);
			Log.info("callUpdateUsername executing call");
			stmt.execute();
		}catch (SQLException e) {
			Log.error("callUpdateUsername returned false - encountered SQL Exception: ", e);
			return false;
		}finally {
			ConnectionUtil.tryToClose(conn);
		}
		Log.info("callUpdateUsername returned true - successfully called stored function");
		return true;
	}
	
	public Boolean callUpdatePassword(String username, String newPassword) {
		Log.traceEntry("Calling update_password");
		Connection conn = ConnectionUtil.getConnection();
		if(conn == null) {
			return false;
		}
		String sql = "{call update_password (?, ?)}";
		try {
			CallableStatement stmt = conn.prepareCall(sql);
			stmt.setString(1, username);
			stmt.setString(2, newPassword);
			stmt.execute();
			Log.traceExit("Completed call to update_password");
			return true;
		}catch (SQLException e) {
			Log.error("callUpdatePassword SQL Exception occurred: ", e);
		}finally {
			ConnectionUtil.tryToClose(conn);
		}
		Log.traceExit("Failed to call update_password");
		return false;
	}
}
