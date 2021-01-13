package com.bank.service.impl.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.bank.exception.CustomerException;
import com.bank.exception.TransactionException;
import com.bank.model.Transaction;
import com.bank.service.CustomerService;
import com.bank.service.TransactionService;
import com.bank.service.impl.CustomerServiceImpl;
import com.bank.service.impl.TransactionServiceImpl;

class TransactionServiceImplTest {
	TransactionService  transactionServiceImpl = new TransactionServiceImpl();
	CustomerService customerServicer = new CustomerServiceImpl();
	@Test
	void testGetTransactionById() {
		try {
			Transaction transaction = transactionServiceImpl.getTransactionById(11);
			assertEquals("1001",transaction.getAccountNumber());
			assertEquals(45,transaction.getTransactionAmount());
		} catch (TransactionException e) {
			// TODO Auto-generated catch block
			fail(e);
		}
		
	}

	@Test
	void testGetAllTransactions()/*doesn't apply anymore*/ {
		List<Transaction> transactions = new ArrayList();
		
		
		transactions.add(new Transaction("1002",3,13,10,-3,Date.valueOf("2020-04-20"),"withdraw"));
		transactions.add(new Transaction("1002",4,15,14,-1,Date.valueOf("2021-01-10"),"withdraw"));
		transactions.add(new Transaction("1001",8,15,14,-1,Date.valueOf("2021-01-10"),"withdraw"));
		transactions.add(new Transaction("1001",9,169,240,71,Date.valueOf("2021-01-12"),"deposit"));
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
//	Commented out because creates a transaction each time
//	@Test
//	void testNewTransaction() throws TransactionException {
//		transactionServiceImpl.updateCount();
//		Transaction transaction = new Transaction("1001",Transaction.getCount(),15,14,-1,Date.valueOf("2021-01-10"),"withdraw");
//		try {
//			transactionServiceImpl.newTransaction(transaction, customerServicer.findCustomerByAccountNumber("1001"));
//		} catch (TransactionException | CustomerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}

}
