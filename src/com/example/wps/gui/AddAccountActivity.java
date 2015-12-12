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
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class AddAccountActivity extends Activity {

	EditText mEtName;
	EditText mEtId;
	EditText mEtPassword;
	EditText mEtUrl;
	EditText mEtCategory;
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
		mEtCategory = (EditText) findViewById(R.id.mEtCategory);
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
			String accountCategory = mEtCategory.getText().toString();
			String accountNote = mEtNote.getText().toString();
			Boolean isFavorite = mCbIsFavorite.isChecked();

			Account newAccount = new Account(accountName, accountId,
					accountPassword, accountUrl, today.toString(formatter),
					accountNote, accountCategory, isFavorite);

			AccountDatabase.getInstance().addAccount(newAccount);

		} else {

			MessageDialogBox alreadyExistDialogBox = new MessageDialogBox(
					context, "Error", "The account is already existing !", "Ok");
			alreadyExistDialogBox.displayDialogBox();
		}
	}
}