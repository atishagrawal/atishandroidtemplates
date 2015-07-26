package com.techage.employeemanagementsystem;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class EmployeeLoginActivity extends Activity {

	@Override
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_employee_login);

		// Make sure we're running on Honeycomb or higher to use ActionBar APIs
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// Show the Up button in the action bar.
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}

		// Get the message from the intent
		Intent intent = getIntent();
		String uname = intent.getStringExtra("USERNAME");
		String pswd = intent.getStringExtra("PASSWORD");

		TextView txtUsername = (TextView) findViewById(R.id.loginUsername);
		txtUsername.setText(uname);

		TextView txtPassword = (TextView) findViewById(R.id.loginPassword);
		txtPassword.setText(pswd);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.employee_login, menu);
		return true;
	}

}
