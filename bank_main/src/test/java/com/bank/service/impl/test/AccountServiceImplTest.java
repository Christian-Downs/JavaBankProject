package com.bank.service.impl.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.bank.exception.AccountException;
import com.bank.service.AccountService;
import com.bank.service.impl.AccountServiceImpl;

class AccountServiceImplTest {
	private AccountService accountServicer = new AccountServiceImpl();


	@Test
	void testDoesAccountExists() {
		try {
			assertTrue(accountServicer.doesAccountExists("1"));
		} catch (AccountException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		}
	}

}
