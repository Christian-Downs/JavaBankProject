package com.bank.service.impl.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.bank.exception.AccountException;
import com.bank.service.AccountService;
import com.bank.service.impl.AccountServiceImpl;

class AccountServiceImplTest {
	private AccountService accountServicer = new AccountServiceImpl();
	@Test
	void testGetAccountByAccountNumber() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAllAccounts() {
		fail("Not yet implemented");
	}

	@Test
	void testInsertAccount() {
		fail("Not yet implemented");
	}

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

	@Test
	void testObject() {
		fail("Not yet implemented");
	}

	@Test
	void testGetClass() {
		fail("Not yet implemented");
	}

	@Test
	void testHashCode() {
		fail("Not yet implemented");
	}

	@Test
	void testEquals() {
		fail("Not yet implemented");
	}

	@Test
	void testClone() {
		fail("Not yet implemented");
	}

	@Test
	void testToString() {
		fail("Not yet implemented");
	}

	@Test
	void testNotify() {
		fail("Not yet implemented");
	}

	@Test
	void testNotifyAll() {
		fail("Not yet implemented");
	}

	@Test
	void testWait() {
		fail("Not yet implemented");
	}

	@Test
	void testWaitLong() {
		fail("Not yet implemented");
	}

	@Test
	void testWaitLongInt() {
		fail("Not yet implemented");
	}

	@Test
	void testFinalize() {
		fail("Not yet implemented");
	}

}
