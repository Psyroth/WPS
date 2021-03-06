package com.example.wps.web;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Parser for Pointcarre that autofill user and password fields.
 */
public class VubAutoLogin extends Thread {
	
	protected String url, username, pass;
	protected HtmlSource htmlSource;
	
	public VubAutoLogin(String url, String username, String pass, HtmlSource htmlSource) {
		super();
		this.url = url;
		this.username = username;
		this.pass = pass;
		this.htmlSource = htmlSource;
	}
	
	@Override
	public void run() {
		super.run();
		try {
			String html = getHtml();
			html = autoCompleteLogin(html);
			htmlSource.html = html;
			System.out.println(html);
			System.out.println("Loading html completed");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected String getHtml() throws Exception {
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter("User-Agent",
				"Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
		HttpGet request = new HttpGet(url);
		HttpResponse response = client.execute(request);
		InputStream in = response.getEntity().getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder str = new StringBuilder();

		String line = null;
		while ((line = reader.readLine()) != null) {
			str.append(line);
		}
		in.close();

		String html = str.toString();
		return html;
	}
	
	protected String autoCompleteLogin(String html) {
		Document doc = Jsoup.parse(html);
		Elements login = doc.select("#username");
		login.get(0).attr("value", username);
		Elements pwd = doc.select("#password");
		pwd.get(0).attr("value", pass);
		
		html = doc.html();
		return html;
	}

}
