package com.example.wps;

import javax.xml.transform.TransformerFactoryConfigurationError;

import com.example.wps.db.AccountDatabase;
import com.example.wps.gui.ListOfAccounts;
import com.example.wps.gui.PasswordGenViewActivity;
import com.example.wps.tagManipulation.NdefReaderTask;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	public static final String MIME_TEXT_PLAIN = "text/plain";
	public static final String TAG = "NfcDemo";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// shows initial app window
		setContentView(R.layout.activity_main);

		// check if NFC is active
		checkNFCActive();
		try {
			AccountDatabase.initDatabase(this, "lol", "abcd");
			AccountDatabase.saveDatabase(this);
		} catch (TransformerFactoryConfigurationError | Exception e) {
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

			// start scanning for NFC tag
			handleIntent(getIntent());
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

	public void showPassGen() {
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

	private void handleIntent(Intent intent) {
		String action = intent.getAction();
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {

			String type = intent.getType();
			if (MIME_TEXT_PLAIN.equals(type)) {

				Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
				new NdefReaderTask().execute(tag);

			} else {
				Log.d(TAG, "Wrong mime type: " + type);
			}
		} else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {

			// In case we would still use the Tech Discovered Intent
			Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			String[] techList = tag.getTechList();
			String searchedTech = Ndef.class.getName();

			for (String tech : techList) {
				if (searchedTech.equals(tech)) {
					new NdefReaderTask().execute(tag);
					break;
				}
			}
		}
	}
}