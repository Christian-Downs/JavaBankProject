package com.bank.model;

public class Account {
	private String accountNumber;
	private String password;
	private String type;
	private boolean approved;
	

	
	public Account(String accountNumber,String password,boolean approved) {
		super();
		this.accountNumber = accountNumber;
		this.password = password;
		this.approved = approved;
	}
	
	public String toString() {
		return ("Account Number: " + this.accountNumber +"\nPassword: " + this.password + "\nAccountType: " + this.type);
	}
	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public Account() {
		
	}
	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccountType() {
		return type;
	}

	public void setAccountType(String type) {
		this.type = type;
	}
	
	
}
