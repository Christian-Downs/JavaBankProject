package com.bank.service;

import java.util.List;

import com.bank.exception.CustomerException;
import com.bank.model.Customer;

public interface CustomerService {
	public Customer findCustomerByAccountNumber(String accountNumber) throws CustomerException;
	public void approveCustomer(Customer customer) throws CustomerException;
	public List<Customer> allCustomers() throws CustomerException;
	public List<Customer> allUnapprovedCustomers() throws CustomerException;
}