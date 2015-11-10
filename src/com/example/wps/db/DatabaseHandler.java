package com.example.wps.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.example.wps.db.Account;
import com.example.wps.db.XMLHandler;

public class DatabaseHandler {

	private static String xmlStringDatabase;
	private static List<Element> elementList;
	private static Document document;

	public static void initDatabaseHandler() {
		xmlStringDatabase = null;
		elementList = null;
		document = null;
	}
	
	public static void initDatabaseHandler(String xmlSD) {
		xmlStringDatabase = xmlSD;
		document = XMLHandler.buildDocument(xmlStringDatabase);
		elementList = XMLHandler.xmlToElementList();
	}
	
	/*
	 * Returns an empty .xml file as a String as the database.
	 */
	public static String createEmptyDatabase() {

		String database = "<?xml version=\"1.0\""
				+ "encoding=\"UTF-8\" standalone=\"no\"?>" + "<WPS-database>"
				+ "<accounts>" + "</accounts>" + "</WPS-database>";

		return database;
	}

	/* Adds the "account" to the database. */
	public static void addAccount(Account account) {

		try {

			if (!accountExists(account)) {

				Element rootElement = document.createElement(account.getName()
						+ "Account");

				XMLHandler.addNode("name", account.getName(), rootElement);
				XMLHandler.addNode("id", account.getId(), rootElement);
				XMLHandler.addNode("password", account.getPassword(), rootElement);
				XMLHandler.addNode("url", account.getUrl(), rootElement);
				XMLHandler.addNode("lastAccess", account.getLastAccess(), rootElement);
				XMLHandler.addNode("note", account.getNote(), rootElement);
				XMLHandler.addNode("category", account.getCategory(), rootElement);
				XMLHandler.addNode("favorite", Boolean.toString(account.getIsFavorite()),
						rootElement);

				NodeList nodeList = document.getElementsByTagName("accounts");
				nodeList.item(0).appendChild(rootElement);
				XMLHandler.xmlToElementList();
			}

		} catch (Exception e) {
			System.out.println("Failed to add " + account.getName()
					+ " account.");
			e.printStackTrace();
		}
	}

	/* Removes the account from the database. */
	public static void removeAccount(Account account) {

		NodeList nodeList = document.getElementsByTagName("accounts").item(0)
				.getChildNodes();

		try {

			if (accountExists(account)) {
				Element element = (Element) XMLHandler.getNodeByValue(account.getName(),
						nodeList);
				Element parentElement = (Element) element.getParentNode();
				element.getParentNode().getParentNode()
						.removeChild(parentElement);
				XMLHandler.xmlToElementList();
			}
		}

		catch (Exception e) {
			System.out.println("Failed to remove " + account.getName()
					+ " account.");
			e.printStackTrace();
		}
	}

	/* Modify the oldAccount to the newAccount. */
	public static void modifyAccount(Account oldAccount, Account newAccount) {

		try {
			if (accountExists(oldAccount)) {
				removeAccount(oldAccount);
				addAccount(newAccount);
			}
		} catch (Exception e) {
			System.out.println("Failed to modify " + oldAccount.getName()
					+ " account.");
			e.printStackTrace();
		}
	}
	
	/* Returns if the account exists or not. */
	public static boolean accountExists(Account account) {

		for (int i = 0; i < elementList.size(); i++) {
			String retrievedName = elementList.get(i)
					.getElementsByTagName("name").item(0).getTextContent();
			if (account.getName().equals(retrievedName)) {
				return true;
			}
		}
		System.out.println("\nA " + account.getName()
				+ " account does not exist yet.");
		return false;
	}
	
	/* Returns true if the account was accessed more than 4 months ago. */
	public static boolean accessedLongAgo(Account account) {

		Boolean accessedLongAgo = false;
		DateTime today = new DateTime();
		DateTimeFormatter formatter = DateTimeFormat
				.forPattern("yyyy-MM-dd HH:mm:ss");

		DateTime lastAccess = formatter.parseDateTime(account.getLastAccess());
		Interval i = new Interval(lastAccess, today);

		if ((i.toPeriod().getYears() > 0) || (i.toPeriod().getMonths() > 4)) {
			accessedLongAgo = true;
		}
		return accessedLongAgo;
	}

	/* Returns all the accounts in database. */
	public static List<Account> getAllAccounts() {

		List<Account> allAccounts = new ArrayList<Account>();

		for (int i = 0; i < elementList.size(); i++) {

			Element currentElement = elementList.get(i);
			Account retrievedAccount = XMLHandler.elementToObject(currentElement);
			allAccounts.add(retrievedAccount);
		}

		return allAccounts;
	}

	/* Returns all the accounts in a given category. */
	public static List<Account> getAllAccountsInCategory(String category) {

		List<Account> categoryAccounts = new ArrayList<Account>();

		for (int i = 0; i < elementList.size(); i++) {

			Element currentElement = elementList.get(i);
			String retrievedCategory = currentElement
					.getElementsByTagName("category").item(0).getTextContent();

			if (category.equals(retrievedCategory)) {
				Account categoryAccount = XMLHandler.elementToObject(currentElement);
				categoryAccounts.add(categoryAccount);
			}
		}
		return categoryAccounts;
	}

	/* Returns all favorite accounts. */
	public static List<Account> getAllFavoriteAccounts() {
		List<Account> favoriteAccounts = new ArrayList<Account>();

		for (int i = 0; i < elementList.size(); i++) {

			Element currentElement = elementList.get(i);
			Boolean isFavorite = Boolean.parseBoolean(currentElement
					.getElementsByTagName("favorite").item(0).getTextContent());

			if (isFavorite) {
				Account favoriteAccount = XMLHandler.elementToObject(currentElement);
				favoriteAccounts.add(favoriteAccount);
			}
		}
		return favoriteAccounts;
	}

	/*
	 * Sorts the Accounts in the accountsList by their name attribute
	 * (Alphabetic order)
	 */
	public static void sortAccountListByAlphabeticOrder(ArrayList<Account> accountsList) {
		Collections.sort(accountsList, Account.COMPARE_BY_NAME);
	}

	/*
	 * Sorts the Accounts in the accountsList by their lastAccess attribute
	 * (most recent at the beginning)
	 */
	public static void sortAccountListByLastAccess(ArrayList<Account> accountsList) {
		Collections.sort(accountsList, Account.COMPARE_BY_LASTACCESS);
	}

	/* Print the database using the elementList. */
	public static void printDatabase() {

		for (int i = 0; i < elementList.size(); i++) {
			System.out.println("\nAccount Name : "
					+ elementList.get(i).getElementsByTagName("name").item(0)
							.getTextContent());
			System.out.println("Identifier : "
					+ elementList.get(i).getElementsByTagName("id").item(0)
							.getTextContent());
			System.out.println("Password : "
					+ elementList.get(i).getElementsByTagName("password")
							.item(0).getTextContent());
			System.out.println("Url : "
					+ elementList.get(i).getElementsByTagName("url").item(0)
							.getTextContent());
			System.out.println("Last Accessed : "
					+ elementList.get(i).getElementsByTagName("lastAccess")
							.item(0).getTextContent());
			System.out.println("Note : "
					+ elementList.get(i).getElementsByTagName("note").item(0)
							.getTextContent());
			System.out.println("Category : "
					+ elementList.get(i).getElementsByTagName("category")
							.item(0).getTextContent());
		}
	}
}