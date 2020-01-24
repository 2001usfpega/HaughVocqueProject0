package com.revature.bankapp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Databases{
	private static final String AccountFile="Accounts.txt";
	public static List<Account> Accountlist= new ArrayList<Account>();
	private static final String UserFile="Users.txt";
	public static List<User> Userlist= new ArrayList<User>();
	
	//write method
	public static void writeFiles() {
		ObjectOutputStream objectOut;
		try {
			objectOut= new ObjectOutputStream(new FileOutputStream(AccountFile));
			objectOut.writeObject(Accountlist);
			objectOut.close();
			objectOut = new ObjectOutputStream(new FileOutputStream(UserFile));
			objectOut.writeObject(Userlist);
			objectOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Read methods
	public static void readFiles() {
		ObjectInputStream objectIn;
		
		try {
			objectIn= new ObjectInputStream( new FileInputStream(AccountFile));
			//Accountlist= (ArrayList<Account>)objectIn.readObject();
			Accountlist= (ArrayList<Account>)objectIn.readObject();
			objectIn.close();
			objectIn= new ObjectInputStream( new FileInputStream(UserFile));
			Userlist= (ArrayList<User>)objectIn.readObject();
			objectIn.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	Databases(){
		readFiles();
		
	}
	
}