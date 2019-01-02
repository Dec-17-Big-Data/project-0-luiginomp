package com.revature.project_0.services;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.project_0.models.User;

public abstract class UserService {
	
	private static Logger Log = LogManager.getLogger(UserService.class);
	
	protected static User currentUser;
	
	public static Optional<User> logIn(String username, String password) {
		return Optional.of(currentUser);
	}
	
	public static Boolean logOff() {
		return false;
	}
	
	public static Boolean register(String username, String password) {
		return false;
	}
}