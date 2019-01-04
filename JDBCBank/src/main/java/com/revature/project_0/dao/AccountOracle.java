package com.revature.project_0.dao;

import java.beans.Statement;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.project_0.models.Account;
import com.revature.project_0.utils.ConnectionUtil;

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

	public Optional <Integer> createAccount() {
		Log.traceEntry("Attempting to call insert_account");
		Connection conn = ConnectionUtil.getConnection();
		String sql = "{call insert_account (?)}";
		CallableStatement stmt = null;
		Integer accountId = null;
		try {
			stmt = conn.prepareCall(sql);
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.execute();
			accountId = stmt.getInt(1);
			Log.traceExit("Successfully called insert_account");
			return Optional.of(accountId);
		}catch (SQLException e) {
			Log.error("SQL Exception Ocurred:", e);
		}catch (Exception e) {
			Log.error("Exception Occurred: ", e);
		}finally {
			try {
				if(stmt != null) {
					conn.close();
				}
			}catch (SQLException e) {
				Log.error("SQL Exception occurred while attempting to close connection", e);
			}
			try {
				if(conn != null) {
					conn.close();
					Log.info("Closed connection to database");
				}
			}catch (SQLException e) {
				Log.error("SQL Exception occurred while attempting to close connection", e);
			}
		}
		return Optional.empty();
	}

	public Optional<Account> getAccount(Integer accountId) {
		Log.traceEntry("Attempting to perform query to find account ID " + accountId);
		Connection conn = ConnectionUtil.getConnection();
		String sql = "select * from bank_account where account_id = ?";
		PreparedStatement stmt = null;
		Account account = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, accountId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				account = new Account (rs.getInt("account_id"), rs.getDouble("account_balance"));
			}
			if(account == null) {
				Log.traceExit("No account found with given ID");
				return Optional.empty();
			}else {
				Log.traceExit("Successfully retrieved account");
				return Optional.of(account);
			}
		}catch (SQLException e) {
			Log.error("SQL Exception Ocurred:", e);
		}catch (Exception e) {
			Log.error("Exception Occurred: ", e);
		}finally {
			try {
				if(stmt != null) {
					conn.close();
				}
			}catch (SQLException e) {
				Log.error("SQL Exception occurred while attempting to close connection", e);
			}
			try {
				if(conn != null) {
					conn.close();
					Log.info("Closed connection to database");
				}
			}catch (SQLException e) {
				Log.error("SQL Exception occurred while attempting to close connection", e);
			}
		}
		return Optional.empty();
	}

	public Boolean deleteAccount(Integer accountId) {
		Connection conn = ConnectionUtil.getConnection();
		String sql = "{call delete_account (?)}";
		CallableStatement stmt = null;
		try {
			stmt = conn.prepareCall(sql);
			stmt.setInt(1, accountId);
			stmt.execute();
			Log.traceExit("Successfully called delete_account");
			return true;
		}catch (SQLException e) {
			Log.error("SQL Exception Ocurred:", e);
		}catch (Exception e) {
			Log.error("Exception Occurred: ", e);
		}finally {
			try {
				if(stmt != null) {
					conn.close();
				}
			}catch (SQLException e) {
				Log.error("SQL Exception occurred while attempting to close connection", e);
			}
			try {
				if(conn != null) {
					conn.close();
					Log.info("Closed connection to database");
				}
			}catch (SQLException e) {
				Log.error("SQL Exception occurred while attempting to close connection", e);
			}
		}
		return false;
	}

	public Boolean depositToAccount(Integer accountId, Double amount) {
		
		return null;
	}

	public Boolean withdrawFromAccount(Integer accountId, Double amount) {
		// TODO Auto-generated method stub
		return null;
	}

}
