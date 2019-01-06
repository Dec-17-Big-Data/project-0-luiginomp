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
	
	public Boolean callInsertUser(String username, String password){
		Log.traceEntry("Calling insert_user and passing strings " + username + ", " + password);
		Connection conn = ConnectionUtil.getConnection();
		String sql = "CALL insert_user (?, ?)";
		try {
			CallableStatement stmt = conn.prepareCall(sql);
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.execute();
			Log.traceExit("Completed call to insert_user");
			return true;
		}catch(SQLException e) {
			Log.error("SQL Exception occurred: ", e);
		}finally {
			ConnectionUtil.tryToClose(conn);
		}
		Log.traceExit("Unable to call insert_user");
		return false;
	}
	
	public Optional<User> sendUserQuery(String username){
		Log.traceEntry("Sending query for username " + username);
		Connection conn = ConnectionUtil.getConnection();
		String sql = "SELECT * FROM bank_user where user_name = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				User user = new User(
						rs.getInt("user_id"),
						rs.getString("user_name"),
						rs.getString("user_password"));
				Log.traceExit("Completed query");
				return Optional.of(user);
			}
			Log.traceExit("Username doesn't exist");
			return Optional.empty();
		}catch (SQLException e) {
			Log.error("SQL Exception occurred: ", e);
		}finally {
			ConnectionUtil.tryToClose(conn);
		}
		Log.traceExit("Unable to retrieve user");
		return Optional.empty();
	}
}
