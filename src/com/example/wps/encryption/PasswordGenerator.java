package com.example.wps.encryption;

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

	public PasswordGenerator() {
	}

	public PasswordGenerator(Integer length, Boolean withNumbers,
			Boolean withAlpha, Boolean withSpecial) {
		this.length = length;
		this.withNumbers = withNumbers;
		this.withAlpha = withAlpha;
		this.withSpecial = withSpecial;
	}

	public String generatePassword() {

		if (!(this.getWithNumbers() || this.getWithAlpha() || this
				.getWithSpecial())) {
			System.out.println("You need something to build your password !");
		} else if (!this.getWithNumbers() && !this.getWithAlpha()
				&& this.getWithSpecial()) {
			result = RandomStringUtils
					.random(length, SPECIALCHAR.toCharArray());
		} else if (this.getWithNumbers() && !this.getWithAlpha()
				&& !this.getWithSpecial()) {
			result = RandomStringUtils.random(length, (NUMBERS).toCharArray());
		} else if (!this.getWithNumbers() && this.getWithAlpha()
				&& !this.getWithSpecial()) {
			result = RandomStringUtils.random(length,
					(ALPHABETICCHAR).toCharArray());
		} else if (this.getWithNumbers() && this.getWithAlpha()
				&& !this.getWithSpecial()) {
			result = RandomStringUtils.random(length,
					(NUMBERS + ALPHABETICCHAR).toCharArray());
		} else if (!this.getWithNumbers() && this.getWithAlpha()
				&& this.getWithSpecial()) {
			result = RandomStringUtils.random(length,
					(ALPHABETICCHAR + SPECIALCHAR).toCharArray());
		} else if (this.getWithNumbers() && !this.getWithAlpha()
				&& this.getWithSpecial()) {
			result = RandomStringUtils.random(length,
					(NUMBERS + SPECIALCHAR).toCharArray());
		} else {
			result = RandomStringUtils.random(length,
					(NUMBERS + ALPHABETICCHAR + SPECIALCHAR).toCharArray());
		}
		return result;
	}

	public String generatePassword(Integer length, Boolean withNumbers,
			Boolean withAlpha, Boolean withSpecial) {

		this.setAll(length, withNumbers, withAlpha, withSpecial);
		return this.generatePassword();

	}

	public void setAll(Integer length, Boolean withNumbers, Boolean withAlpha,
			Boolean withSpecial) {
		this.length = length;
		this.withNumbers = withNumbers;
		this.withAlpha = withAlpha;
		this.withSpecial = withSpecial;
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

	public void passwordTest() {
		// Empty Password
		this.setLength(10);
		this.setWithNumbers(false);
		this.setWithAlpha(false);
		this.setWithSpecial(false);
		this.setResult(this.generatePassword());
		System.out.println("Here is your password : " + result);

		// Numeric Password
		this.setWithNumbers(true);
		this.setResult(this.generatePassword());
		System.out.println("Here is your password : " + result);

		// AlphaNumeric Password
		this.setWithAlpha(true);
		this.setResult(this.generatePassword());
		System.out.println("Here is your password : " + result);

		// SpecialAlphaNumeric Password
		this.setWithSpecial(true);
		this.setResult(this.generatePassword());
		System.out.println("Here is your password : " + result);

		// SpecialAlpha Password
		this.setWithNumbers(false);
		this.setResult(this.generatePassword());
		System.out.println("Here is your password : " + result);

		// Alpha Password
		this.setWithSpecial(false);
		this.setResult(this.generatePassword());
		System.out.println("Here is your password : " + result);

		// SpecialNumeric Password
		this.setWithNumbers(true);
		this.setWithAlpha(false);
		this.setWithSpecial(true);
		this.setResult(this.generatePassword());
		System.out.println("Here is your password : " + result);

		// Special Password
		this.setWithNumbers(false);
		this.setResult(this.generatePassword());
		System.out.println("Here is your password : " + result);
	}
}