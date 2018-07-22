/**
 * @(#)WSZiBClientApp.java
 *
 *
 * @author Maciej Maciaszek
 * @version 1.00 2018/07/21
 * @description: Class which contains main Java method
 * 				 and implements the User console based interface
 */

package com.mm.wszib.client;

import java.util.Scanner;

public class WSZiBClientApp {
	
	/**
	 * @param args
	 */
	public static void main(String args[]) {
		
		Client client;
		Scanner sc = new Scanner(System.in);
		String command = "";
		
		if(args != null && args.length == 2) {
			if(args[0].matches("\\S+") && args[1].matches("\\d+")) {
				
				client = new Client(args[0], Integer.valueOf(args[1]));
				
				if(client.checkHostAccessability()) {
					
					manageConnection(client, sc, command);
					
				} else {
					System.out.println("[ERROR]: " + "Cannot connect to: " + 
							client.getHost() + ":" + client.getPort());
				}
			}
		} else {
			
			System.out.println("[ERROR]: " + "Bad arguments JVM - trying to create default connection.");
			
			client = new Client();
			
			if(client.checkHostAccessability()) {
				
				manageConnection(client, sc, command);
				
			} else {
				System.out.println("[ERROR]: " + "Cannot connect to: " + 
						client.getHost() + ":" + client.getPort());
				
				do {
					System.out.println("[INFO]: " + "Type: connect address port OR exit to quit");
					command = sc.next();
					
					if(command.matches("^connect \\S+ \\d+$")) {
						String[] table = command.split(" ");
						client = new Client(table[1],Integer.valueOf(table[2]));
						
						if(client.checkHostAccessability()) {
							
							manageConnection(client, sc, command);
							
						}
					}
				} while(!command.equals("exit"));
				
			}
			
		}
		
		sc.close();
	}
	
	/**
	 * @param client
	 * @param sc
	 * @param command
	 */
	private static void manageConnection(Client client, Scanner sc, String command) {
		System.out.println("[SUCCESS]: " + "Connected to: " + 
				client.getHost() + ":" + client.getPort());
		
		do {
			System.out.println("[INFO]: " + "Available commands: " + 
					"login, register, register-pw-auto,disconnect") ;
			
			command = sc.next();
			
			switch(command) {	
				case "login": {
					System.out.print("Provide username:");
					String username = sc.next();
					System.out.print("\nProvide password:");
					String password = sc.next();
					
					if(!client.login(username, password).equals("Unauthorized")) {	
						
						do {
							System.out.println("[INFO]: " + "Available commands: " + 
									"edit, edit-email, edit-phonenumber, logout") ;
							
							command = sc.next();
							
							switch(command) {
								case "edit": {
									System.out.print("Provide email:");
									String email = sc.next();
									System.out.print("\nProvide phonenumber:");
									String phonenumber = sc.next();
									client.editUser(email, phonenumber);
								};
								break;
								case "edit-email": {
									System.out.print("Provide email:");
									String email = sc.next();
									client.editUserEmail(email);
								};
								break;
								case "edit-phonenumber": {
									System.out.print("Provide phonenumber:");
									String phonenumber = sc.next();
									client.editUserPhoneNumber(phonenumber);

								};
								break;
							}
						} while(!command.equals("logout"));
						
					} else {
						System.out.println("[ERROR]: " + "Bad login/password");
					}
				};
				break;
				
				case "register": {
					validateRegistration(sc, client, false);
				};
				break;
				
				case "register-pw-auto": {
					validateRegistration(sc, client, true);
				};
				break;
			}
		} while(!command.equals("disconnect"));
		
		System.out.println("[INFO]: " + "Disconnected.");
	}
	
	/**
	 * @param sc
	 * @param client
	 * @param isPwGenerated
	 */
	private static void validateRegistration(Scanner sc, Client client, boolean isPwGenerated) {
		
		String username;
		String password;
		String email;
		String phonenumber;
		
		boolean isUserNameValid = false;
		
		do {
			System.out.print("Provide username:");
			username = sc.next();
			isUserNameValid = CustomValidation.validateUsername(username);
			if(!isUserNameValid) {
				System.out.println("[ERROR]: " + "Bad username format");
			}
		} while (!isUserNameValid);
		
		if(!isPwGenerated) {
			boolean isPasswordValid = false;
			
			do {
				System.out.print("\nProvide password:");
				password = sc.next();
				isPasswordValid = CustomValidation.validatePassword(password);
				if(!isPasswordValid) {
					System.out.println("[ERROR]: " + "Bad password format" +"\nPW must atleast containt:" + 
							"\n*8 chars"+"\n*1 capital char"+"\n*1 digit"+"\n*1 special char");
				}
			} while (!isPasswordValid);
		} else {
			password = PasswordGenerator.generate();
			System.out.print("\nPassword generated: " +password);
			
		}
		
		boolean isEmailValid = false;
		
		do {
			System.out.print("\nProvide email:");
			email = sc.next();
			isEmailValid = CustomValidation.validateEmail(email);
			if(!isEmailValid) {
				System.out.println("[ERROR]: " + "Bad email format");
			}
		} while (!isEmailValid);
		
		boolean isPhoneNumberValid = false;
		
		do {
			System.out.print("\nProvide phonenumber:");
			phonenumber = sc.next();
			if(phonenumber == null) {
				System.out.println("[ERROR]: " + "Phone number cannot be null");
			} else {
				isPhoneNumberValid = true;
			}
		} while (!isPhoneNumberValid);
		
		client.register(username, password, email, phonenumber);
		
	}
}