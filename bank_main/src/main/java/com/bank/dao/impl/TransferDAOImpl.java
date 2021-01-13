package com.bank.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bank.dao.TransferDAO;
import com.bank.dao.dbutil.PostresqlConnection;
import com.bank.exception.TransactionException;
import com.bank.exception.TransferException;
import com.bank.model.Customer;
import com.bank.model.Transfer;


public class TransferDAOImpl implements TransferDAO{
	private static Logger log =  Logger.getLogger(TransferDAOImpl.class);
	@Override
	public Transfer getTransferByID(int id) throws TransferException {
		try {
			Connection connection = PostresqlConnection.getConnection();
			String sql = "select * from \"BankProject\".transfer where id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				return new Transfer(resultSet.getString("senderaccountnumber"),resultSet.getString("receiveraccountnumber"),resultSet.getDouble("amount"),resultSet.getDate("dateofcreation"),resultSet.getBoolean("approved"),resultSet.getInt("id"),resultSet.getString("sendername"));
			}
			throw new TransferException("THERE ISN'T A TRANSFER WITH  THAT ID");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			log.trace(e);
			throw new TransferException("ERROR PLEASE CONTACT CUSTOMER SUPPORT");
		}
		
	}

	@Override
	public List<Transfer> getUnapprovedTransfersForAnAccount(String accountNumberOfReceiver) throws TransferException {
		List<Transfer> transfers = new ArrayList();
		try {
			Connection connection = PostresqlConnection.getConnection();
			String sql = "Select * from \"BankProject\".transfer where receiveraccountnumber = ? AND approved =false";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, accountNumberOfReceiver);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Transfer t = new Transfer(resultSet.getString("senderaccountnumber"),resultSet.getString("receiveraccountnumber"),resultSet.getDouble("amount"),resultSet.getDate("dateofcreation"),resultSet.getBoolean("approved"),resultSet.getInt("id"),resultSet.getString("sendername"));
				transfers.add(t);
			}
			return transfers;
		} catch (ClassNotFoundException | SQLException e) {
			log.trace(e.getMessage());
			throw new TransferException("There was an error connecting to the server please contact your local bank.");
		}
	}

	@Override
	public void newTransfer(Transfer transfer) throws TransferException {
		try {
			
			Connection connection = PostresqlConnection.getConnection();
			String sqlTransfer ="INSERT INTO \"BankProject\".transfer\r\n"
					+ "(senderaccountnumber, receiveraccountnumber, amount, dateofcreation,sendername)\r\n"
					+ "VALUES(?, ?, ?, ?,?);";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlTransfer);
			preparedStatement.setString(1, transfer.getSenderAccountNumber());
			preparedStatement.setString(2, transfer.getReceiverAccountNumber());
			preparedStatement.setDouble(3, transfer.getAmount());
			preparedStatement.setDate(4, transfer.getDateOfCreation());
			preparedStatement.setString(5, transfer.getSenderName());
			if(preparedStatement.executeUpdate()!=0) {
				log.info("Transfer successfully queed");
			}
			else {
				throw new TransferException("Transfer unsuccessfully");
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			log.trace(e);
			throw new TransferException("ERROR INSERTING TRANSFER");
		}
		
	}

	@Override
	public boolean approveTransfer(int id,String accountNumberOfReceiver) throws TransferException {
		try {
			Connection connection = PostresqlConnection.getConnection();
			String sql = "UPDATE \"BankProject\".transfer\r\n"
					+ "	SET approved=true\r\n"
					+ "	WHERE id=? AND receiveraccountnumber=?;";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, accountNumberOfReceiver);
			if(preparedStatement.executeUpdate()!=0) {
				log.debug("Transfer approved");
				return true;
			}else {
				log.debug("There isn't a match in the database");
				return false;
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			log.trace(e);
			throw new TransferException("There was an error connecting to the database please contact your bank.");
		}
	}

	@Override
	public int getNumberOfUnapporvedTransfers(Customer customer) throws TransferException{
		try {
			Connection connection = PostresqlConnection.getConnection();
			String sql = "SELECT COUNT(id) from \"BankProject\".transfer where receiveraccountnumber=? and approved = false";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, customer.getAccountNumber());
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				return resultSet.getInt("count");
			}
			return 0;
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			log.trace(e);
			throw new TransferException("There was an issue connecting to the database please contact customer support");
		}
	}

}
