package com.revature.project_0.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.*;

import com.revature.project_0.dao.*;
import com.revature.project_0.models.Account;
import com.revature.project_0.models.Transaction;
import com.revature.project_0.models.User;

public class AccountService {
	
	private static Logger Log = LogManager.getLogger(AccountService.class);
	private static AccountService instance;
	private static AccountDAO accountOracle;
	private static TransactionService transactionService;
	
	private AccountService() {
	}
	
	public static AccountService getService() {
		accountOracle = AccountOracle.getDAO();
		transactionService = TransactionService.getService();
		if(instance == null) {
			instance = new AccountService();
		}
		return instance;
	}
	
	//A user can view their own existing account and balances.
	public Account retrieveAccount(Integer accountId){
		Log.traceEntry("Service retrieving account " + accountId);
		try {
			Account account = accountOracle.sendAccountQuery(accountId).get();
			Log.traceExit("Service returning account " + account.toString());
			return account;
		}catch (NoSuchElementException e) {
		}
		Log.traceExit("Service unable to obtain account " + accountId);
		return null;
	}
	
	//A user can create an account.
	public Account createAccount(Integer userId) {
		Log.traceEntry("Service creating account for user " + userId);
		Integer accountId = null;
		try {
			accountId = accountOracle.callInsertAccount(userId).get();
		}catch (NoSuchElementException e) {
		}
		if(accountId != null) {
			Account account = null;
			try {
				account = accountOracle.sendAccountQuery(accountId).get();
			}catch(NoSuchElementException e) {
			}
			if(account != null) {
				Log.traceExit("Service successfully created " + account.toString());
				return account;
			}
		}
		Log.traceExit("Service failed to create new account for user " + userId);
		return null;
	}
	
	//A user can delete an account if it is empty.
	public Boolean deleteAccount(Integer accountId) {
		Log.traceEntry("Service deleting account " + accountId);
		Account account = retrieveAccount(accountId);
		if(account == null) {
			Log.traceExit("Service failed to find an account to delete");
			return false;
		}
		else if(accountOracle.callDeleteAccount(accountId)) {
			account = retrieveAccount(accountId);
			if(account == null) {
				Log.traceExit("Service deleted account " + accountId);
				return true;
			}
			Log.traceExit("Service failed to delete account " + accountId);
			return false;
		}
		Log.traceExit("Service failed to delete account " + accountId);
		return false;
	}
	
	//A user can add to an account.
	public Transaction makeDeposit(Integer accountId, Double amount) {
		Log.traceEntry("Service depositing " + amount + " to account " + accountId);
		if(amount <= 0) {
			Log.traceExit("Service can only make deposits from amounts greater than 0");
			return null;
		}
		if(retrieveAccount(accountId) == null) {
			Log.traceExit("Service failed to make deposit");
			return null;
		}
		Integer transactionId = null;
		try {
			transactionId = accountOracle.callDepositBalance(accountId, amount).get();
		}catch (NoSuchElementException e) {
			transactionId = null;
		}
		if(transactionId == null) {
			Log.traceExit("Service failed to make deposit");
			return null;
		}
		Transaction transaction = transactionService.retrieveTransaction(transactionId);
		if(transaction != null) {
			Log.traceExit("Service completed deposit and returning " + transaction);
			return transaction;
		}
		Log.traceExit("Service failed to make deposit");
		return null;
	}
	
	//A user can withdraw from an account.
	public Transaction makeWithdrawal(Integer accountId, Double amount) {
		Log.traceEntry("Service withdrawing " + amount + " from account " + accountId);
		if(amount <= 0) {
			Log.traceExit("Service can only make withdrawals from amounts greater than 0");
			return null;
		}
		Account account = retrieveAccount(accountId);
		if(account == null) {
			Log.traceExit("Service failed to make withdrawal");
			return null;
		}
		if(amount > account.getBalance()) {
			Log.traceExit("Service can't withdraw more than the account balance");
			return null;
		}
		Integer transactionId = null;
		try {
			transactionId = accountOracle.callWithdrawBalance(accountId, amount).get();
		}catch (NoSuchElementException e) {
			transactionId = null;
		}
		if(transactionId == null) {
			Log.traceExit("Service failed to make withdrawal");
			return null;
		}
		Transaction transaction = transactionService.retrieveTransaction(transactionId);
		if(transaction != null) {
			Log.traceExit("Service completed withdrawal and returning " + transaction);
			return transaction;
		}
		return null;
	}
	
	public List<Account> retrieveAccountsForUser(User user){
		Log.traceEntry("Service retrieving all acounts for user " + user.getUserName());
		List<Account> accountList = new ArrayList<Account>();
		Integer userId = user.getUserId();
		try {
			accountList = accountOracle.sendAccountsQuery(userId).get();
		}catch(NoSuchElementException e) {
			Log.traceExit("Service found no accounts for user " + user.getUserName());
			return accountList;
		}
		Log.traceExit("Service retrieved all acounts for user " + user.getUserName());
		return accountList;
	}
}
