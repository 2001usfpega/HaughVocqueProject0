package com.revature.bankapp;

public class SuperUser extends Employee {

	private static final long serialVersionUID = 6131395887616034843L;

	public SuperUser(String username, String password, String fullName) {
		super(username, password, fullName);
	}
	public String toString() {
		return  "Admin:" + getUsername() + ':' + getFullName();
	}
}
