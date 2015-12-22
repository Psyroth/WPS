package com.example.wps.gui;

import java.util.ArrayList;

import com.example.wps.db.Account;
import com.example.wps.db.AccountDatabase;

/**
 * Display accounts by last access.
 */
public class FrequencyActivity extends ListOfAccountsActivity {

	@Override
	protected ArrayList<Account> getSortedAccounts() {
		ArrayList<Account> accounts = AccountDatabase.getInstance().getAllAccounts();
		AccountDatabase.sortAccountListByLastAccess(accounts);
		return accounts;
	}
	
}