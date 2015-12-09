package com.example.wps.gui;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.example.wps.R;
import com.example.wps.db.Account;
import com.example.wps.db.AccountDatabase;

import android.app.Activity;
import android.content.Context;
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
	
	KeyListener idKeyListener, passwordKeyListener, urlKeyListener, categoryKeyListener, noteKeyListener, favoriteKeyListener;

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
		String accountCategory = bundle.getString("AccountCategory");
		String accountNote = bundle.getString("AccountNote");
		Boolean accountIsFavorite = bundle.getBoolean("AccountIsFavorite");

		// Fill the Account

		vEtName.setText(accountName);
		vEtId.setText(accountId);
		vEtPassword.setText(accountPassword);
		vEtUrl.setText(accountUrl);
		vEtCategory.setText(accountCategory);
		vEtNote.setText(accountNote);
		vCbIsFavorite.setChecked(accountIsFavorite);
		
		idKeyListener = vEtId.getKeyListener();
		passwordKeyListener = vEtPassword.getKeyListener();
		urlKeyListener = vEtUrl.getKeyListener();
		categoryKeyListener = vEtCategory.getKeyListener();
		noteKeyListener = vEtNote.getKeyListener();
		favoriteKeyListener = vCbIsFavorite.getKeyListener();

		// Disable interaction until user pushes "Modify" button.
		vEtName.setKeyListener(null);
		vEtId.setKeyListener(null);
		vEtPassword.setKeyListener(null);
		vEtUrl.setKeyListener(null);
		vEtCategory.setKeyListener(null);
		vEtNote.setKeyListener(null);
		vCbIsFavorite.setEnabled(false);;
	}

	public void modifyAccount(View view) {
		Button button = (Button) view;
		if ("Modify".equals(button.getText())) {
			button.setText("Save");
			vEtId.setKeyListener(idKeyListener);
			vEtPassword.setKeyListener(passwordKeyListener);
			vEtUrl.setKeyListener(urlKeyListener);
			vEtCategory.setKeyListener(categoryKeyListener);
			vEtNote.setKeyListener(noteKeyListener);
			vCbIsFavorite.setEnabled(true);;
		}
		else {
			String accountName = vEtName.getText().toString();
			String accountId = vEtId.getText().toString();
			String accountPassword = vEtPassword.getText().toString();
			String accountUrl = vEtUrl.getText().toString();
			DateTime today = new DateTime();
			DateTimeFormatter formatter = DateTimeFormat
					.forPattern("yyyy-MM-dd HH:mm:ss");
			String accountCategory = vEtCategory.getText().toString();
			String accountNote = vEtNote.getText().toString();
			Boolean isFavorite = vCbIsFavorite.isChecked();

			Account newAccount = new Account(accountName, accountId,
					accountPassword, accountUrl, today.toString(formatter),
					accountNote, accountCategory, isFavorite);
			
			AccountDatabase.getInstance().modifyAccount(newAccount);
			finish();
		}
	}
}