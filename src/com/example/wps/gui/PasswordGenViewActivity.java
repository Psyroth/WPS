package com.example.wps.gui;

import com.example.wps.R;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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

/**
 * Generate random passwords.
 */
public class PasswordGenViewActivity extends Activity {

	private EditText mEtPwd;
	private CheckBox mCbShowPwd, mCbWithNumbers, mCbWithSpecial, mCbWithAlphabet;
	private NumberPicker np;
	private PasswordGenerator pwdGen = new PasswordGenerator();
	private final Context context = this;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.password_generator_viewer);

		// Locate the TextViews and CheckBoxes in password_generator_viewer.xml
		mEtPwd = (EditText) findViewById(R.id.PasswordGenerated);
		mCbShowPwd = (CheckBox) findViewById(R.id.ShowPwdCheckBox);
		mCbWithNumbers = (CheckBox) findViewById(R.id.NumberCheckBox);
		mCbWithSpecial = (CheckBox) findViewById(R.id.SpecialCharCheckBox);
		mCbWithAlphabet = (CheckBox) findViewById(R.id.AlphabeticCheckBox);

		np = (NumberPicker) findViewById(R.id.lengthOfPwdNumberPicker);
		np.setMinValue(0);
		np.setMaxValue(30);
		np.setWrapSelectorWheel(false);

		// When user clicks on this checkbox, this is the handler.
		mCbShowPwd.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// Checkbox status is changed from uncheck to checked.
				if (!isChecked) {
					// Show password
					mEtPwd.setTransformationMethod(PasswordTransformationMethod
							.getInstance());
				} else {
					// Hide password
					mEtPwd.setTransformationMethod(HideReturnsTransformationMethod
							.getInstance());
				}
			}
		});
	}

	/*
	 * When user pushes "Generate Password" button, it will generate a password
	 * according to what parameters he used
	 */
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

	/*
	 * When user pushes "Copy to Clipboard" button, it will copy the generated
	 * password in his Clipboard for future use
	 */
	public void copyToClipboardButtonClicked(View view) {
		System.out.println(mEtPwd.getText());
		ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
		ClipData clip = ClipData.newPlainText("PwdGenCopy", mEtPwd.getText());
		clipboard.setPrimaryClip(clip);
	}

}