package com.bank.service.impl.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.bank.exception.TransactionException;
import com.bank.model.Transaction;
import com.bank.service.TransactionService;
import com.bank.service.impl.TransactionServiceImpl;

class TransactionServiceImplTest {
	TransactionService  transactionServiceImpl = new TransactionServiceImpl();

	@Test
	void testGetTransactionById() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAllTransactions() {
		List<Transaction> transactions = new ArrayList();
		transactions.add(new Transaction("1001",1,15,14,-1,Date.valueOf("2021-01-10")));
		transactions.add(new Transaction("1001",2,14,13,-1,Date.valueOf("2020-08-15")));
		transactions.add(new Transaction("1002",3,13,10,-3,Date.valueOf("2020-04-20")));
		try {
			List<Transaction> testTransactions = transactionServiceImpl.getAllTransactions();
			for(int i =0; i<transactions.size();i++) {
				assertEquals(transactions.get(i).getAccountNumber(),testTransactions.get(i).getAccountNumber());
				assertEquals(transactions.get(i).getDateOf(),testTransactions.get(i).getDateOf());
				assertEquals(transactions.get(i).getId(),testTransactions.get(i).getId());
				assertEquals(transactions.get(i).getNewAmount(),testTransactions.get(i).getNewAmount());
				assertEquals(transactions.get(i).getPreviousAmount(),testTransactions.get(i).getPreviousAmount());
			}
		} catch (TransactionException e) {

		}
	}

	@Test
	void testGetAllTransactionsOfACustomer() {
		fail("Not yet implemented");
	}

	@Test
	void testNewTransaction() throws TransactionException {
		transactionServiceImpl.updateCount();
		Transaction transaction = new Transaction("1002",Transaction.getCount(),15,14,-1,Date.valueOf("2021-01-10"));
		transactionServiceImpl.newTransaction(transaction);
		
	}

}
