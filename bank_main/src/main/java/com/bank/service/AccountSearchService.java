package com.bank.service;

import java.util.List;

import com.bank.exception.AccountException;
import com.bank.model.Account;

public interface AccountSearchService {
	public Account getAccountByAccountNumber(String accountNumber) throws AccountException;
	public List<Account> getAllAccounts() throws AccountException;
}
