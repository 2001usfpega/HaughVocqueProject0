package com.revature.bankapp;

import java.util.Scanner;

public class BankingApp {
	// UserObject currentUser;
	// DatabasesObject databases 
	//live databases in memory
//:P:
	public static double balance;
	public static double savings;
	public static double withdraw;
	public static double deposit;
	public static double tempDou;
	 public static String userName;
	 public static String passWord;
	 public static String realName;
	
	public static void main(String[] args) {
	
		Databases dataBase = new Databases();
		 Scanner bankScan = new Scanner(System.in);
		 User user = new Client(userName, passWord, realName );
		
		 int userChoice;
		 String userInput;
		 boolean quit = false;
		 boolean whileStop = false;
		 dataBase.readFiles();
		 //dataBase.makeEmployee("Avocque", "icantreadcode", "Amanda Vocque");
		 //dataBase.makeSU("MHaugh", "C0d3r", "Michael Haugh");
		 dataBase.makeClient("2hands", "Pickle", "Two-hands McGee");
		 
		 
		System.out.println("Welcome to Rob-U Bank!");
		System.out.println("Are you a returning user?");
		System.out.println(" Yes or No ");
		userInput = bankScan.next();
		
		//Start of Do
		do { 
		while (!whileStop) {
			if(userInput.equals("Yes") || userInput.equals("yes") || userInput.equals("y")) {
				System.out.println("Welcome back! How are you signing in today?");
				whileStop = true;
				System.out.println("\n1.User" + "\n2.Employee" + "\n3.Admin");
				//userChoice = bankScan.nextInt();
				userInput = bankScan.next();
				//Client Enters
				if(userInput.equals("User") || userInput.equals("user") || userInput.equals("1") || userInput.equals("1.")) {
					System.out.println("Hello User, let's get you signed in.");
					//String userName;
					//String passWord;
					//String fullName;
					System.out.println("Username:");
					userName = bankScan.next();
					System.out.println("Password:");
					passWord = bankScan.next();
					
					if(dataBase.checkLoggin(userName, passWord) == true) {
						System.out.println("Hello there, " + userName + ". How can I help you today?");
						System.out.println("1. Balance" + "\n2. Withdraw" + "\n3. Deposit" + "\n4. Transfer" + "\n5. Add another user for joint account" + "\nInput 0 to exit.");
						dataBase.printAccounts(user);
						userInput = bankScan.next();
						balance = 500.45;
						savings = 1375.89;
						
						
						boolean oneStep = true;
						boolean userMenu = false;
						while(!userMenu) {
							if(!oneStep) {
								System.out.println("1. Balance" + "\n2. Withdraw" + "\n3. Deposit" + "\n4. Transfer" + "\n5. Add another user for joint account" + "\nInput 0 to exit.");
								userInput = bankScan.next();
							}
							
							switch(userInput) {
							
							case "1":
							case "1.":
							case "Balance":
							case "balance":
								System.out.println("You have $" + balance + " in your checking." + "\nYou have $" + savings + " in your savings.");
								System.out.println("Would you like to continue?" + "\nYes or No");
								userInput = bankScan.next();
								if(userInput.equals("Yes") || userInput.equals("yes") || userInput.equals("y")) {
									userMenu = false;
									oneStep = false;
								}
								else if (userInput.equals("No") || userInput.equals("no") || userInput.equals("n")) {
									System.out.println("Have a good day!");
									userMenu = true;
									quit = true;
								}
								
								break;
							case "2":
							case "2.":
							case "Withdraw":
							case "withdraw":
								System.out.println("You currently have $" + balance + " in your checking.");
								System.out.println("How much would you like to withdraw?");
								withdraw = bankScan.nextDouble();
								
								if(withdraw <= 700.00) {
									System.out.println("You now have $" + (balance - withdraw));
								} else if(withdraw > 700.00) {
									System.out.println("You do not have enough to withdraw.");
									
								}
								System.out.println("Would you like to continue?" + "\nYes or No");
								userInput = bankScan.next();
								if(userInput.equals("Yes") || userInput.equals("yes") || userInput.equals("y")) {
									userMenu = false;
									oneStep = false;
								}
								else if (userInput.equals("No") || userInput.equals("no") || userInput.equals("n")) {
									System.out.println("Have a good day!");
									userMenu = true;
									quit = true;
								}
								//dataBase.withdraw(user, ihatemylife, Lol);
								//userMenu = true;
								break;
							case "3":
							case "3.":
							case "Deposit":
							case "deposit":
								System.out.println("You currently have $" + balance + " in your checking.");
								System.out.println("How much would you like to deposit?");
								deposit = bankScan.nextDouble();
								
								System.out.println("You have deposited $" + deposit + "into your account." + "\nYou now have $" + (deposit + balance));
								System.out.println("Would you like to continue?" + "\nYes or No");
								userInput = bankScan.next();
								if(userInput.equals("Yes") || userInput.equals("yes") || userInput.equals("y")) {
									userMenu = false;
									oneStep = false;
								}
								else if (userInput.equals("No") || userInput.equals("no") || userInput.equals("n")) {
									System.out.println("Have a good day!");
									userMenu = true;
									quit = true;
								}
								break;
							case "4":
							case "4.":
							case "Transfer":
							case "transfer":
								System.out.println("You have $" + balance + " in your checking." + "\nYou have $" + savings + " in your savings.");
								System.out.println("Would you like to transfer to Savings or Checking?");
								userInput = bankScan.next();
								if(userInput.equals("Checking") || userInput.equals("checking")) {
									System.out.println("How much would you like to transfer from savings?");
									tempDou = bankScan.nextDouble();
									if(tempDou <= savings) {
										System.out.println("You have transfered $" + tempDou + " from savings." + "\nYou now have $" + (savings - tempDou) + " in savings.");
										System.out.println("You now have $" + (balance + tempDou) + " in your checking.");
									}else {
										System.out.println("You do not have enough in savings for this transfer.");
									}
									
									
								} else if(userInput.equals("Savings") || userInput.equals("savings")) {
									System.out.println("How much would you like to transfer from checking?");
									tempDou = bankScan.nextDouble();
									if(tempDou <= balance) {
										System.out.println("You have transfered $" + tempDou + " from checking." + "\nYou now have $" + (balance - tempDou) + " in checking.");
										System.out.println("You now have $" + (savings + tempDou) + " in your savings.");
									}else {
										System.out.println("You do not have enough in checking for this transfer.");
									}
									
								}
								System.out.println("Would you like to continue?" + "\nYes or No");
								userInput = bankScan.next();
								if(userInput.equals("Yes") || userInput.equals("yes") || userInput.equals("y")) {
									userMenu = false;
									oneStep = false;
								}
								else if (userInput.equals("No") || userInput.equals("no") || userInput.equals("n")) {
									System.out.println("Have a good day!");
									userMenu = true;
									quit = true;
								}
								break;
							case "5":
							case "5.":
							case "Add":
							case "add":
								//Would Add even work without account?
								System.out.println("Would you like to continue?" + "\nYes or No");
								userInput = bankScan.next();
								if(userInput.equals("Yes") || userInput.equals("yes") || userInput.equals("y")) {
									userMenu = false;
									oneStep = false;
								}
								else if (userInput.equals("No") || userInput.equals("no") || userInput.equals("n")) {
									System.out.println("Have a good day!");
									userMenu = true;
									quit = true;
								}
								break;
							case "0":

				                quit = true;
				                System.out.println("Goodbye!");
				                System.exit(0);
								break;
							default:
								System.out.println("I'm sorry, that is not a valid input. Please try again.");
								
							
						}
						
						
							
						
						}
						
					}else {
						System.out.println("I'm sorry, the username/password is incorrect.");
					}
					
					
				
				}//Employee start
				else if(userInput.equals("Employee") || userInput.equals("employee") || userInput.equals("2") || userInput.equals("2.")) {
					System.out.println("Hello Employee, let's get you signed in.");
					//String userName;
					//String passWord;
					//String fullName;
					System.out.println("Username:");
					userName = bankScan.next();
					System.out.println("Password:");
					passWord = bankScan.next();
					
					if(dataBase.checkLoggin(userName, passWord) == true) {
						System.out.println("Hello there, " + userName + ". How can I help you today?");
						System.out.println("1. Account" + "\n2. Balances" + "\n3. Personal" +  "\nInput 0 to exit.");
						dataBase.printAccounts(user);
						userInput = bankScan.next();
						boolean oneStep = true;
						boolean userMenu = false;
						while(!userMenu) {
							if(!oneStep) {
								System.out.println("1. Account" + "\n2. Balances" + "\n3. Personal" +  "\nInput 0 to exit.");
								userInput = bankScan.next();
							}
							switch(userInput) {
							case "1":
							case "1.":
							case "Account":
							case "account":
								System.out.println("This is where the accounts will go for Employee");
								System.out.println("Would you like to continue?" + "\nYes or No");
								userInput = bankScan.next();
								if(userInput.equals("Yes") || userInput.equals("yes") || userInput.equals("y")) {
									userMenu = false;
									oneStep = false;
								}
								else if (userInput.equals("No") || userInput.equals("no") || userInput.equals("n")) {
									System.out.println("Have a good day!");
									userMenu = true;
									quit = true;
									System.exit(0);
								}
								break;
							case "2":
							case "2.":
							case "Balances":
							case "balances":
							case "balance":
							case "Balance":
								System.out.println("This is where the Balances go.");
								System.out.println("Would you like to continue?" + "\nYes or No");
								userInput = bankScan.next();
								if(userInput.equals("Yes") || userInput.equals("yes") || userInput.equals("y")) {
									userMenu = false;
									oneStep = false;
								}
								else if (userInput.equals("No") || userInput.equals("no") || userInput.equals("n")) {
									System.out.println("Have a good day!");
									userMenu = true;
									quit = true;
									System.exit(0);
								}
								break;
							case "3":
							case "3.":
							case "Personal":
							case "personal":
								System.out.println("This is where the Personal go.");
								System.out.println("Would you like to continue?" + "\nYes or No");
								userInput = bankScan.next();
								if(userInput.equals("Yes") || userInput.equals("yes") || userInput.equals("y")) {
									userMenu = false;
									oneStep = false;
								}
								else if (userInput.equals("No") || userInput.equals("no") || userInput.equals("n")) {
									System.out.println("Have a good day!");
									userMenu = true;
									quit = true;
									System.exit(0);
								}
								break;
							case "0":

				                quit = true;
				                System.out.println("Goodbye!");
				                System.exit(0);
								break;
							default:
								System.out.println("I'm sorry, that is not a valid input. Please try again.");
							
							
							}
						}
						
				}else {
					System.out.println("I'm sorry, the username/password is incorrect.");
				}
			} else if(userInput.equals("Admin") || userInput.equals("Admin") || userInput.equals("3") || userInput.equals("3.")) {
				System.out.println("Hello Admin, let's get you signed in.");
				//String userName;
				//String passWord;
				//String fullName;
				System.out.println("Username:");
				userName = bankScan.next();
				System.out.println("Password:");
				passWord = bankScan.next();
				
				if(dataBase.checkLoggin(userName, passWord) == true) {
					System.out.println("Hello there, " + userName + ". How can I help you today?");
					System.out.println("1. Approve" + "\n2. Functions" + "\n3. Canceling" +  "\nInput 0 to exit.");
					dataBase.printAccounts(user);
					userInput = bankScan.next();
					boolean oneStep = true;
					boolean userMenu = false;
					while(!userMenu) {
						if(!oneStep) {
							System.out.println("1. Approve" + "\n2. Functions" + "\n3. Canceling" +  "\nInput 0 to exit.");
							userInput = bankScan.next();
						}
						switch(userInput) {
						case "1":
						case "1.":
						case "Approve":
						case "approve":
							System.out.println("This is where the accounts will go for Admin");
							System.out.println("Would you like to continue?" + "\nYes or No");
							userInput = bankScan.next();
							if(userInput.equals("Yes") || userInput.equals("yes") || userInput.equals("y")) {
								userMenu = false;
								oneStep = false;
							}
							else if (userInput.equals("No") || userInput.equals("no") || userInput.equals("n")) {
								System.out.println("Have a good day!");
								userMenu = true;
								quit = true;
								System.exit(0);
							}
							break;
						case "2":
						case "2.":
						case "Functions":
						case "functions":
						case "Function":
						case "function":
							System.out.println("This is where the Balances go.");
							System.out.println("Would you like to continue?" + "\nYes or No");
							userInput = bankScan.next();
							if(userInput.equals("Yes") || userInput.equals("yes") || userInput.equals("y")) {
								userMenu = false;
								oneStep = false;
							}
							else if (userInput.equals("No") || userInput.equals("no") || userInput.equals("n")) {
								System.out.println("Have a good day!");
								userMenu = true;
								quit = true;
								System.exit(0);
							}
							break;
						case "3":
						case "3.":
						case "Canceling":
						case "canceling":
							System.out.println("This is where the Personal go.");
							System.out.println("Would you like to continue?" + "\nYes or No");
							userInput = bankScan.next();
							if(userInput.equals("Yes") || userInput.equals("yes") || userInput.equals("y")) {
								userMenu = false;
								oneStep = false;
							}
							else if (userInput.equals("No") || userInput.equals("no") || userInput.equals("n")) {
								System.out.println("Have a good day!");
								userMenu = true;
								quit = true;
								System.exit(0);
							}
							break;
						case "0":

			                quit = true;
			                System.out.println("Goodbye!");
			                System.exit(0);
							break;
						default:
							System.out.println("I'm sorry, that is not a valid input. Please try again.");
						
						
						}
					}
					
			}else {
				System.out.println("I'm sorry, the username/password is incorrect.");
			}
				
			}
		
				
			}	else if(userInput.equals("No") || userInput.equals("no") || userInput.equals("n")) {
				System.out.println("Welcome new user! Would you like to setup a account?");
				whileStop = true;
				System.out.println("Yes or No");
				userInput = bankScan.next();
				if(userInput.equals("Yes") || userInput.equals("yes") || userInput.equals("y")) {
					System.out.println("Wonderful! Let's begin.");
					System.out.println("What is your name?");
					//Hates spaces for some reason (Amanda look into it when there's more time
					realName = bankScan.next();
					System.out.println("And what username would you like?");
					
					userName = bankScan.next();
					System.out.println("");
					System.out.println("What password will you use?");
					passWord = bankScan.next();
					//Needs to check to see if Admin or Employee approves
					dataBase.makeClient(userName, passWord, realName);
					System.out.println("Perfect! A account has been made for you.");
				}
				else {
					System.out.println("Alright, thanks for visiting!");
					quit = true;
					System.exit(0);
					
				}
			}else {
				System.out.println("Wrong Input, Try again.");
				userInput = bankScan.next();
		
		}
		
		
		
		
		/*
		 * do { System.out.println("1. Deposit money"); System.out.
		 * print("Input a number through 1 - 4 to select a menu. Input 0 to exit.");
		 * userChoice = bankScan.nextInt(); if(userChoice == 0) quit = true;
		 * 
		 * 
		 * } while (!quit); System.out.println("Have a good day!");
		 */

		 //bankScan.close();
	} 

	// Sysout "NAME"
	// Scanner.nextinput
	// Sysout "
	// Password"
	// scanner.nextinput
	// Input Options
	// If statements;
	// WriteFIle
	// Method
	//

}while (!quit);}}
