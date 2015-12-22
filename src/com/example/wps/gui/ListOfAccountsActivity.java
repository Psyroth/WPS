package com.example.wps.gui;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import com.example.wps.R;
import com.example.wps.db.Account;
import com.example.wps.db.AccountDatabase;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

/**
 * Parent class of classes that display accounts in a list style.
 */
public abstract class ListOfAccountsActivity extends Activity implements Observer {
	
	protected ArrayList<Account> listOfAcc;
	protected ListView list;
	protected ListViewAdapter adapter;
	
	protected abstract ArrayList<Account> getSortedAccounts();
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.account_list_view);

		addAccountsToListView();

		AccountDatabase.getInstance().addObserver(this);
	}
	
	public void accountsFromCategory(String category) {
		if (category.equalsIgnoreCase("All")) {
			listOfAcc = (ArrayList<Account>) AccountDatabase.getInstance()
					.getAllAccounts();
		} else if (category.equalsIgnoreCase("Gaming")
				|| category.equalsIgnoreCase("Internet Sites")
				|| category.equalsIgnoreCase("Social Network")
				|| category.equalsIgnoreCase("Work")
				|| category.equalsIgnoreCase("Other")) {
			listOfAcc = (ArrayList<Account>) AccountDatabase.getInstance()
					.getAllAccountsInCategory(category);
		} else {
			throw new IllegalArgumentException("Invalid category : " + category);
		}
	}

	public void addAccountsToListView() {
		Bundle bundle = getIntent().getExtras();
		String category = bundle.getString("WithCategory");

		// Need “No account in category" message
		accountsFromCategory(category);

		listOfAcc = getSortedAccounts();

		// Locate the ListView in listview_main.xml
		list = (ListView) findViewById(R.id.accountListView);

		// Pass results to ListViewAdapter Class
		adapter = new ListViewAdapter(this, listOfAcc);

		// Binds the Adapter to the ListView
		list.setAdapter(adapter);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		AccountDatabase.getInstance().deleteObserver(this);
	}

	@Override
	public void update(Observable observable, Object data) {
		addAccountsToListView();
	}

}
