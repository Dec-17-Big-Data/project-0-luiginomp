package com.revature.project_0.services;

import java.util.*;

import com.revature.project_0.dao.*;
import com.revature.project_0.models.*;

public class UserService {
	
	private static UserService userServiceInstance;
	
	final static UserDAO userDAO = UserOracle.getDAO();
	
	public UserService() {
		
	}
	
	
	public static UserService getUserService() {
		if(userServiceInstance == null) {
			userServiceInstance = new UserService();
		}
		return userServiceInstance;
	}
	
	
	public Optional <User> getUser(String name){
		return userDAO.getUserByUsername(name);
	}
	
	public Optional<User> getUser(Integer id){
		return userDAO.getUserById(id);
	}
	
	public Boolean createUser(String username, String password) {
		return userDAO.createUser(username, password);
	}
}

