package com.example.wps.db;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.wps.db.Account;

public class ReadXMLFile {

	private static File fXmlFile = new File("database.xml");
	private static List<Element> elementList = null;
	private static Document document = null;

	/* Main entry point */
	public static void main(String argv[]) {

		initDatabase();
		printDatabase();
		
		// runTests();
		// printDatabase();
	}

	/* Initializes the Database, the Document and the elementList */
	public static void initDatabase() {

		createDatabase();
		initDocument();
		readXml();
	}

	/*
	 * Creates an empty .xml file for the database, does not do anything if it
	 * already exists
	 */
	public static void createDatabase() {

		PrintWriter writer;

		try {
			if (!fXmlFile.exists() || fXmlFile.isDirectory()) {
				writer = new PrintWriter("database.xml", "UTF-8");
				writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
				writer.println("<WPS-database>");
				writer.println("<accounts>");
				writer.println("</accounts>");
				writer.println("</WPS-database>");
				writer.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to create the .xml file.");
		}
	}

	/* Initializes the DOM of the .xml file */
	public static void initDocument() {

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbf.newDocumentBuilder();
			document = dBuilder.parse(fXmlFile);
			document.getDocumentElement().normalize();

		} catch (Exception e) {
			e.printStackTrace();
			System.out
					.println("Failed to get create a document for the .xml file.");
		}
	}

	/* Returns the node named "tagName" in the nodeList, null otherwise */
	public static Node getNodeByName(String tagName, NodeList nodeList) {

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);

			if (node.getNodeName().equalsIgnoreCase(tagName)) {
				return node;
			}
		}
		return null;
	}

	/* Returns the node with the "value" in the nodeList, null otherwise */
	public static Node getNodeByValue(String value, NodeList nodeList) {

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

	/* Adds a node named "tagName" with "value" as value and "parent" as parent */
	public static void addNode(String tagName, String value, Node parent) {

		// Create a new Node with the given tag name
		Node node = document.createElement(tagName);

		// Add the node value as a child text node
		Text nodeVal = document.createTextNode(value);
		node.appendChild(nodeVal);

		// Add the new node structure to the parent node
		parent.appendChild(node);
	}

	/* Updates the elementList with the nodes in the "nodeList" */
	public static void addNodesValueToElementList(NodeList nodeList) {

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);

			NodeList childNodes = node.getChildNodes();

			for (int j = 0; j < childNodes.getLength(); j++) {
				Node data = childNodes.item(j);

				if (data.getNodeType() == Node.ELEMENT_NODE) {
					addToElementList(data);
				}
			}
		}
	}

	/* Adds an element (representing the node "data") to the elementList */
	public static void addToElementList(Node data) {

		Element element = (Element) data;
		elementList.add(element);
	}

	/* Updates the elementList according to the .xml file */
	public static void readXml() {

		elementList = new ArrayList<Element>();

		try {

			NodeList nodeList = document.getElementsByTagName("accounts");
			addNodesValueToElementList(nodeList);

		} catch (Exception e) {
			System.out.println("Failed to read the .xml file");
			e.printStackTrace();
		}
	}

	/* Adds the "account" to the database */
	public static void addAccount(Account account) {

		try {
			if (!accountExists(account)) {

				Element rootElement = document.createElement(account.getName()
						+ "Account");

				addNode("name", account.getName(), rootElement);
				addNode("id", account.getId(), rootElement);
				addNode("password", account.getPassword(), rootElement);
				addNode("url", account.getUrl(), rootElement);
				addNode("lastAccess", account.getLastAccess(), rootElement);
				addNode("note", account.getNote(), rootElement);
				addNode("category", account.getCategory(), rootElement);

				NodeList nodeList = document.getElementsByTagName("accounts");
				nodeList.item(0).appendChild(rootElement);

				saveChanges(document);
			}

		} catch (Exception e) {
			System.out.println("Failed to add " + account.getName()
					+ " account.");
			e.printStackTrace();
		}
	}

	/* Returns if the account exists or not */
	public static boolean accountExists(Account account) {

		for (int i = 0; i < elementList.size(); i++) {
			String retrievedName = elementList.get(i)
					.getElementsByTagName("name").item(0).getTextContent();
			if (account.getName().equals(retrievedName)) {
				System.out.println("\nA " + account.getName()
						+ " account already exists.");
				return true;
			}
		}
		System.out.println("\nA " + account.getName()
				+ " account does not exist yet.");
		return false;
	}

	/* Returns all the accounts in a given category */
	public static List<Account> accountsInCategory(String category) {
		List<Account> retrievedAccounts = new ArrayList<Account>();

		for (int i = 0; i < elementList.size(); i++) {

			Element currentElement = elementList.get(i);
			String retrievedCategory = currentElement
					.getElementsByTagName("category").item(0).getTextContent();

			if (category.equals(retrievedCategory)) {
				Account retrievedAccount = elementToObject(currentElement);
				retrievedAccounts.add(retrievedAccount);
			}
		}
		return retrievedAccounts;
	}

	/* Removes the account from the database */
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

				saveChanges(document);
			}
		}

		catch (Exception e) {
			System.out.println("Failed to remove " + account.getName()
					+ " account.");
			e.printStackTrace();
		}
	}

	/* Modify the oldAccount to the newAccount */
	public static void modifyAccount(Account oldAccount, Account newAccount) {

		try {
			if (accountExists(oldAccount)) {
				removeAccount(oldAccount);
				addAccount(newAccount);

				saveChanges(document);
			}
		} catch (Exception e) {
			System.out.println("Failed to modify " + oldAccount.getName()
					+ " account.");
			e.printStackTrace();
		}
	}

	/* Returns an account made from the element */
	public static Account elementToObject(Element element) {

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

		return new Account(name, id, password, url, lastAccess, note, category);
	}

	public static List<Account> getAllAccounts() {
		List<Account> allAccounts = new ArrayList<Account>();

		for (int i = 0; i < elementList.size(); i++) {

			Element currentElement = elementList.get(i);
			Account anAccount = elementToObject(currentElement);
			allAccounts.add(anAccount);
		}

		return allAccounts;
	}

	/* Saves the created DOM tree to .xml file */
	public static void saveChanges(Document document) {

		try {

			Transformer transformer = TransformerFactory.newInstance()
					.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			StreamResult result = new StreamResult(new FileWriter(fXmlFile));
			DOMSource source = new DOMSource(document);
			transformer.transform(source, result);
			document.normalize();
			readXml();

			System.out.println("Changes successfuly made");

		} catch (Exception e) {
			System.out.println("Failed to save the changes");
			e.printStackTrace();
		}
	}

	/* Print the database using the elementList */
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

	/* Launch the basic tests */
	public static void runTests() {
		Account testAccount1 = new Account("Facebook",
				"facebookUser@hotmail.com", "facebook",
				"https://www.facebook.com", "2015-10-26 22:00:00",
				"Less useful than Linkedin", "Social Network");
		Account testAccount1Bis = new Account("FacebookBis",
				"facebookUser@gmail.com", "facebook",
				"https://www.facebook.com", "2009-06-01 18:45:00",
				"Less useful than Linkedin", "Social Network");
		Account testAccount2 = new Account("Gmail", "gmailUser@hotmail.com",
				"gmail", "https://www.gmail.com", "2012-02-24 13:42:00",
				"Avoid Spam please", "E-Mail");
		Account testAccount3 = new Account("Youtube", "youtubeUser", "youtube",
				"https://www.youtube.com", "2010-12-13 12:30:00",
				"Best Channel Ever", "Entertainment");
		Account testAccount4 = new Account("Webmail", "webmailUser@ulb.ac.be",
				"webmail", "https://webmail.ulb.ac.be/", "2005-08-20 09:30:00",
				"E-mail delivery system", "E-Mail");

		testCompareDates(testAccount1);

		// testAddAccount(testAccount1);
		// testModifyAccount(testAccount1, testAccount1Bis);
		//
		// testAccountExists(testAccount1);
		// testAccountExists(testAccount1Bis);
		//
		// testRemoveAccount(testAccount2);
		// testAddAccount(testAccount2);
		// testAccountExists(testAccount2);
		//
		// testAddAccount(testAccount3);
		//
		// testAddAccount(testAccount4);
		// testRemoveAccount(testAccount4);
		// testAccountExists(testAccount4);
		//
		// testGetAllAccounts();
		// testSearchAccountByCategory("E-Mail");
	}

	public static void testAccountExists(Account account) {

		System.out.println("\nTesting the presence of " + account.getName()
				+ " account in database.");

		accountExists(account);
	}

	public static void testAddAccount(Account account) {

		System.out.println("\nTrying to add " + account.getName()
				+ " account in database.");

		addAccount(account);
	}

	public static void testModifyAccount(Account oldAccount, Account newAccount) {

		System.out.println("\nTrying to modify " + oldAccount.getName()
				+ " account in database.");

		modifyAccount(oldAccount, newAccount);
	}

	public static void testRemoveAccount(Account account) {

		System.out.println("\nTrying to remove " + account.getName()
				+ " account in database.");

		removeAccount(account);
	}

	public static void testSearchAccountByCategory(String category) {

		System.out.println("\nRetrieving accounts in database from the "
				+ category + " category.\n");

		List<Account> retrievedAccounts = accountsInCategory(category);
		System.out.println(Arrays.toString(retrievedAccounts.toArray()));
	}

	public static void testGetAllAccounts() {
		System.out.println("\nRetrieving all accounts from database.\n");
		List<Account> allAccounts = getAllAccounts();
		System.out.println(Arrays.toString(allAccounts.toArray()));
	}

	public static void testCompareDates(Account account) {

		DateTime today = new DateTime();
		DateTimeFormatter formatter = DateTimeFormat
				.forPattern("yyyy-MM-dd HH:mm:ss");

		System.out.println("\nToday's time : " + today.toString(formatter));

		DateTime lastAccess = formatter.parseDateTime(account.getLastAccess());

		System.out.println("\nLast access to the account on : "
				+ lastAccess.toString(formatter));

	}
}