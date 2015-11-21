package com.example.wps.gui;

import com.example.wps.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class ViewAccountActivity extends Activity {
	
	EditText vEtName;
	EditText vEtId;
	EditText vEtPassword;
	EditText vEtUrl;
	EditText vEtCategory;
	EditText vEtNote;
	CheckBox vCbIsFavorite;
	final Context context = this;
	
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
		
		// Disable interaction until user pushes "Modify" button.
		vEtName.setKeyListener(null);
		vEtId.setKeyListener(null);
		vEtPassword.setKeyListener(null);
		vEtUrl.setKeyListener(null);
		vEtCategory.setKeyListener(null);
		vEtNote.setKeyListener(null);
		vCbIsFavorite.setKeyListener(null);
	}
	
	public void modifyAccount(View view) {
		// TODO
	}
}