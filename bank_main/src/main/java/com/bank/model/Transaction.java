package com.bank.model;

import java.sql.Date;
import java.text.SimpleDateFormat;

import com.bank.exception.TransactionException;

public class Transaction {
	private Customer customer;
	
	private int id;
	private String accountNumber;
	private double previousAmount;
	private double newAmount;
	private double transactionAmount;
	private Date dateOf;
	private static int count=0;
	private String type;
	
	public Transaction() {
		
	}
	
	public Transaction(Customer customer, double transactionAmount, boolean deposit) throws TransactionException {
		this.setCustomer(customer);
		this.accountNumber= customer.getAccountNumber();
		this.previousAmount = customer.getAmount();
		this.transactionAmount = transactionAmount;
		this.dateOf = new Date(System.currentTimeMillis());
		if(deposit) {
			this.type = "deposit";
			this.newAmount = previousAmount+transactionAmount;
		}
		else {
			this.type = "withdraw";
			double newAmount = previousAmount-transactionAmount; 
			if(newAmount>= 0.00) {
				this.newAmount= newAmount;
			} else {
				throw new TransactionException("Insuffecent funds");
			}
		}
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Transaction(String accountNumber, int id,double previousAmount, double newAmount, double transactionAmount, Date date,String type) {
		this.accountNumber = accountNumber;
		this.id = id;
		this.previousAmount = previousAmount;
		this.newAmount = newAmount;
		this.transactionAmount = transactionAmount;
		this.dateOf = date;
		this.type = type;
		
	}
	
	@Override
	public String toString() {
		return ("id: " + this.id+"\naccount number: " +this.accountNumber+ "\nprevious amount: " + this.previousAmount + "\nnew amount: " + this.newAmount + "\ntransaction amount: " + this.transactionAmount+ "\ndate created: " + this.dateOf + "\ntype: " + this.type);
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getPreviousAmount() {
		return previousAmount;
	}

	public void setPreviousAmount(double previousAmount) {
		this.previousAmount = previousAmount;
	}

	public double getNewAmount() {
		return newAmount;
	}

	public void setNewAmount(double newAmount) {
		this.newAmount = newAmount;
	}

	public double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public Date getDateOf() {
		return dateOf;
	}

	public void setDateOf(Date dateOf) {
		this.dateOf = dateOf;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public static int getCount() {
		return count;
	}

	public static void setCount(int count) {
		Transaction.count = count;
	}
	
	public static void incrementCount() {
		Transaction.count++;
	}
	
}
