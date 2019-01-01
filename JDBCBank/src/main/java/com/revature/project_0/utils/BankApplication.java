package com.revature.project_0.utils;

import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.project_0.dao.UserDAO;
import com.revature.project_0.dao.UserOracle;
import com.revature.project_0.services.*;

public class BankApplication {
	
	private static Logger Log = LogManager.getLogger(BankApplication.class);
	final static UserDAO userDAO = UserOracle.getDAO();
	
	public static void main (String[] args) {
		Log.traceEntry("Application starting");
		try {
			DatabaseManager.setupSchema();

			DatabaseManager.tearDownSchema();
		}catch(NoSuchElementException e) {
			Log.error("Unable to find user");
		}
		Log.traceExit("Application ended");
	}
}
