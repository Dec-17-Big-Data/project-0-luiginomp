package com.revature.project_0.dao;

import java.util.List;

import com.revature.project_0.models.Transaction;

public interface TransactionDAO {
	//A user's transactions are recorded. A user may view transaction history. 
	public Transaction createTransaction(Integer accountId);
	public List<Transaction> getallTransactions(Integer accountId);
}
