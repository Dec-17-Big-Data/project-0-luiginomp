package com.revature.project_0.dao;

import java.util.List;
import java.util.Optional;

import com.revature.project_0.models.Account;
import com.revature.project_0.models.User;

public interface UserDAO {
	//Standard services
	Boolean createUser(String username, String password, String type);
	Optional <User> logIn(String username, String password);
	Boolean createAccount();
	Boolean deleteAccount();
	Optional <List<Account>> getAllAccounts();
	Optional <Account> getAccountByAccountId(Integer id);
	Boolean addToAccount();
	Boolean withdrawFromAccount();
	
	 //Super services
	Optional <User> getUserByUsername(String username);
	Boolean deleteUser(String username);
	Optional <List<User>> getAllUsers();
	Boolean deleteAllUsers(String username);
	
}
