package com.revature.bankapp;

import java.io.Serializable;

abstract public class User implements Serializable{

	/**
	 * V 1.0.0
	 */
	private static final long serialVersionUID = 6085027198494302988L;
	private String username;
	private String password;
	private String fullName;
	final String type;

	User(String username,  String password, String fullName, String type){// for truly new users strips user input
		this.username = username;
		this.password = password;
		this.fullName = fullName;
		this.type = type;
	}
	public String getUsername() {
		return username;
	}
	/*
	public boolean setUsername(String username) {
		if(!database.hasUser(username)){
			database.remap(this.username, this, username)
			this.username = username;
		}
	}
	*/
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
	}
	public String toString(){
		return type+":"+username +':'+ password+':'+fullName;
	}
}

