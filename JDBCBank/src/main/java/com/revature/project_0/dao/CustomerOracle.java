package com.revature.project_0.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomerOracle extends UserOracle{
	
	private static Logger Log = LogManager.getLogger(AdminOracle.class);
	private static CustomerOracle instance;
	private CustomerOracle () {
	}
	public static UserDAO getDAO() {
		if(instance == null) {
			instance = new CustomerOracle();
		}
		return instance;
	}
}
