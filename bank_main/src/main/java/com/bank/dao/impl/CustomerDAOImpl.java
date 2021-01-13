package com.bank.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bank.dao.CustomerDAO;
import com.bank.dao.dbutil.PostresqlConnection;
import com.bank.exception.AccountException;
import com.bank.exception.CustomerException;
import com.bank.model.Account;
import com.bank.model.Customer;
import com.bank.service.AccountService;
import com.bank.service.impl.AccountServiceImpl;

import jdk.internal.net.http.common.Log;

public class CustomerDAOImpl implements CustomerDAO{
	private static Logger log = Logger.getLogger(CustomerDAOImpl.class);
	private AccountService accountServicer = new AccountServiceImpl();
	@Override
	public Customer findCustomerByAccountNumber(String accountNumber) throws CustomerException {
		log.debug("starting the DAO search");
		try {
			Connection connection = PostresqlConnection.getConnection();
			String sql = "Select * from \"BankProject\".customer where accountnumber=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1,accountNumber);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				return new Customer(resultSet.getString("name"),resultSet.getString("accountnumber"),resultSet.getDate("dateOfBirth"),resultSet.getDate("creationDate"),resultSet.getString("type"),resultSet.getDouble("amount"),resultSet.getBoolean("approved"),resultSet.getBoolean("reviewed"));
			}
			
		} catch (ClassNotFoundException |SQLException  e){
			log.trace("Error in the find the customer by account number DAO");
			throw new CustomerException("ERROR IN THE FIND CUSTOMER BY ACCOUNT DAO");
		}
		return null;
	}

	@Override
	public List<Customer> allCustomers() throws CustomerException {
		List<Customer> customers= new ArrayList();
		
		try {
			log.debug("Starting connection for all customers");
			Connection connection = PostresqlConnection.getConnection();
			String sql = "Select * from \"BankProject\".customer";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			log.debug("entering customers into the list");
			while(resultSet.next())
			{
				Customer customer = new Customer(resultSet.getString("name"),resultSet.getString("accountnumber"),resultSet.getDate("\"dateOfBirth\""),resultSet.getDate("\"creationDate\""),resultSet.getString("type"),resultSet.getDouble("amount"),resultSet.getBoolean("approved"),resultSet.getBoolean("reviewed"));
				customers.add(customer);
			}
		}catch(ClassNotFoundException |SQLException  e) {
			log.trace(e.getMessage());
			throw new CustomerException("Error inside get all customers connection");
		}
		
		return customers;
	}

	@Override
	public List<Customer> allUnreviewedCustomers() throws CustomerException {
		List<Customer> customers= new ArrayList();
		
		try {
			log.debug("Starting connection for all unapproved customers");
			Connection connection = PostresqlConnection.getConnection();
			String sql = "select * from \"BankProject\".customer c where c.reviewed = false";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			log.debug("entering customers into the list");
			while(resultSet.next())
			{
				Customer customer = new Customer(resultSet.getString("name"),resultSet.getString("accountnumber"),resultSet.getDate("dateOfBirth"),resultSet.getDate("creationDate"),resultSet.getString("type"),resultSet.getDouble("amount"),resultSet.getBoolean("approved"),resultSet.getBoolean("reviewed"));
				customers.add(customer);
			}
		}catch(ClassNotFoundException |SQLException  e) {
			log.trace(e.getMessage());
			throw new CustomerException("Error inside customers unapproved connection");
		}
		
		return customers;
	}

	@Override
	public void  changeApprovealStatusOfCustomer(Customer customer, boolean approved)  throws CustomerException {
		try {
			log.debug("Starting connection for the approval process");
			Connection connection = PostresqlConnection.getConnection();
			String sql ="UPDATE \"BankProject\".customer set approved = ?, reviewed = true where accountnumber = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setBoolean(1, approved);
			preparedStatement.setString(2, customer.getAccountNumber());
			int output = preparedStatement.executeUpdate();
			//log.debug(output);
			if(output!=0) {
				customer.setApproved(approved);
				customer.setReviewed(true);
				log.info("Successefull update of "+ customer.getName()+" account.");
			} else {
				throw new CustomerException("Error in update");
			}
			
		}catch(ClassNotFoundException|SQLException e) {
			log.debug(e);
			throw new CustomerException("ERROR INSIDE THE CONNECTOR FOR THE APPROVAL PROCESS");
			//log.info("PLEASE CONTACT IT THERE IS AN ERROR IN THE APPROVAL SECTION");
		}
		
	}

	@Override
	public void insertCustomer(Customer customer) throws CustomerException {
		log.debug("Inserting customer from the DAO");
		Connection connection;
		try {
			connection = PostresqlConnection.getConnection();
			accountServicer.insertAccount((Account)customer);
			String sql = "INSERT INTO \"BankProject\".customer (name,accountnumber,\"dateOfBirth\",\"creationDate\",type,amount,approved) VALUES(?,?,?,?,?,?,false);";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, customer.getName());
			preparedStatement.setString(2,	customer.getAccountNumber());
			preparedStatement.setDate(3, customer.getDateOfBirth());
			preparedStatement.setDate(4, customer.getCreationDate());
			preparedStatement.setString(5, customer.getType());
			preparedStatement.setDouble(6, customer.getAmount());
			
			if(preparedStatement.executeUpdate()!=0) {
				log.info("Your successfully Registered");
			}
			else {
				
				throw new CustomerException("An error occured please contact customer service");
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			log.trace("ERROR INSIDE OF DAO FOR INSERT CUSTOMER");
			log.trace(e);
			throw new CustomerException("An error occured please contact customer service");
		}catch( AccountException e) {
			throw new CustomerException(e.getMessage());
		}

		
	}

	@Override
	public String makeAccountNumber() throws CustomerException {
		log.debug("finding the next accountNumber");
		try {
			Connection connection = PostresqlConnection.getConnection();
			String sql = "Select MAX(accountnumber) from \"BankProject\".customer";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			String output =null;
			while(resultSet.next()) {
				output =  resultSet.getString("max");
			}
			double outputIntoDouble = Double.parseDouble(output);
			outputIntoDouble++;
			output = String.valueOf(Double.valueOf(outputIntoDouble).intValue());
			return output;
		}catch (ClassNotFoundException | SQLException e) {
			log.trace(e);
			throw new CustomerException("ERROR INSIDE THE MAKE ACCOUNT NUMBER METHOD");
		}
		
	}

	@Override
	public void updateCustomerAmount(Customer customer) throws CustomerException {
		log.debug("Starting the update customer amount process");
		try {
			Connection connection = PostresqlConnection.getConnection();
			String sql = "UPDATE \"BankProject\".customer\r\n"
					+ "	SET amount=?\r\n"
					+ "	WHERE accountnumber=?;";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setDouble(1, customer.getAmount());
			preparedStatement.setString(2, customer.getAccountNumber());
			if(preparedStatement.executeUpdate()!=0) {
				return;
			}
			else {
				
				throw new CustomerException("An error occured please contact customer service");
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			log.trace(e.getMessage());
			throw new CustomerException("Error at updateCustomerAmountDAO");
		}
		
		
	}

}
