package com.bank.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.bank.dao.EmployeeDAO;
import com.bank.dao.dbutil.PostresqlConnection;
import com.bank.exception.AccountException;
import com.bank.exception.EmployeeException;
import com.bank.main.BankMain;
import com.bank.model.Account;
import com.bank.model.Employee;

public class EmployeeDAOImpl implements EmployeeDAO{
	private static Logger log = Logger.getLogger(EmployeeDAOImpl.class);
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
		Employee employee = null;
		
		try(Connection connection = PostresqlConnection.getConnection()){
			String sql = "select * from \"BankProject\".employee where \"accountNumber\"=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, accountNumber);
			ResultSet resultSet = preparedStatement.executeQuery();
			log.debug("Query Excecuted");
			if(resultSet.next())
			{
				log.debug("If in DAO");
				employee = new Employee(resultSet.getString("accountNumber"),resultSet.getString("accessLevel"),resultSet.getString("name"),resultSet.getDate("dateOfBirth"),resultSet.getDate("startDate"),resultSet.getBoolean("stillhired"));
				
			} else {
				log.debug("else in dao");
				throw new EmployeeException("No employee under the account number " + accountNumber);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			log.trace("exception in DAO");
			log.trace(e.getMessage());
			throw new EmployeeException("Internal error occured contact bank");
		}
		return employee;
	}
		
	
}