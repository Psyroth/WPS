package com.example.wps.gui;

import com.example.wps.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.wps.db.Account;

public class ListViewActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Account testAccount1 = new Account("Facebook",
				"facebookUser@hotmail.com", "facebook",
				"https://www.facebook.com", "Sat, 12 Aug 2005 13:30:00 GMT",
				"Less useful than Linkedin", "Social Network");
        
        String userTitle = testAccount1.getName();
        String userID = testAccount1.getId();
        String userPass = testAccount1.getPassword();
        
        TextView tv = (TextView) findViewById(R.id.editText15);
        tv.setText(userTitle+"\n"+userID+userPass);
//        tv.setText(userPass);
        
		setContentView(R.layout.account_list_scrollview_layout);
		
		
		
		
//        TextView textview = new TextView(this);
//        textview.setText("This is the list view tab");
//        setContentView(textview);
    }
}