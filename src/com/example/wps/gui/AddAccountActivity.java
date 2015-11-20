package com.example.wps.gui;

import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.example.wps.R;
import com.example.wps.db.Account;
import com.example.wps.db.AccountDatabase;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

		// Still an issue here
		if (AccountDatabase.getAccountFromName(accountName) == null) {

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
			
			AccountDatabase.addAccount(newAccount);
			// Notify account saved ? Go back to previous view / activity ?
		} else {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					context);

			alertDialogBuilder.setTitle("Error");
			alertDialogBuilder
					.setMessage("The account is already existing !")
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
}