package com.revature.bankapp;

import java.util.HashMap;

import com.revature.bankapp.Account;
import com.revature.bankapp.MappingNotFound;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Databases {
	private static final String AccountFile = "Accounts.txt";
	public static HashMap<Integer, Account> Accountlist = new HashMap<Integer, Account>();
	private static final String UserFile = "Users.txt";
	public static HashMap<String, User> Userlist = new HashMap<String, User>();

	// write method
	public static void writeFiles() {
		ObjectOutputStream objectOut;
		try {
			objectOut = new ObjectOutputStream(new FileOutputStream(AccountFile));
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

	// Read methods
	public static void readFiles() {
		ObjectInputStream objectIn;

		try {
			objectIn = new ObjectInputStream(new FileInputStream(AccountFile));
			// Accountlist= (ArrayList<Account>)objectIn.readObject();
			Accountlist = (HashMap<Integer, Account>) objectIn.readObject();
			objectIn.close();
			objectIn = new ObjectInputStream(new FileInputStream(UserFile));
			Userlist = (HashMap<String, User>) objectIn.readObject();
			objectIn.close();
		} catch (FileNotFoundException e) {
			Userlist.put("root", new Superuser("root", "root", "Default Superuser, change to secure password"));
			writeFiles();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	Databases() {
		readFiles();
	}

	boolean validateAccountModification(User u, Account a) {// Validates a user has permissions for some action aggaisnt
															// an account
		if (a.checkUser(u.getUsername()) || Superuser.class.isInstance(u)) {
			return true;
		}
		return false;
	}

	boolean validateAccountInfoCheck(User u, Account a) {// Validates a user has permissions for some action aggaisnt
															// an account
		if (a.checkUser(u.getUsername()) || Employee.class.isInstance(u)) {
			return true;
		}
		return false;
	}

	Account getAccount(Integer a) throws MappingNotFound {
		if (Accountlist.containsKey(a)) {
			return Accountlist.get(a);
		} else {
			System.out.println("\r\nCould not resolve account: " + a);
			throw new MappingNotFound();
		}
	}

	User getUser(String a) throws MappingNotFound {
		if (Userlist.containsKey(a)) {
			return Userlist.get(a);
		} else {
			System.out.println("\r\nCould not resolve User: " + a);
			throw new MappingNotFound();
		}
	}

	void transfer(User u, String w, String d, String a) {
		Double amount = Double.valueOf(a);
		Account withdraw;
		Account deposit;

		try {
			withdraw = getAccount(Integer.parseInt(w));
		} catch (MappingNotFound e) {
			return;
		}

		try {
			deposit = getAccount(Integer.parseInt(d));
		} catch (MappingNotFound e) {
			return;
		}

		if (!validateAccountModification(u, withdraw) && !validateAccountModification(u, deposit)) {
			System.out.println("\r\nAccess denied");
			return;
		}
		if (amount <= 0) {
			System.out.println("\r\nValue must be positive");
			return;
		}
		if (withdraw.getBalance() < amount) {
			System.out.println("\r\nCharge would result in overdraft");
			return;
		}
		withdraw.withdraw(amount);
		deposit.deposit(amount);
		System.out.println("\r\nSucsesfuly transfered " + a + " from " + w + " to " + d);
		writeFiles();
	}

	void withdraw(User u, String w, String a) {
		Double amount = Double.valueOf(a);
		Account withdraw;
		try {
			withdraw = getAccount(Integer.parseInt(w));
		} catch (MappingNotFound e) {
			return;
		}
		if (!validateAccountModification(u, withdraw)) {
			System.out.println("\r\nAccess denied");
			return;
		}
		if (amount <= 0) {
			System.out.println("\r\nValue must be positive");
			return;
		}
		if (withdraw.getBalance() < amount) {
			System.out.println("\r\nCharge would result in overdraft");
			return;
		}
		withdraw.withdraw(amount);
		System.out.println("\r\nSucsesfuly made a withdrawal of " + a + " from " + w);
		writeFiles();
	}

	void Deposit(User u, String d, String a) {
		Double amount = Double.valueOf(a);
		Account deposit;
		try {
			deposit = getAccount(Integer.parseInt(d));
		} catch (MappingNotFound e) {
			return;
		}
		if (!validateAccountModification(u, deposit)) {
			System.out.println("\r\nAccess denied");
			return;
		}
		if (amount <= 0) {
			System.out.println("\r\nValue must be positive");
			return;
		}
		deposit.deposit(amount);
		System.out.println("\r\nSucsesfuly made a deposit of " + a + " from " + d);
		writeFiles();
	}

	boolean makeClient(String username, String password, String fullName) {
		if (!Userlist.containsKey(username)) {
			Userlist.put(username, new Client(username, password, fullName));
			System.out.println("\r\nClient profile created successfully");
			return true;
		}
		System.out.println("\r\nUsername alredy exists.");
		return false;
	}

	boolean makeEmployee(String username, String password, String fullName) {// should be exposed only to superusers
		if (!Userlist.containsKey(username)) {
			Userlist.put(username, new Employee(username, password, fullName));
			System.out.println("\r\nEmployee profile created sucsesfuly");
			writeFiles();
			return true;
		}
		System.out.println("\r\nUsername alredy exists.");
		return false;
	}

	boolean makeSU(String username, String password, String fullName) {// should be exposed only to superusers
		if (!Userlist.containsKey(username)) {
			Userlist.put(username, new Superuser(username, password, fullName));
			System.out.println("\r\nAdministrator profile created successfully");
			writeFiles();
			return true;
		}
		System.out.println("\r\nUsername already exists.");
		return false;
	}

	boolean addUserToAccount(User u, String toadd, String a) {
		User usertoAdd;
		Account account;
		try {
			usertoAdd = getUser(toadd);
			account = getAccount(Integer.parseInt(a));
		} catch (MappingNotFound e) {
			return false;
		}

		if (usertoAdd.getClass().isInstance(Employee.class)) {
			System.out.println("\r\nEmployee profies are not permitted on accounts");
			return false;
		}
		if (!validateAccountModification(u, account)) {
			System.out.println("\r\nAccess denied");
			return false;
		}
		account.addUser(toadd);
		System.out.println("Added " + toadd + " to " + a);
		writeFiles();
		return true;
	}

	boolean removeUserFromAccount(User u, String toRemove, String a) { // expose only ot super
		Account account;
		try {
			account = getAccount(Integer.parseInt(a));
		} catch (MappingNotFound e) {
			return false;
		}
		boolean out = account.removeUser(toRemove);
		writeFiles();
		return out;
	}

	void printAccountInfo(String a, User u) {
		System.out.println();
		Account toPrint;
		try {
			toPrint = getAccount(Integer.parseInt(a));
		} catch (MappingNotFound e) {
			System.out.println("Account not found");
			return;
		}
		if (validateAccountInfoCheck(u, toPrint)) {
			System.out.println(toPrint.toString());
		}
	}

	void printAccounts(User u) {// should only be exposed to employees
		if (Employee.class.isInstance(u)) {
			System.out.println();
			for (Account a : Accountlist.values()) {
				System.out.println(a.toString());
			}
		}
	}

	boolean checkLoggin(String user, String password) {
		User userLoggingIn;
		try {
			userLoggingIn = getUser(user);
		} catch (MappingNotFound e) {
			return false;
		}
		if (password.equals(userLoggingIn.getPassword())) {
			return true;
		}
		return false;
	}

}
