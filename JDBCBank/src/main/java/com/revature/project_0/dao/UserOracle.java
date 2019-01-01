package com.revature.project_0.dao;

import java.sql.Connection;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.project_0.models.Account;
import com.revature.project_0.models.User;

public class UserOracle implements UserDAO{
	
	private static Logger Log = LogManager.getLogger(UserOracle.class);

	private static UserOracle instance;
	
	public UserOracle() {
	}
	
	public static UserDAO getDAO() {
		if(instance == null) {
			instance = new UserOracle();
		}
		return instance;
	}

	public Boolean createUser(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	public Optional<User> getUser(String username) {
		// TODO Auto-generated method stub
		return null;
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
