package com.techage.employeemanagementsystem;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.techage.employeemanagementsystem.CreateEmployeeContract.CreateEmployee;

public class LoginActivity extends Activity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

	}

	@Override
	public void onPause() {
		super.onPause();
		finish();
	}

	public void login(View view) {

		EditText editText = (EditText) findViewById(R.id.username);
		String uname = editText.getText().toString();

		EditText editText1 = (EditText) findViewById(R.id.password);
		String pswd = editText1.getText().toString();

		if (uname.isEmpty() || pswd.isEmpty()) {
			// 1. Instantiate an AlertDialog.Builder with its constructor
			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			// 2. Chain together various setter methods to set the dialog
			// characteristics
			builder.setMessage(R.string.incompletelogindetails).setTitle(
					R.string.error);

			// Add the buttons
			builder.setPositiveButton(R.string.ok,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							// User clicked OK button
						}
					});

			// Create the AlertDialog
			AlertDialog dialog = builder.create();
			dialog.show();
			return;

		} else {

			String str = uname;

			// Log.d("Username", str);
			// Log.d("Password", pswd);

			EmployeeCRUD employeeCRUD = new EmployeeCRUD();
			Cursor c = employeeCRUD.showEmployeeDetails(getBaseContext(), str);

			// checking for empty cursor

			if (c.getCount() == 0) {
				// 1. Instantiate an AlertDialog.Builder with its constructor
				AlertDialog.Builder builder1 = new AlertDialog.Builder(this);

				// 2. Chain together various setter methods to set the dialog
				// characteristics
				builder1.setMessage(R.string.usernamedoesnotexists).setTitle(
						R.string.error);

				// Add the buttons
				builder1.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								// User clicked OK button
							}
						});

				// Create the AlertDialog
				AlertDialog dialog = builder1.create();
				dialog.show();
				return;

			}

			/*
			
			
			
			*/

			c.moveToFirst();
			// Log.d("Employee Password",c.getString(c.getColumnIndex("password")));
			if (pswd.equals(c.getString(c.getColumnIndex("password")))) {

				if ((c.getString(c
						.getColumnIndex(CreateEmployee.COLUMN_NAME_EMP_TYPE))
						.equals("User"))) {

					// Show user activity

					Intent intent = new Intent(LoginActivity.this,
							UserActivity.class);

					intent.putExtra("username", uname);

					startActivity(intent);

					return;
				} else {

					// else load admin activity

					Intent intent = new Intent(this,
							ShowEmployeesActivity.class);
					startActivity(intent);

				}
			} else {
				// Log.d("Invalid", "Invalid Username or password");
				// 1. Instantiate an AlertDialog.Builder with its constructor
				AlertDialog.Builder builder = new AlertDialog.Builder(this);

				// 2. Chain together various setter methods to set the dialog
				// characteristics
				builder.setMessage(R.string.invalidusernameorpassword)
						.setTitle(R.string.error);

				// Add the buttons
				builder.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								// User clicked OK button
							}
						});

				// Create the AlertDialog
				AlertDialog dialog = builder.create();
				dialog.show();
				return;

			}

			// Intent intent = new Intent(this, EmployeeLoginActivity.class);

		}
	}

	public void createAccount(View view) {
		Intent intent = new Intent(this, CreateAccountActivity.class);
		startActivity(intent);

	}

}
