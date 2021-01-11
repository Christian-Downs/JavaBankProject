package com.bank.model;

import java.sql.Date;
import java.text.NumberFormat;

public class Customer extends Account{
	private String name;
	private String accountNumber;
	private Date dateOfBirth;
	private Date creationDate;
	private String type;
	private double amount;
	private boolean approved;
	
	public Customer(String name, String accountNumber, Date dateOfBirth, Date creationDate, String type, double amount, boolean approved) {
		this.name = name;
		this.accountNumber = accountNumber;
		this.dateOfBirth = dateOfBirth;
		this.creationDate = creationDate;
		this.type = type;
		this.amount = amount;
		this.approved = approved;
	}
	
	public String toString() {
		return ("\nCustomer name: " + name+ "\nAccount Number: " + accountNumber +"\nDate Of Birth: " + dateOfBirth+ "\nType of Account: " + type+ "\nAmount in Account: " + NumberFormat.getCurrencyInstance().format(amount));
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

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}
}
