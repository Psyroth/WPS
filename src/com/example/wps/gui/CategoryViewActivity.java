package com.example.wps.gui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class CategoryViewActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);

        TextView textview = new TextView(this);
        textview.setText("This is the category view tab");
        setContentView(textview);
    }
}