package com.bank.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bank.dao.TransactionDAO;
import com.bank.dao.dbutil.PostresqlConnection;
import com.bank.exception.TransactionException;
import com.bank.model.Customer;
import com.bank.model.Transaction;

import jdk.internal.net.http.common.Log;

public class TransactionDAOImpl implements TransactionDAO {
	private static Logger log = Logger.getLogger(TransactionDAOImpl.class);
	@Override
	public void updateCount() throws TransactionException {
		try {
			String sql = "select count(id) from \"BankProject\".transaction";
			Connection connection = PostresqlConnection.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery(sql);
			int count = resultSet.getInt("count");
			Transaction.setCount(count);
		}catch(ClassNotFoundException| SQLException e) {
			log.trace("ERROR INSIDE COUNT FOR TRANSACTION DAO");
		}
		
	}
	@Override
	public Transaction getTransactionById(int id) throws TransactionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Transaction> getAllTransactions() throws TransactionException {
		List<Transaction> transactions = new ArrayList();
		try {
			String sql = "select * from \"BankProject\".transaction";
			Connection connection = PostresqlConnection.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				transactions.add(new Transaction(resultSet.getString("accountnumber"),resultSet.getInt("id"),resultSet.getDouble("previousamount"),resultSet.getDouble("newamount"),resultSet.getDouble("transactionamount"),resultSet.getDate("date")));
			}
			log.debug("all transactions given");
		}catch(ClassNotFoundException| SQLException e) {
			log.trace("ERROR INSIDE GET ALL FOR TRANSACTION DAO");
		}
		return transactions;
	}

	@Override
	public List<Transaction> getAllTransactionsOfACustomer(Customer customer) throws TransactionException {
		List<Transaction> transactions = new ArrayList();
		try {
			String sql = "select * from \"BankProject\".transaction where accountnumber = ?";
			Connection connection = PostresqlConnection.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, customer.getAccountNumber());
			ResultSet resultSet = preparedStatement.executeQuery(sql);
			while(resultSet.next()) {
				transactions.add(new Transaction(resultSet.getString("accountnumber"),resultSet.getInt("id"),resultSet.getDouble("previousamount"),resultSet.getDouble("newamount"),resultSet.getDouble("transactionamount"),resultSet.getDate("date")));
			}
			return transactions;
		}catch(ClassNotFoundException| SQLException e) {
			throw new TransactionException("ERROR INSIDE THE GET ALL TRANSACTION DAO");
		}
	}

	@Override
	public void newTransaction(Transaction transaction) throws TransactionException {
		try {
			String sql = "INSERT INTO \"BankProject\".\"transaction\" (accountnumber, previousamount, newamount, transactionamount, \"date\") VALUES(?, (select count(id)+1 from \"BankProject\".\"transaction\"), ?, ?, ?, ?);";
			Connection connection = PostresqlConnection.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, transaction.getAccountNumber());
			preparedStatement.setFloat(2, Float.parseFloat(transaction.getPreviousAmount() +""));
			preparedStatement.setFloat(3, Float.parseFloat(transaction.getNewAmount()+""));
			preparedStatement.setFloat(4, Float.parseFloat(transaction.getTransactionAmount()+""));
			preparedStatement.setDate(5, transaction.getDateOf());
			if(preparedStatement.executeUpdate()!=0) {
				log.info("Transaction successfully added");
			}
			else {
				throw new TransactionException("Transaction unsuccessfully added");
			}
			
		}catch (ClassNotFoundException| SQLException e){
			
			log.trace(e);
			throw new TransactionException("ERROR INSERTING TRANSACTION");
		}
	}
	@Override
	public void newTransfer(String accountNumber) throws TransactionException {
		
	}



}
