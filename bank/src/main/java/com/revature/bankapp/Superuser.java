package com.revature.bankapp;

public class Superuser extends Employee {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6131395887616034843L;

	public Superuser(String username, String password, String fullName) {
		super(username, password, fullName);
		type = "SuperUser";
	}

}