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
import com.bank.exception.CustomerException;
import com.bank.model.Customer;

import jdk.internal.net.http.common.Log;

public class CustomerDAOImpl implements CustomerDAO{
	private static Logger log = Logger.getLogger(CustomerDAOImpl.class);
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
				return new Customer(resultSet.getString("name"),resultSet.getString("accountnumber"),resultSet.getDate("dateOfBirth"),resultSet.getDate("creationDate"),resultSet.getString("type"),resultSet.getDouble("amount"),resultSet.getBoolean("approved"));
			}
			
		} catch (ClassNotFoundException |SQLException  e){
			
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
				Customer customer = new Customer(resultSet.getString("name"),resultSet.getString("accountnumber"),resultSet.getDate("\"dateOfBirth\""),resultSet.getDate("\"creationDate\""),resultSet.getString("type"),resultSet.getDouble("amount"),resultSet.getBoolean("approved"));
				customers.add(customer);
			}
		}catch(ClassNotFoundException |SQLException  e) {
			log.trace(e.getMessage());
			throw new CustomerException("Error inside get all customers connection");
		}
		
		return customers;
	}

	@Override
	public List<Customer> allUnapprovedCustomers() throws CustomerException {
		List<Customer> customers= new ArrayList();
		
		try {
			log.debug("Starting connection for all unapproved customers");
			Connection connection = PostresqlConnection.getConnection();
			String sql = "select * from \"BankProject\".customer c where c.approved = false;";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			log.debug("entering customers into the list");
			while(resultSet.next())
			{
				Customer customer = new Customer(resultSet.getString("name"),resultSet.getString("accountnumber"),resultSet.getDate("dateOfBirth"),resultSet.getDate("creationDate"),resultSet.getString("type"),resultSet.getDouble("amount"),resultSet.getBoolean("approved"));
				customers.add(customer);
			}
		}catch(ClassNotFoundException |SQLException  e) {
			log.trace(e.getMessage());
			throw new CustomerException("Error inside customers unapproved connection");
		}
		
		return customers;
	}

	@Override
	public void approveCustomer(Customer customer) throws CustomerException {
		try {
			log.debug("Starting connection for the approval process");
			Connection connection = PostresqlConnection.getConnection();
			String sql ="UPDATE \"BankProject\".customer set approved = true where accountnumber = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, customer.getAccountNumber());
			int output = preparedStatement.executeUpdate();
			log.debug(output);
			if(output!=0) {
				customer.setApproved(true);
				log.info("Successefull update of "+ customer.getName()+" account.");
			} else {
				throw new CustomerException("Error in update");
			}
			
		}catch(ClassNotFoundException|SQLException e) {
			log.debug(e);
			log.debug("ERROR INSIDE THE CONNECTOR FOR THE APPROVAL PROCESS");
			log.info("PLEASE CONTACT IT THERE IS AN ERROR IN THE APPROVAL SECTION");
		}
		
	}

}
