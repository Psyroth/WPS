package com.example.wps.db;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import com.example.wps.db.Account;

public class XMLHandler {

	private static List<Element> elementList = null;
	private static Document document = null;

	/* Initializes the DOM of the .xml file. */
	public static Document buildDocument(String xmlStringDatabase) {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;

		try {

			builder = factory.newDocumentBuilder();
			document = builder.parse(new InputSource(new StringReader(
					xmlStringDatabase)));
			document.getDocumentElement().normalize();

		} catch (Exception e) {
			System.out
					.println("Failed to get create a document for the .xml file.");
			e.printStackTrace();
		}
		return document;
	}

	/* Updates the elementList according to the .xml file. */
	public static List<Element> xmlToElementList() {

		elementList = new ArrayList<Element>();

		try {

			NodeList nodeList = document.getElementsByTagName("accounts");
			addNodesValueToElementList(nodeList);

		} catch (Exception e) {
			System.out.println("Failed to read the .xml file");
			e.printStackTrace();
		}

		return elementList;
	}

	/* Returns the node named "tagName" in the nodeList, null otherwise. */
	public static Node getNodeByName(String tagName, NodeList nodeList) {

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);

			if (node.getNodeName().equalsIgnoreCase(tagName)) {
				return node;
			}
		}
		return null;
	}

	/* Returns the node with the "value" in the nodeList, null otherwise. */
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

	/* Adds a node named "tagName" with "value" as value and "parent" as parent. */
	public static void addNode(String tagName, String value, Node parent) {

		// Create a new Node with the given tag name
		Node node = document.createElement(tagName);

		// Add the node value as a child text node
		Text nodeVal = document.createTextNode(value);
		node.appendChild(nodeVal);

		// Add the new node structure to the parent node
		parent.appendChild(node);
	}

	/* Updates the elementList with the nodes in the "nodeList". */
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

	/* Adds an element (representing the node "data") to the elementList. */
	public static void addToElementList(Node data) {

		Element element = (Element) data;
		elementList.add(element);
	}

	/* Returns an account made from the element. */
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
		Boolean favorite = Boolean.parseBoolean(element
				.getElementsByTagName("favorite").item(0).getTextContent());

		return new Account(name, id, password, url, lastAccess, note, category,
				favorite);
	}

	/*
	 * Saves the created DOM tree to .xml file. Only when using local .xml file
	 */
	@Deprecated
	public static void saveChanges() {

		try {

			Transformer transformer = TransformerFactory.newInstance()
					.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			StreamResult result = new StreamResult();
			DOMSource source = new DOMSource(document);
			transformer.transform(source, result);
			document.normalize();
			xmlToElementList();

		} catch (Exception e) {
			System.out.println("Failed to save the changes");
			e.printStackTrace();
		}
	}
}