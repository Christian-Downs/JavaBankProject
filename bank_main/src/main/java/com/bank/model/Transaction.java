package com.bank.model;

import java.sql.Date;

public class Transaction {
	private Account account;
	
	private int id;
	private String accountNumber;
	private double previousAmount;
	private double newAmount;
	private double transactionAmount;
	private Date dateOf;
	
	public Transaction() {
		
	}
	
	public Transaction(String accountNumber, double transactionAmount) {
		this.accountNumber= accountNumber;
	}
}
