package com.example.wps.gui;

import android.content.Context;
import android.content.Intent;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class MessageDialogBox {

	Context context;
	String title;
	String message;
	String buttonText;
	Boolean changeIntent = false;
	Activity oldActivity;
	Context packageContext;
	Class nextClass;

	public MessageDialogBox(Context context, String title, String message,
			String buttonText) {
		this.context = context;
		this.title = title;
		this.message = message;
		this.buttonText = buttonText;
	}

	public void prepareNewIntent(Activity oldActivity, Context packageContext,
			Class nextClass) {
		this.oldActivity = oldActivity;
		this.changeIntent = true;
		this.packageContext = packageContext;
		this.nextClass = nextClass;
	}

	public void displayDialogBox() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				this.getContext());

		// set title
		alertDialogBuilder.setTitle(this.getTitle());

		// set dialog message
		alertDialogBuilder
				.setMessage(this.getMessage())
				.setCancelable(false)
				.setPositiveButton(this.getButtonText(),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								if (changeIntent) {
									dialog.cancel();
									Intent i = new Intent(packageContext,
											nextClass);
									oldActivity.startActivity(i);
								} else {
									dialog.cancel();
								}
							}
						});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
		// show it
		alertDialog.show();
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getButtonText() {
		return buttonText;
	}

	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}

	public Boolean getChangeIntent() {
		return changeIntent;
	}

	public void setChangeIntent(Boolean changeIntent) {
		this.changeIntent = changeIntent;
	}

	public Context getPackageContext() {
		return packageContext;
	}

	public void setPackageContext(Context packageContext) {
		this.packageContext = packageContext;
	}

	public Class getNextClass() {
		return nextClass;
	}

	public void setNextClass(Class nextClass) {
		this.nextClass = nextClass;
	}
}