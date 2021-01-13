package com.bank.dao;

import java.util.List;

import com.bank.exception.TransactionException;
import com.bank.model.Customer;
import com.bank.model.Transaction;

public interface TransactionDAO {
	//GET
	public void updateCount() throws TransactionException;
	public Transaction getTransactionById(int id) throws TransactionException;
	public List<Transaction> getAllTransactions() throws TransactionException;
	public List<Transaction> getAllTransactionsOfACustomer(Customer customer) throws TransactionException;
	
	//PUT
	public void newTransaction(Transaction transaction,Customer customer) throws TransactionException;
}
