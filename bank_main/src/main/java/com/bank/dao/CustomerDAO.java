package com.bank.dao;

import java.util.List;

import com.bank.exception.CustomerException;
import com.bank.model.Customer;

public interface CustomerDAO {
	//GET	
	public String makeAccountNumber() throws CustomerException;
	public Customer findCustomerByAccountNumber(String accountNumber) throws CustomerException;
	public List<Customer> allCustomers() throws CustomerException;
	public List<Customer> allUnreviewedCustomers() throws CustomerException;

	
	//POST
	public void changeApprovealStatusOfCustomer(Customer customer, boolean approved) throws CustomerException;
	public void insertCustomer(Customer customer) throws CustomerException;
	public void updateCustomerAmount(Customer customer) throws CustomerException;
}
