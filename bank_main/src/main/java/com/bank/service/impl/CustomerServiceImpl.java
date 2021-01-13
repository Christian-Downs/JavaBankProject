package com.bank.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.bank.dao.CustomerDAO;
import com.bank.dao.impl.CustomerDAOImpl;
import com.bank.exception.CustomerException;
import com.bank.model.Customer;
import com.bank.service.CustomerService;

public class CustomerServiceImpl implements CustomerService{
	private CustomerDAO customerDAO = new CustomerDAOImpl();
	private static Logger log = Logger.getLogger(CustomerServiceImpl.class);
	@Override
	public Customer findCustomerByAccountNumber(String accountNumber) throws CustomerException {
		
		return customerDAO.findCustomerByAccountNumber(accountNumber);
	}

	@Override
	public List<Customer> allCustomers() throws CustomerException {
		log.debug("inside the service customer  all");
		return customerDAO.allCustomers();
	}

	@Override
	public List<Customer> allUnreviewedCustomers() throws CustomerException {
		log.debug("getting all int unapproved customers in customer  service impl");
		return customerDAO.allUnreviewedCustomers();
	}

	@Override
	public void changeApprovealStatusOfCustomer(Customer customer,boolean approved) throws CustomerException {
		customerDAO.changeApprovealStatusOfCustomer(customer,approved);
	}

	@Override
	public void insertCustomer(Customer customer) throws CustomerException {
		customerDAO.insertCustomer(customer);
		
	}

	@Override
	public String makeAccountNumber() throws CustomerException {
		return customerDAO.makeAccountNumber();
	}

	@Override
	public void updateCustomerAmount(Customer customer, double amountToChangeItBy,String type) throws CustomerException {
		if(type.equals("deposit"))
			customer.setAmount(customer.getAmount()+amountToChangeItBy);
		else if(type.equals("withdraw"))
			customer.setAmount(customer.getAmount()-amountToChangeItBy);
		else
			throw new CustomerException("The type of transaction doesn't exist");
		customerDAO.updateCustomerAmount(customer);
	}

}
