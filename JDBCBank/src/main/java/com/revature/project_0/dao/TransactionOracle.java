package com.revature.project_0.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.project_0.models.Transaction;

public class TransactionOracle implements TransactionDAO {
	
	private static Logger Log = LogManager.getLogger(AdminOracle.class);
	
	private static TransactionOracle instance;
	
	public TransactionOracle() {
	}
	
	public static TransactionDAO getDAO() {
		if(instance == null) {
			instance = new TransactionOracle();
		}
		return instance;
	}

	public Transaction createTransaction(Integer accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Transaction> getallTransactions(Integer accountId) {
		// TODO Auto-generated method stub
		return null;
	}

}
