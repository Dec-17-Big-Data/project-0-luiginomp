package com.revature.project_0.dao;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.project_0.models.Account;
import com.revature.project_0.models.User;

public class AccountOracle implements AccountDAO {
	
	private static Logger Log = LogManager.getLogger(AdminOracle.class);

	private static AccountOracle instance;
	
	public AccountOracle() {
	}
	
	public static AccountDAO getDAO() {
		if(instance == null) {
			instance = new AccountOracle();
		}
		return instance;
	}

	public Integer createAccount(User owner) {
		// TODO Auto-generated method stub
		return null;
	}

	public Optional<Account> getAccount(Integer accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean deleteAccount(Integer accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean depositToAccount(Double amount) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean withdrawFromAccount(Double amount) {
		// TODO Auto-generated method stub
		return null;
	}

}
