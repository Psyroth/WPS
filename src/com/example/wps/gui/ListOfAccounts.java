package com.example.wps.gui;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.example.wps.MainActivity;
import com.example.wps.R;

public class ListOfAccounts extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.search_or_category_layout);

		Resources ressources = getResources();
		TabHost tabHost = getTabHost();

		// Favorite List View tab
		Intent intentFavoriteView = new Intent().setClass(this,
				FavoriteViewActivity.class);
		TabSpec tabSpecFavoriteView = tabHost
				.newTabSpec("Favorite View")
				.setIndicator("",
						ressources.getDrawable(R.drawable.ic_favorite))
				.setContent(intentFavoriteView);

		// A_Z List View tab
		Intent intentListView = new Intent().setClass(this,
				ListViewActivity.class);
		// Added data to display the accounts of all categories
		intentListView.putExtra("WithCategory", "All");
		TabSpec tabSpecListView = tabHost
				.newTabSpec("List View")
				.setIndicator("",
						ressources.getDrawable(R.drawable.ic_listicon))
				.setContent(intentListView);

		// Category View tab
		Intent intentCategoryView = new Intent().setClass(this,
				CategoryViewActivity.class);
		TabSpec tabSpecCategoryView = tabHost.newTabSpec("Category View")
				.setIndicator("", ressources.getDrawable(R.drawable.ic_action))
				.setContent(intentCategoryView);

		// Frequency View tab
		Intent intentFrequencyView = new Intent().setClass(this,
				FrequencyViewActivity.class);
		TabSpec tabSpecFrequencyView = tabHost
				.newTabSpec("Frequency View")
				.setIndicator("",
						ressources.getDrawable(R.drawable.ic_timeicon))
				.setContent(intentFrequencyView);

		// Search tab
		Intent intentSearch = new Intent().setClass(this, SearchActivity.class);
		TabSpec tabSpecSearch = tabHost.newTabSpec("Search")
				.setIndicator("", ressources.getDrawable(R.drawable.ic_search))
				.setContent(intentSearch);

		// Add Account tab
		Intent intentAddAccount = new Intent().setClass(this,
				AddAccountActivity.class);
		TabSpec tabSpecAddAccount = tabHost.newTabSpec("Add Account")
				.setIndicator("", ressources.getDrawable(R.drawable.ic_add))
				.setContent(intentAddAccount);

		// Add all tabs
		tabHost.addTab(tabSpecFavoriteView);
		tabHost.addTab(tabSpecListView);
		tabHost.addTab(tabSpecCategoryView);
		tabHost.addTab(tabSpecFrequencyView);
		tabHost.addTab(tabSpecSearch);
		tabHost.addTab(tabSpecAddAccount);

		// Set Favortie tab as default (zero based)
		tabHost.setCurrentTab(0);
	}

	public void showPassGen() {
		Intent i = new Intent(this, PasswordGenViewActivity.class);
		startActivity(i);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		switch (id) {
		case R.id.action_settings:
			// settings();
			return true;
		case R.id.action_passgen:
			showPassGen();
			return true;
		case R.id.action_exit:
			System.exit(1);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}