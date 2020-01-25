package com.revature.bankapp;

public class Client extends User{

	/**
	 *
	 */
	private static final long serialVersionUID = 4107273125997056355L;

	final static String type = "Client";

	Client(String username,  String password, String fullName){
		super(username, password, fullName);
	}

}
