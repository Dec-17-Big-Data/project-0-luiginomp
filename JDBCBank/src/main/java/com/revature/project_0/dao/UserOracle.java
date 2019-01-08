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
		Connection conn = ConnectionUtil.getConnection();
		if(conn == null) {
			Log.info("callInsertUser returning false - failed to establish connection");
			return false;
		}
		String sql = "CALL insert_user (?, ?)";
		try {
			CallableStatement stmt = conn.prepareCall(sql);
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.execute();
		}catch(SQLException e) {
			Log.error("callInsertUser returning false - encountered SQL Exception");
			return false;
		}finally {
			ConnectionUtil.tryToClose(conn);
		}
		Log.info("callInsertUser returning true - successfully called stored function");
		return true;
	}
	
	public Optional<User> sendUserQuery(String username){
		Connection conn = ConnectionUtil.getConnection();
		if(conn == null) {
			Log.info("sendUserQuery returning empty optional - failed to establish connection");
			return Optional.empty();
		}
		String sql = "SELECT * FROM bank_user where user_name = ?";
		User user = null;
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				user = new User(
						rs.getInt("user_id"),
						rs.getString("user_name"),
						rs.getString("user_password"));
			}else {
				Log.info("sendUserQuery returning empty optional - failed to retrieve user object with given username");
				return Optional.empty();
			}
		}catch (SQLException e) {
			Log.error("sendUserQuery returning empty optional - encountered SQL Exception");
			return Optional.empty();
		}finally {
			ConnectionUtil.tryToClose(conn);
		}
		Log.info("sendUserQuery found and returning optional of " + user.toString());
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
