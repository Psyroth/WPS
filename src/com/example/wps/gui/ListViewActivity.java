package com.example.wps.gui;

import java.util.ArrayList;

import com.example.wps.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wps.db.Account;

public class ListViewActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Account testAccount1 = new Account("Facebook",
				"facebookUser@hotmail.com", "facebook",
				"https://www.facebook.com", "Sat, 12 Aug 2005 13:30:00 GMT",
				"Less useful than Linkedin", "Social Network");
        
        Account testAccount2 = new Account("Gmail", "gmailUser@hotmail.com",
				"gmail", "https://www.gmail.com",
				"Mon,20 Oct 2015 17:40:00 GMT", "Avoid Spam please", "E-Mail");
        
        ArrayList<Account> listOfAcc = new ArrayList<Account>();
        listOfAcc.add(testAccount1);
        listOfAcc.add(testAccount2);
        
		setContentView(R.layout.account_list_scrollview_layout);
        
		addAccountsToLinearLayout(listOfAcc);

    }
    
    
    public void addAccountsToLinearLayout(ArrayList<Account> listOfAccounts){
    	
    	for(int acc=0; acc<listOfAccounts.size(); acc++){
            String userTitle = listOfAccounts.get(acc).getName();
            String userID = listOfAccounts.get(acc).getId();
            String userPass = listOfAccounts.get(acc).getPassword();
            
    		LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearLayoutAccountsList);
    		
            TextView tv = new TextView(this);
            tv.setText(userTitle+"\n"+userID+"\n"+userPass);
            
            linearLayout.addView(tv);
    	}
    }
}