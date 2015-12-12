package com.example.wps.gui;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.example.wps.R;
import com.example.wps.db.Account;
import com.example.wps.db.AccountDatabase;
import com.example.wps.gui.MessageDialogBox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class AddAccountActivity extends Activity {

	EditText mEtName;
	EditText mEtId;
	EditText mEtPassword;
	EditText mEtUrl;
	Spinner categorySpinner;
	EditText mEtNote;
	CheckBox mCbIsFavorite;
	final Context context = this;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_account_layout);

		mEtName = (EditText) findViewById(R.id.mEtName);
		mEtId = (EditText) findViewById(R.id.mEtId);
		mEtPassword = (EditText) findViewById(R.id.mEtPassword);
		mEtUrl = (EditText) findViewById(R.id.mEtUrl);
		categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
		String[] items = new String[] { "Gaming", "Internet Site",
				"Social Network", "Work", "Other" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, items);
		categorySpinner.setAdapter(adapter);
		mEtNote = (EditText) findViewById(R.id.mEtNote);

		mCbIsFavorite = (CheckBox) findViewById(R.id.mCbIsFavorite);
	}

	public void processInputData(View view) {
		String accountName = mEtName.getText().toString();

		if (accountName.equals("")) {
			MessageDialogBox alreadyExistDialogBox = new MessageDialogBox(
					context, "Error",
					"An empty name for an account is not valid !", "Ok");
			alreadyExistDialogBox.displayDialogBox();
		}

		else if (AccountDatabase.getInstance().getAccountFromName(accountName) == null) {

			String accountId = mEtId.getText().toString();
			String accountPassword = mEtPassword.getText().toString();
			String accountUrl = mEtUrl.getText().toString();
			DateTime today = new DateTime();
			DateTimeFormatter formatter = DateTimeFormat
					.forPattern("yyyy-MM-dd HH:mm:ss");
			String accountCategory = categorySpinner.getSelectedItem()
					.toString();
			String accountNote = mEtNote.getText().toString();
			Boolean isFavorite = mCbIsFavorite.isChecked();

			Account newAccount = new Account(accountName, accountId,
					accountPassword, accountUrl, today.toString(formatter),
					accountNote, accountCategory, isFavorite);

			AccountDatabase.getInstance().addAccount(newAccount);

			MessageDialogBox alreadyExistDialogBox = new MessageDialogBox(
					context, "Success",
					"Your account has successfuly been saved !", "Ok");
			alreadyExistDialogBox.prepareNewIntent(this,
					AddAccountActivity.this, ListOfAccounts.class);
			alreadyExistDialogBox.displayDialogBox();

		} else {

			MessageDialogBox alreadyExistDialogBox = new MessageDialogBox(
					context, "Error", "The account already exists !", "Ok");
			alreadyExistDialogBox.displayDialogBox();
		}
	}

	public void showPassGen() {
		Intent i = new Intent(this, PasswordGenViewActivity.class);
		startActivity(i);
	}

	public void openPassGen(View view) {
		showPassGen();
	}
}