package com.bank.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.bank.dao.EmployeeDAO;
import com.bank.dao.impl.EmployeeDAOImpl;
import com.bank.exception.EmployeeException;
import com.bank.model.Employee;
import com.bank.service.EmployeeService;

public class EmployeeServiceImpl implements EmployeeService {
	private EmployeeDAO employee = new EmployeeDAOImpl();
	private static Logger log = Logger.getLogger(EmployeeServiceImpl.class);
	@Override
	public List<Employee> getAllEmployees() throws EmployeeException {
		log.debug("getting all the employees");
		return employee.getAllEmployees();
	}

	@Override
	public Employee findEmployeeByAccountNumber(String accountNumber) throws EmployeeException {
		log.debug("Inside the employee service searcher by account number = " + accountNumber);
		return employee.findEmployeeByAccountNumber(accountNumber);
	}

}
