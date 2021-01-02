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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Account> getAllAccounts() throws AccountException {
		List<Account> accountList = new ArrayList<>();
		try (Connection connection = PostresqlConnection.getConnection()){
			String sql = "select * from \"BankProject\".account";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultset = preparedStatement.executeQuery();
			while(resultset.next()) {
				Account account = new Account(resultset.getString("number"),resultset.getString("password"), resultset.getBoolean("approved"));
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
	
}
