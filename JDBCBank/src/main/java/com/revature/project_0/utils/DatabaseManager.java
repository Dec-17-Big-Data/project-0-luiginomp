package com.revature.project_0.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DatabaseManager {
	
	private static Logger Log = LogManager.getLogger(BankApplication.class);
	private static DatabaseManager instance;
	
	public DatabaseManager() {
	}
	
	public static DatabaseManager getDBManager() {
		if(instance == null) {
			instance = new DatabaseManager();
		}
		return instance;
	}
	
	public static Boolean setupSchema() {
		Log.traceEntry("Creating database schema");
		Connection connection = ConnectionUtil.getConnection();
		if(connection == null) {
			Log.traceExit("No connection - could not create new user");
			return false;
		}
		try {
			Log.info("Creating statement");
			Statement statement = connection.createStatement();
			Log.info("Adding command strings to batch");
			String command = "create table bank_user (user_id number (10) primary key, username varchar2 (255), password varchar2 (255), account_id number (10))";
			statement.addBatch(command);
			command = "create table bank_account (account_id number (10) primary key, balance binary_float default '0', user_id number (10))";
			statement.addBatch(command);
			command = "alter table bank_user add constraint user_to_account_foreign_key foreign key (account_id) references bank_account (account_id) on delete cascade";
			statement.addBatch(command);
			command = "Insert into bank_account values (0, 20.0, null)";
			statement.addBatch(command);
			command = "commit";
			statement.addBatch(command);
			Log.info("Sending commands to database");
			statement.executeBatch();
			Log.traceExit("Schema setup was successful");
			return true;
		}catch(SQLException e) {
			
		}
		return false;
	}
	
	public static Boolean tearDownSchema() {
		Log.traceEntry("Tearing down database schema");
		Connection connection = ConnectionUtil.getConnection();
		if(connection == null) {
			Log.traceExit("No connection - could not create new user");
			return false;
		}
		try {
			Log.info("Creating statement");
			Statement statement = connection.createStatement();
			Log.info("Adding command strings to batch");
			String command = "drop table bank_user";
			statement.addBatch(command);
			command = "drop table bank_account";
			statement.addBatch(command);
			command = "commit";
			statement.addBatch(command);
			Log.info("Sending commands to database");
			statement.executeBatch();
			Log.traceExit("Schema teardown was successful");
			return true;
		}catch(SQLException e) {
			
		}
		return false;
	}
}
