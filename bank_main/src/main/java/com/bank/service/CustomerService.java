package com.bank.service;

import java.util.List;

import com.bank.exception.CustomerException;
import com.bank.model.Customer;

public interface CustomerService {	
	public String makeAccountNumber() throws CustomerException;
	public Customer findCustomerByAccountNumber(String accountNumber) throws CustomerException;
	public void changeApprovealStatusOfCustomer(Customer customer,boolean approved) throws CustomerException;
	public List<Customer> allCustomers() throws CustomerException;
	public List<Customer> allUnreviewedCustomers() throws CustomerException;
	public void insertCustomer(Customer customer) throws CustomerException;
	public void updateCustomerAmount(Customer customer, double amountToChangeItBy, String type) throws CustomerException;

}
