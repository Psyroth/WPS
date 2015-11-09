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
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;

import com.example.wps.encryption.PasswordGenerator;


public class PasswordGenViewActivity extends Activity {

	EditText mEtPwd;
	CheckBox mCbShowPwd;
	CheckBox mCbWithNumbers;
	CheckBox mCbWithSpecial;
	CheckBox mCbWithAlphabet;
	NumberPicker np;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.password_generator_viewer);
		
		 // get the password EditText
	    mEtPwd = (EditText) findViewById(R.id.PasswordGenerated);
	    // get the show/hide password Checkbox
	    mCbShowPwd = (CheckBox) findViewById(R.id.ShowPwdCheckBox);
	    mCbWithSpecial = (CheckBox) findViewById(R.id.SpecialCharCheckBox);
	    mCbWithAlphabet = (CheckBox) findViewById(R.id.AlphabeticCheckBox);
	    
	    
	    np = (NumberPicker) findViewById(R.id.lengthOfPwdNumberPicker);
	    np.setMinValue(0);
        np.setMaxValue(30);
        np.setWrapSelectorWheel(false); 
	    
        np.setOnValueChangedListener(new OnValueChangeListener() {
			
    		@Override
    		public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
    			// TODO Auto-generated method stub
    			
    			String Old = "Old Value : ";
    			String New = "New Value : ";
    			
//    			tv1.setText(Old.concat(String.valueOf(oldVal)));
//    			tv2.setText(New.concat(String.valueOf(newVal)));
    		}
    	});
        
	    
	    mCbWithNumbers = (CheckBox) findViewById(R.id.NumberCheckBox);
	    mCbWithAlphabet = (CheckBox) findViewById(R.id.AlphabeticCheckBox);
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



   