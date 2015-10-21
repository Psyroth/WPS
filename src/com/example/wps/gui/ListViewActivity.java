package com.example.wps.gui;

import com.example.wps.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ListViewActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		setContentView(R.layout.account_list_scrollview_layout);
//        TextView textview = new TextView(this);
//        textview.setText("This is the list view tab");
//        setContentView(textview);
    }
}