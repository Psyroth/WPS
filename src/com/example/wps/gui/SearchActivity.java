package com.example.wps.gui;

import com.example.wps.R;
import com.example.wps.db.Account;
import com.example.wps.db.AccountDatabase;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

/**
 * Search among accounts.
 */
public class SearchActivity extends Activity implements Observer {
	
	private ListView list;
	private ListViewAdapter adapter;
	private EditText editsearch;
	private ArrayList<Account> listOfAcc = new ArrayList<Account>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_layout);

		addAccountsOnLayout();
		AccountDatabase.getInstance().addObserver(this);
	}
	
	private void addAccountsOnLayout() {
		listOfAcc = (ArrayList<Account>) AccountDatabase.getInstance().getAllAccounts();

		// Locate the ListView in listview_main.xml
		list = (ListView) findViewById(R.id.listview);

		// Pass results to ListViewAdapter Class
		adapter = new ListViewAdapter(this, listOfAcc);

		// Binds the Adapter to the ListView
		list.setAdapter(adapter);

		// Locate the EditText in listview_main.xml
		editsearch = (EditText) findViewById(R.id.search);

		// Capture Text in EditText
		editsearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				String text = editsearch.getText().toString()
						.toLowerCase(Locale.getDefault());
				adapter.filter(text);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		AccountDatabase.getInstance().deleteObserver(this);
	}

	@Override
	public void update(Observable observable, Object data) {
		addAccountsOnLayout();
	}
}