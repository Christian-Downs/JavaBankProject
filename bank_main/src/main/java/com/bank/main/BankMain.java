package com.bank.main;


import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.apache.log4j.chainsaw.Main;

import com.bank.model.*;
import com.bank.service.*;
import com.bank.service.impl.*;

import jdk.internal.org.jline.utils.Log;

import com.bank.dao.AccountDAO;
import com.bank.dao.impl.*;
import com.bank.exception.CustomerException;
import com.bank.exception.TransactionException;
import com.bank.exception.TransferException;

public class BankMain{
		private static AccountService accountServicer = new AccountServiceImpl();
		private static EmployeeService employeeServicer = new EmployeeServiceImpl();
		private static CustomerService customerServicer = new CustomerServiceImpl();
		private static TransactionService transactionServicer = new TransactionServiceImpl();
		private static TransferService transferServicer = new TransferServiceImpl();
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
			log.info("3)Exit");
			try {
				int input = Integer.parseInt(scanner.nextLine());
				switch (input) {
				case 1:
					endProgram = login();
					break;
				case 2:
					endProgram = register();
					break;
				case 3:
					return;
				default:
					endProgram = false;
					break;
				}
				
			} catch(Exception e) {
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
							Customer customer = customerServicer.findCustomerByAccountNumber(accountNumber);
							if(customer.isApproved())
								customerMenu(customer);
							else if(!customer.isReviewed())
								log.info("Please wait 3-5 business days for the review process.\n");
							else if(!customer.isApproved())
								log.info("Your account has been denied approval please contact your bank");
							return false;
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
			log.info("1) Review unreviewed customers");
			log.info("2) Find a customer by account number");
			log.info("3) View log of all transactions");
			log.info("4) Exit");

			try {
				int choice = Integer.parseInt(scanner.nextLine());
				switch (choice) {
				case 1:
					employeeReviewUnreviewedCustomers();
					break;
				case 2:
					employeeFindCustomersByAccountNumber();
					break;
				case 3:
					employeeViewAllTransactions();
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
	


	private static void employeeViewAllTransactions() {
		log.debug("Showing All Transactions");
		
		try {
			List<Transaction> transactions = transactionServicer.getAllTransactions();
			for(Transaction t: transactions) {
				log.info(t + "\n");
			}
		} catch (TransactionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void employeeReviewUnreviewedCustomers() {
		log.debug("Starting to display customers that are unapproved");
		try {
			List<Customer> customers = customerServicer.allUnreviewedCustomers();
			if(customers.size()>0) {
				for(boolean valid = false;!valid;) {
					int options = 0;
					for(Customer customer:customers) {
						options++;
						log.info(options+")"+customer.toString()+"\n");
						//log.info();;
					}
				
					log.info("Which customer would you like to review? Type E to exit");
					String input = scanner.nextLine().toLowerCase();
					if(input.equals("e")) return;
					try {
						int choicenNumber = Integer.parseInt(input)-1;
						employeeEditCustomerMenu(customers.get(choicenNumber));
						
					}catch(NumberFormatException e) {
						log.info("Invalid option try again.");
						break;
					}
					
				}
			
			} else {
				log.info("There are no customers that need review at the moment.");
			}
		} catch (CustomerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void employeeFindCustomersByAccountNumber()/*done*/ {
		log.debug("Starting to display customer that is found by accountNumber");
		try {
			log.info("Please enter the account number you would like to find");
			String accountNumberToFind = scanner.nextLine();
			Customer customer = customerServicer.findCustomerByAccountNumber(accountNumberToFind);
			employeeEditCustomerMenu(customer);
		} catch(CustomerException e) {
			log.trace(e);
			log.info("There was an error finding that customer try again");
		}
	}
	
	
	public static void employeeEditCustomerMenu(Customer customer)/*still in progress*/ {
		do {
			String reviewedStatus = "";
			if(customer.isReviewed())
				reviewedStatus = "Reviewed";
			else
				reviewedStatus = "Pending";
			String isApproved = customer.isApproved() +"";
			
			//Customer is displayed
			log.info(customer+ "\nApproval Status: " + isApproved.toUpperCase() +"\nReviewed Status: " + reviewedStatus.toUpperCase() + "\n");
		
			//Displaying options
			log.info("What would you like to do?"
					+"\n1) Change approval status."
					+"\n2) View past transactions."
					+"\n3) Exit Profile");
			try{ 
				int input = Integer.parseInt(scanner.nextLine());
				
	
				
				switch(input) {
				case 1:
					for(boolean validInput = false; !validInput;) {
						log.info("To approve type approve to deny type deny");
						String approvalInput = scanner.nextLine();
						if(approvalInput.equals("approve")) {
							validInput=true;
							try {
								customerServicer.changeApprovealStatusOfCustomer(customer,true);
							} catch (CustomerException e) {
								// TODO Auto-generated catch block
								log.info("ERROR IN THE APPROVAL PROCESS");
							}
						} else if(approvalInput.equals("deny")) {
							validInput = true;
							try {
								customerServicer.changeApprovealStatusOfCustomer(customer,false);
							} catch (CustomerException e) {
								// TODO Auto-generated catch block
								log.info("ERROR IN THE APPROVAL PROCESS");
							}
						} else {
							log.info("Invalid response.");
						}
					}
					break;
				case 2:
					try {
						List<Transaction> transactions = transactionServicer.getAllTransactionsOfACustomer(customer);
						for(Transaction t : transactions)
							log.info(t.toString()+"\n");
					} catch (TransactionException e) {
						// TODO Auto-generated catch block
						log.trace(e);
						log.info("There was an error getting all transactions.");
					}
					break;
				case 3:
					return;
				default:
					log.info("Invalid choice");
					break;
				}
			} catch(NumberFormatException e) {
				log.info("Invalid input");
			}
		}while(true);
	}

	
	public static void customerMenu(Customer customer) /*still in progress*/{	
		log.info("Welcome back " + customer.getName().split(" ")[0]);
		try {
			for(boolean endProgram = false; !endProgram;) {
				int numberOfUnapprovedTransfers = transferServicer.getNumberOfUnapprovedTransfers(customer);
				log.info("You have "+ numberOfUnapprovedTransfers + " unapproved transfers waiting for review.");
				log.info("How may we help you today?");
				log.info("1)Account info.");
				log.info("2)Make a deposit.");
				log.info("3)Make a withdraw.");
				log.info("4)Make a transfer.");
				log.info("5)Review unapproved transfers.");
				log.info("6)Exit.");
				try {
					int choice = Integer.parseInt(scanner.nextLine());
					log.debug(choice);
					switch (choice) {
					case 1:
						customerAccountInfo(customer);
						break;
					case 2:
						customerMakeADeposit(customer);
						break;
					case 3:
						customerMakeAWithdraw(customer);
						break;
					case 4:
						log.info("Make a new transfer?");
						log.info("1)Yes");
						log.info("2)No");
						if(scanner.nextLine().equals("1"))
							customerMakeATransfer(customer);
						break;
					case 5:
						customerAcceptATransfer(customer);
						break;
					case 6:
						endProgram = true;
						log.info("Goodbye");
						break;
					default:
						log.info("Invalid choice please try again.");
						break;
					}
				} catch(NumberFormatException e) {
					log.info("Invalid choice please try again");
				}
				log.info("\n\n");
			}
		} catch(TransferException e) {
			log.info("ERROR Connecting to server");
		}
		
	}
	
	private static void customerAccountInfo(Customer customer)/*done*/ {
		log.info(customer);
		log.info("To change anything about this profile please contact customer service.");
	}
	private static void customerMakeADeposit(Customer customer)/* done*/ {
		double cashInputed = 0;
		for(boolean approved = false; !approved;) {
			for(boolean done=false;!done;) {
		
				log.info("Please enter money type done when done or back to exit.");
				try{
					String input = scanner.nextLine();
					if(input.equals("done")) {
						done = true;
						break;
					} else if(input.equals("exit"))
						return;
					double tempCashStorage = Double.parseDouble(input);
					if(tempCashStorage>0) {
						cashInputed+=tempCashStorage;
						log.info(tempCashStorage + " has been inserted. Current inserted amount is " + cashInputed);
					} else {
						log.info("Please do not steal money from machine!");
						log.info("Try again.");
					}
				} catch (NumberFormatException e) {
					log.info("Invalid input please try again");
				}
			}
			log.info("You've entered " + cashInputed + " is this correct?");
			log.info("1)Yes");
			log.info("2)No");
			for(boolean errorChecker = false; !errorChecker;)
				if(scanner.nextLine().equals("1")) { errorChecker = true; approved = true;}
				else if(scanner.nextLine().equals("2")) {
					errorChecker = true;
					approved = false;
					cashInputed = 0;
				} else 
					log.info("Invalid input try again.");
			log.info("Processing Transaction");
			try {
				Transaction transaction = new Transaction(customer,cashInputed,true);
				transaction.setType("deposit");
				transactionServicer.newTransaction(transaction, customer);
			} catch (TransactionException e) {
				log.trace(e);
				log.info("An error occured while transaction was being processed please contact your local bank or try again.");
				break;
			}
			log.info("Transaction Complete!");
			approved = true;			
		}
		
	}

	private static void customerMakeAWithdraw(Customer customer) {
		double tempCashToRemove = 0;
		for(boolean approved = false; !approved;) {
			for(boolean correct = false; !correct;) {
				log.info("Enter the amount you would like to withdraw enter back to go back");
				String input = scanner.nextLine();
				if(input.equals("back"))
					return;
				try{
					tempCashToRemove = Double.parseDouble(input);
	
				}catch (NumberFormatException e) {
					log.info("Invalid input please try again");
					break;
				}
				if(customer.getAmount()<tempCashToRemove) {
					log.info("Not enough money in the account to withdraw that amount.");
					tempCashToRemove = 0;
					break;
				}
				log.info("You've entered "+ tempCashToRemove+" is this correct?");
				log.info("1)yes");
				log.info("2)no");
				if(!scanner.nextLine().equals("1"))
					break;
				else
					correct =true;
				log.info("Processing Transaction");
				try {
					Transaction transaction = new Transaction(customer,tempCashToRemove,false);
					transaction.setType("withdraw");
					transactionServicer.newTransaction(transaction, customer);
				} catch (TransactionException e) {
					log.trace(e);
					log.info("An error occured while transaction was being processed please contact your local bank or try again.");
					break;
				}
				log.info("Transaction Complete!");
				approved = true;
			}
		}
		
	}

	private static void customerMakeATransfer(Customer customer) {
		int numberOfTimes = 0;
		for(boolean submit = false; !submit;) {
			if(numberOfTimes>0) {
				log.info("To exit type E to make another transfer press enter");
				if(scanner.nextLine().toLowerCase().equals("e"))
					return;
			}
			numberOfTimes++;
			log.info("Please enter receiver account number:");
			String receiverAccountNumber = scanner.nextLine();
			try {
				if(customerServicer.findCustomerByAccountNumber(receiverAccountNumber)==null) {
					log.info("Account doesn't exist. Please try again.");
					break;
				}
			} catch (CustomerException e) {
				log.info(e);
				break;
			}
			log.info("Enter how much you'll be sending: ");
			double amountToSend;
			try {
				amountToSend = Double.parseDouble(scanner.nextLine());
				if(customer.getAmount()<amountToSend) {
					log.info("Insuffienct funds to send");
					break;
				}
			} catch(NumberFormatException | NullPointerException e) {
				log.info("Invalid input please try again.");
				break;
			}
			
			Transfer transfer = new Transfer(customer,receiverAccountNumber,amountToSend);
			
			log.info(transfer.showAllInfoBeforeSending());
			log.info("All info correct?");
			log.info("1)Yes");
			log.info("2)No");
			if(scanner.nextLine().equals("1")) {
				try {
					transferServicer.newTransfer(transfer);
				} catch (TransferException e) {
					// TODO Auto-generated catch block
					log.info(e);
					break;
				}
			}else {
				log.info("Starting over.");
				break;
			}
				
		}
	}

	private static void customerAcceptATransfer(Customer customer) {
		try {
			for(boolean end = false; !end;) {
				List<Transfer> unapprovedTransfers = transferServicer.getUnapprovedTransfersForAnAccount(customer.getAccountNumber());
				int options = 0;
				for(Transfer t: unapprovedTransfers) {
					options++;
					log.info(options+")" + t.showToReciever() + "\n");
				}
				log.info("Type the number to approve the corresponding transfer,type all to approve all transfers, or type back to go back.");
				String input = scanner.nextLine();
				if(input.equals("back")) {
					return;
				}else if(input.equals("all")){
					if(customerAcceptAllTransfers( customer, unapprovedTransfers)) {
						log.info("Approvals were a success.");
						end=true;
					} else {
						log.info("Approvals were not successfull please try again.");
					}
				} else {
					try {
						int choice = Integer.parseInt(input)-1;
						try{ 
							transferServicer.approveTransfer(unapprovedTransfers.get(choice).getId(), customer.getAccountNumber());
						} catch(TransferException e) {
							log.info(e);
							break;
						}
					} catch(NumberFormatException e) {
						log.info("Invalid choice please try again.");
					}
				}
			}
		} catch (TransferException e) {
			log.info(e);
		}
	}


	private static boolean customerAcceptAllTransfers(Customer customer, List<Transfer> transfers) {
		log.info("Confirm to accept all.");
		log.info("1)Yes");
		log.info("2)No");
		if(scanner.nextLine().equals("1")) {
			log.info("Approving all");
			int option =0;
			for(Transfer t: transfers) {
				option++;
				try {
					customerServicer.updateCustomerAmount(customerServicer.findCustomerByAccountNumber(t.getReceiverAccountNumber()), t.getAmount(), "withdraw");
				} catch (CustomerException e) {
					log.trace(e);
					log.info("There was an error with the transferring process from the sender side for option " + option + ".");
				}
				
				try {
					customerServicer.updateCustomerAmount(customer, t.getAmount(), "deposit");
				} catch (CustomerException e) {
					try {
						customerServicer.updateCustomerAmount(customerServicer.findCustomerByAccountNumber(t.getReceiverAccountNumber()), t.getAmount(), "deposit");
					} catch (CustomerException e1) {
						log.trace(e);
						log.info("There was a major error, for option " + option + ", please contact customer service.");
					}
					log.trace(e);
					log.info("There was an error with the transferring process from the receiver side for option " + option + ".");
				}
			}
			return true;
		} else {
			return false;
		}
		
	}

	public static boolean register() {
		
		Customer customer = new Customer();
		log.info("Welcome to Downs and Downs bank please fill out this form and we will get the approval process underway!");
		log.info("--------------------------------------------------------------------------------------------------------");
		log.info("Full Name:");
		customer.setName(scanner.nextLine());
		log.info("Password:");
		customer.setPassword(scanner.nextLine());
		boolean validDateOfBirth=false;
		do {
			log.info("Date Of Birth in yyyy-mm-dd:");
			try {
				
				String dateOfBirthString = scanner.nextLine();
				if(dateOfBirthString.matches("[1-2][0-9]{3}-[0-1][0-9]-[0-3][0-9]")) {
					SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
					formater.setLenient(false);
					java.util.Date dateOfBirthUtil = formater.parse(dateOfBirthString);
					Date dateOfBirthSQL = new Date(dateOfBirthUtil.getTime()); 
					customer.setDateOfBirth(dateOfBirthSQL);
					validDateOfBirth=true;
				}else {
					log.info("invalid date please try again");
				}
			}catch(ParseException e) {
				log.info("invalid date please try again");
			}
		}while(!validDateOfBirth);
		
		customer.setCreationDate(Date.valueOf(LocalDate.now()));
		for(boolean validChoice=false;!validChoice;) {
			log.info("What type of account would you like to open?:");
			log.info("1)Checkings");
			log.info("2)Savings");
			try {
				int choice = Integer.parseInt(scanner.nextLine());
				switch (choice) {
				case 1:
					customer.setType("Checkings");
					validChoice=true;
					break;
				case 2:
					customer.setType("Savings");
					validChoice=true;
					break;
				default:
					log.info("Invalid choice please try again.");
					break;
				}
			}catch(NumberFormatException e) {
				log.info("Ivalid input please try again.");
				break;
			}
		}
		
		log.info("Please insert cash (type done when done):");
		int cashInserted =0;
		for(boolean done=false; !done;) {
			try {
				String input = scanner.nextLine();
				if(input.equals("done")) {
					done=true;
					break;
				}
				int cash = Integer.parseInt(input);
				cashInserted+=cash;
				log.info(cash + " has been accepted your new total is " + cashInserted +" please enter more to continue or type done to be done");
				
			}catch(NumberFormatException e) {
				log.info("Invalid input please try again");
			}
			
		}
		
		customer.setAmount(cashInserted);

		customer.setApproved(false);
		try {
			customer.setAccountNumber(customerServicer.makeAccountNumber());
		
			customerServicer.insertCustomer(customer);
			log.info("Succefully registered");
			log.info("Your account number is " + customer.getAccountNumber());
			log.info("Please wait 3-5 business days to be approved");
		} catch (CustomerException e) {
			// TODO Auto-generated catch block
			log.info("AN ERROR HAS OCCURED PLEASE SEE A CUSTOMER SERVICE REP");
			log.debug(e.getMessage());
			return false;
		}
		return true;    
	}
	
	
}
