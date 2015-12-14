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

		String url = getIntent().getExtras().getString("url");

		if ("pointcarre.vub.ac.be".equals(url) || "https://pointcarre.vub.ac.be".equals(url)
				|| "http://pointcarre.vub.ac.be".equals(url) || "cas.vub.ac.be/cas/login".equals(url)
				|| "https://cas.vub.ac.be/cas/login".equals(url)) {

			HtmlSource htmlSource = new HtmlSource();

			String username = getIntent().getExtras().getString("login"),
					pwd = getIntent().getExtras().getString("pwd");
			Thread vubAutoLogin = new VubAutoLogin(
					"https://cas.vub.ac.be/cas/login?service=https%3A%2F%2Fpointcarre.vub.ac.be%2F", username, pwd,
					htmlSource);
			vubAutoLogin.start();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			WebView webview = new WebView(this);
			webview.getSettings().setJavaScriptEnabled(true);
			webview.getSettings().setDomStorageEnabled(true);
			webview.setWebViewClient(new WebViewClient());
			webview.loadDataWithBaseURL("https://cas.vub.ac.be/", htmlSource.html, "text/html", null, null);

			setContentView(webview);
		} else {
			MessageDialogBox message = new MessageDialogBox(this, "Error", "This website is not supported yet !", "Ok");
			message.displayDialogBox();
		}
	}
}
