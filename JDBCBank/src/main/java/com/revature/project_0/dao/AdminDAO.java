package com.revature.project_0.dao;

import java.util.*;

import com.revature.project_0.models.*;

public interface AdminDAO extends UserDAO {
	//A superuser can view, create, update, and delete all users.
	public Optional<List<User>> getAllUsers();
	public Boolean deleteUser(String username);
	public Boolean deleteAllUsers();
	public Optional<List<Account>> getAllAccounts();
	public Boolean deleteAllAccounts();
}
