package com.techage.employeemanagementsystem;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class UserActivity extends Activity {
	Context context = this;
	public Cursor c;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		Intent intent = getIntent();

		String str = intent.getStringExtra("username");

		EmployeeCRUD employeeCRUD = new EmployeeCRUD();
		c = employeeCRUD.showEmployeeDetails(getBaseContext(), str);
		c.moveToFirst();

		TextView txtView = (TextView) findViewById(R.id.txtFname);
		TextView txtView1 = (TextView) findViewById(R.id.txtLname);
		TextView txtView2 = (TextView) findViewById(R.id.txtAge);
		TextView txtView3 = (TextView) findViewById(R.id.txtGender);
		TextView txtView4 = (TextView) findViewById(R.id.txtDesignation);
		TextView txtView5 = (TextView) findViewById(R.id.txtType);
		TextView txtView6 = (TextView) findViewById(R.id.txtUsername);
		TextView txtView7 = (TextView) findViewById(R.id.txtPassword);

		txtView.setText("First Name:  " + c.getString(0));
		txtView1.setText("Last Name:  " + c.getString(1));
		txtView2.setText("Age:  " + c.getString(2));
		txtView3.setText("Gender:  " + c.getString(3));
		txtView4.setText("Designation:  " + c.getString(4));
		txtView5.setText("Type:  " + c.getString(5));
		txtView6.setText("Username:  " + c.getString(6));
		txtView7.setText("Password:  " + c.getString(7));

	}

	public void btnDelete(View view) {

		// Log.d("ShowEmployee", "Inside btnDelete function");

		// Asking user to be sure to delete the data
		// 1. Instantiate an AlertDialog.Builder with its constructor
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		// 2. Chain together various setter methods to set the dialog
		// characteristics
		builder.setMessage(R.string.areyousure).setTitle(R.string.warning);

		// Add the buttons
		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						// User clicked OK button
						// Log.d("ShowEmployee", "User clicked ok button");
						TextView txtView = (TextView) findViewById(R.id.txtUsername);
						String uname1 = (String) txtView.getText();

						uname1 = uname1.replaceAll("Username:  ", "");
						uname1 = uname1.trim();
						// Log.d("Inside ShowEmployeesActivity", "Username:"
						// + uname1);

						// Calling deleteEmployee function in EmployeeCRUD
						EmployeeCRUD crud = new EmployeeCRUD();
						int row = crud.deleteEmployeeDetails(getBaseContext(),
								uname1);
						// Log.d("rows deleted", Integer.toString(row));

						if (row > 0) {
							// 1. Instantiate an AlertDialog.Builder with its
							// constructor
							// finish();
							AlertDialog.Builder builder1 = new AlertDialog.Builder(
									context);

							// 2. Chain together various setter methods to set
							// the dialog
							// characteristics
							builder1.setMessage(
									R.string.employeedeletedsuccessfully)
									.setTitle(R.string.success);

							// Add the buttons
							builder1.setPositiveButton(R.string.ok,
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog1, int id) {
											// User clicked OK button
											Intent intent2 = new Intent(
													context,
													ShowEmployeesActivity.class);
											intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
											startActivity(intent2);
										}
									});

							// Create the AlertDialog
							AlertDialog dialog1 = builder1.create();
							dialog1.show();
							return;
						} else {
							// 1. Instantiate an AlertDialog.Builder with its
							// constructor
							AlertDialog.Builder builder2 = new AlertDialog.Builder(
									context);

							// 2. Chain together various setter methods to set
							// the dialog
							// characteristics
							builder2.setMessage(R.string.unabletodeleteemployee)
									.setTitle(R.string.error);

							// Add the buttons
							builder2.setPositiveButton(R.string.ok,
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog2, int id) {
											// User clicked OK button
										}
									});

							// Create the AlertDialog
							AlertDialog dialog2 = builder2.create();
							dialog2.show();

						}

					}
				});

		builder.setNegativeButton(R.string.cancel,
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

	public void editEmployee(View view) {
		// Calling EditEmployeeActivity
		Intent intent = new Intent(this, EditEmployeeActivity.class);
		Log.d("btnEdit", c.getString(c.getColumnIndex("username")));
		// sending username to editEmployeeActivity
		Log.d("btnedit",
				"Setting uername into intent  "
						+ c.getString(c.getColumnIndex("username")));
		intent.putExtra("username", c.getString(c.getColumnIndex("username")));
		startActivity(intent);

	}
}
