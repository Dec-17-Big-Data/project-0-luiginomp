package com.revature.project_0.services;

import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.project_0.dao.TransactionDAO;
import com.revature.project_0.dao.TransactionOracle;
import com.revature.project_0.models.Transaction;

public class TransactionService {
	
	private static Logger Log = LogManager.getLogger(TransactionService.class);
	private static TransactionService instance;
	private static TransactionDAO transactionOracle;
	
	private TransactionService() {
	}
	
	public static TransactionService getService() {
		transactionOracle = TransactionOracle.getDAO();
		if(instance == null) {
			instance = new TransactionService();
		}
		return instance;
	}
	
	public Transaction retrieveTransaction(Integer transactionId) {
		Log.traceEntry("Service retrieving transaction for ID " + transactionId);
		Transaction transaction = null;
		try {
			transaction = transactionOracle.sendTransactionQuery(transactionId).get();
		}catch(NoSuchElementException e) {
			transaction = null;
		}
		return transaction;
	}
}
