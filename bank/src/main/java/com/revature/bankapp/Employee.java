package com.revature.bankapp;

public class Employee extends User {
	/**
	 *
	 */
	private static final long serialVersionUID = -4519685946421591372L;

	Employee(String username, String password, String fullName) {
		super(username, password, fullName);
	}
	public String toString() {
		return  "Employee:" + getUsername() + ':' + getFullName();
	}
}
