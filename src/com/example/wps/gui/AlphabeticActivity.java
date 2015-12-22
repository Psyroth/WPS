package com.example.wps.gui;

import java.util.ArrayList;

import com.example.wps.db.Account;
import com.example.wps.db.AccountDatabase;

/**
 * Display accounts in alphabetic order.
 */
public class AlphabeticActivity extends ListOfAccountsActivity {

	@Override
	protected ArrayList<Account> getSortedAccounts() {
		ArrayList<Account> accounts = AccountDatabase.getInstance().getAllAccounts();
		AccountDatabase.sortAccountListByAlphabeticOrder(accounts);
		return accounts;
	}

}