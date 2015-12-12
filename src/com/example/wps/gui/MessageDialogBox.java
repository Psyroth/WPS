package com.example.wps.gui;

import android.content.Context;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class MessageDialogBox {

	Context context;
	String title;
	String message;
	String buttonText;

	public MessageDialogBox(Context context, String title, String message,
			String buttonText) {
		this.context = context;
		this.title = title;
		this.message = message;
		this.buttonText = buttonText;
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
								dialog.cancel();
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
}