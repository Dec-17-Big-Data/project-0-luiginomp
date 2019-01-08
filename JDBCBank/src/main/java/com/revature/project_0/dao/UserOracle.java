package com.revature.project_0.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;
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
		Log.info("sendUserQuery called and passed username " + username);
		Log.info("sendUserQuery calling getConnection to create connection");
		Connection conn = ConnectionUtil.getConnection();
		String sql = "SELECT * FROM bank_user where user_name = ?";
		User user = null;
		try {
			Log.info("sendUserQuery preparing statement for string " + sql);
			PreparedStatement stmt = conn.prepareStatement(sql);
			Log.info("sendUserQuery setting first ? to username ");
			stmt.setString(1, username);
			Log.info("sendUserQuery executing query and retrieving result set");
			ResultSet rs = stmt.executeQuery();
			Log.info("sendUserQuery iterating through result set");
			if(rs.next()) {
				user = new User(
						rs.getInt("user_id"),
						rs.getString("user_name"),
						rs.getString("user_password"));
			}
		}catch (SQLException e) {
			Log.error("sendUserQuery returned optional empty - encountered SQLException: ", e);
			return Optional.empty();
		}finally {
			ConnectionUtil.tryToClose(conn);
		}
		if(user == null) {
			Log.info("sendUserQuery returned empty optional - failed to retrieve user object with given username");
			return Optional.empty();
		}
		Log.info("sendUserQuery found and returning optional of" + user.toString());
		return Optional.of(user);
	}
	
	public Boolean userExists(String username) {
		Log.info("userExists called and passed " + username);
		Log.info("userExists calling sendUserQuery and passing username to try and get user object");
		User user = null;
		try {
			user = sendUserQuery(username).get();
		}catch(NoSuchElementException e) {
		}
		if(user == null) {
			Log.info("userExists returning false - user was null");
			return false;
		}
		Log.info("userExists returning true - successfully retrieved user object");
		return true;
	}
}
