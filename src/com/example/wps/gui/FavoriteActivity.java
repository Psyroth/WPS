package com.example.wps.gui;

import java.util.ArrayList;
import com.example.wps.db.Account;
import com.example.wps.db.AccountDatabase;

/**
 * Show Favorite accounts.
 */
public class FavoriteActivity extends ListOfAccountsActivity {

	@Override
	protected ArrayList<Account> getSortedAccounts() {
		ArrayList<Account> accounts = AccountDatabase.getInstance().getAllFavoriteAccounts();
		return accounts;
	}
	
}