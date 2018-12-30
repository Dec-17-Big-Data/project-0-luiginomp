package com.revature.project_0.dao;

import java.util.List;
import java.util.Optional;

import com.revature.project_0.models.User;

public interface UserDAO {
	 Optional <List<User>> getAllUsers();
	 Optional <User> getUserByUsername(String name);
	 Optional <User> getUserById(Integer id);
	 Boolean createUser(String username, String password);
}
