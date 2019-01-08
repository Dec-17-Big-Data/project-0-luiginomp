package com.revature.project_0.dao;

import java.util.List;
import java.util.Optional;

import com.revature.project_0.models.Account;
import com.revature.project_0.models.User;

public interface AccountDAO {
	//A user can create an account. 
	public Optional<Integer> callInsertAccount(Integer userId);
	//A user can view their own existing account and balances. 
	public Optional <Account> sendAccountQuery(Integer accountId);
	public Optional<List<Account>> sendAccountsQuery(Integer userId);
	public Optional<Integer> sendAccountOwnerQuery(Integer accountId);
	//A user can delete an account if it is empty.
	public Boolean callDeleteAccount(Integer accountId);
	//A user can add to or withdraw from an account. 
	public Optional <Integer> callDepositBalance(Integer accountId, Double amount);
	public Optional <Integer> callWithdrawBalance(Integer accountId, Double amount);
}