package com.revature.project_0.services;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.project_0.models.User;

public class AdminService extends UserService {
	private static Logger Log = LogManager.getLogger(AdminService.class);
	private static AdminService instance;
	private AdminService() {
	}
	public AdminService getService() {
		if(instance == null) {
			instance = new AdminService();
		}
		return instance;
	}
	
	public static Optional <User> viewUser(String username) {
		if(currentUser.getType() == "Admin") {
			
		}
		return Optional.empty();
	}
	
	public static Optional<List<User>> viewAllUsers(){
		if(currentUser.getType() == "Admin") {
			
		}
		return Optional.empty();
	}
	
	public static Boolean deleteUser(String username) {
		if(currentUser.getType() == "Admin") {
			
		}
		return false;
	}
	
	public static Boolean deleteAllUsers(String username) {
		if(currentUser.getType() == "Admin") {
			
		}
		return false;
	}
}