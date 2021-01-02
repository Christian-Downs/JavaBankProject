package com.bank.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.bank.dao.EmployeeFinderDAO;
import com.bank.dao.dbutil.PostresqlConnection;
import com.bank.exception.EmployeeException;
import com.bank.model.Account;
import com.bank.model.Employee;

public class EmployeeFinderDAOImpl implements EmployeeFinderDAO{
	@Override
	public List<Employee> getAllEmployees() throws EmployeeException{
		List<Employee> employeeList = new ArrayList();
		try (Connection connection = PostresqlConnection.getConnection()){
			String sql = "select * from \"BankProject\".employee";
			PreparedStatement preparedStatment = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatment.executeQuery();
			while(resultSet.next()) {
				Employee employee = new Employee(resultSet.getString("accountNumber"),resultSet.getString("accessLevel"),resultSet.getString("name"),resultSet.getDate("dateOfBirth"),resultSet.getDate("startDate"));
				employeeList.add(employee);		
			}
			if(employeeList.size()==0) {
				throw new EmployeeException("No Employees in the DB yet");
			}
		}
		catch (ClassNotFoundException |SQLException  e) {
			System.out.println("There was an error connecting to the server");
			System.out.println(e.getMessage());
		}
		return employeeList;
	}
	@Override
	public Employee findEmployeeByAccountNumber(String accountNumber) throws EmployeeException {
		try{
			return getAllEmployees().stream().filter(e->e.getAccountNumber().equals(accountNumber)).collect(Collectors.toList()).get(0);	}
		}
	catch(EmployeeException e) {
		
	}
		
}
