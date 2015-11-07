package com.example.wps.gui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.wps.R;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wps.db.Account;
import com.example.wps.db.ReadXMLFile;

public class FrequencyViewActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ArrayList<Account> listOfAcc = (ArrayList<Account>) ReadXMLFile
				.getAllAccounts();

		setContentView(R.layout.account_list_scrollview_layout);
		// addAccountsToLinearLayout(listOfAcc);

		addAccountsToLinearLayout(listOfAcc);
	}

	public void addAccountsToLinearLayout(ArrayList<Account> listOfAccounts) {

		ArrayList<Account> orderedListOfAccounts = makeOrderedListOfAccounts(listOfAccounts);

		for (int acc = 0; acc < orderedListOfAccounts.size(); acc++) {
			String userTitle = orderedListOfAccounts.get(acc).getName();
			String userID = orderedListOfAccounts.get(acc).getId();
			String userPass = orderedListOfAccounts.get(acc).getPassword();

			TextView tv = new TextView(this);
			tv.setId(acc);
			tv.setText(userTitle + "\n" + userID + "\n" + userPass);
			tv.setClickable(true);
			tv.setLines(3);

			LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayoutAccountsList);
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
			
			System.out.println(tv.getText());
			linearLayout.addView(tv);
		}
	}

	public ArrayList<Account> makeOrderedListOfAccounts(
			ArrayList<Account> listOfAccounts) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ArrayList<Account> orderedListOfAccounts = new ArrayList<Account>();
		// we first order the accounts by last date used
		for (int acc = 0; acc < listOfAccounts.size(); acc++) {

			Account currentAccount = listOfAccounts.get(acc);

			if (orderedListOfAccounts.size() == 0) {
				orderedListOfAccounts.add(currentAccount);
				System.out.println("Adding cz 0" + currentAccount.getName());
			} else {
				
//				TODO comparison of dates not working as intended (just checking hour?).
//				Use joda?
				
				
				Account firstAccount = orderedListOfAccounts.get(0);

				Date currentAccountLastAccessed = null;
				try {
					currentAccountLastAccessed = formatter.parse(currentAccount
							.getLastAccess());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Date firstAccountLastAccessed = null;
				try {
					firstAccountLastAccessed = formatter.parse(firstAccount
							.getLastAccess());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// we compare the account to the most recently used (ie the
				// first
				// account in the list) if it was used more recently, it will be
				// added
				// as the new first account
				if (currentAccountLastAccessed.before(firstAccountLastAccessed)) {
					orderedListOfAccounts.add(0, currentAccount);

					System.out.println("Adding first" + currentAccount.getName());
				}
				// else it gets added at end 
				else {
					orderedListOfAccounts.add(currentAccount);
					System.out.println("Adding last" + currentAccount.getName());
				}

			}
		}
		return orderedListOfAccounts;
	}
}