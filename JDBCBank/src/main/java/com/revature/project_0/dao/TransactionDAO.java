package com.revature.project_0.dao;

import java.util.List;
import java.util.Optional;

import com.revature.project_0.models.Transaction;

public interface TransactionDAO {
	//A user's transactions are recorded. A user may view transaction history. 
	public Optional<List<Transaction>> getallTransactions(Integer accountId);
}
