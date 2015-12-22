package com.example.wps.gui;

import com.example.wps.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SingleItemActivity extends Activity {

	private TextView txtname, txtid, txtpassword;
	private String name, id, password;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.singleitemview);
		// Retrieve data from MainActivity on item click event
		Intent i = getIntent();
		// Get the results
		name = i.getStringExtra("Name");
		id = i.getStringExtra("Id");
		password = i.getStringExtra("Password");

		// Locate the TextViews in singleitemview.xml
		txtname = (TextView) findViewById(R.id.name);
		txtid = (TextView) findViewById(R.id.id);
		txtpassword = (TextView) findViewById(R.id.password);

		// Load the results into the TextViews
		txtname.setText(name);
		txtid.setText(id);
		txtpassword.setText(password);
	}
}