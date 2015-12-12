package com.example.wps.gui;

import com.example.wps.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.example.wps.encryption.PasswordGenerator;

public class PasswordGenViewActivity extends Activity {

	EditText mEtPwd;
	CheckBox mCbShowPwd;
	CheckBox mCbWithNumbers;
	CheckBox mCbWithSpecial;
	CheckBox mCbWithAlphabet;
	NumberPicker np;
	PasswordGenerator pwdGen = new PasswordGenerator();
	final Context context = this;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.password_generator_viewer);

		// get the password EditText
		mEtPwd = (EditText) findViewById(R.id.PasswordGenerated);
		// get the show/hide password Checkbox
		mCbShowPwd = (CheckBox) findViewById(R.id.ShowPwdCheckBox);
		mCbWithNumbers = (CheckBox) findViewById(R.id.NumberCheckBox);
		mCbWithSpecial = (CheckBox) findViewById(R.id.SpecialCharCheckBox);
		mCbWithAlphabet = (CheckBox) findViewById(R.id.AlphabeticCheckBox);

		np = (NumberPicker) findViewById(R.id.lengthOfPwdNumberPicker);
		np.setMinValue(0);
		np.setMaxValue(30);
		np.setWrapSelectorWheel(false);

		// when user clicks on this checkbox, this is the handler.
		mCbShowPwd.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// checkbox status is changed from uncheck to checked.
				if (!isChecked) {
					// show password
					mEtPwd.setTransformationMethod(PasswordTransformationMethod
							.getInstance());
				} else {
					// hide password
					mEtPwd.setTransformationMethod(HideReturnsTransformationMethod
							.getInstance());
				}
			}
		});
	}

	public void genPasswordAfterButtonClicked(View view) {

		if (mCbWithNumbers.isChecked() || mCbWithAlphabet.isChecked()
				|| mCbWithSpecial.isChecked()) {
			mEtPwd.setText(pwdGen.generatePassword(np.getValue(),
					mCbWithNumbers.isChecked(), mCbWithAlphabet.isChecked(),
					mCbWithSpecial.isChecked()));
		} else {

			MessageDialogBox passwordDialogBox = new MessageDialogBox(context,
					"Error", "You need something to build your password !",
					"Ok");
			passwordDialogBox.displayDialogBox();
		}
	}
	
	public void copyToClipboardButtonClicked(View view){
		System.out.println(mEtPwd.getText());
		 ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE); 
		 ClipData clip = ClipData.newPlainText("PwdGenCopy", mEtPwd.getText());
		 clipboard.setPrimaryClip(clip);
	}
	
}