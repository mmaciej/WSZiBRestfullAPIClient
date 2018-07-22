/**
 * @(#)User.java
 *
 *
 * @author Maciej Maciaszek
 * @version 1.00 2018/07/21
 * @description: Class which describes how the User object should be managed
 */

package com.mm.wszib.client;

public class User {
	
	private String userName;

	private String password;
	
	private String email;
	
	private String phoneNumber;
	
	public User() {};
	
	public User(String userName, String password, String email, String phoneNumber) {
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	public User(String userName, String email, String phoneNumber) {
		this.userName = userName;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}
	
	public User(String userName) {
		this.userName = userName;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
}
