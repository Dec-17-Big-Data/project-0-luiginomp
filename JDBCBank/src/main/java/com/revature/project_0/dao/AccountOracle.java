package com.revature.project_0.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.project_0.models.Account;
import com.revature.project_0.models.User;
import com.revature.project_0.utils.ConnectionUtil;

public class AccountOracle implements AccountDAO {
	
	private static Logger Log = LogManager.getLogger(AdminOracle.class);

	private static AccountOracle instance;
	
	private AccountOracle() {
	}
	
	public static AccountDAO getDAO() {
		if(instance == null) {
			instance = new AccountOracle();
		}
		return instance;
	}
	
	public Optional<Integer> callInsertAccount(Integer userId){
		Log.traceEntry("Calling insert_account");
		Connection conn = ConnectionUtil.getConnection();
		String sql = "{CALL insert_account (?, ?)}";
		Integer newAccountId = null;
		try {
			CallableStatement stmt = conn.prepareCall(sql);
			stmt.setInt(1, userId);
			stmt.registerOutParameter(2,  Types.INTEGER);
			stmt.execute();
			newAccountId = stmt.getInt(2);
			Log.trace("Completed call");
		}catch (SQLException e) {
			Log.error("SQL Exception occurred: ", e);
		}finally {
			ConnectionUtil.tryToClose(conn);
		}
		if(newAccountId != null) {
			Log.traceExit("Returning new account ID " + newAccountId);
			return Optional.of(newAccountId);
		}
		Log.traceExit("Oracle failed to complete call");
		return Optional.empty();
	}
	
	public Optional<Account> sendAccountQuery(Integer accountId){
		Log.traceEntry("Sending query for account with ID " + accountId);
		Connection conn = ConnectionUtil.getConnection();
		String sql = "SELECT * FROM bank_account WHERE account_id = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, accountId);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				Account account = new Account(
						rs.getInt("account_id"),
						rs.getDouble("account_balance"));
				Log.traceExit("Retrieved " + account.toString());
				return Optional.of(account);
			}else {
				Log.traceExit("Account doesn't exist");
				return Optional.empty();
			}
		}catch (SQLException e) {
			Log.error("SQL Exception occurred: ", e);
		}finally {
			ConnectionUtil.tryToClose(conn);
		}
		Log.traceExit("Unable to send query");
		return Optional.empty();
	}
	
	public Optional<List<Account>> sendAccountsQuery(Integer userId){
		Log.traceEntry("Oracle sending query for all accounts for user " + userId);
		Connection conn = ConnectionUtil.getConnection();
		String sql = "{SELECT * FROM bank_account WHERE user_id = ?}";
		List<Account> accountList = new ArrayList<Account>();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Account account = new Account(
						rs.getInt("account_id"),
						rs.getDouble("account_balance"));
				accountList.add(account);
				Log.trace("Oracle found " + account.toString());
			}
		}catch (SQLException e) {
			Log.error("SQL Exception occurred: ", e);
		}finally {
			ConnectionUtil.tryToClose(conn);
		}
		if(accountList.isEmpty()) {
			Log.traceExit("Oracle found no accounts for this user");
			return Optional.empty();
		}
		Log.traceExit("Oracle retrieved accounts for user");
		return Optional.of(accountList);
	}
	
	public Boolean callDeleteAccount(Integer accountId) {
		Log.traceEntry("Oracle calling delete_account and passing in account ID " + accountId);
		Connection conn = ConnectionUtil.getConnection();
		String sql = "{CALL delete_account (?)}";
		try {
			CallableStatement stmt = conn.prepareCall(sql);
			stmt.setInt(1, accountId);
			stmt.execute();
			Log.traceExit("Oracle completed call");
			return true;
		}catch (SQLException e) {
			Log.error("SQL Exception occurred: ", e);
		}finally {
			ConnectionUtil.tryToClose(conn);
		}
		Log.traceExit("Oracle failed to complete call");
		return false;
	}
	
	public Optional<Integer> callDepositBalance(Integer accountId, Double amount){
		Log.traceEntry("Oracle calling deposit_balance for " + amount + " from  account " + accountId);
		Connection conn = ConnectionUtil.getConnection();
		String sql = "{CALL deposit_balance (?, ?, ?)}";
		Integer transactionId = null;
		try {
			CallableStatement stmt = conn.prepareCall(sql);
			stmt.setInt(1,  accountId);
			stmt.setDouble(2, amount);
			stmt.registerOutParameter(3, Types.INTEGER);
			stmt.execute();
			transactionId = stmt.getInt(3);
		}catch (SQLException e) {
			Log.error("SQL Exception occurred: ", e);
		}finally {
			ConnectionUtil.tryToClose(conn);
		}
		if(transactionId != null) {
			Log.traceExit("Oracle completed call and returned transaction ID " + transactionId);
			return Optional.of(transactionId);
		}
		return Optional.empty();
	}
	
	public Optional<Integer> callWithdrawBalance(Integer accountId, Double amount){
		Log.traceEntry("Oracle calling withdraw_balance for " + amount + " from account " + accountId);
		Connection conn = ConnectionUtil.getConnection();
		String sql = "{CALL withdraw_balance (?, ?, ?)}";
		Integer transactionId = null;
		try {
			CallableStatement stmt = conn.prepareCall(sql);
			stmt.setInt(1, accountId);
			stmt.setDouble(2,  amount);
			stmt.registerOutParameter(3,  Types.INTEGER);
			stmt.execute();
			transactionId = stmt.getInt(3);
		}catch (SQLException e) {
			Log.error("SQL Exception occurred: ", e);
		}finally {
			ConnectionUtil.tryToClose(conn);
		}
		if(transactionId != null) {
			Log.traceExit("Oracle completed call and returned transaction ID" + transactionId);
			return Optional.of(transactionId);
		}
		Log.traceExit("Oracle failed to complete call");
		return Optional.empty();
	}
}
