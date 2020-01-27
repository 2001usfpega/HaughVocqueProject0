package com.revature.bankapp;

import java.io.Serializable;

abstract public class User implements Serializable {

	/**
	 * V 1.0.0
	 */
	private static final long serialVersionUID = 6085027198494302988L;
	private String username;
	private String password;
	private String fullName;

	User(String username,  String password, String fullName){//creates new user profiels, call the inhereted versions
		this.username = username;
		this.password = password;
		this.fullName = fullName;
	}
	//geters and seters
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setType(String type) {
	}
	//for listing user profiles, password not shown
	public String toString(){
		return "User:"+username +':'+fullName;
	}
}
