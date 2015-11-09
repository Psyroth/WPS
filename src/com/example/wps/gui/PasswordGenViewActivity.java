package com.example.wps.gui;

import com.example.wps.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.wps.encryption.PasswordGenerator;


public class PasswordGenViewActivity extends Activity {

	EditText mEtPwd;
	CheckBox mCbShowPwd;
	CheckBox mCbWithNumbers;
	CheckBox mCbWithSpecial;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.password_generator_viewer);
		
		 // get the password EditText
	    mEtPwd = (EditText) findViewById(R.id.PasswordGenerated);
	    // get the show/hide password Checkbox
	    mCbShowPwd = (CheckBox) findViewById(R.id.ShowPwdCheckBox);
	    
	    mCbWithNumbers = (CheckBox) findViewById(R.id.NumberCheckBox);
	    mCbWithSpecial = (CheckBox) findViewById(R.id.SpecialCharCheckBox);
	    
	    // add onCheckedListener on checkbox
	    // when user clicks on this checkbox, this is the handler.
	    mCbShowPwd.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	        
	        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	            // checkbox status is changed from uncheck to checked.
	            if (!isChecked) {
	                    // show password
	            	mEtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
	            } else {
	                    // hide password
	            	mEtPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
	            }
	        }
	    });
	    
	    
	    
	    
	    
	    
		
	}
	
	public void genPasswordAfterButtonClicked(View view)
	{
		PasswordGenerator pwdGen = new PasswordGenerator(20, true, true, true);
	    
	    mEtPwd.setText(pwdGen.generatePassword(20, true, true, true));
	}
}



   