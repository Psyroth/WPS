package com.example.wps.gui;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wps.R;
import com.example.wps.db.Account;
import com.example.wps.db.AccountDatabase;

public class FavoriteViewActivity extends Activity implements Observer {
	ArrayList<Account> favAccounts;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.account_list_scrollview_layout);
		addAccountsToLinearLayout();
		AccountDatabase.getInstance().addObserver(this);
	}

	public void addAccountsToLinearLayout() {

		favAccounts = (ArrayList<Account>) AccountDatabase.getInstance()
				.getAllFavoriteAccounts();
		
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayoutAccountsList);
		linearLayout.removeAllViews();

		for (int acc = 0; acc < favAccounts.size(); acc++) {
			String userTitle = favAccounts.get(acc).getName();
			String userID = favAccounts.get(acc).getId();
			String userPass = favAccounts.get(acc).getPassword();

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
							FavoriteViewActivity.this,
							ViewAccountActivity.class);

					viewAccountIntent.putExtra("AccountName",
							favAccounts.get(v.getId()).getName());
					viewAccountIntent.putExtra("AccountId",
							favAccounts.get(v.getId()).getId());
					viewAccountIntent.putExtra("AccountPassword", favAccounts
							.get(v.getId()).getPassword());
					viewAccountIntent.putExtra("AccountUrl",
							favAccounts.get(v.getId()).getUrl());
					viewAccountIntent.putExtra("AccountCategory", favAccounts
							.get(v.getId()).getCategory());
					viewAccountIntent.putExtra("AccountNote",
							favAccounts.get(v.getId()).getNote());
					viewAccountIntent.putExtra("AccountIsFavorite", favAccounts
							.get(v.getId()).getIsFavorite());
					startActivity(viewAccountIntent);
				}
			});

			Resources res = getResources();
			Drawable drawable = setCategoryIcon(favAccounts.get(acc).getCategory(), res);
			
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