package com.example.wps.encryption;

import java.util.Scanner;

import org.apache.commons.lang3.RandomStringUtils;

public class PasswordGenerator {
	
	// Constants

	private static String NUMBERS = "0123456789";
	private static String ALPHABETICCHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static String SPECIALCHAR = "@#&(§!)-_<>,?;.:/=+%^$*€£";

	// Attributes
	
	private Integer length = null;
	private Boolean withNumbers = null;
	private Boolean withAlpha = null;
	private Boolean withSpecial = null;
	private String result = "";

	// Constructor
	
	public PasswordGenerator(Integer length, Boolean withNumbers,
			Boolean withAlpha, Boolean withSpecial) {
		this.length = length;
		this.withNumbers = withNumbers;
		this.withAlpha = withAlpha;
		this.withSpecial = withSpecial;
	}

	public void userInteraction() {

		// For user Input

		this.askUserInput();
		this.setResult(generatePassword(this.getLength(), this.getWithNumbers(), this.getWithAlpha(), this.getWithSpecial()));
		System.out.println("Here is your password : " + this.getResult());

		// Or just to test :
		// this.passwordTest();
	}

	public void askUserInput() {
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter a number for the length of your password : ");
		this.setLength(reader.nextInt());

		System.out
				.println("Do you want NUMBERS in your password ? (true/false) : ");
		this.setWithNumbers(reader.nextBoolean());

		System.out
				.println("Do you want alphabetic characters in your password ? (true/false) : ");
		this.setWithAlpha(reader.nextBoolean());

		System.out
				.println("Do you want special characters in your password ? (true/false) : ");
		this.setWithSpecial(reader.nextBoolean());
		reader.close();
	}

	public String generatePassword(Integer length, Boolean withNumbers,
			Boolean withAlpha, Boolean withSpecial) {

		if (!(withNumbers || withAlpha || withSpecial)) {
			System.out.println("You need something to build your password ! ");
		} else if (!withNumbers && withSpecial && !withAlpha) {
			result = RandomStringUtils
					.random(length, SPECIALCHAR.toCharArray());
		} else if (withNumbers && !withAlpha && !withSpecial) {
			result = RandomStringUtils.random(length, (NUMBERS).toCharArray());
		} else if (!withNumbers && withAlpha && !withSpecial) {
			result = RandomStringUtils.random(length,
					(ALPHABETICCHAR).toCharArray());
		} else if (withNumbers && withAlpha && !withSpecial) {
			result = RandomStringUtils.random(length,
					(NUMBERS + ALPHABETICCHAR).toCharArray());
		} else if (!withNumbers && withAlpha && withSpecial) {
			result = RandomStringUtils.random(length,
					(ALPHABETICCHAR + SPECIALCHAR).toCharArray());
		} else if (withNumbers && !withAlpha && withSpecial) {
			result = RandomStringUtils.random(length,
					(NUMBERS + SPECIALCHAR).toCharArray());
		} else {
			result = RandomStringUtils.random(length,
					(NUMBERS + ALPHABETICCHAR + SPECIALCHAR).toCharArray());
		}
		return result;
	}

	public void passwordTest() {
		// Empty Password
		this.setResult(this.generatePassword(10, false, false, false));
		System.out.println("Here is your password : " + result);

		// Numeric Password
		this.setResult(this.generatePassword(10, true, false, false));
		System.out.println("Here is your password : " + result);

		// AlphaNumeric Password
		this.setResult(this.generatePassword(10, true, true, false));
		System.out.println("Here is your password : " + result);

		// SpecialAlphaNumeric Password
		this.setResult(this.generatePassword(10, true, true, true));
		System.out.println("Here is your password : " + result);

		// Alpha Password
		this.setResult(this.generatePassword(10, false, true, false));
		System.out.println("Here is your password : " + result);

		// SpecialAlpha Password
		this.setResult(this.generatePassword(10, false, true, true));
		System.out.println("Here is your password : " + result);

		// SpecialNumeric Password
		this.setResult(this.generatePassword(10, true, false, true));
		System.out.println("Here is your password : " + result);

		// Special Password
		this.setResult(this.generatePassword(10, false, false, true));
		System.out.println("Here is your password : " + result);
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Boolean getWithNumbers() {
		return withNumbers;
	}

	public void setWithNumbers(Boolean withNumbers) {
		this.withNumbers = withNumbers;
	}

	public Boolean getWithAlpha() {
		return withAlpha;
	}

	public void setWithAlpha(Boolean withAlpha) {
		this.withAlpha = withAlpha;
	}

	public Boolean getWithSpecial() {
		return withSpecial;
	}

	public void setWithSpecial(Boolean withSpecial) {
		this.withSpecial = withSpecial;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}