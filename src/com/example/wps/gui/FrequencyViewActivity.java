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
	ArrayList<Account> listOfAcc;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		listOfAcc = (ArrayList<Account>) AccountDatabase.getAllAccounts();

		setContentView(R.layout.account_list_scrollview_layout);
		addAccountsToLinearLayout();
	}

	public void addAccountsToLinearLayout() {

		AccountDatabase.sortAccountListByLastAccess(listOfAcc);

		for (int acc = 0; acc < listOfAcc.size(); acc++) {
			String userTitle = listOfAcc.get(acc).getName();
			String userID = listOfAcc.get(acc).getId();
			String userPass = listOfAcc.get(acc).getPassword();

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
					Intent viewAccountIntent = new Intent(
							FrequencyViewActivity.this,
							ViewAccountActivity.class);

					// Need to change lastAccess then

					viewAccountIntent.putExtra("AccountName",
							listOfAcc.get(v.getId()).getName());
					viewAccountIntent.putExtra("AccountId",
							listOfAcc.get(v.getId()).getId());
					viewAccountIntent.putExtra("AccountPassword", listOfAcc
							.get(v.getId()).getPassword());
					viewAccountIntent.putExtra("AccountUrl",
							listOfAcc.get(v.getId()).getUrl());
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