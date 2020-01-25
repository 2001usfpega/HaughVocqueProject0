package com.revature.bankapp;

public class Employee extends User{
	/**
	 *
	 */
	private static final long serialVersionUID = -4519685946421591372L;

	final static String type = "Employee";

	Employee(String username,  String password, String fullName){
		super(username, password, fullName);
	}
}
