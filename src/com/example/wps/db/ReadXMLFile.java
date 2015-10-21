package db.test;

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

import java.io.File;

import db.test.Account;

public class ReadXMLFile {

	public static void main(String argv[]) {
		readXml();
//		Account account = new Account("Webmail", "webmailUser@ulb.ac.be",
//				"webmail", "https://webmail.ulb.ac.be/",
//				"Thu, 20 Aug 2005 9:30:00 GMT", "E-mail delivery system");
//      writeInXmlFile(account);
//		readXml();
	}

	public static Account xmlToObject(Element element) {
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

		return new Account(name, id, password, url, lastAccess, note);
	}

	public static void writeInXmlFile(Account account) {

		try {
			File fXmlFile = new File(
					"/Users/nicoomer/Desktop/VUB/MA2/NextGenerationUserInterface/Exercices/Session2/NGUI_tangible/db/test/databaseTest.xml");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbf.newDocumentBuilder();
			Document document = dBuilder.parse(fXmlFile);

			Element element = document.createElement("account4");

			Element elementInside = document.createElement("name");
			Element elementInside2 = document.createElement("id");
			Element elementInside3 = document.createElement("password");
			Element elementInside4 = document.createElement("url");
			Element elementInside5 = document.createElement("lastAccess");
			Element elementInside6 = document.createElement("note");

			Text text = document.createTextNode(account.getName());
			Text text2 = document.createTextNode(account.getId());
			Text text3 = document.createTextNode(account.getPassword());
			Text text4 = document.createTextNode(account.getUrl());
			Text text5 = document.createTextNode(account.getLastAccess());
			Text text6 = document.createTextNode(account.getNote());

			elementInside.appendChild(text);
			element.appendChild(elementInside);
			elementInside2.appendChild(text2);
			element.appendChild(elementInside2);
			elementInside3.appendChild(text3);
			element.appendChild(elementInside3);
			elementInside4.appendChild(text4);
			element.appendChild(elementInside4);
			elementInside5.appendChild(text5);
			element.appendChild(elementInside5);
			elementInside6.appendChild(text6);
			element.appendChild(elementInside6);

			NodeList myNodeList = document.getElementsByTagName("accounts");
			myNodeList.item(0).appendChild(element);

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();
			t.setOutputProperty(OutputKeys.INDENT, "yes");

			File ff = new File(
					"/Users/nicoomer/Desktop/VUB/MA2/NextGenerationUserInterface/Exercices/Session2/NGUI_tangible/db/test/databaseTest.xml");
			StreamResult srf = new StreamResult(ff);
			Node nodef = document.getDocumentElement();
			DOMSource srcf = new DOMSource(nodef);
			t.transform(srcf, srf);
			t.setOutputProperty(OutputKeys.INDENT, "yes");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void readXml() {

		try {

			File fXmlFile = new File(
					"/Users/nicoomer/Desktop/VUB/MA2/NextGenerationUserInterface/Exercices/Session2/NGUI_tangible/db/test/databaseTest.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			Element racine = doc.getDocumentElement();

			System.out.println("Root element : " + racine.getNodeName());

			NodeList myNodeList = doc.getElementsByTagName("accounts");

			System.out.println("----------------------------");

			for (int temp = 0; temp < myNodeList.getLength(); temp++) {

				Node nNode = myNodeList.item(temp);

				NodeList nList2 = nNode.getChildNodes();

				for (int i = 0; i < nList2.getLength(); i++) {

					Node nNode2 = nList2.item(i);

					if (nNode2.getNodeType() == Node.ELEMENT_NODE) {

						System.out.println("\nCurrent Element : "
								+ nNode2.getNodeName());

						Element eElement = (Element) nNode2;

						printAccount(eElement);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void printAccount(Element element) {
		System.out
				.println("Account Name : "
						+ element.getElementsByTagName("name").item(0)
								.getTextContent());
		System.out.println("Identifier : "
				+ element.getElementsByTagName("id").item(0).getTextContent());
		System.out.println("Password : "
				+ element.getElementsByTagName("password").item(0)
						.getTextContent());
		System.out.println("Url : "
				+ element.getElementsByTagName("url").item(0).getTextContent());
		System.out.println("Last Accessed : "
				+ element.getElementsByTagName("lastAccess").item(0)
						.getTextContent());
		System.out
				.println("Note : "
						+ element.getElementsByTagName("note").item(0)
								.getTextContent());
	}
}