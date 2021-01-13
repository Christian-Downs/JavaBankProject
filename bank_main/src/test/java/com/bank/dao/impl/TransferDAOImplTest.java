package com.bank.dao.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.bank.dao.TransferDAO;
import com.bank.exception.CustomerException;
import com.bank.exception.TransferException;
import com.bank.model.Customer;
import com.bank.model.Transfer;
import com.bank.service.CustomerService;
import com.bank.service.impl.CustomerServiceImpl;

class TransferDAOImplTest {
	@Test
	void testGetTransferByID() {
		TransferDAO transferDAO = new TransferDAOImpl();
		Transfer testerTransfer = new Transfer("1001","1002",5,Date.valueOf("2021-01-12"),false,2,"TestAccount");
		try {
			Transfer outputedTransfer = transferDAO.getTransferByID(2);
			assertEquals(testerTransfer.getAmount(),outputedTransfer.getAmount());
			assertEquals(testerTransfer.getReceiverAccountNumber(),outputedTransfer.getReceiverAccountNumber());
			assertEquals(testerTransfer.getSenderAccountNumber(),outputedTransfer.getSenderAccountNumber());
			assertEquals(testerTransfer.getDateOfCreation(),outputedTransfer.getDateOfCreation());
		} catch (TransferException e) {
			// TODO Auto-generated catch block
			fail(e);
		}
	}

	@Test
	void testGetUnapprovedTransfersForAnAccount() {
		TransferDAO transferDAO = new TransferDAOImpl();
		try {
			List<Transfer> transfers = transferDAO.getUnapprovedTransfersForAnAccount("1001");
			for(Transfer t : transfers) {
				assertEquals(t.getAmount(),15);
				assertEquals(t.getSenderAccountNumber(),"1002");
				
			}
		} catch (TransferException e) {
			// TODO Auto-generated catch block
			fail(e);
		}
		
	}

//	 insert test has to be commented out so that random transfers don't happen while testing
//	@Test 
//	void testNewTransfer() {
//		CustomerService customerService = new CustomerServiceImpl();
//		Transfer transferToInsert;
//		try {
//			transferToInsert = new Transfer(customerService.findCustomerByAccountNumber("1001"),"1002",12);
//			TransferDAO transferDAO = new TransferDAOImpl();
//			try {
//				transferDAO.newTransfer(transferToInsert);
//			} catch (TransferException e) {
//				// TODO Auto-generated catch block
//				fail(e);
//			}
//		} catch (CustomerException e1) {
//			// TODO Auto-generated catch block
//			fail(e1);
//		}
//
//	}

	@Test
	void testApproveTransfer() {
		TransferDAO transferDAO = new TransferDAOImpl();
		try {
			assertTrue(transferDAO.approveTransfer(2, "1002"));
		} catch (TransferException e) {
			// TODO Auto-generated catch block
			fail(e);
		}
	}

	@Test
	void testGetNumberOfUnapporvedTransfers() {
		TransferDAO transferDAO = new TransferDAOImpl();
		try {
			Customer customerToTestWith = new Customer();
			customerToTestWith.setAccountNumber("1001");
			assertEquals(1,transferDAO.getNumberOfUnapporvedTransfers(customerToTestWith));
		} catch(Exception e) {
			fail(e);
		}
	}

}
