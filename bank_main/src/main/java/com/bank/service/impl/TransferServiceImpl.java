package com.bank.service.impl;

import java.util.List;

import com.bank.dao.TransferDAO;
import com.bank.dao.impl.TransferDAOImpl;
import com.bank.exception.TransferException;
import com.bank.model.Customer;
import com.bank.model.Transfer;
import com.bank.service.TransferService;

public class TransferServiceImpl implements TransferService{
	private TransferDAO transferDAO = new TransferDAOImpl();
	
	@Override
	public int getNumberOfUnapprovedTransfers(Customer customer) throws TransferException {
		return transferDAO.getNumberOfUnapporvedTransfers(customer);
	}

	@Override
	public Transfer getTransferByID(int id) throws TransferException {
		return transferDAO.getTransferByID(id);
	}

	@Override
	public List<Transfer> getUnapprovedTransfersForAnAccount(String accountNumberOfReceiver) throws TransferException {
		return transferDAO.getUnapprovedTransfersForAnAccount(accountNumberOfReceiver);
	}

	@Override
	public void newTransfer(Transfer transfer) throws TransferException {
		transferDAO.newTransfer(transfer);
	}

	@Override
	public void approveTransfer(int id, String accountNumberOfTheReceiver) throws TransferException {
		transferDAO.approveTransfer(id, accountNumberOfTheReceiver);
	}

}
