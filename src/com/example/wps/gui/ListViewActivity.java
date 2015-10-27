package com.example.wps.gui;

import java.util.ArrayList;

import com.example.wps.R;

import android.app.Activity;
import android.os.Bundle;
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