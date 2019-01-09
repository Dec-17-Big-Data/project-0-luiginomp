package com.revature.project_0.Test;

import static org.junit.Assert.*;

import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;

import com.revature.project_0.dao.*;
import com.revature.project_0.models.*;
import com.revature.project_0.utils.BankApplication;

public class ApplicationTest {
	private static Logger Log = LogManager.getLogger(ApplicationTest.class);
	
	@BeforeClass
	public static void begin() {
		Log.info("BEGIN ORACLE TESTING");
	}
	

	
	private void logBegin() {
		Log.info("BeginUnitTest");
	}
	
	private void logEnd() {
		Log.info("End Unit Test" + System.lineSeparator());
	}
	@AfterClass
	public static void end() {
		Log.info("END ORACLE TESTING"
				+ System.lineSeparator() + "==========================================================================================="
				+ System.lineSeparator() + "===========================================================================================");
	}

}