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

	// For the account modification
	Bundle bundle;
	String oldAccountName;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_account_layout);

		mEtName = (EditText) findViewById(R.id.mEtName);
		mEtId = (EditText) findViewById(R.id.mEtId);
		mEtPassword = (EditText) findViewById(R.id.mEtPassword);
		mEtUrl = (EditText) findViewById(R.id.mEtUrl);
		categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
		String[] items = new String[] { "Gaming", "Internet Sites",
				"Social Network", "Work", "Other" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, items);
		categorySpinner.setAdapter(adapter);
		mEtNote = (EditText) findViewById(R.id.mEtNote);
		mCbIsFavorite = (CheckBox) findViewById(R.id.mCbIsFavorite);

		// For the account modification
		this.bundle = getIntent().getExtras();
		if (bundle != null) {
			oldAccountName = bundle.getString("AccountName");
			String oldAccountId = bundle.getString("AccountId");
			String oldAccountPassword = bundle.getString("AccountPassword");
			String oldAccountUrl = bundle.getString("AccountUrl");
			String oldAccountCategory = bundle.getString("AccountCategory");
			String oldAccountNote = bundle.getString("AccountNote");
			Boolean oldAccountIsFavorite = bundle
					.getBoolean("AccountIsFavorite");

			mEtName.setText(oldAccountName);
			mEtId.setText(oldAccountId);
			mEtPassword.setText(oldAccountPassword);
			mEtUrl.setText(oldAccountUrl);

			if (!oldAccountCategory.equals(null)) {
				int spinnerPosition = adapter.getPosition(oldAccountCategory);
				categorySpinner.setSelection(spinnerPosition);
			}

			mEtNote.setText(oldAccountNote);
			mCbIsFavorite.setChecked(oldAccountIsFavorite);
		}
	}

	public void processInputData(View view) {

		String accountName = mEtName.getText().toString();
		String accountId = mEtId.getText().toString();
		String accountPassword = mEtPassword.getText().toString();
		String accountUrl = mEtUrl.getText().toString();
		DateTime today = new DateTime();
		DateTimeFormatter formatter = DateTimeFormat
				.forPattern("yyyy-MM-dd HH:mm:ss");
		String accountCategory = categorySpinner.getSelectedItem().toString();
		String accountNote = mEtNote.getText().toString();
		Boolean isFavorite = mCbIsFavorite.isChecked();

		Account newAccount = new Account(accountName, accountId,
				accountPassword, accountUrl, today.toString(formatter),
				accountNote, accountCategory, isFavorite);

		if (accountName.equals("")) {
			MessageDialogBox alreadyExistDialogBox = new MessageDialogBox(
					context, "Error",
					"An empty name for an account is not valid !", "Ok");
			alreadyExistDialogBox.displayDialogBox();
		}

		// For the account modification
		else if (bundle != null) {
			AccountDatabase.getInstance().removeAccount(oldAccountName);
			AccountDatabase.getInstance().addAccount(newAccount);

			MessageDialogBox successDialogBox = new MessageDialogBox(context,
					"Success", "Your account has successfuly been modified !",
					"Ok");
			successDialogBox.prepareNewIntent(this, AddAccountActivity.this,
					ListOfAccounts.class);
			successDialogBox.displayDialogBox();
		}

		// For a new account creation
		else if (AccountDatabase.getInstance().getAccountFromName(accountName) == null) {

			AccountDatabase.getInstance().addAccount(newAccount);

			MessageDialogBox successDialogBox = new MessageDialogBox(context,
					"Success", "Your account has successfuly been saved !",
					"Ok");
			successDialogBox.prepareNewIntent(this, AddAccountActivity.this,
					ListOfAccounts.class);
			successDialogBox.displayDialogBox();

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