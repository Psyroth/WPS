package com.example.wps.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

public class AccountDatabase {

	private static String filename = "database.xml";
	private static Document document;
	private static byte[] key;

	public static void initDatabase(Context context, String serialNumber,
			String nfcTag) throws TransformerFactoryConfigurationError,
			NoSuchAlgorithmException, Exception {
		File file = new File(context.getFilesDir(), filename);
		key = Encryption.xor(Encryption.sha1(serialNumber),
				Encryption.sha1(nfcTag));
		if (file.exists()) {
			System.out.println("File exists");
			document = openDatabase(file);
			
		} else {
			System.out.println("File does not exist");
			document = createEmptyDatabase();
			saveDatabase(context);
			
//			Account testAccount3 = new Account("Youtube", "youtubeUser", "youtube",
//					"https://www.youtube.com", "2010-12-13 12:30:00",
//					"Best Channel Ever", "Entertainment", true);
//
//			AccountDatabase.addAccount(testAccount3);
		}
	}

	/*
	 * Load an existing database.
	 */
	private static Document openDatabase(File xmlFile) throws SAXException,
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
	private static Document createEmptyDatabase() throws SAXException,
			IOException, ParserConfigurationException {

		String databaseContent = "<?xml version=\"1.0\""
				+ "encoding=\"UTF-8\" standalone=\"no\"?>"
				+ "<WPS-database>"
				+ "<accounts>"
				+ "<account><name>Facebook</name><id>facebookUser@hotmail.com</id><password>facebook</password><url>https://www.facebook.com</url><lastAccess>2015-10-26 22:00:00</lastAccess><note>Less useful than Linkedin</note><category>Social Network</category><favorite>true</favorite></account></accounts>"
				+ "</WPS-database>";
		
		document = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder()
				.parse(new InputSource(new StringReader(databaseContent)));
		
		return document;
	}

	/* Adds a node named "tagName" with "value" as value and "parent" as parent. */
	private static void addNode(String tagName, String value, Node parent) {

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
	public static void addAccount(Account account) {

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
				Element element = (Element) getNodeByValue(account.getName(),
						nodeList);
				Element parentElement = (Element) element.getParentNode();
				element.getParentNode().getParentNode()
						.removeChild(parentElement);
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

		for (Account currentAccount : getAllAccounts()) {
			if (account.getName().equals(currentAccount.getName())) {
				return true;
			}
		}
		System.out.println("\nA " + account.getName()
				+ " account does not exist yet.");
		return false;
	}

	/* Returns the account that has "name" as account name */
	public static Account getAccountFromName(String name) {
		Account retrievedAccount = null;

		NodeList nodeList = document.getElementsByTagName("accounts").item(0)
				.getChildNodes();

		Element element = (Element) getNodeByValue(name, nodeList);
		Element parentElement = (Element) element.getParentNode();
		retrievedAccount = elementToAccount(parentElement);

		return retrievedAccount;
	}

	/* Returns true if the account was accessed more than 3 months ago. */
	public static boolean accessedLongAgo(Account account) {

		Boolean accessedLongAgo = false;
		DateTime today = new DateTime();
		DateTimeFormatter formatter = DateTimeFormat
				.forPattern("yyyy-MM-dd HH:mm:ss");

		DateTime lastAccess = formatter.parseDateTime(account.getLastAccess());
		Interval i = new Interval(lastAccess, today);

		if ((i.toPeriod().getYears() > 0) || (i.toPeriod().getMonths() > 3)) {
			accessedLongAgo = true;
		}
		return accessedLongAgo;
	}

	/* Returns all the accounts in database. */
	public static List<Account> getAllAccounts() {

		List<Account> allAccounts = new ArrayList<Account>();

		NodeList accountNodes = document.getElementsByTagName("accounts");
		for (int i = 0; i < accountNodes.getLength(); i++) {
			Node currentNode = accountNodes.item(i);
			Element currentElement = (Element) currentNode;
			Account currentAccount = elementToAccount(currentElement);
			allAccounts.add(currentAccount);
		}

		return allAccounts;
	}

	/* Returns all the accounts in a given category. */
	public static List<Account> getAllAccountsInCategory(String category) {

		List<Account> categoryAccounts = new ArrayList<Account>();

		for (Account currentAccount : getAllAccounts()) {
			if (category.equals(currentAccount.getCategory())) {
				categoryAccounts.add(currentAccount);
			}
		}
		return categoryAccounts;
	}

	/* Returns all favorite accounts. */
	public static List<Account> getAllFavoriteAccounts() {
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
	public static void printDatabase() {

		for (Account account : getAllAccounts()) {
			System.out.println(account.toString());
		}
	}

	public static void saveDatabase(Context context)
			throws TransformerConfigurationException, TransformerException,
			TransformerFactoryConfigurationError, IOException,
			InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {
		deleteDatabase(context);
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

	public static void flushDatabase() {
		document = null;
	}

	public static void deleteDatabase(Context context) {
		File file = new File(context.getFilesDir(), filename);
		if (file.exists()) {
			file.delete();
		}
	}
}