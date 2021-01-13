package com.bank.dao;

import java.util.List;

import com.bank.exception.TransferException;
import com.bank.model.Customer;
import com.bank.model.Transfer;

public interface TransferDAO {
	public Transfer getTransferByID(int id) throws TransferException;
	public List<Transfer> getUnapprovedTransfersForAnAccount(String accountNumber) throws TransferException;
	public int getNumberOfUnapporvedTransfers(Customer customer) throws TransferException;
	
	public void newTransfer(Transfer transfer) throws TransferException;
	public boolean approveTransfer(int id,String accountNumberOfReceiver) throws TransferException;
}
