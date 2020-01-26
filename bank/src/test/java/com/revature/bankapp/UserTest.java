package com.revature.bankapp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UserTest {
	@Test
	void testGetUsername() {
		User U = new Client("Person","Password","A person");
		assertEquals("Person",U.getUsername());
	}

	@Test
	void testGetPassword() {
		User U = new Client("Person","Password","A person");
		assertEquals("Password",U.getPassword());
	}

	@Test
	void testGetFullName() {
		User U = new Client("Person","Password","A person");
		assertEquals("A person",U.getFullName());
	}

	@Test
	void testSetFullName() {
		User U = new Client("Person","Password","A person");
		U.setFullName("A Permison");
		assertEquals("A Permison",U.getFullName());
	}

	@Test
	void testSetPassword() {
		User U = new Client("Person","Password","A person");
		U.setPassword("A Better Password");
		assertEquals("A Better Password",U.getPassword());
	}


	@Test
	void testToString() {
		User U = new Client("Person","Password","A person");
		assertEquals("Client:Person:A person",U.toString());
		U = new Employee("Person","Password","A person");
		assertEquals("Employee:Person:A person",U.toString());
		U = new SuperUser("Person","Password","A person");
		assertEquals("Admin:Person:A person",U.toString());
	}

}
