package com.example.wps.gui;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import com.example.wps.R;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wps.db.Account;
import com.example.wps.db.AccountDatabase;

public class ListViewActivity extends Activity implements Observer {
	ArrayList<Account> listOfAcc;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.account_list_scrollview_layout);
		
		addAccountsToLinearLayout();
		
		AccountDatabase.getInstance().addObserver(this);
	}

	public void accountsFromCategory(String category) {
		if (category.equalsIgnoreCase("All")) {
			listOfAcc = (ArrayList<Account>) AccountDatabase.getInstance().getAllAccounts();
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

	public void addAccountsToLinearLayout() {
		Bundle bundle = getIntent().getExtras();
		String category = bundle.getString("WithCategory");

		// Need “No account in category" message
		accountsFromCategory(category);

		AccountDatabase.sortAccountListByAlphabeticOrder(listOfAcc);

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayoutAccountsList);
		linearLayout.removeAllViews();
		
		for (int acc = 0; acc < listOfAcc.size(); acc++) {
			String userTitle = listOfAcc.get(acc).getName();
			String userID = listOfAcc.get(acc).getId();
			String userPass = listOfAcc.get(acc).getPassword();
			
			TextView tv = new TextView(this);
			tv.setId(acc);
			tv.setText(userTitle + "\n" + userID + "\n" + userPass);
			tv.setClickable(true);
			tv.setLines(3);
			tv.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					System.out.println("Clicked on element : " + v.getId());
					// Launch viewAccountActivity
					Intent viewAccountIntent = new Intent(
							ListViewActivity.this, ViewAccountActivity.class);

					viewAccountIntent.putExtra("AccountName",
							listOfAcc.get(v.getId()).getName());
					viewAccountIntent.putExtra("AccountId",
							listOfAcc.get(v.getId()).getId());
					viewAccountIntent.putExtra("AccountPassword", listOfAcc
							.get(v.getId()).getPassword());
					viewAccountIntent.putExtra("AccountUrl",
							listOfAcc.get(v.getId()).getUrl());
					viewAccountIntent.putExtra("AccountLastAccess", listOfAcc
							.get(v.getId()).getLastAccess());
					viewAccountIntent.putExtra("AccountCategory", listOfAcc
							.get(v.getId()).getCategory());
					viewAccountIntent.putExtra("AccountNote",
							listOfAcc.get(v.getId()).getNote());
					viewAccountIntent.putExtra("AccountIsFavorite", listOfAcc
							.get(v.getId()).getIsFavorite());
					startActivity(viewAccountIntent);
				}
			});

			Resources res = getResources();
			Drawable drawable = setCategoryIcon(listOfAcc.get(acc).getCategory(), res);
			
			if (acc % 2 == 0) {
				tv.setBackgroundColor(Color.WHITE);
			} else {
				tv.setBackgroundColor(Color.LTGRAY);
			}
			tv.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null,
					null, null);

			linearLayout.addView(tv);
		}
	}

	public Drawable setCategoryIcon(String category, Resources res)
	{
		Drawable drawable = null;
		switch(category){
		case "Gaming":
			drawable = res.getDrawable(R.drawable.ic_games);
			break;
		case "Internet Sites":
			drawable = res.getDrawable(R.drawable.ic_internet);
			break;
		case "Social Network":
			drawable = res.getDrawable(R.drawable.ic_fb);
			break;
		case "Work":
			drawable = res.getDrawable(R.drawable.ic_work);
			break;
		default: //other
			drawable = res.getDrawable(R.drawable.ic_other);
			break;	
		}
		return drawable;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		AccountDatabase.getInstance().deleteObserver(this);
	}
	
	@Override
	public void update(Observable observable, Object data) {
		addAccountsToLinearLayout();
	}
}