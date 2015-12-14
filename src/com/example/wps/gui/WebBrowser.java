package com.example.wps.gui;

import com.example.wps.web.HtmlSource;
import com.example.wps.web.VubAutoLogin;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebBrowser extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		System.out.println("auto login 2");
		
		HtmlSource htmlSource = new HtmlSource();
		
		String username=getIntent().getExtras().getString("login"),
				pwd=getIntent().getExtras().getString("pwd");
		Thread vubAutoLogin = new VubAutoLogin("https://cas.vub.ac.be/cas/login?service=https%3A%2F%2Fpointcarre.vub.ac.be%2F", username, pwd, htmlSource);
		vubAutoLogin.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(htmlSource.html);
		
		WebView webview = new WebView(this);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setDomStorageEnabled(true);
		webview.setWebViewClient(new WebViewClient());
		webview.loadDataWithBaseURL("https://cas.vub.ac.be/", htmlSource.html, "text/html", null, null);

		setContentView(webview);
	}
}
