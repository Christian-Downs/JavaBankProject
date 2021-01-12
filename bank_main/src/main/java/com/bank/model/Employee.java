package com.bank.model;

import java.sql.Date;

public class Employee extends Account{
	private String accountNumber;
	private String type;
	private String name;
	private Date dateOfBirth;
	private Date hireDate;
	private Boolean stillHired;
	
	public Employee() {
		
	}
	
	public Employee(String accountNumber, String type, String name, Date dateOfBirth, Date hireDate) {
		this.accountNumber = accountNumber;
		this.type = type;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.hireDate = hireDate;
		this.stillHired = true;
	}
	public Employee(String accountNumber, String type, String name, Date dateOfBirth, Date hireDate, Boolean isHired) {
		this.accountNumber = accountNumber;
		this.type = type;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.hireDate = hireDate;
		this.stillHired = isHired;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public Boolean getStillHired() {
		return stillHired;
	}

	public void setStillHired(Boolean stillHired) {
		this.stillHired = stillHired;
	}
	
	
}
