package com.revature.project_0.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.*;

import com.revature.project_0.dao.*;
import com.revature.project_0.models.Account;
import com.revature.project_0.models.Transaction;

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
	
	public Account retrieveAccount(Integer accountId){
		Log.info("retrieveAccount called and passed account ID " + accountId);
		Account account = null;
		try {
			Log.info("retrieveAccount calling sendAccountQuery and passing account ID to try and get account object");
			account = accountOracle.sendAccountQuery(accountId).get();
		}catch (NoSuchElementException e) {
			Log.info("retrieveAccount returning false - account was null");
			return null;
		}
		Log.info("retrieveAccount returning true - successfully retrieved account");
		return account;
	}
	
	//A user can view their own existing account and balances.
	public List<Account> retrieveUserAccounts(Integer userId){
		Log.info("retrieveUserAccounts called and passed user ID " + userId);
		List<Account> accountList = new ArrayList<Account>();
		try {
			Log.info("retrieveUserAccounts calling sendAccountsQuery and passing user ID " + userId + " to try and list of accounts");
			accountList = accountOracle.sendAccountsQuery(userId).get();
		}catch(NoSuchElementException e) {
			Log.info("retrieveUserAccounts failed to retrieve any accounts for user");
			return accountList;
		}
		Log.info("retrieveUserAccounts retrieved all accounts for user");
		return accountList;
	}
	
	//A user can create an account.
	public Account createAccount(Integer userId) {
		Log.info("createAccount called and passed user ID " + userId);
		Integer accountId = null;
		try {
			Log.info("createAccount calling callInsertAccount and passing user ID to try and get account ID");
			accountId = accountOracle.callInsertAccount(userId).get();
		}catch (NoSuchElementException e) {
			Log.info("createAccount returned null - failed to retrieve an account ID");
			return null;
		}
		Account account = null;
		try {
			Log.info("createAccount calling sendAccountQuery and passing account ID to try and get account object");
			account = accountOracle.sendAccountQuery(accountId).get();
		}catch(NoSuchElementException e) {
			Log.info("createAccount returned null - failed to retrieve account object");
			return null;
		}
		Log.info("createAccount returned account object");
		System.out.println("Successfully created new " + account.toString());
		return account;
	}
	
	//A user can delete an account if it is empty.
	public Boolean deleteAccount (Integer accountId, Integer userId) {
		Log.info("deleteAccount called and passed account ID" + accountId);
		Log.info("deleteAccount calling retrieve account to see if account exists");
		Account account = retrieveAccount(accountId);
		if(account == null) {
			Log.info("deleteAccount returning false - account doesn't exist");
			System.out.println("Couldn't retrieve account. Please ensure correct ID is entered");
			return false;
		}
		Log.info("deleteAccount checking if account belongs to user");
		try {
			if(userId != accountOracle.sendAccountOwnerQuery(accountId).get()) {
				Log.info("deleteAccount returning false - account doesn't belong to user");
				System.out.println("Account doesn't belong to this user");
				return false;
			}
		}catch(NoSuchElementException e) {
			Log.info("deleteAccount returning false - unable to obtain account");
			System.out.println("Failed to retrieve account. Please try again.");
			return false;
		}
		
		if(account.getBalance() > 0) {
			Log.info("deleteAccount returning false - account balance is greater than 0");
			System.out.println("Can't delete accounts with a balance greater than 0");
			return false;
		}
		if(accountOracle.callDeleteAccount(accountId) == false) {
			Log.info("deleteAccount returning false - callDeleteAccount failed");
			System.out.println("Failed to delete account. Please try again");
			return false;
		}
		Log.info("deleteAccount returned true - successfully deleted acocunt");
		System.out.println("Successfully deleted account");
		return true;
	}
	
	//A user can add to an account.
	public Transaction makeDeposit(Integer accountId, Double amount, Integer userId) {
		Log.info("makeDeposit called and passed account ID " + accountId + ", amount " + amount + ", and user ID " + userId);
		Log.info("makeDeposit checking if amount is valid");
		if(amount <= 0) {
			Log.info("makeDepsit returning null - amount was 0 or less");
			System.out.println("You can't make deposits of 0 or less");
			return null;
		}
		Log.info("makeDeposit calling retrieveAccount and passing account ID to see if account exists");
		if(retrieveAccount(accountId) == null) {
			Log.info("makeDeposit returning null - unable to retrieve account");
			System.out.println("Failed to retrieve account. Please try again");
			return null;
		}
		try {
			Log.info("makeDeposit calling sendAccountOwnerQuery and passing account ID to try and check if user ID matches");
			if(userId != accountOracle.sendAccountOwnerQuery(accountId).get()) {
				Log.info("makeDeposit returning null - account doesn't belong to user");
				System.out.println("Account doesn't belong to this user");
				return null;
			}
		}catch(NoSuchElementException e) {
			Log.info("makeDeposit returning null - unable to retrieve account");
			System.out.println("Failed to retrieve account. Please try again.");
			return null;
		}
		Integer transactionId = null;
		try {
			Log.info("makeDeposit calling callDepositBalance and passing account ID and amount to try and get transaction ID");
			transactionId = accountOracle.callDepositBalance(accountId, amount).get();
		}catch (NoSuchElementException e) {
			Log.info("makeDeposit returning null - callDepositBalance failed");
			System.out.println("Failed to make deposit. Please try again");
			return null;
		}
		Log.info("makeDeposit calling retrieveTransaction and passing transaction ID to retrieve transaction object");
		Transaction transaction = transactionService.retrieveTransaction(transactionId);
		if(transaction == null) {
			Log.info("makeDeposit returning null - failed to retrieve transaction");
			System.out.println("Failed to verify if transaction succeeded");
			return null;
		}
		Log.info("makeDeposit returning transaction - deposit successful");
		System.out.println("Deposit successful - " + transaction.toString());
		return transaction;
	}
	
	//A user can withdraw from an account.
	public Transaction makeWithdrawal(Integer accountId, Double amount, Integer userId) {
		Log.info("makeWithdrawal called and passed account ID " + accountId + ", amount " + amount + ", and user ID " + userId);
		Log.info("makeWithdrawal checking if amount is valid");
		if(amount <= 0) {
			Log.info("makeWithdrawal returning null - amount was 0 or less");
			System.out.println("You can't make deposits of 0 or less");
			return null;
		}
		Log.info("makeWithdrawal calling retrieveAccount and passing account ID to retrieve acocunt object");
		Account account = retrieveAccount(accountId);
		if(account == null) {
			Log.info("makeWithdrawal returning null - failed to retrieveAccount");
			System.out.println("Failed to retrieve account. Please try again");
			return null;
		}
		Log.info("makeWithdrawal checking if funds are available");
		if(amount > account.getBalance()) {
			Log.info("makeWithdrawal returning null - not enough funds available");
			System.out.println("Not enough funds to make withdrawal");
			return null;
		}
		Integer transactionId = null;
		try {
			Log.info("makeWithdrawal calling callWithdrawBalance and passing account ID and amount to try and get transaction ID");
			transactionId = accountOracle.callWithdrawBalance(accountId, amount).get();
		}catch (NoSuchElementException e) {
			Log.info("makeWithdrawal returning null - failed to retrieve transaction ID");
			System.out.println("Failed to make withdrawal. Please try again");
			return null;
		}
		Log.info("makeWithdrawal calling retrieveTransaction and passing transaction ID to retrieve transaction object");
		Transaction transaction = transactionService.retrieveTransaction(transactionId);
		if(transaction == null) {
			Log.info("makeWithdrawal returning null - failed to retrieve transaction");
			System.out.println("Failed to verify if transaction succeedced");
			return null;
		}
		Log.info("makeWithdrawal returning transaction - withdrawal successful");
		System.out.println("Withdrawal successful - " + transaction.toString());
		return transaction;
	}
}
