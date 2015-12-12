package com.example.wps.gui;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.example.wps.R;
import com.example.wps.db.Account;
import com.example.wps.db.AccountDatabase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Button;

public class ViewAccountActivity extends Activity {

	EditText vEtName;
	EditText vEtId;
	EditText vEtPassword;
	EditText vEtUrl;
	EditText vEtCategory;
	EditText vEtNote;
	CheckBox vCbIsFavorite;
	final Context context = this;

	KeyListener idKeyListener, passwordKeyListener, urlKeyListener,
			categoryKeyListener, noteKeyListener, favoriteKeyListener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_account_layout);

		vEtName = (EditText) findViewById(R.id.vEtName);
		vEtId = (EditText) findViewById(R.id.vEtId);
		vEtPassword = (EditText) findViewById(R.id.vEtPassword);
		vEtUrl = (EditText) findViewById(R.id.vEtUrl);
		vEtCategory = (EditText) findViewById(R.id.vEtCategory);
		vEtNote = (EditText) findViewById(R.id.vEtNote);
		vCbIsFavorite = (CheckBox) findViewById(R.id.vCbIsFavorite);

		// Way to share Data between Activities

		Bundle bundle = getIntent().getExtras();

		String accountName = bundle.getString("AccountName");
		String accountId = bundle.getString("AccountId");
		String accountPassword = bundle.getString("AccountPassword");
		String accountUrl = bundle.getString("AccountUrl");
		String accountLastAccess = bundle.getString("AccountLastAccess");
		String accountCategory = bundle.getString("AccountCategory");
		String accountNote = bundle.getString("AccountNote");
		Boolean accountIsFavorite = bundle.getBoolean("AccountIsFavorite");

		if (AccountDatabase.accessedLongAgo(accountLastAccess)) {
			MessageDialogBox changePasswordDialogBox = new MessageDialogBox(
					context,
					"Reminder",
					"It has been more than 3 months you accessed this account, you might need to use another password !",
					"Ok");
			changePasswordDialogBox.displayDialogBox();
		}

		// Update lastAccess

		DateTime today = new DateTime();
		DateTimeFormatter formatter = DateTimeFormat
				.forPattern("yyyy-MM-dd HH:mm:ss");
		String lastAccess = today.toString(formatter);
		Account account = new Account(accountName, accountId, accountPassword,
				accountUrl, lastAccess, accountNote, accountCategory,
				accountIsFavorite);
		AccountDatabase.getInstance().modifyAccount(account);

		// Fill the Account

		vEtName.setText(accountName);
		vEtId.setText(accountId);
		vEtPassword.setText(accountPassword);
		vEtUrl.setText(accountUrl);
		vEtCategory.setText(accountCategory);
		vEtNote.setText(accountNote);
		vCbIsFavorite.setChecked(accountIsFavorite);

		// Disable interaction until user pushes "Modify" button.
		idKeyListener = vEtId.getKeyListener();
		passwordKeyListener = vEtPassword.getKeyListener();
		urlKeyListener = vEtUrl.getKeyListener();
		categoryKeyListener = vEtCategory.getKeyListener();
		noteKeyListener = vEtNote.getKeyListener();
		favoriteKeyListener = vCbIsFavorite.getKeyListener();

		vEtName.setKeyListener(null);
		vEtName.setBackgroundColor(Color.LTGRAY);
		vEtId.setKeyListener(null);
		vEtId.setBackgroundColor(Color.LTGRAY);
		vEtPassword.setKeyListener(null);
		vEtPassword.setBackgroundColor(Color.LTGRAY);
		vEtUrl.setKeyListener(null);
		vEtUrl.setBackgroundColor(Color.LTGRAY);
		vEtCategory.setKeyListener(null);
		vEtCategory.setBackgroundColor(Color.LTGRAY);
		vEtNote.setKeyListener(null);
		vEtNote.setBackgroundColor(Color.LTGRAY);
		vCbIsFavorite.setEnabled(false);

	}

	public void modifyAccount(View view) {

		Intent addAccountIntent = new Intent(ViewAccountActivity.this,
				AddAccountActivity.class);

		addAccountIntent.putExtra("AccountName", vEtName.getText().toString());
		addAccountIntent.putExtra("AccountId", vEtId.getText().toString());
		addAccountIntent.putExtra("AccountPassword", vEtPassword.getText()
				.toString());
		addAccountIntent.putExtra("AccountUrl", vEtUrl.getText().toString());
		addAccountIntent.putExtra("AccountCategory", vEtCategory.getText()
				.toString());
		addAccountIntent.putExtra("AccountNote", vEtNote.getText().toString());
		addAccountIntent.putExtra("AccountIsFavorite",
				vCbIsFavorite.isChecked());

		startActivity(addAccountIntent);

		// Button button = (Button) view;
		// if ("Modify".equals(button.getText())) {
		// button.setText("Save");
		// vEtId.setKeyListener(idKeyListener);
		// vEtId.setBackgroundColor(Color.WHITE);
		// vEtPassword.setKeyListener(passwordKeyListener);
		// vEtPassword.setBackgroundColor(Color.WHITE);
		// vEtUrl.setKeyListener(urlKeyListener);
		// vEtUrl.setBackgroundColor(Color.WHITE);
		// vEtCategory.setKeyListener(categoryKeyListener);
		// vEtCategory.setBackgroundColor(Color.WHITE);
		// vEtNote.setKeyListener(noteKeyListener);
		// vEtNote.setBackgroundColor(Color.WHITE);
		// vCbIsFavorite.setEnabled(true);;
		// }
		// else {
		// String accountName = vEtName.getText().toString();
		// String accountId = vEtId.getText().toString();
		// String accountPassword = vEtPassword.getText().toString();
		// String accountUrl = vEtUrl.getText().toString();
		// DateTime today = new DateTime();
		// DateTimeFormatter formatter = DateTimeFormat
		// .forPattern("yyyy-MM-dd HH:mm:ss");
		// String accountCategory = vEtCategory.getText().toString();
		// String accountNote = vEtNote.getText().toString();
		// Boolean isFavorite = vCbIsFavorite.isChecked();
		//
		// Account newAccount = new Account(accountName, accountId,
		// accountPassword, accountUrl, today.toString(formatter),
		// accountNote, accountCategory, isFavorite);
		//
		// AccountDatabase.getInstance().modifyAccount(newAccount);
		// finish();
		// }
	}
}