package com.example.wps.gui;

import java.util.ArrayList;

import com.example.wps.R;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wps.db.Account;
import com.example.wps.db.ReadXMLFile;

public class ListViewActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        
//        ReadXMLFile.initDatabase();
//        ArrayList<Account> listOfAcc = (ArrayList<Account>) ReadXMLFile.getAllAccounts();
        
//        System.out.println("!!!!!!!!!!!!"+listOfAcc.size());
        
		setContentView(R.layout.account_list_scrollview_layout);
        
//		addAccountsToLinearLayout(listOfAcc);
		
		
		Account testAccount1 = new Account("Facebook",
				"facebookUser@hotmail.com", "facebook",
				"https://www.facebook.com", "2014-09-12 22:00:00",
				"Less useful than Linkedin", "Social Network");
		Account testAccount1Bis = new Account("FacebookBis",
				"facebookUser@gmail.com", "facebook",
				"https://www.facebook.com", "2009-06-01 18:45:00",
				"Less useful than Linkedin", "Social Network");
		Account testAccount2 = new Account("Gmail", "gmailUser@hotmail.com",
				"gmail", "https://www.gmail.com", "2012-02-24 13:42:00",
				"Avoid Spam please", "E-Mail");
		Account testAccount3 = new Account("Youtube", "youtubeUser", "youtube",
				"https://www.youtube.com", "2010-12-13 12:30:00",
				"Best Channel Ever", "Entertainment");
		Account testAccount4 = new Account("Webmail", "webmailUser@ulb.ac.be",
				"webmail", "https://webmail.ulb.ac.be/", "2005-08-20 09:30:00",
				"E-mail delivery system", "E-Mail");
		
		ArrayList<Account> listOfAcc = new ArrayList<Account>();
		
		listOfAcc.add(testAccount1);
		listOfAcc.add(testAccount1Bis);
		listOfAcc.add(testAccount2);
		listOfAcc.add(testAccount3);
		listOfAcc.add(testAccount4);
		listOfAcc.add(testAccount4);
		listOfAcc.add(testAccount4);
		listOfAcc.add(testAccount4);
		listOfAcc.add(testAccount4);
		listOfAcc.add(testAccount4);
		listOfAcc.add(testAccount4);
		listOfAcc.add(testAccount4);
		listOfAcc.add(testAccount1);
		listOfAcc.add(testAccount2);
		
		addAccountsToLinearLayout(listOfAcc);
		
    }
    
    
    public void addAccountsToLinearLayout(ArrayList<Account> listOfAccounts){

    	Color color = new Color();
    	for(int acc=0; acc<listOfAccounts.size(); acc++){
            String userTitle = listOfAccounts.get(acc).getName();
            String userID = listOfAccounts.get(acc).getId();
            String userPass = listOfAccounts.get(acc).getPassword();
            
    		LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearLayoutAccountsList);
    		
            TextView tv = new TextView(this);
            tv.setId(acc);
            tv.setText(userTitle+"\n"+userID+"\n"+userPass);
            tv.setClickable(true);
            tv.setLines(3);
            
            Resources res = getResources();
            Drawable drawable = null;
            
            if(acc % 2 == 0)
            {
                tv.setBackgroundColor(color.WHITE);
                drawable = res.getDrawable(R.drawable.ic_face_black_24dp);
            }
            else
            {
            	tv.setBackgroundColor(color.LTGRAY);
                drawable = res.getDrawable(R.drawable.ic_phonelink_lock_black_24dp);
            }
            tv.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null);
            
            
            linearLayout.addView(tv);
    	}
    }
}