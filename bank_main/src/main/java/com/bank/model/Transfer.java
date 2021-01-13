package com.bank.model;

import java.sql.Date;
import java.time.LocalDate;

public class Transfer {
	private int id;
	private String senderAccountNumber;
	private String senderName;
	private String receiverAccountNumber;
	private double amount;
	private Date dateOfCreation;
	public boolean approved;
	
	public Transfer() {
	}
	public Transfer(Customer customer,String receiverAccountNumber, double amount) {
		this.senderName = customer.getName();
		this.senderAccountNumber = customer.getAccountNumber();
		this.amount = amount;
		this.receiverAccountNumber = receiverAccountNumber;
		this.dateOfCreation = Date.valueOf(LocalDate.now());
		
	}
	
	public Transfer(String senderAccountNumber,String receiverAccountNumber,double amount, Date dateOfCreation,boolean approved, int id, String senderName) {
		this.senderAccountNumber = senderAccountNumber;
		this.receiverAccountNumber = receiverAccountNumber;
		this.amount = amount;
		this.dateOfCreation = dateOfCreation;
		this.approved = approved;
		this.id = id;
		this.setSenderName(senderName);
	}
	public String showToReciever() {
		return "Sender name: " + senderName + "\nDate Sent: " + dateOfCreation + "\nAmount: " + amount;
	}
	public String showAllInfoBeforeSending() {
		return "Receiver Account Number: " + receiverAccountNumber + "\nAmount: " + amount;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSenderAccountNumber() {
		return senderAccountNumber;
	}
	public void setSenderAccountNumber(String senderAccountNumber) {
		this.senderAccountNumber = senderAccountNumber;
	}
	public String getReceiverAccountNumber() {
		return receiverAccountNumber;
	}
	public void setReceiverAccountNumber(String receiverAccountNumber) {
		this.receiverAccountNumber = receiverAccountNumber;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Date getDateOfCreation() {
		return dateOfCreation;
	}
	public void setDateOfCreation(Date dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}


}
