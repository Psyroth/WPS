package com.example.wps.encryption;

import java.util.Scanner;

import org.apache.commons.lang3.RandomStringUtils;

public class PasswordGenerator {

	private static String NUMBERS = "0123456789";
	private static String ALPHABETICCHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static String SPECIALCHAR = "@#&(§!)-_<>,?;.:/=+%^$*€£";

	private static Integer length = null;
	private static Boolean withNUMBERS = null;
	private static Boolean withAlpha = null;
	private static Boolean withSpecial = null;
	
	private static String result = "";

	// Need to integrate with a small GUI

	public static void main(String[] args) {

		// For user Input

		userInput();
		result = generatePassword(length, withNUMBERS, withAlpha,
				withSpecial);
		System.out.println("Here is your password : " + result);

		// Or just to test :
		// passwordTest();
	}

	public static void userInput() {
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter a number for the length of your password : ");
		length = reader.nextInt();

		System.out
				.println("Do you want NUMBERS in your password ? (true/false) : ");
		withNUMBERS = reader.nextBoolean();

		System.out
				.println("Do you want alphabetic characters in your password ? (true/false) : ");
		withAlpha = reader.nextBoolean();

		System.out
				.println("Do you want special characters in your password ? (true/false) : ");
		withSpecial = reader.nextBoolean();
	}

	public static String generatePassword(Integer length, Boolean withNUMBERS,
			Boolean withAlpha, Boolean withSpecial) {

		String result = "";

		if (!(withNUMBERS || withAlpha || withSpecial)) {
			System.out.println("You need something to build your password ! ");
		} else if (withSpecial && !withNUMBERS && !withAlpha) {
			result = RandomStringUtils
					.random(length, SPECIALCHAR.toCharArray());
		} else if (withNUMBERS && !withAlpha && !withSpecial) {
			result = RandomStringUtils.random(length, (NUMBERS).toCharArray());
		} else if (!withNUMBERS && withAlpha && !withSpecial) {
			result = RandomStringUtils.random(length,
					(ALPHABETICCHAR).toCharArray());
		} else if (withNUMBERS && withAlpha && !withSpecial) {
			result = RandomStringUtils.random(length,
					(NUMBERS + ALPHABETICCHAR).toCharArray());
		} else if (!withNUMBERS && withAlpha && withSpecial) {
			result = RandomStringUtils.random(length,
					(ALPHABETICCHAR + SPECIALCHAR).toCharArray());
		} else if (withNUMBERS && !withAlpha && withSpecial) {
			result = RandomStringUtils.random(length,
					(NUMBERS + SPECIALCHAR).toCharArray());
		} else {
			result = RandomStringUtils.random(length,
					(NUMBERS + ALPHABETICCHAR + SPECIALCHAR).toCharArray());
		}
		return result;
	}

	public static void passwordTest() {
		// Empty Password
		result = generatePassword(10, false, false, false);
		System.out.println("Here is your password : " + result);

		// Numeric Password
		result = generatePassword(10, true, false, false);
		System.out.println("Here is your password : " + result);

		// AlphaNumeric Password
		result = generatePassword(10, true, true, false);
		System.out.println("Here is your password : " + result);

		// SpecialAlphaNumeric Password
		result = generatePassword(10, true, true, true);
		System.out.println("Here is your password : " + result);

		// Alpha Password
		result = generatePassword(10, false, true, false);
		System.out.println("Here is your password : " + result);

		// SpecialAlpha Password
		result = generatePassword(10, false, true, true);
		System.out.println("Here is your password : " + result);

		// SpecialNumeric Password
		result = generatePassword(10, true, false, true);
		System.out.println("Here is your password : " + result);

		// Special Password
		result = generatePassword(10, false, false, true);
		System.out.println("Here is your password : " + result);
	}
}