package com.bank.model;

import java.sql.Date;

public class Customer {
	private String name;
	private String accountNumber;
	private Date dateOfBirth;
	private Date creationDate;
	private String type;
	private double amount;
	
	public Customer(String name, String accountNumber, Date dateOfBirth, Date creationDate, String type, double amount) {
		this.name = name;
		this.accountNumber = accountNumber;
		this.dateOfBirth = dateOfBirth;
		this.creationDate = creationDate;
		this.type = type;
		this.amount = amount;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date crationDate) {
		this.creationDate = crationDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
}
