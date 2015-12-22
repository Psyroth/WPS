package com.example.wps.gui;

import com.example.wps.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.example.wps.db.Account;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class ListViewAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater inflater;
	private List<Account> accountList = null;
	private ArrayList<Account> arraylist;

	/* ListViewAdapter Constructor */
	public ListViewAdapter(Context context, List<Account> accountList) {
		mContext = context;
		this.accountList = accountList;
		inflater = LayoutInflater.from(mContext);
		this.arraylist = new ArrayList<Account>();
		this.arraylist.addAll(accountList);
	}

	/* Nested class to manage what elements should appear in the view */
	public class ViewHolder {
		TextView name;
		TextView id;
		TextView password;
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
	public View getView(final int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.listview_item, null);
			// Locate the TextViews in listview_item.xml
			holder.name = (TextView) view.findViewById(R.id.name);
			holder.id = (TextView) view.findViewById(R.id.id);
			holder.password = (TextView) view.findViewById(R.id.password);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		// Set the results into TextViews
		holder.name.setText(accountList.get(position).getName());
		holder.id.setText(accountList.get(position).getId());
		holder.password.setText(accountList.get(position).getPassword());

		// Listen for ListView Item Click
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Send single item click data to SingleItemView Class
				Intent intent = new Intent(mContext, SingleItemActivity.class);
				// Pass all data
				intent.putExtra("Name", (accountList.get(position).getName()));
				intent.putExtra("Id", (accountList.get(position).getId()));
				intent.putExtra("Password",
						(accountList.get(position).getPassword()));
				// Start SingleItemView Class
				mContext.startActivity(intent);
			}
		});
		return view;
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