package com.example.wps.gui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wps.R;
import com.example.wps.db.Account;
import com.example.wps.db.AccountDatabase;

public class FavoriteViewActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.account_list_scrollview_layout);
		addAccountsToLinearLayout();
	}

	public void addAccountsToLinearLayout() {

		ArrayList<Account> favAccounts = (ArrayList<Account>) AccountDatabase.getAllFavoriteAccounts();
		
		for (int acc = 0; acc < favAccounts.size(); acc++) {
				String userTitle = favAccounts.get(acc).getName();
				String userID = favAccounts.get(acc).getId();
				String userPass = favAccounts.get(acc).getPassword();

				LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayoutAccountsList);

				TextView tv = new TextView(this);
				tv.setId(acc);
				tv.setText(userTitle + "\n" + userID + "\n" + userPass);
				tv.setClickable(true);
				tv.setLines(3);

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
				tv.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable,
						null, null, null);

				linearLayout.addView(tv);
			}
	}
}