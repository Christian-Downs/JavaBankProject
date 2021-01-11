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

import com.bank.dao.AccountDAO;
import com.bank.dao.impl.*;
import com.bank.exception.CustomerException;
import com.bank.exception.TransactionException;

public class BankMain{
		private static AccountService accountServicer = new AccountServiceImpl();
		private static EmployeeService employeeServicer = new EmployeeServiceImpl();
		private static CustomerService customerServicer = new CustomerServiceImpl();
		private static TransactionService transactionServicer = new TransactionServiceImpl();
		private static Scanner scanner = new Scanner(System.in);
		private static Logger log = Logger.getLogger(BankMain.class);
	public static void main(String[] args) {
		
		boolean endProgram = false;
		do {
			log.info("Welcome to Downs and Downs bank!");
			log.info("-----------------------------------");
			log.info("What would you like to do?");
			log.info("1)Login");
			log.info("2)Register");
			try {
				int input = Integer.parseInt(scanner.nextLine());
				switch (input) {
				case 1:
					endProgram = login();
					break;
				case 2:
					endProgram = register();
					break;
				default:
					endProgram = false;
					break;
				}
				
			} catch(Exception e)
			{
				log.error("Invalid input please try again.");
			}
			
		}while(!endProgram);
		scanner.close();
	}
	
	public static boolean login() {
		try {
			boolean passwordAccepted = false;
			do {
				log.info("Please input your account number and password");
				log.info("Account number:");
				String accountNumber = scanner.nextLine();
				log.info("Password:");
			
				String password = scanner.nextLine();
				try {
					Account currentAccount = accountServicer.getAccountByAccountNumber(accountNumber);
					if(currentAccount == null) {
						log.warn("No account found please contact your local bank");
						
						return false;
					}
					else if(currentAccount.getPassword().equals(password)) {
						passwordAccepted = true;
						
						if(Double.parseDouble(accountNumber)<=1000) {
							//Employee Menu
							employeeMenu(employeeServicer.findEmployeeByAccountNumber(accountNumber));
							return true;
						}
						else {
							//Customer Menu
							customerMenu(customerServicer.findCustomerByAccountNumber(accountNumber));
							return true;
						}
					}
					else {
						log.warn("Incorrect password please try again.");
					}
				} 
				catch (NumberFormatException e) {
					log.warn("Incorrect format");
				}
				System.out.print("\n\n");
			} while(!passwordAccepted);
		} 
		catch(Exception e) {
			log.error(e.getMessage());
			return false;
		}	
		return false;
	}
	
	public static void employeeMenu(Employee employee) {
		log.info("\nWelome back " + employee.getName());
		boolean endProgram =false;
		do {
			log.info("What task would you like to do?");
			log.info("1) Review unapproved customers");
			log.info("2) Find a customer by account number");
			log.info("3) View log of all transactions");
			log.info("4) Exit");

			try {
				int choice = Integer.parseInt(scanner.nextLine());
				switch (choice) {
				case 1:
					reviewCustomers();
					break;
				case 2:
					findCustomersByAccountNumber();
					break;
				case 3:
					viewAllTransactions();
					break;
				case 4:
					log.info("Have a good day.");
					endProgram = true;
					break;
				}
			} catch(NumberFormatException e) {
				log.info("Invalid format please try again.");
			}
		} while(!endProgram);
		
	}
	


	private static void viewAllTransactions() {
		log.debug("Showing All Transactions");
		
		try {
			List<Transaction> transactions = transactionServicer.getAllTransactions();
			for(Transaction t: transactions) {
				log.info(t);
			}
		} catch (TransactionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void reviewCustomers() {
		log.debug("Starting to display customers that are unapproved");
		try {
			List<Customer> customers = customerServicer.allUnapprovedCustomers();
			
			for(Customer customer:customers) {
				log.info(customer.toString()+"\n");
				//log.info();;
			}
			
		} catch (CustomerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void findCustomersByAccountNumber()/*done*/ {
		log.debug("Starting to display customer that is found by accountNumber");
		try {
			log.info("Please enter the account number you would like to find");
			String accountNumberToFind = scanner.nextLine();
			Customer customer = customerServicer.findCustomerByAccountNumber(accountNumberToFind);
			editCustomerMenu(customer);
		} catch(CustomerException e) {
			
		}
	}
	
	
	public static void editCustomerMenu(Customer customer)/*still in progress*/ {
		do {
			String isApproved = customer.isApproved() + "";
			//Customer is displayed
			log.info(customer +"\nApproval Status: " + isApproved.toUpperCase());
		
			//Displaying options
			log.info("What would you like to do?"
					+"\n1) Change approval status."
					+"\n2) View past transactions."
					+"\n3) Transfer money to another account."
					+"\n4) Exit Profile");
			int input = Integer.parseInt(scanner.nextLine());
			switch(input) {
			case 1:
				try {
					customerServicer.approveCustomer(customer);
				} catch (CustomerException e) {
					// TODO Auto-generated catch block
					log.info("ERROR IN THE APPROVAL PROCESS");
				}
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				return;
			default:
				break;
			}
		}while(true);
	}

	
	public static void customerMenu(Customer customer) /*still in progress*/{
		log.info(customer);	
		
	}
	
	
	public static boolean register() {
		
		return false;
	}
	
	
}
