/**
 * @(#)CustomValidation.java
 *
 *
 * @author Maciej Maciaszek
 * @version 1.00 2018/07/21
 * @description: Class which describes validation input
 */

package com.mm.wszib.client;

public class CustomValidation {
	
	/**
	 * Checks if username parameter: 
	 * 1:contains whitespaces
	 * 2:has atleast 3 word characters
	 * 
	 * @param username
	 * @return
	 * @
	 */
	public static boolean validateUsername(String username) {
		
		if(username != null) {
			if(!hasWhiteSpace(username) && username.matches("\\w{3,}")) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Checks if password parameter: 
	 * 1:contains whitespaces
	 * 2:has atleast 8 characters
	 * 3:contains atleast 1 UpperCase later
	 * 4:contains atleast 1 Special character
	 * 5:contains atleast 1 digit
	 * 
	 * @param password
	 * @return
	 */
	public static boolean validatePassword(String password) {
		
		if(password != null) {
			if(!hasWhiteSpace(password) && password.length() >= 8 && 
					password.matches(".*[A-Z]+.*") && password.matches(".*\\W+.*") && password.matches(".*\\d+.*")) {
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Checks if email parameter: 
	 * 1:contains whitespaces
	 * 2:is a valid regex
	 * 
	 * @param email
	 * @return
	 */
	public static boolean validateEmail(String email) {
		if(email != null) {
			if(!hasWhiteSpace(email) && email.matches("^\\S+@\\S+$")) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Checks if txt parameter: 
	 * 1:contains whitespaces
	 * 
	 * @param txt
	 * @return
	 */
	private static boolean hasWhiteSpace(String txt) {
		if(txt.matches("\\s+")) {
			return true;
		}
		
		return false;
	}
	
}
