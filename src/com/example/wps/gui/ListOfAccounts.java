package com.example.wps.gui;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.example.wps.R;

public class ListOfAccounts extends TabActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		//shows initial app window
		setContentView(R.layout.search_or_category_layout);
		
		
		Resources ressources = getResources(); 
		TabHost tabHost = getTabHost(); 
		
		// List View tab
		Intent intentListView = new Intent().setClass(this, ListViewActivity.class);
		TabSpec tabSpecListView = tabHost
		  .newTabSpec("List View")
		  .setIndicator("", ressources.getDrawable(R.drawable.ic_launcher))
		  .setContent(intentListView);
		
		// Category View tab
		Intent intentCategoryView = new Intent().setClass(this, CategoryViewActivity.class);
		TabSpec tabSpecCategoryView = tabHost
			.newTabSpec("Category View")
			.setIndicator("", ressources.getDrawable(R.drawable.ic_launcher))
			.setContent(intentCategoryView);
		
		// add all tabs 
		tabHost.addTab(tabSpecListView);
		tabHost.addTab(tabSpecCategoryView);
		
		//set Windows tab as default (zero based)
		tabHost.setCurrentTab(0);
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
		
		switch (id)
        {
        	case R.id.action_settings:
        		//settings();
        		return true;
        	case R.id.action_exit:
        		System.exit(1);
        		return true;
        	default:
        		return super.onOptionsItemSelected(item);
        }
	}
}