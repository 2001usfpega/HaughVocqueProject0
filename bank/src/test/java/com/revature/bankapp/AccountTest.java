package com.revature.bankapp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AccountTest {

	@Test
	void testToString() {
		Account A = new Account(24.5,1234,"User");
		assertEquals("1234:24.5:User", A.toString());
	}

	@Test
	void testGetAccountNumber() {
		Account A = new Account(24.5,1234,"User");
		assertEquals(1234,A.getAccountNumber());
	}

	@Test
	void testGetBalance() {
		Account A = new Account(24.5,1234,"User");
		assertEquals(24.5, A.getBalance());
	}

	@Test
	void testWithdraw() {
		Account A = new Account(24.5,1234,"User");
		assertEquals(24.5,A.withdraw(-1));
		assertEquals(24.5,A.withdraw(0));
		assertEquals(24.5,A.withdraw(100));
		assertEquals(14.5,A.withdraw(10));
	}

	@Test
	void testDeposit() {
		Account A = new Account(24.5,1234,"User");
		assertEquals(24.5,A.deposit(-1));
		assertEquals(24.5,A.deposit(0));
		assertEquals(124.5,A.deposit(100));
	}

	@Test
	void testCheckUser() {
		Account A = new Account(24.5,1234,"User");
		assertTrue(A.checkUser("User"));
		assertFalse(A.checkUser("Other"));
	}

	@Test
	void testAddUser() {
		Account A = new Account(24.5,1234,"User");
		assertTrue(A.addUser("OtherUser"));
		assertFalse(A.addUser("User"));
		assertTrue(A.checkUser("User"));
		assertTrue(A.checkUser("OtherUser"));
	}

	@Test
	void testRemoveUser() {
		Account A = new Account(24.5,1234,"User");
		A.addUser("OtherUser");
		assertTrue(A.removeUser("OtherUser"));
		assertFalse(A.removeUser("User"));
		A.withdraw(24.5);
		assertTrue(A.removeUser("User"));
	}

}
