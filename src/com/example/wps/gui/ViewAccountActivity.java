package com.example.wps.gui;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.example.wps.R;
import com.example.wps.db.Account;
import com.example.wps.db.AccountDatabase;
import com.example.wps.web.VubAutoLogin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.view.View;
import android.widget.Button;
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

	// To disable fields to avoid mistyping
	KeyListener idKeyListener, passwordKeyListener, urlKeyListener,
			categoryKeyListener, noteKeyListener, favoriteKeyListener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_account_layout);

		// Locate the TextViews in view_account_layout.xml
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
		vEtPassword.setText("******");
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
	
	public void autoLogin(View view) {
		Intent i = new Intent(ViewAccountActivity.this, WebBrowserActivity.class);
		Bundle bundle = getIntent().getExtras();
		String login = bundle.getString("AccountId");
		String pwd = bundle.getString("AccountPassword");
		String url = bundle.getString("AccountUrl");
		i.putExtra("login", login);
		i.putExtra("pwd", pwd);
		i.putExtra("url", url);
		startActivity(i);
	}

	/*
	 * When user pushes "Modify" button, it will transfer him to the
	 * addAccountActivity with already completed fields
	 */
	public void modifyAccount(View view) {

		Intent addAccountIntent = new Intent(ViewAccountActivity.this,
				AddAccountActivity.class);
		
		Bundle bundle = getIntent().getExtras();

		addAccountIntent.putExtra("AccountName", bundle.getString("AccountName"));
		addAccountIntent.putExtra("AccountId", bundle.getString("AccountId"));
		addAccountIntent.putExtra("AccountPassword", bundle.getString("AccountPassword"));
		addAccountIntent.putExtra("AccountUrl", bundle.getString("AccountUrl"));
		addAccountIntent.putExtra("AccountCategory", bundle.getString("AccountCategory"));
		addAccountIntent.putExtra("AccountNote", bundle.getString("AccountNote"));
		addAccountIntent.putExtra("AccountIsFavorite", bundle.getBoolean("AccountIsFavorite"));

		startActivity(addAccountIntent);
	}

	/*
	 * When user pushes "Delete" button, it will show a confirmation box and
	 * notice him afterwards
	 */
	public void removeAccount(View view) {

		// Cannot using custom build because of 2 buttons
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);

		alertDialogBuilder.setTitle("Confirmation");
		alertDialogBuilder
				.setMessage("Are you sure you want to delete this account ?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								AccountDatabase.getInstance().removeAccount(
										vEtName.getText().toString());
								dialog.cancel();
								MessageDialogBox successDialogBox = new MessageDialogBox(
										context,
										"Success",
										"Your account has successfuly been deleted !",
										"Ok");
								successDialogBox.prepareNewIntent(
										ViewAccountActivity.this, context,
										AccountsTab.class);
								successDialogBox.displayDialogBox();
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
	
	public void togglePassword(View view) {
		String pass = vEtPassword.getText().toString();
		Button button = (Button) findViewById(R.id.showPasswordButton);
		if ("******".equals(pass)) {
			vEtPassword.setText(getIntent().getExtras().getString("AccountPassword"));
			button.setText("Hide");
		}
		else {
			vEtPassword.setText("******");
			button.setText("Show");
		}
	}
}