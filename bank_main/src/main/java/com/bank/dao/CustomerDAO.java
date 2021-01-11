package com.bank.dao;

import java.util.List;

import com.bank.exception.CustomerException;
import com.bank.model.Customer;

public interface CustomerDAO {
	//GET
	public Customer findCustomerByAccountNumber(String accountNumber) throws CustomerException;
	public List<Customer> allCustomers() throws CustomerException;
	public List<Customer> allUnapprovedCustomers() throws CustomerException;
	
	//POST
	public void approveCustomer(Customer customer) throws CustomerException;
}
