package com.revature.project_0.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.project_0.dao.*;
import com.revature.project_0.models.User;

public class BankApplication {
	
	private static Logger Log = LogManager.getLogger(BankApplication.class);
	private User currentUser = null;
	private UserDAO userOracle = UserOracle.getDAO();
	private AdminDAO adminOracle = AdminOracle.getDAO();
	public static void main (String[] args) {
		Log.traceEntry("Application starting");
		
		Log.traceExit("Application ended");
	}
}