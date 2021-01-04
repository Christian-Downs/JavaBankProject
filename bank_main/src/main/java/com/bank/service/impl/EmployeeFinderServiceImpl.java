package com.bank.service.impl;

import java.util.List;

import com.bank.dao.EmployeeFinderDAO;
import com.bank.dao.impl.EmployeeFinderDAOImpl;
import com.bank.exception.EmployeeException;
import com.bank.model.Employee;
import com.bank.service.EmployeeFinderService;

public class EmployeeFinderServiceImpl implements EmployeeFinderService {
	private EmployeeFinderDAO employeeFinder = new EmployeeFinderDAOImpl();
	@Override
	public List<Employee> getAllEmployees() throws EmployeeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee findEmployeeByAccountNumber(String accountNumber) throws EmployeeException {
		System.out.println("Inside the employee service searcher by account number = " + accountNumber);
		return employeeFinder.findEmployeeByAccountNumber(accountNumber);
	}

}
