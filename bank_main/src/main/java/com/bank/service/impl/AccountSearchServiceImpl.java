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
		System.out.println("AccountSearchService within getAccountNumber with accountNumber = " + accountNumber);
		return accountSearcher.getAccountByAccountNumber(accountNumber);
	}

	@Override
	public List<Account> getAllAccounts() throws AccountException {
		return accountSearcher.getAllAccounts();
	}

}
