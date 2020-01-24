package com.revature.bankapp;

public class Superuser extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6131395887616034843L;

	public Superuser(String username, String password, String fullName) {
		super(username, password, fullName, "SuperUser");
	}

}
