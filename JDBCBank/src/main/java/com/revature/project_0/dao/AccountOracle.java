package com.revature.project_0.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
		Log.info("callInsertAccount called and passed user ID " + userId);
		Log.info("callInsertAccount calling getConnection to create connection");
		Connection conn = ConnectionUtil.getConnection();
		String sql = "{CALL insert_account (?, ?)}";
		Integer newAccountId = null;
		try {
			Log.info("callInsertAccount preparing call for string " + sql);
			CallableStatement stmt = conn.prepareCall(sql);
			Log.info("callInsertAccount setting first ? to userId");
			stmt.setInt(1, userId);
			Log.info("callInsertAccount registering second ? as an integer for account ID");
			stmt.registerOutParameter(2,  Types.INTEGER);
			Log.info("callInsertAccount executing call");
			stmt.execute();
			Log.info("callInsertAccount retrieving integer for account ID");
			newAccountId = stmt.getInt(2);
		}catch (SQLException e) {
			Log.error("callInsertAccount returned empty optional - encountered SQL Exception: ", e);
			return Optional.empty();
		}
		Log.info("callInsertAccount returned optional of account ID " + newAccountId);
		return Optional.of(newAccountId);
	}
	
	public Optional<Account> sendAccountQuery(Integer accountId){
		Log.info("sendAccountQuery called and passed account ID " + accountId);
		Log.info("sendAccountQuery calling getConnection to establish connection");
		Connection conn = ConnectionUtil.getConnection();
		if(conn == null) {
			Log.info("sendAccountQuery returning empty optional - failed to establish connection");
			return Optional.empty();
		}
		Account account = null;
		String sql = "SELECT * FROM bank_account WHERE account_id = ?";
		try {
			Log.info("sendAccountQuery preparing statement for string " + sql);
			PreparedStatement stmt = conn.prepareStatement(sql);
			Log.info("sendAccountQuery setting ? as account ID");
			stmt.setInt(1, accountId);
			Log.info("sendAccountQuery executing query and retrieving result set");
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				account = new Account(
						rs.getInt("account_id"),
						rs.getDouble("account_balance"));
				Log.info("sendAccountQuery retrieved " + account.toString());
			}else {
				Log.info("sendAccountQuery returning empty optional - result set was empty");
				return Optional.empty();
			}
		}catch (SQLException e) {
			Log.info("sendAccountQuery returning empty optional - encountered SQL Exception:", e);
			return Optional.empty();
		}
		Log.info("sendAccountQuery returning optional of account");
		return Optional.of(account);
	}
	
	public Optional<List<Account>> sendAccountsQuery(Integer userId){
		Log.info("sendAccountsQuery called and passed user ID " + userId);
		Log.info("sendAccountsQuery calling getConnection to try and establish connection");
		Connection conn = ConnectionUtil.getConnection();
		if(conn == null) {
			Log.info("sendAccountsQuery returned null - failed to establish connection");
			return null;
		}
		String sql = "SELECT * FROM bank_account WHERE user_id = ?";
		List<Account> accountList = new ArrayList<Account>();
		try {
			Log.info("sendAccountsQuery preparing statement for string " + sql);
			PreparedStatement stmt = conn.prepareStatement(sql);
			Log.info("sendAccountsQuery setting first ? as userId");
			stmt.setInt(1, userId);
			Log.info("sendAccountsQuery executing query and retrieving result set");
			ResultSet rs = stmt.executeQuery();
			Log.info("sendAccountsQuery iterating through results");
			while (rs.next()) {
				Account account = new Account(
						rs.getInt("account_id"),
						rs.getDouble("account_balance"));
				accountList.add(account);
				Log.trace("Oracle found " + account.toString());
			}
		}catch (SQLException e) {
			Log.error("sendAccountsQuery returned empty optional - encountered SQL Exception: ", e);
			return Optional.empty();
		}
		if(accountList.isEmpty()) {
			Log.info("sendAccountsQuery returned empty optional - no accounts found");
			return Optional.empty();
		}
		Log.info("sendAccountsQuery returning optional of account list");
		return Optional.of(accountList);
	}
	
	public Boolean callDeleteAccount(Integer accountId) {
		Log.info("callDeleteAccount called and passed account ID " + accountId);
		Log.info("callDeleteAccount calling getConnection to establish connection");
		Connection conn = ConnectionUtil.getConnection();
		if(conn == null) {
			Log.info("callDeleteAccount returning false - failed to establish connection");
			return false;
		}
		String sql = "{CALL delete_account (?)}";
		try {
			Log.info("callDeleteAccount preparing callable statement for string " + sql);
			CallableStatement stmt = conn.prepareCall(sql);
			Log.info("callDeleteAccount setting ? ass integer " + accountId);
			stmt.setInt(1, accountId);
			Log.info("callDeleteAccount executing call");
			stmt.execute();
		}catch (SQLException e) {
			Log.error("callDeleteAccount returning false - encountered SQL Exception:", e);
			return false;
		}
		Log.info("callDeleteAccount returning true - successfully called stored procedure");
		return true;
	}
	
	public Optional<Integer> callDepositBalance(Integer accountId, Double amount){
		Log.info("callDepositBalance called and passed account ID " + accountId + " and amount " + amount);
		Log.info("callDepositBalance calling getConnection to establish connection");
		Connection conn = ConnectionUtil.getConnection();
		if(conn == null) {
			Log.info("callDepositBalance returning empty optional - failed to establish connection");
			return Optional.empty();
		}
		Integer transactionId = null;
		String sql = "{CALL deposit_balance (?, ?, ?)}";
		try {
			Log.info("callDepositBalance preparing callable statement for string " + sql);
			CallableStatement stmt = conn.prepareCall(sql);
			Log.info("callDepositBalance setting first ? to account ID");
			stmt.setInt(1, accountId);
			Log.info("callDepositBalance setting second ? to amount");
			stmt.setDouble(2, amount);
			Log.info("callDepositBalance registering third ? as out parameter integer");
			stmt.registerOutParameter(3, Types.INTEGER);
			Log.info("callDepositBalance executing call");
			stmt.execute();
			Log.info("callDepositBalance retrieving transaction ID");
			transactionId = stmt.getInt(3);
		}catch (SQLException e) {
			Log.error("callDepositBalance returning empty optional - encountered SQL Exception:", e);
			return Optional.empty();
		}
		Log.info("callDepositBalance returning optional of transaction ID - successfully called stored procedure");
		return Optional.of(transactionId);
	}
	
	public Optional<Integer> callWithdrawBalance(Integer accountId, Double amount){
		Log.info("callWithdrawBalance called and passed account ID " + accountId + " and amount " + amount);
		Log.info("callWithdrawBalance calling getConnection to establish connection");
		Connection conn = ConnectionUtil.getConnection();
		if(conn == null) {
			Log.info("callWithdrawBalance returning empty optional - failed to setablish connection");
			return Optional.empty();
		}
		Integer transactionId = null;
		String sql = "{CALL withdraw_balance (?, ?, ?)}";
		try {
			Log.info("callWithdrawBalance preparing callable statement for string " + sql);
			CallableStatement stmt = conn.prepareCall(sql);
			Log.info("callWithdrawBalance setting first ? as account ID");
			stmt.setInt(1, accountId);
			Log.info("callWithdrawBalance setting second ? as amount");
			stmt.setDouble(2,  amount);
			Log.info("callWithdrawBalance registering third ? as out parameter integer");
			stmt.registerOutParameter(3,  Types.INTEGER);
			Log.info("callWithdrawBalance executing call");
			stmt.execute();
			Log.info("callWithdrawBalance retrieving transaction ID");
			transactionId = stmt.getInt(3);
		}catch (SQLException e) {
			Log.error("callWithdrawBalance returning empty optional - encountered SQL Exception:", e);
			return Optional.empty();
		}
		Log.info("callWithdrawBalance returning optional of transaction ID");
		return Optional.of(transactionId);
	}

	public Optional<Integer> sendAccountOwnerQuery(Integer accountId) {
		Log.info("sendAccountOwnerQuery called and passed account ID " + accountId);
		Log.info("sendAccountOwnerQuery calling getConnection to establish connection");
		Connection conn = ConnectionUtil.getConnection();
		if(conn == null) {
			Log.info("sendAccountOwnerQuery returning empty optional - failed to establish connection");
			return Optional.empty();
		}
		Integer userId = null;
		String sql = "SELECT user_id FROM bank_account WHERE account_id = ?";
		try {
			Log.info("sendAccountOwnerQuery preparing statement for string " + sql);
			PreparedStatement stmt = conn.prepareStatement(sql);
			Log.info("sendAccountOwnerQuery setting ? as integer " + accountId);
			stmt.setInt(1, accountId);
			Log.info("sendAccountOwnerQuery executing query and retrieving result set");
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				userId = rs.getInt("user_id");
			}else {
				Log.info("sendAccountOwnerQuery returning empty optional - account wasn't found");
			}
		}catch (SQLException e) {
			Log.error("sendAccountOwnerQuery returning empty optional - encountered SQL Exception: ", e);
			return Optional.empty();
		}
		if(userId == null) {
			Log.info("sendAccountOwnerQuery returning empty optional - user ID was null");
			return Optional.empty();
		}
		Log.info("sendAccountOwnerQuery returning optional of user ID");
		return Optional.of(userId);
	}
	
	
}
