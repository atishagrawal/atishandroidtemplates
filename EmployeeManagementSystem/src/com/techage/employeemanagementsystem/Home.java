package com.techage.employeemanagementsystem;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;

public class Home extends Activity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		// Make sure we're running on Honeycomb or higher to use ActionBar APIs
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// Show the Up button in the action bar.
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		final Intent intent = new Intent(this, LoginActivity.class);
		// startActivity(intent);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				// Intent intent = new Intent(this, LoginActivity.class);
				startActivity(intent);

			}
		}, 1000);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	public void openLoginActivity(View view) {
		Intent intent1 = new Intent(this, LoginActivity.class);
		startActivity(intent1);

	}

	public void createAccount(View view) {
		Intent intent = new Intent(this, CreateAccountActivity.class);
		startActivity(intent);

	}

}
