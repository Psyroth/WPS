package com.example.wps.gui;

import com.example.wps.R;
import com.example.wps.db.Account;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

/**
 * Graphical representation of an account in a list.
 */
public class AccountView extends TextView {

	public AccountView(final Context context, final Account account, final int id) {
		super(context);
		String userTitle = account.getName();
		String userID = account.getId();
		String userPass = "******";

		this.setId(id);
		this.setText(userTitle + "\n" + userID + "\n" + userPass);
		this.setClickable(true);
		this.setLines(3);
		this.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("Clicked on element : " + v.getId());
				// Launch viewAccountActivity
				Intent viewAccountIntent = new Intent(
						context, ViewAccountActivity.class);

				viewAccountIntent.putExtra("AccountName",
						account.getName());
				viewAccountIntent.putExtra("AccountId",
						account.getId());
				viewAccountIntent.putExtra("AccountPassword",
						account.getPassword());
				viewAccountIntent.putExtra("AccountUrl",
						account.getUrl());
				viewAccountIntent.putExtra("AccountLastAccess",
						account.getLastAccess());
				viewAccountIntent.putExtra("AccountCategory",
						account.getCategory());
				viewAccountIntent.putExtra("AccountNote",
						account.getNote());
				viewAccountIntent.putExtra("AccountIsFavorite",
						account.getIsFavorite());
				context.startActivity(viewAccountIntent);
			}
		});

		Resources res = getResources();
		Drawable drawable = setCategoryIcon(account.getCategory(), res);

		if (id % 2 == 0) {
			this.setBackgroundColor(Color.WHITE);
		} else {
			this.setBackgroundColor(Color.LTGRAY);
		}
		this.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null,
				null, null);
	}
	
	/* Adds the corresponding Icon to each category */
	private Drawable setCategoryIcon(String category, Resources res) {
		Drawable drawable = null;
		switch (category) {
		case "Gaming":
			drawable = res.getDrawable(R.drawable.ic_games);
			break;
		case "Internet Sites":
			drawable = res.getDrawable(R.drawable.ic_internet);
			break;
		case "Social Network":
			drawable = res.getDrawable(R.drawable.ic_fb);
			break;
		case "Work":
			drawable = res.getDrawable(R.drawable.ic_work);
			break;
		default: // other
			drawable = res.getDrawable(R.drawable.ic_other);
			break;
		}
		return drawable;
	}

}
