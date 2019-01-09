package com.revature.project_0.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.project_0.models.Transaction;
import com.revature.project_0.utils.ConnectionUtil;

public class TransactionOracle implements TransactionDAO {
	
	private static Logger Log = LogManager.getLogger(AdminOracle.class);
	
	private static TransactionOracle instance;
	
	private TransactionOracle() {
	}
	
	public static TransactionDAO getDAO() {
		if(instance == null) {
			instance = new TransactionOracle();
		}
		return instance;
	}

	public Optional<List<Transaction>> sendTransactionsQuery(Integer accountId) {
		Log.traceEntry("Sending request for all transactions for account " + accountId);
		Connection conn = ConnectionUtil.getConnection();
		List<Transaction> transactionList = new ArrayList<Transaction>();
		String sql = "SELECT * FROM account_transaction WHERE account_id = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, accountId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Transaction transaction = new Transaction(
						rs.getInt("transaction_id"),
						rs.getTimestamp("transaction_timestamp").toString(),
						rs.getDouble("transaction_amount"));
				transactionList.add(transaction);
				Log.trace("Found transaction: "  + transaction);
			}
			if(transactionList.isEmpty()) {
				Log.traceExit("No transactions found for this account");
				return Optional.empty();
			}
			Log.traceExit("Completed retrieving all transactions for this account");
			return Optional.of(transactionList);
		}catch (SQLException e) {
			Log.error("SQL Exception Occurred: ", e);
		}
		Log.traceExit("Failed to send request");
		return Optional.empty();
	}
	
	public Optional<Transaction> sendTransactionQuery(Integer transactionId){
		Log.traceEntry("Oracle sending query for transaction ID " + transactionId);
		Connection conn = ConnectionUtil.getConnection();
		String sql = "SELECT * FROM account_transaction where transaction_id = ?";
		Transaction transaction = null;
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, transactionId);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				transaction = new Transaction(
						rs.getInt("transaction_id"),
						rs.getTimestamp("transaction_timestamp").toString(),
						rs.getDouble("transaction_amount"));
				Log.traceExit("Oracle found " + transaction.toString());
				return Optional.of(transaction);
			}else {
				Log.traceExit("Oracle couldn't find any existing transactions with ID " + transactionId);
				return Optional.empty();
			}
		}catch (SQLException e) {
			Log.error("SQL Exception occurred: ", e);
		}
		Log.traceExit("Oracle failed to send query");
		return Optional.empty();
	}
}
