package com.revature.bankapp;

import java.util.HashSet;

public class Account implements java.io.Serializable{

	/**
	 * accounts should be organized by number.  each account should have a unique number, a balance, and a list of owners by user name
	 */

	private static final long serialVersionUID = -8445258882204098398L;
	private final int accountNumber;
	private double balance;
	private Databases parent;
	HashSet<String> users = new HashSet<String>();
	Account(double openingbalance, int accountnumber, String users, Databases d){
		this.accountNumber = accountnumber;
		this.balance = openingbalance;
		for(String s: users.split(":")) {
			this.users.add(s);
		}
		parent = d;
	}
	Account(String saved, Databases d){
		String [] input = saved.split(":");
		accountNumber = Integer.parseInt(input[0]);
		balance = Double.parseDouble(input[1]);
		for(int i = 2; i<input.length; i++){
			users.add(input[i]);
		}
		parent = d;
	}

	public String toString(){
		String users = "";
		for(String s: this.users){
			users += ":"+s;
		}
		return accountNumber+":"+balance + users;
	}
	int getAccountNumber(){
		return accountNumber;
	}
	double getBalance(){
		return balance;
	}
	//transfers should be handled by the database object to validate the existence and permissions of the accounts
	//withdraw and deposit should also have validation at a higher level/implement passing user reqests down
	double withdraw(double amount){ //Withdraws a positive amount from the account
		if(balance - amount>=0&&amount>0){  //validates that the passed ammount does not overdraw
			balance -= amount;
		}
		return balance;  //returning the new balence instead of using void allows for greater interactivity
	}
	double deposit(double amount){  //deposits positive values into the account, returns final balance
		if(amount>=0){
			balance += amount;
		}
		return balance;
	}
	boolean checkUser(String user){
		return users.contains(user);
	}
	boolean addUser(String user){  //attempts to add a user, returns true if successful and false if the user is already a member of the account
		return users.add(user);
	}
	boolean removeUser(String user){//attempts to remove a user from the account, fails if the user does not exist or the account has a nonzero balance
		boolean out = users.remove(user);
		if (users.size() ==0) {
			if(balance != 0){  //only empty accounts can close
				users.add(user);
				out = false;
			}else{
				//parrent.removeAccount(Account Number); //tells the parent to close the account
			}
		}
		return out;  //returns the success or failure of the attempt
	}
}