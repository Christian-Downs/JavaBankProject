package com.bank.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.bank.dao.AccountSearchDAO;
import com.bank.dao.impl.AccountSearchDAOImpl;
import com.bank.exception.AccountException;
import com.bank.model.Account;
import com.bank.service.AccountSearchService;

public class AccountSearchServiceImpl implements AccountSearchService {
	private AccountSearchDAO accountSearcher = new AccountSearchDAOImpl();
	@Override
	public Account getAccountByAccountNumber(String accountNumber) throws AccountException {
		List<Account> accounts = accountSearcher.getAllAccounts();
		try{
			Account currentAccount = accounts.stream().filter(a->(accountNumber.equals(a.getAccountNumber()))).collect(Collectors.toList()).get(0);
			return currentAccount;
		}
		catch(IndexOutOfBoundsException e) {
			return null;
		}
		
	}

	@Override
	public List<Account> getAllAccounts() throws AccountException {
		return accountSearcher.getAllAccounts();
	}

}
