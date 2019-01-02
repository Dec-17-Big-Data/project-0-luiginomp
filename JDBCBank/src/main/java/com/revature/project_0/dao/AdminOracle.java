package com.revature.project_0.dao;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.project_0.models.Account;
import com.revature.project_0.models.User;

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
		// TODO Auto-generated method stub
		return null;
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
