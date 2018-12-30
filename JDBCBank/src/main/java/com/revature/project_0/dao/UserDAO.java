package com.revature.project_0.dao;

import java.util.List;
import java.util.Optional;

import com.revature.project_0.models.User;

public interface UserDAO {
	 Optional <List<User>> getAllUsers();
	 Optional <User> getUserByUsername(String username);
	 Boolean createUser(String username, String password);
	 Boolean logOut();
}
