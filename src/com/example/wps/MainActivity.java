package com.example.wps;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.example.wps.db.Account;
import com.example.wps.db.ReadXMLFile;
import com.example.wps.gui.ListOfAccounts;
import com.example.wps.gui.PasswordGenViewActivity;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// shows initial app window
		setContentView(R.layout.activity_main);

		// check if NFC is active
		checkNFCActive();
		testDatabase();
	}

	public void testDatabase() {

		String FILENAME = "database";
		String xmlStringDatabase = ReadXMLFile.createEmptyDatabase();

		Account testAccount1 = new Account("Facebook",
				"facebookUser@hotmail.com", "facebook",
				"https://www.facebook.com", "2015-09-12 22:00:00",
				"Less useful than Linkedin", "Social Network", true);
		
		Account testAccount1Bis = new Account("FacebookBis",
				"facebookUser@gmail.com", "facebook",
				"https://www.facebook.com", "2009-06-01 18:45:00",
				"Less useful than Linkedin", "Social Network", false);
		
		Account testAccount2 = new Account("Gmail", "gmailUser@hotmail.com",
				"gmail", "https://www.gmail.com", "2012-02-24 13:42:00",
				"Avoid Spam please", "E-Mail", false);
		
		Account testAccount3 = new Account("Youtube", "youtubeUser", "youtube",
				"https://www.youtube.com", "2010-12-13 12:30:00",
				"Best Channel Ever", "Entertainment", true);
		
		Account testAccount4 = new Account("Webmail", "webmailUser@ulb.ac.be",
				"webmail", "https://webmail.ulb.ac.be/", "2005-08-20 09:30:00",
				"E-mail delivery system", "E-Mail", false);

		FileOutputStream fos = null;

		try {
			fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
			fos.write(xmlStringDatabase.getBytes());
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		FileInputStream fis = null;

		try {
			fis = openFileInput(FILENAME);
			StringBuilder builder = new StringBuilder();
			int ch;
			while ((ch = fis.read()) != -1) {
				builder.append((char) ch);
			}

			ReadXMLFile.initDatabase(builder.toString());

			ReadXMLFile.addAccount(testAccount1);
			ReadXMLFile.addAccount(testAccount1Bis);
			ReadXMLFile.addAccount(testAccount2);
			ReadXMLFile.addAccount(testAccount3);
			ReadXMLFile.addAccount(testAccount4);
			System.out.println("--------------");
			ReadXMLFile.printDatabase();

			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void checkNFCActive() {
		Context context = this.getApplicationContext();

		// check if NFC is active
		NfcManager manager = (NfcManager) context
				.getSystemService(Context.NFC_SERVICE);
		NfcAdapter adapter = manager.getDefaultAdapter();
		// if active, launch NFC scan
		if (adapter != null && adapter.isEnabled()) {
			// adapter exists and is enabled.

			// start scanning for RFID tag

			// if successful then we ask the user what he wants to look for
			Intent i = new Intent(MainActivity.this, ListOfAccounts.class);
			startActivity(i);
		}
		// if not active, take the user to wireless settings to enable NFC
		else {
			Toast.makeText(
					getApplicationContext(),
					"Please activate NFC and press Back to return to the application!",
					Toast.LENGTH_LONG).show();
			startActivity(new Intent(
					android.provider.Settings.ACTION_WIRELESS_SETTINGS));

			// TODO dialog box to make sure he wants to go to settings maybe?
			// final Dialog dialog = new Dialog(this);
			//
			// dialog.setContentView(R.layout.activity_dialog_nfc_inactive);
			// dialog.setTitle("NFC disabled");
			//
			// dialog.show();
		}
	}
	
	public void showPassGen(){
		   Intent i = new Intent(MainActivity.this, PasswordGenViewActivity.class);
		   startActivity(i);
	   } 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		switch (id) {
		case R.id.action_settings:
			// settings();
			checkNFCActive();
			return true;
		case R.id.action_passgen:
			showPassGen();
			return true;
		case R.id.action_exit:
			System.exit(1);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
