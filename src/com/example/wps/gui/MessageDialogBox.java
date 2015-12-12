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
	Boolean changeActivity = false;
	Activity oldActivity;
	Context packageContext;
	Class nextClass;

	/* MessageDialogBox Constructor */
	public MessageDialogBox(Context context, String title, String message,
			String buttonText) {
		this.context = context;
		this.title = title;
		this.message = message;
		this.buttonText = buttonText;
	}

	/* If you want to switch Activity after displaying the MessageDialogBox */
	public void prepareNewIntent(Activity oldActivity, Context packageContext,
			Class nextClass) {
		this.oldActivity = oldActivity;
		this.changeActivity = true;
		this.packageContext = packageContext;
		this.nextClass = nextClass;
	}

	/* To display the Dialog Box with current attributes */
	public void displayDialogBox() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				this.getContext());

		// Set title
		alertDialogBuilder.setTitle(this.getTitle());

		// Set dialog message
		alertDialogBuilder
				.setMessage(this.getMessage())
				.setCancelable(false)
				.setPositiveButton(this.getButtonText(),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								if (changeActivity) { // If we want to switch
														// Activity
									dialog.cancel();
									Intent i = new Intent(packageContext,
											nextClass);
									oldActivity.startActivity(i);
								} else {
									dialog.cancel();
								}
							}
						});

		// Create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
		// Show it
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

	public Boolean getChangeActivity() {
		return changeActivity;
	}

	public void setChangeActivity(Boolean changeActivity) {
		this.changeActivity = changeActivity;
	}
}