package com.revature.project_0.dao;

import java.util.Optional;

import com.revature.project_0.models.Account;
import com.revature.project_0.models.User;

public interface AccountDAO {
	//A user can create an account. 
	public Integer createAccount(User owner);
	//A user can view their own existing account and balances. 
	public Optional <Account> getAccount(Integer accountId);
	//A user can delete an account if it is empty.
	public Boolean deleteAccount(Integer accountId);
	//A user can add to or withdraw from an account. 
	public Boolean depositToAccount(Double amount);
	public Boolean withdrawFromAccount(Double amount);
}
