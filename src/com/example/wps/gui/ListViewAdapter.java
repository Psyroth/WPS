package com.example.wps.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.example.wps.db.Account;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Help populating list view.
 */
public class ListViewAdapter extends BaseAdapter {

	private Context context;
	private List<Account> accountList = null;
	private ArrayList<Account> arraylist;

	/* ListViewAdapter Constructor */
	public ListViewAdapter(Context context, List<Account> accountList) {
		this.context = context;
		this.accountList = accountList;
		this.arraylist = new ArrayList<Account>();
		this.arraylist.addAll(accountList);
	}

	@Override
	public int getCount() {
		return accountList.size();
	}

	@Override
	public Account getItem(int position) {
		return accountList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/* Displays the clickable singleItem in a listView */
	public View getView(int position, View view, ViewGroup parent) {
		return  new AccountView(context, accountList.get(position), position);
	}

	// Filter Class for Search functionality
	public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		accountList.clear();
		if (charText.length() == 0) {
			accountList.addAll(arraylist);
		} else {
			for (Account account : arraylist) {
				if (account.getName().toLowerCase(Locale.getDefault())
						.contains(charText)) {
					accountList.add(account);
				}
			}
		}
		notifyDataSetChanged();
	}
}