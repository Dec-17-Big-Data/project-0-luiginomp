package com.revature.project_0.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomerService extends UserService {
	private static Logger Log = LogManager.getLogger(CustomerService.class);
	private static CustomerService instance;
	private CustomerService() {
	}
	public CustomerService getService() {
		if(instance == null) {
			instance = new CustomerService();
		}
		return instance;
	}
}