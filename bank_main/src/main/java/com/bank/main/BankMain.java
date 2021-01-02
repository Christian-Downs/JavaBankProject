package com.bank.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.bank.model.*;
import com.bank.service.AccountSearchService;
import com.bank.service.impl.AccountSearchServiceImpl;
import com.bank.dao.AccountSearchDAO;
import com.bank.dao.impl.*;

public class BankMain {
	public static void main(String[] args) {
		AccountSearchService accountSearcher = new AccountSearchServiceImpl();
		
		Scanner scanner = new Scanner(System.in);
		boolean valid = true;
		do {

			
			System.out.println("Welcome to Downs and Downs bank!");
			System.out.println("-----------------------------------");
			System.out.println("Please input your account number and password");
			System.out.print("Account number:  ");
			try {
				String accountNumber = scanner.next();
				boolean passwordAccepted = false;
				do {
					System.out.print("Password:  ");
				
					String password = scanner.next();
					try {
						Account currentAccount = accountSearcher.getAccountByAccountNumber(accountNumber);
						if(currentAccount == null) {
							System.out.println("No account found please contact your local bank");
							valid = false;
							break;
						}
						else if(currentAccount.getPassword().equals(password)) {
							passwordAccepted = true;
							if(currentAccount.isApproved()) {
								if(Double.parseDouble(accountNumber)<=1000) {
									//Employee Menu
									employeeMenu(currentAccount);
								
								}
								else {
									//Customer Menu
									customerMenu(currentAccount);
								}
							}
							else {
								System.out.println("Your account isn't approved yet please wait 3-5 business days since account creation to be approved. \nHave a good day.");
								break;//goes to end of password block
							}
						}
						else {
							System.out.println("Incorrect password please try again.");
						}
					} 
					catch (NumberFormatException e) {
						System.out.println("Incorrect format");
					}
				} while(!passwordAccepted);
			} 
			catch(Exception e) {
				System.out.println(e.getMessage());
			}	
		}while(!valid);
		scanner.close();
	}
	
	public static void employeeMenu(Account account) {
		System.out.println(account.toString());
		System.out.print("Welome back");
	}
	
	public static void customerMenu(Account account) {
		System.out.println(account.toString());	
	}
	
	
}
