package com.revature.project_0.utils;

import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BankApplication {
	
	private static Logger Log = LogManager.getLogger(BankApplication.class);
	private Boolean loggedIn = false;
	private Boolean adminuser = false;
	public static void main (String[] args) {
		Log.traceEntry("Application starting");
		Log.traceExit("Application ended");
	}
}
