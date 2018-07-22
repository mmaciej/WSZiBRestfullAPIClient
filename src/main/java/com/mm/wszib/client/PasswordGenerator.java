/**
 * @(#)PasswordGenerator.java
 *
 *
 * @author Maciej Maciaszek
 * @version 1.00 2018/07/21
 * @description: Class which describes how the password is generated
 */

package com.mm.wszib.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

public class PasswordGenerator {
	
	private static final String ALPHABET = "abcdefghijklmnoprstuwqxyz";
	private static final String NUMBERS = "123456789";
	private static final String SPECIAL_CHARS = "!@#$%^&*()<>,.;'[]";
	
	/**
	 * Returns new (String) password based on this pattern:
	 * 1. Minimum 8 chars length
	 * 2. Atleast 1 LowerCase char
	 * 3. Atleast 1 UpperCase char
	 * 4. Atleast 1 special char
	 * 5. Atleast 1 digit
	 * 
	 * @return
	 */
	public static String generate() {
		
		String password = RandomStringUtils.randomAlphanumeric(15);
		
		Random r = new Random();
		
		char[] pwTable = password.toCharArray();
		
		List<Integer> numbersArray = new ArrayList<Integer>();
		
        do
        {
            int next = r.nextInt(password.length()-1);
            if (!numbersArray.contains(next))
            {
            	numbersArray.add(next);
            }
        } while (numbersArray.size() < 4);
		
        pwTable[numbersArray.get(0)] = ALPHABET.charAt(r.nextInt(ALPHABET.length()-1));
        pwTable[numbersArray.get(1)] = ALPHABET.toUpperCase().charAt(r.nextInt(ALPHABET.length()-1));
        pwTable[numbersArray.get(2)] = NUMBERS.charAt(r.nextInt(NUMBERS.length()-1));
        pwTable[numbersArray.get(3)] = SPECIAL_CHARS.charAt(r.nextInt(SPECIAL_CHARS.length()-1));
        
        password = new String(pwTable);
        return password;
	}
}
