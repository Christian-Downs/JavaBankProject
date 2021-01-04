package com.bank.main;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.bank.model.*;
import com.bank.service.*;
import com.bank.service.impl.*;

import jdk.internal.org.jline.utils.Log;

import com.bank.dao.AccountSearchDAO;
import com.bank.dao.impl.*;

public class BankMain {
		private static AccountSearchService accountSearcher = new AccountSearchServiceImpl();
		private static EmployeeFinderService employeeFinder = new EmployeeFinderServiceImpl();
		private static Scanner scanner = new Scanner(System.in);
		private static Logger log = Logger.getLogger(BankMain.class);
	public static void main(String[] args) {
		
		boolean endProgram = true;
		do {
			System.out.println("Welcome to Downs and Downs bank!");
			System.out.println("-----------------------------------");
			System.out.println("What would you like to do?");
			System.out.println("1)Login");
			System.out.println("2)Register");
			try {
				int input = scanner.nextInt();
				switch (input) {
				case 1:
					endProgram = login();
					break;
				case 2:
					endProgram = register();
					break;
				}
				
			} catch(Exception e)
			{
				log.info("Invalid input please try again.");
			}
			
		}while(!endProgram);
		scanner.close();
	}
	
	public static boolean login() {
		try {
			boolean passwordAccepted = false;
			do {
			System.out.println("Please input your account number and password");
			System.out.print("Account number:  ");
			String accountNumber = scanner.next();
			
				System.out.print("Password:  ");
			
				String password = scanner.next();
				try {
					Account currentAccount = accountSearcher.getAccountByAccountNumber(accountNumber);
					if(currentAccount == null) {
						System.out.println("No account found please contact your local bank");
						
						return false;
					}
					else if(currentAccount.getPassword().equals(password)) {
						passwordAccepted = true;
						if(currentAccount.isApproved()) {
							if(Double.parseDouble(accountNumber)<=1000) {
								//Employee Menu
								employeeMenu(employeeFinder.findEmployeeByAccountNumber(accountNumber));
								return true;
							}
							else {
								//Customer Menu
								customerMenu(currentAccount);
								return true;
							}
						}
						else {
							System.out.println("Your account isn't approved yet please wait 3-5 business days since account creation to be approved. \nHave a good day.");
							return false;
						}
					}
					else {
						System.out.println("Incorrect password please try again.");
					}
					
				} 
				catch (NumberFormatException e) {
					System.out.println("Incorrect format");
				}
				System.out.print("\n\n");
			} while(!passwordAccepted);
		} 
		catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}	
		return false;
	}
	
	public static void employeeMenu(Employee employee) {
		System.out.println("Welome back " + employee.getName());
		do {
			System.out.println("What task would you like to do?");
			System.out.println("1) Review new customers");
			System.out.println("2) Find a customer by account number");
			System.out.println("3) View log of all transactions");
			//System.out.println("4)");
		
			try {
				int choice = Integer.parseInt(scanner.nextLine());
				switch (choice) {
				case 1:
					
					break;
				case 2:
					
					break;
				case 3:
					
					break;
				}
			} catch(NumberFormatException e) {
				System.out.println("Invalid format please try again.");
			}
		} while(true);
		
	}
	
	public static void customerMenu(Account account) {
		System.out.println(account.toString());	
	}
	
	
	public static boolean register() {
		
		return false;
	}
	
	
}
