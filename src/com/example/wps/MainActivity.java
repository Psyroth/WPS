package com.example.wps;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.xml.transform.TransformerFactoryConfigurationError;

import com.example.wps.db.AccountDatabase;
import com.example.wps.gui.ListOfAccounts;
import com.example.wps.gui.PasswordGenViewActivity;
import com.example.wps.nfc.NfcReader;
import com.example.wps.nfc.NfcWriter;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcV;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private static String filename = "database.xml";
	private static String serialNumber = null;
	//private static String nfcTag = "afghjiymphgefuoi";
	private static String nfcTag = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		serialNumber = Build.SERIAL;
		
		/*Tag tag = getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
		try { 
			new NfcWriter(tag, nfcTag.getBytes(Charset.forName("US-ASCII"))).start();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		// shows initial app window
		setContentView(R.layout.activity_main);
		
		// Retrieve data from NFC tag
		getNfcTag();
	}

	public void getNfcTag() {
		Context context = this.getApplicationContext();

		// check if NFC is active
		NfcManager manager = (NfcManager) context.getSystemService(Context.NFC_SERVICE);
		NfcAdapter adapter = manager.getDefaultAdapter();
		// if active, launch NFC scan
		if (adapter != null && adapter.isEnabled()) {
			// adapter exists and is enabled.

			// start scanning for NFC tag
			if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction())) {
				Tag tag = getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
				
				NfcReader nfcReader = new NfcReader(tag);
				nfcReader.start();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				nfcTag = new String(nfcReader.data, Charset.forName("US-ASCII"));
				// stop scanning for NFC tag
				
				try {
					AccountDatabase.initialize(filename, serialNumber, nfcTag, this);
					// if successful then we ask the user what he wants to look for
					Intent i = new Intent(MainActivity.this, ListOfAccounts.class);
					startActivity(i);
				} catch (TransformerFactoryConfigurationError | Exception e) {
					nfcTag = null;
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							this);

					alertDialogBuilder.setTitle("Error");
					alertDialogBuilder
							.setMessage("The NFC tag is invalid ! Database was not decrypted. Try another tag.")
							.setCancelable(false)
							.setPositiveButton("Ok",
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog,
												int id) {
											dialog.cancel();
										}
									});

					AlertDialog alertDialog = alertDialogBuilder.create();
					alertDialog.show();
				}
				
			}
			else if (nfcTag != null) {
				Intent i = new Intent(MainActivity.this, ListOfAccounts.class);
				startActivity(i);
			}

		}
		// if not active, take the user to wireless settings to enable NFC
		else {
			Toast.makeText(getApplicationContext(), "Please activate NFC and press Back to return to the application!",
					Toast.LENGTH_LONG).show();
			startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));

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
			getNfcTag();
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