package com.bank.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bank.dao.AccountSearchDAO;
import com.bank.dao.dbutil.PostresqlConnection;
import com.bank.exception.AccountException;
import com.bank.model.Account;

public class AccountSearchDAOImpl implements AccountSearchDAO{

	@Override
	public Account getAccountByAccountNumber(String accountNumber) throws AccountException {
		Account account = null;
		
		try(Connection connection = PostresqlConnection.getConnection()){
			String sql = "select * from \"BankProject\".account where number=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, accountNumber);
			ResultSet resultSet = preparedStatement.executeQuery();
			System.out.println("Query Excecuted");
			if(resultSet.next())
			{
				System.out.println("If in DAO");
				account = new Account(resultSet.getString("number"),resultSet.getString("password"),resultSet.getBoolean("approved"));
			} else {
				System.out.println("else in dao");
				throw new AccountException("No account under the account number " + accountNumber);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("exception in DAO");
			System.out.println(e.getMessage());
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
				Account account = new Account(resultSet.getString("number"),resultSet.getString("password"), resultSet.getBoolean("approved"));
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
			System.out.println(e);
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
				Account account = new Account(resultSet.getString("number"),resultSet.getString("password"),resultSet.getBoolean("approved"));
				accountList.add(account);
			}
			return accountList;
		} catch (ClassNotFoundException | SQLException e) {
			throw new AccountException("Internal error occured fetching accounts");
		}
		
	}
	
}
