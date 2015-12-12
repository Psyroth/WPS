package com.example.wps.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.content.Context;

import com.example.wps.db.Account;
import com.example.wps.encryption.Encryption;

public class AccountDatabase extends Observable {

	private String filename;
	private byte[] key;
	private Context context;
	private Document document;

	private static AccountDatabase instance = null;

	private AccountDatabase(String filename, String serialNumber,
			String nfcTag, Context context) throws NoSuchAlgorithmException,
			UnsupportedEncodingException, Exception {
		this.filename = filename;
		this.key = Encryption.xor(Encryption.sha1(serialNumber),
				Encryption.sha1(nfcTag));
		this.context = context;
		File file = new File(context.getFilesDir(), filename);

		if (file.exists()) {
			System.out.println("File exists");
			document = openDatabase(file);
		} else {
			System.out.println("File does not exist");
			document = createEmptyDatabase();
			saveDatabase();
		}
	}

	public static void initialize(String filename, String serialNumber,
			String nfcTag, Context context) throws NoSuchAlgorithmException,
			UnsupportedEncodingException, Exception {
		instance = new AccountDatabase(filename, serialNumber, nfcTag, context);
	}

	public static AccountDatabase getInstance() {
		return instance;
	}

	/*
	 * Load an existing database.
	 */
	private Document openDatabase(File xmlFile) throws SAXException,
			IOException, ParserConfigurationException, InvalidKeyException,
			BadPaddingException, IllegalBlockSizeException,
			NoSuchAlgorithmException, NoSuchPaddingException {
		byte[] encodedDatabase = new byte[(int) xmlFile.length()];
		FileInputStream inputStream = new FileInputStream(xmlFile);
		inputStream.read(encodedDatabase);
		inputStream.close();
		String database = Encryption.decrypt(encodedDatabase, key);
		Document result = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder()
				.parse(new InputSource(new StringReader(database)));
		return result;
	}

	/*
	 * Create an empty database.
	 */
	private Document createEmptyDatabase() throws SAXException, IOException,
			ParserConfigurationException {

		String databaseContent = "<?xml version=\"1.0\""
				+ "encoding=\"UTF-8\" standalone=\"no\"?>"
				+ "<WPS-database>"
				+ "<accounts>"
				+ "</accounts>"
				+ "</WPS-database>";

		document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new InputSource(new StringReader(databaseContent)));

		return document;
	}

	public static void addTestAccountsToDatabase() {

		Account oldAccountTest = new Account("Facebook",
				"facebookUser@hotmail.com", "facebook",
				"https://www.facebook.com", "2014-09-12 22:00:00",
				"Less useful than Linkedin", "Social Network", true);

		AccountDatabase.getInstance().addAccount(oldAccountTest);
	}

	/* Adds a node named "tagName" with "value" as value and "parent" as parent. */
	private void addNode(String tagName, String value, Node parent) {

		// Create a new Node with the given tag name
		Node node = document.createElement(tagName);

		// Add the node value as a child text node
		Text nodeVal = document.createTextNode(value);
		node.appendChild(nodeVal);

		// Add the new node structure to the parent node
		parent.appendChild(node);
	}

	/* Returns the node with the "value" in the nodeList, null otherwise. */
	private static Node getNodeByValue(String value, NodeList nodeList) {

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			NodeList childNodes = node.getChildNodes();

			for (int j = 0; j < childNodes.getLength(); j++) {
				Node data = childNodes.item(j);

				if (data.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) data;

					if (element.getTextContent().equalsIgnoreCase(value)) {
						return data;
					}
				}
			}
		}
		return null;
	}

	/* Returns an account made from the element. */
	private static Account elementToAccount(Element element) {
		String name = element.getElementsByTagName("name").item(0)
				.getTextContent();
		String id = element.getElementsByTagName("id").item(0).getTextContent();
		String password = element.getElementsByTagName("password").item(0)
				.getTextContent();
		String url = element.getElementsByTagName("url").item(0)
				.getTextContent();
		String lastAccess = element.getElementsByTagName("lastAccess").item(0)
				.getTextContent();
		String note = element.getElementsByTagName("note").item(0)
				.getTextContent();
		String category = element.getElementsByTagName("category").item(0)
				.getTextContent();
		Boolean favorite = Boolean.parseBoolean(element
				.getElementsByTagName("favorite").item(0).getTextContent());

		return new Account(name, id, password, url, lastAccess, note, category,
				favorite);
	}

	/* Adds the "account" to the database. */
	public void addAccount(Account account) {

		try {

			if (!accountExists(account)) {

				Element rootElement = document.createElement("account");

				addNode("name", account.getName(), rootElement);
				addNode("id", account.getId(), rootElement);
				addNode("password", account.getPassword(), rootElement);
				addNode("url", account.getUrl(), rootElement);
				addNode("lastAccess", account.getLastAccess(), rootElement);
				addNode("note", account.getNote(), rootElement);
				addNode("category", account.getCategory(), rootElement);
				addNode("favorite", Boolean.toString(account.getIsFavorite()),
						rootElement);

				NodeList nodeList = document.getElementsByTagName("accounts");
				nodeList.item(0).appendChild(rootElement);
				try {
					saveDatabase();
					setChanged();
					notifyObservers();
				} catch (InvalidKeyException | NoSuchAlgorithmException
						| NoSuchPaddingException | IllegalBlockSizeException
						| BadPaddingException | TransformerException
						| TransformerFactoryConfigurationError | IOException e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			System.out.println("Failed to add " + account.getName()
					+ " account.");
			e.printStackTrace();
		}

	}

	public void removeAccount(Account account) {
		removeAccount(account.getName());
	}

	/* Removes the account from the database. */
	public void removeAccount(String accountName) {

		NodeList nodeList = document.getElementsByTagName("accounts").item(0)
				.getChildNodes();

		try {

			if (accountExists(accountName)) {
				Element element = (Element) getNodeByValue(accountName,
						nodeList);
				Element parentElement = (Element) element.getParentNode();
				element.getParentNode().getParentNode()
						.removeChild(parentElement);
			}
		}

		catch (Exception e) {
			System.out.println("Failed to remove " + accountName + " account.");
			e.printStackTrace();
		}
		try {
			saveDatabase();
		} catch (InvalidKeyException | NoSuchAlgorithmException
				| NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException | TransformerException
				| TransformerFactoryConfigurationError | IOException e) {
			e.printStackTrace();
		}
		setChanged();
		notifyObservers();
	}

	/* Modify the oldAccount to the newAccount. */
	public void modifyAccount(Account newAccount) {

		try {
			if (accountExists(newAccount.getName())) {
				removeAccount(newAccount.getName());
				addAccount(newAccount);
			}
		} catch (Exception e) {
			System.out.println("Failed to modify " + newAccount.getName()
					+ " account.");
			e.printStackTrace();
		}
	}

	public boolean accountExists(Account account) {
		return accountExists(account.getName());
	}

	/* Returns if the account exists or not. */
	public boolean accountExists(String accountName) {

		for (Account currentAccount : getAllAccounts()) {
			if (accountName.equals(currentAccount.getName())) {
				return true;
			}
		}
		System.out.println("\nA " + accountName
				+ " account does not exist yet.");
		return false;
	}

	/* Returns the account that has "name" as account name */
	public Account getAccountFromName(String name) {

		Account retrievedAccount = null;

		List<Account> accounts = getAllAccounts();

		for (Account account : accounts) {
			if (name.equals(account.getName())) {
				retrievedAccount = account;
				break;
			}
		}
		return retrievedAccount;
	}

	/* Returns true if the account was accessed more than 3 months ago. */
	public static boolean accessedLongAgo(Account account) {

		String stringLastAccess = account.getLastAccess();
		return accessedLongAgo(stringLastAccess);
	}

	/*
	 * Returns true if a String representing a Joda date is greather than 3
	 * months ago.
	 */
	public static boolean accessedLongAgo(String stringLastAccess) {

		Boolean accessedLongAgo = false;
		DateTime today = new DateTime();
		DateTimeFormatter formatter = DateTimeFormat
				.forPattern("yyyy-MM-dd HH:mm:ss");

		DateTime lastAccess = formatter.parseDateTime(stringLastAccess);
		Interval i = new Interval(lastAccess, today);

		if ((i.toPeriod().getYears() > 0) || (i.toPeriod().getMonths() > 3)) {
			accessedLongAgo = true;
		}
		return accessedLongAgo;
	}

	/* Returns all the accounts in database. */
	public List<Account> getAllAccounts() {

		List<Account> allAccounts = new ArrayList<Account>();

		NodeList accountNodes = document.getElementsByTagName("accounts")
				.item(0).getChildNodes();

		for (int i = 0; i < accountNodes.getLength(); i++) {
			Node currentNode = accountNodes.item(i);
			Element currentElement = (Element) currentNode;
			Account currentAccount = elementToAccount(currentElement);
			allAccounts.add(currentAccount);
		}

		return allAccounts;
	}

	/* Returns all the accounts in a given category. */
	public List<Account> getAllAccountsInCategory(String category) {

		List<Account> categoryAccounts = new ArrayList<Account>();

		for (Account currentAccount : getAllAccounts()) {
			if (!category.equalsIgnoreCase("Other")) {
				if (category.equalsIgnoreCase(currentAccount.getCategory())) {
					categoryAccounts.add(currentAccount);
				}
			} else if ((!currentAccount.getCategory()
					.equalsIgnoreCase("Gaming"))
					&& (!currentAccount.getCategory().equalsIgnoreCase(
							"Internet Sites"))
					&& (!currentAccount.getCategory().equalsIgnoreCase(
							"Social Network"))
					&& (!currentAccount.getCategory().equalsIgnoreCase("Work"))) {
				categoryAccounts.add(currentAccount);
			}
		}
		return categoryAccounts;
	}

	/* Returns all favorite accounts. */
	public List<Account> getAllFavoriteAccounts() {
		List<Account> favoriteAccounts = new ArrayList<Account>();

		for (Account currentAccount : getAllAccounts()) {
			if (currentAccount.getIsFavorite()) {
				favoriteAccounts.add(currentAccount);
			}
		}
		return favoriteAccounts;
	}

	/*
	 * Sorts the Accounts in the accountsList by their name attribute
	 * (Alphabetic order)
	 */
	public static void sortAccountListByAlphabeticOrder(
			ArrayList<Account> accountsList) {
		Collections.sort(accountsList, Account.COMPARE_BY_NAME);
	}

	/*
	 * Sorts the Accounts in the accountsList by their lastAccess attribute
	 * (most recent at the beginning)
	 */
	public static void sortAccountListByLastAccess(
			ArrayList<Account> accountsList) {
		Collections.sort(accountsList, Account.COMPARE_BY_LASTACCESS);
	}

	/* Print the whole database. */
	public void printDatabase() {

		for (Account account : getAllAccounts()) {
			System.out.println(account.toString());
		}
	}

	private void saveDatabase() throws TransformerConfigurationException,
			TransformerException, TransformerFactoryConfigurationError,
			IOException, InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {
		deleteDatabase();
		DOMSource domSource = new DOMSource(document);
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		TransformerFactory.newInstance().newTransformer()
				.transform(domSource, result);
		FileOutputStream outputStream = context.openFileOutput(filename,
				Context.MODE_PRIVATE);
		byte[] encryptedDatabase = Encryption.encrypt(writer.toString(), key);
		outputStream.write(encryptedDatabase);
		outputStream.close();
	}

	private void flushDatabase() {
		document = null;
	}

	private void deleteDatabase() {
		File file = new File(context.getFilesDir(), filename);
		if (file.exists()) {
			file.delete();
		}
	}
}