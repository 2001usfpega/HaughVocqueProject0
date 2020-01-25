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
	//Fields for File IO
	//Strings are the file location
	//colections are the live datastructure
	private static final String AccountFile = "Accounts.txt";
	public static HashMap<Integer, Account> Accountlist = new HashMap<Integer, Account>();
	private static final String UserFile = "Users.txt";
	public static HashMap<String, User> Userlist = new HashMap<String, User>();

	// write method
	public static void writeFiles() {
		ObjectOutputStream objectOut;
		try {
			//Creates the filesystem pointer and the object to bytestream converter Class
			objectOut = new ObjectOutputStream(new FileOutputStream(AccountFile));
			//Selects the serializable object (in this case the account list HashMap) to write to the file, converts it as is, and writes to the file.
			objectOut.writeObject(Accountlist);
			//closes the system resources
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
			//reads a file from the system and interperets it as a serialized object
			objectIn = new ObjectInputStream(new FileInputStream(AccountFile));
			// Casts the read object to the corect type and assigns it a space in the heap
			Accountlist = (HashMap<Integer, Account>) objectIn.readObject();
			//closes the system resources
			objectIn.close();
			objectIn = new ObjectInputStream(new FileInputStream(UserFile));
			Userlist = (HashMap<String, User>) objectIn.readObject();
			objectIn.close();
		} catch (FileNotFoundException e) {
			//creates a default Superuser profile if no root user exists
			Userlist.putIfAbsent("root",new Superuser("root", "root", "Default Superuser, change to secure password"));
			//corrects the missing files by running the write method to write all files
			writeFiles();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//the Database object loads files from the system when instantiated
	Databases() {
		readFiles();
	}
//
	boolean validateAccountModification(User u, Account a) {// Validates a user has permissions for some action aggaisnt
																													// an account
		if (a.checkUser(u.getUsername()) || Superuser.class.isInstance(u)) {
			//a user must either be an account owner or an administrator to pass
			return true;
		}
		return false;
	}

	boolean validateAccountInfoCheck(User u, Account a) {// Validates a user has permissions for some action aggaisnt
															// an account
		if (a.checkUser(u.getUsername()) || Employee.class.isInstance(u)) {
			//for actions that employees, administrors and account owners can take
			return true;
		}
		return false;
	}

	Account getAccount (Integer a) throws MappingNotFound {
		//retreves an account form the LOADED datastructure
		if(Accountlist.containsKey(a)){
			return Accountlist.get(a);
		}else{
			//prints to console if the account cannot be found
			System.out.println("\r\nCould not resolve account: "+a);
			throw new MappingNotFound();
			//Passes an error to the caller
		}
	}
	User getUser (String a) throws MappingNotFound {
		//retreves a user type object from the loaded database
		if(Userlist.containsKey(a)){
			return Userlist.get(a);
		} else {
			System.out.println("\r\nCould not resolve User: " + a);
			throw new MappingNotFound();
		}
	}

	void transfer(User u, String w, String d, String a){
		//u is the currently loged in user who requested the action, w and d are the
		//targets of the withdrawal and deposit, a is the ammount as a string entered  by the user
		Double amount = Double.valueOf(a);
		Account withdraw;
		Account deposit;

		try{
			//retreves the account objects from the in memory database structure
			withdraw = getAccount(Integer.parseInt(w));
			deposit = getAccount(Integer.parseInt(d));
		} catch (MappingNotFound e) {
			return;
		}

		if(!validateAccountModification(u, withdraw)&&!validateAccountModification(u, deposit)){
			//denies access based on bank policy, account owners and admins only
				System.out.println("\r\nAccess denied");
				return;
		}
		if(amount <= 0){
			//safetychecks user input interactivly, no negative transferes
			System.out.println("\r\nValue must be positive");
			return;
		}
		if(withdraw.getBalance()<amount){
			//ensures no overdraft will occur
			System.out.println("\r\nCharge would result in overdraft");
			return;
		}
		//passes the transfer as a withdrawal and a deposit to the actual account
		withdraw.withdraw(amount);
		deposit.deposit(amount);
		// informs the user of sucses
		System.out.println("\r\nSucsesfuly transfered "+a+" from "+w+" to " + d);
		writeFiles();
		//saves files
	}


	void withdraw(User u, String w, String a){
		//format of inputs is User calling action, string representing the account number, string representing the double ammount
		Double amount = Double.valueOf(a);
		Account withdraw;
		try{
			//gets the account object form the loaded database
			withdraw = getAccount(Integer.parseInt(w));
		} catch (MappingNotFound e) {
			return;
		}
		if(!validateAccountModification(u, withdraw)){
			//validates user permisions
			System.out.println("\r\nAccess denied");
			return;
		}
		if(amount <= 0){
			//validates user input interactivly
			System.out.println("\r\nValue must be positive");
			return;
		}
		if (withdraw.getBalance() < amount) {
			System.out.println("\r\nCharge would result in overdraft");
			return;
		}
		//executes the withdraw on the account
		withdraw.withdraw(amount);
		System.out.println("\r\nSucsesfuly made a withdrawal of "+a+" from "+w);
		//writes changes to the on disk database
		writeFiles();
	}

	void Deposit(User u, String d, String a){
		//format of inputs is User calling action, string representing the account number, string representing the double ammount
		Double amount = Double.valueOf(a);
		Account deposit;
		try{
			//retreves target account
			deposit = getAccount(Integer.parseInt(d));
		} catch (MappingNotFound e) {
			return;
		}
		//provides interactive validation
		if(!validateAccountModification(u, deposit)){
			System.out.println("\r\nAccess denied");
			return;
		}
		if (amount <= 0) {
			System.out.println("\r\nValue must be positive");
			return;
		}
		//executes transaction
		deposit.deposit(amount);
		System.out.println("\r\nSucsesfuly made a deposit of "+a+" from "+d);
		//saves data to disk
		writeFiles();
	}

	boolean makeClient(String username,String password, String fullName){
		//creates a new Client type user account, shoud be avalible before login
		if(!Userlist.containsKey(username)){
			//validates username will not colide
			Userlist.put(username ,new Client(username, password, fullName));
			//creates new user
			System.out.println("\r\nClient profile created sucsesfuly");
			return true;
		}
		//informs reason for failure
		System.out.println("\r\nUsername alredy exists.");
		return false;
	}

	boolean makeEmployee(String username,String password, String fullName){//should be exposed only to superusers
		//ensures username does not colide
		if(!Userlist.containsKey(username)){
			//creates the new employee
			Userlist.put(username ,new Employee(username, password, fullName));
			System.out.println("\r\nEmployee profile created sucsesfuly");
			writeFiles();
			return true;
		}
		System.out.println("\r\nUsername alredy exists.");
		return false;
	}
	boolean makeSU(String username,String password, String fullName){//should be exposed only to superusers
		if(!Userlist.containsKey(username)){
			//same as other user creation methods
			Userlist.put(username ,new Superuser(username, password, fullName));
			System.out.println("\r\nAdministrator profile created sucsesfuly");
			writeFiles();
			return true;
		}
		System.out.println("\r\nUsername already exists.");
		return false;
	}
	boolean addUserToAccount(User u, String toadd, String a){
		//adss the user with the name passed as toadd to the account number passed as string a. User u is used for permisions validation,
		// though this should probably only be exposed to superusers
		User usertoAdd;
		Account account;
		//gets the targest from the hashmaps
		try{
			usertoAdd = getUser(toadd);
			account = getAccount(Integer.parseInt(a));
		} catch (MappingNotFound e) {
			return false;
		}
		//preforms validation
		if(usertoAdd.getClass().isInstance(Employee.class)){
			//employees are implicetly seperate from cusotmers
			System.out.println("\r\nEmployee profies are not permitted on accounts");
			return false;
		}
		//should probably replace with a direct superuser check
		if(!validateAccountModification(u, account)){
			System.out.println("\r\nAccess denied");
			return false;
		}
		//adds the user to the account
		account.addUser(toadd);
		System.out.println("Added "+toadd+" to "+a);
		//writes to disk
		writeFiles();
		return true;
	}
	boolean removeUserFromAccount(User u, String toRemove, String a){ //expose only to superusers
		Account account;
		//loads targets into memory
		try{
			account = getAccount(Integer.parseInt(a));
		} catch (MappingNotFound e) {
			return false;
		}
		//no validation neded handeld internaly
			boolean out = account.removeUser(toRemove);
			//Interactivity on sucss and failure
			System.out.print(out?"Removed user: "+toRemove: "could not remove user, check account to ensure balence is zero and user name is corect");
			writeFiles();
			return out;
	}
	void printAccountInfo(String a, User u){
		//prints account info for account number given as string a
	 System.out.println();
	 Account toPrint;
	 //gest the account object
	 try{
		 toPrint = getAccount(Integer.parseInt(a));
	 } catch (MappingNotFound e) {
		 System.out.println("Account not found");
		 return;
	 }
	 //validates the user permissions and outputs
	 if(validateAccountInfoCheck(u, toPrint)){
		 System.out.println(toPrint.toString());
	 }
 	}
	void printAccounts(User u){//should only be exposed to employees
			if(Employee.class.isInstance(u)){
				//validates the user attempting is an employee
				System.out.println();
				for(Account a: Accountlist.values()){
					//prints all the accounts in memory
					System.out.println(a.toString());
				}
			}
		}

	boolean checkLoggin(String user, String password){
		//for login function, takes a username and password and returns true if they match an obect in the database
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
