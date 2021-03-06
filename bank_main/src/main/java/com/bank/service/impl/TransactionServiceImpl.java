package com.bank.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.bank.dao.TransactionDAO;
import com.bank.dao.impl.TransactionDAOImpl;
import com.bank.exception.TransactionException;
import com.bank.model.Customer;
import com.bank.model.Transaction;
import com.bank.service.TransactionService;

public class TransactionServiceImpl implements TransactionService{
	private TransactionDAO transactionDAO = new TransactionDAOImpl();
	private Logger log = Logger.getLogger(TransactionServiceImpl.class);
	@Override
	public void updateCount() throws TransactionException {
		log.debug("Entering updateCount for transactions");
		transactionDAO.updateCount();
	}

	@Override
	public Transaction getTransactionById(int id) throws TransactionException {
		return transactionDAO.getTransactionById(id);
	}

	@Override
	public List<Transaction> getAllTransactions() throws TransactionException {
		return transactionDAO.getAllTransactions();
	}

	@Override
	public List<Transaction> getAllTransactionsOfACustomer(Customer customer) throws TransactionException {
		return transactionDAO.getAllTransactionsOfACustomer(customer);
	}

	@Override
	public Transaction newTransaction(Transaction transaction, Customer customer) throws TransactionException {
		log.debug("inserting new Transaction from Service");
		transactionDAO.newTransaction(transaction, customer);
		return transaction;
	}

}
