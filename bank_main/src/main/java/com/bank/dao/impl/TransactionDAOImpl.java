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
import com.bank.exception.CustomerException;
import com.bank.exception.TransactionException;
import com.bank.model.Customer;
import com.bank.model.Transaction;
import com.bank.service.CustomerService;
import com.bank.service.impl.CustomerServiceImpl;

import jdk.internal.net.http.common.Log;

public class TransactionDAOImpl implements TransactionDAO {
	private static Logger log = Logger.getLogger(TransactionDAOImpl.class);
	@Override
	public void updateCount() throws TransactionException {
		try {
			String sql = "select count(id) from \"BankProject\".transaction";
			Connection connection = PostresqlConnection.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			int count = resultSet.getInt("count");
			Transaction.setCount(count);
		}catch(ClassNotFoundException| SQLException e) {
			log.trace("ERROR INSIDE COUNT FOR TRANSACTION DAO");
		}
		
	}
	@Override
	public Transaction getTransactionById(int id) throws TransactionException {
		try {
			String sql = "select * from \"BankProject\".transaction where id = ?";
			Connection connection = PostresqlConnection.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				return (new Transaction(resultSet.getString("accountnumber"),resultSet.getInt("id"),resultSet.getDouble("previousamount"),
						resultSet.getDouble("newamount"),resultSet.getDouble("transactionamount"),resultSet.getDate("date"),resultSet.getString("type")));
			}
			throw new TransactionException ("Account not found");
			
		}catch(ClassNotFoundException| SQLException e) {
			log.trace("ERROR INSIDE GET ALL FOR TRANSACTION DAO");
			throw new TransactionException("Had an error finding the account");
		}

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
				transactions.add(new Transaction(resultSet.getString("accountnumber"),resultSet.getInt("id"),resultSet.getDouble("previousamount"),
						resultSet.getDouble("newamount"),resultSet.getDouble("transactionamount"),resultSet.getDate("date"),resultSet.getString("type")));
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
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				transactions.add(new Transaction(resultSet.getString("accountnumber"),resultSet.getInt("id"),resultSet.getDouble("previousamount"),
						resultSet.getDouble("newamount"),resultSet.getDouble("transactionamount"),resultSet.getDate("date"),
						resultSet.getString("type")));
			}
			return transactions;
		}catch(ClassNotFoundException| SQLException e) {
			log.trace(e);
			throw new TransactionException("ERROR INSIDE THE GET ALL TRANSACTION DAO");
		}
	}

	@Override
	public void newTransaction(Transaction transaction,Customer customer) throws TransactionException {
		try {
			CustomerService customerServicer = new CustomerServiceImpl();
			customerServicer.updateCustomerAmount(customer, transaction.getTransactionAmount(), transaction.getType());
			
			String sqlForTransaction = "INSERT INTO \"BankProject\".\"transaction\" (accountnumber, previousamount, newamount, transactionamount, \"date\",type) VALUES(?, ?, ?, ?, ?,?);";
			Connection connection = PostresqlConnection.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sqlForTransaction);
			preparedStatement.setString(1, transaction.getAccountNumber());
			preparedStatement.setFloat(2, Float.parseFloat(transaction.getPreviousAmount() +""));
			preparedStatement.setFloat(3, Float.parseFloat(transaction.getNewAmount()+""));
			preparedStatement.setFloat(4, Float.parseFloat(transaction.getTransactionAmount()+""));
			preparedStatement.setDate(5, transaction.getDateOf());
			preparedStatement.setString(6, transaction.getType());
			if(preparedStatement.executeUpdate()!=0) {
				log.debug("Transaction successfully added");
			}
			else {
				throw new TransactionException("Transaction unsuccessfully added");
			}
			

		}catch (ClassNotFoundException| SQLException e){
			
			log.trace(e);
			throw new TransactionException("ERROR INSERTING TRANSACTION");
		} catch(CustomerException e) {
			
		}
	}



}
