package com.bank.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.bank.dao.AccountDAO;
import com.bank.dao.impl.AccountDAOImpl;
import com.bank.exception.AccountException;
import com.bank.model.Account;
import com.bank.service.AccountService;


public class AccountServiceImpl implements AccountService {
	private AccountDAO accountSearcher = new AccountDAOImpl();
	private static Logger log = Logger.getLogger(AccountServiceImpl.class);
	@Override
	public Account getAccountByAccountNumber(String accountNumber) throws AccountException {
		log.debug("AccountSearchService within getAccountNumber with accountNumber = " + accountNumber);
		return accountSearcher.getAccountByAccountNumber(accountNumber);
	}

	@Override
	public List<Account> getAllAccounts() throws AccountException {
		log.debug("getting all accounts");
		return accountSearcher.getAllAccounts();
	}

}
