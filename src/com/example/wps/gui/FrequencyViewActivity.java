package com.example.wps.gui;

import java.util.ArrayList;

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

public class FrequencyViewActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ArrayList<Account> listOfAcc = (ArrayList<Account>) AccountDatabase
				.getAllAccounts();

		setContentView(R.layout.account_list_scrollview_layout);
		addAccountsToLinearLayout(listOfAcc);
	}

	public void addAccountsToLinearLayout(ArrayList<Account> listOfAccounts) {

		AccountDatabase.sortAccountListByLastAccess(listOfAccounts);

		for (int acc = 0; acc < listOfAccounts.size(); acc++) {
			String userTitle = listOfAccounts.get(acc).getName();
			String userID = listOfAccounts.get(acc).getId();
			String userPass = listOfAccounts.get(acc).getPassword();

			LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayoutAccountsList);
			
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
					Intent viewAccountIntent = new Intent(FrequencyViewActivity.this, ViewAccountActivity.class);
					
					// TODO : Need to get the correct data
					
					viewAccountIntent.putExtra("AccountName", "DummyFacebook");
					viewAccountIntent.putExtra("AccountId", "DummyId");
					viewAccountIntent.putExtra("AccountPassword", "DummyPassword");
					viewAccountIntent.putExtra("AccountUrl", "DummyUrl");
					viewAccountIntent.putExtra("AccountCategory", "DummyCategory");
					viewAccountIntent.putExtra("AccountNote", "DummyNote");
					viewAccountIntent.putExtra("AccountIsFavorite", true);
					startActivity(viewAccountIntent);
				}
			});

			Resources res = getResources();
			Drawable drawable = null;

			if (acc % 2 == 0) {
				tv.setBackgroundColor(Color.WHITE);
				drawable = res.getDrawable(R.drawable.ic_face_black_24dp);
			} else {
				tv.setBackgroundColor(Color.LTGRAY);
				drawable = res
						.getDrawable(R.drawable.ic_phonelink_lock_black_24dp);
			}
			tv.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null,
					null, null);

			linearLayout.addView(tv);
		}
	}
}