package com.bank.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bank.dao.AccountDAO;
import com.bank.dao.dbutil.PostresqlConnection;
import com.bank.exception.AccountException;
import com.bank.model.Account;

public class AccountDAOImpl implements AccountDAO{
	private static Logger log = Logger.getLogger(AccountDAOImpl.class);
	@Override
	public Account getAccountByAccountNumber(String accountNumber) throws AccountException {
		Account account = null;
		
		try(Connection connection = PostresqlConnection.getConnection()){
			String sql = "select * from \"BankProject\".account where number=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, accountNumber);
			ResultSet resultSet = preparedStatement.executeQuery();
			log.debug("Query Excecuted");
			if(resultSet.next())
			{
				log.debug("If in DAO");
				account = new Account(resultSet.getString("number"),resultSet.getString("password"));
			} else {
				log.debug("else in dao");
				throw new AccountException("No account under the account number " + accountNumber);
			}
			if(Double.parseDouble(account.getAccountNumber())>1000){
				account.setAccountType("Customer");
			} else {
				account.setAccountType("Employee");
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			log.trace("exception in DAO");
			log.trace(e.getMessage());
			throw new AccountException("Internal error occured contact bank");
		}
		return account;
	}

	@Override
	public List<Account> getAllAccounts() throws AccountException {
		List<Account> accountList = new ArrayList<>();
		try (Connection connection = PostresqlConnection.getConnection()){
			String sql = "select * from \"BankProject\".account";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Account account = new Account(resultSet.getString("number"),resultSet.getString("password"));
				if(Double.parseDouble(account.getAccountNumber())>=1000)
					account.setAccountType("customer");
				else
					account.setAccountType("employee");
				accountList.add(account);
			}
			if(accountList.size()==0) {
				throw new AccountException("No Accounts in the DB yet");
			}
			
		} catch(ClassNotFoundException | SQLException e) {
			log.trace(e.getMessage());
			throw new AccountException("Internal error occured");
		}
		return accountList;
	}
	
	@Override
	public List<Account> getAllUnapproved() throws AccountException {
		List<Account> accountList = new ArrayList<>();
		try(Connection connection = PostresqlConnection.getConnection()){
			String sql = "select * from \"BankProject\".account where account = false";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Account account = new Account(resultSet.getString("number"),resultSet.getString("password"));
				accountList.add(account);
			}
			return accountList;
		} catch (ClassNotFoundException | SQLException e) {
			throw new AccountException("Internal error occured fetching accounts");
		}
		
	}

	@Override
	public void insertAccount(Account account) throws AccountException {
		
			Connection connection;
			try {
				connection = PostresqlConnection.getConnection();
				String sql = "INSERT INTO \"BankProject\".account (number,password) VALUES(?,?);";
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, account.getAccountNumber());
				preparedStatement.setString(2, account.getPassword());
				if(preparedStatement.executeUpdate()!=0) {
					log.info("Account successfully added");
				}
				else {
					throw new AccountException("Account unsuccessfully added");
				}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				log.trace(e);
				throw new AccountException("ERROR INSIDE ACCOUNT ADDER DAO");
			}
			

		
	}

	@Override
	public boolean doesAccountExists(String accountNumber) throws AccountException {
		try {
			Connection connection = PostresqlConnection.getConnection();
			String sql = "select COUNT(number) from \"BankProject\".account where number =?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, accountNumber);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
				if(resultSet.getInt("count")>0)
					return true;
				else
					return false;
			return false;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			log.trace(e.getMessage());
			throw new AccountException("ERROR INSIDE THE DOES ACCOUNT EXIST METHOD");
		}
	}
	
}
