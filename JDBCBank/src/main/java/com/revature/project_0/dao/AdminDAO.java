package com.revature.project_0.dao;

import java.util.*;

import com.revature.project_0.models.*;

public interface AdminDAO extends UserDAO {
	//A superuser can view, create, update, and delete all users.
	public Optional<List<User>> sendUsersQuery();
	public Boolean callDeleteUser(String username);
	public Boolean deleteAllUsers();
	public Boolean callUpdateUsername(String username, String newName);
	public Boolean callUpdatePassword(String username, String newPassword);
}
