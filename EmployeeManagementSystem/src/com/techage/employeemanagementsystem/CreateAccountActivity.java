package com.techage.employeemanagementsystem;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class CreateAccountActivity extends Activity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_account);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// Show the Up button in the action bar.
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}

		Spinner spinner = (Spinner) findViewById(R.id.createDesignation);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.designation_array,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);

		Spinner spinner2 = (Spinner) findViewById(R.id.createType);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
				this, R.array.type_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner2.setAdapter(adapter2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_account, menu);
		return true;
	}

	public void createUser(View view) {

		EditText editText2 = (EditText) findViewById(R.id.createFname);
		String fname = editText2.getText().toString();

		if (fname.isEmpty() == true) {
			// 1. Instantiate an AlertDialog.Builder with its constructor
			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			// 2. Chain together various setter methods to set the dialog
			// characteristics
			builder.setMessage(R.string.blankfname).setTitle(R.string.error);

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
			editText2.requestFocus();
			editText2.setFocusable(true);
			editText2.setFocusableInTouchMode(true);
			return;

		}

		EditText editText3 = (EditText) findViewById(R.id.createLname);
		String lname = editText3.getText().toString();
		if (lname.isEmpty() == true) {
			// 1. Instantiate an AlertDialog.Builder with its constructor
			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			// 2. Chain together various setter methods to set the dialog
			// characteristics
			builder.setMessage(R.string.blanklname).setTitle(R.string.error);

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
			editText3.requestFocus();
			editText3.setFocusable(true);
			editText3.setFocusableInTouchMode(true);
			return;
		}

		EditText editText4 = (EditText) findViewById(R.id.createAge);
		int empage = 0;
		if (editText4.getText().toString().isEmpty() == true) {
			// 1. Instantiate an AlertDialog.Builder with its constructor
			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			// 2. Chain together various setter methods to set the dialog
			// characteristics
			builder.setMessage(R.string.blankage).setTitle(R.string.error);

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
			editText4.requestFocus();
			editText4.setFocusable(true);
			editText4.setFocusableInTouchMode(true);
			return;

		} else {
			empage = Integer.parseInt(editText4.getText().toString());
			if (empage <= 0 || empage >= 100) {
				// 1. Instantiate an AlertDialog.Builder with its
				// constructor
				AlertDialog.Builder builder = new AlertDialog.Builder(this);

				// 2. Chain together various setter methods to set the
				// dialog
				// characteristics
				builder.setMessage(R.string.ageerror).setTitle(R.string.error);

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
				editText4.requestFocus();
				editText4.setFocusable(true);
				editText4.setFocusableInTouchMode(true);
				return;

			}

			/*
			 * 
			 * Age validation starts here
			 */

			// else if (empage > 0 && empage < 18) {
			// // 1. Instantiate an AlertDialog.Builder with its
			// // constructor
			// AlertDialog.Builder builder = new AlertDialog.Builder(this);
			//
			// // 2. Chain together various setter methods to set the
			// // dialog
			// // characteristics
			// builder.setMessage(R.string.notauthorised).setTitle(
			// R.string.error);
			//
			// // Add the buttons
			// builder.setPositiveButton(R.string.ok,
			// new DialogInterface.OnClickListener() {
			// @Override
			// public void onClick(DialogInterface dialog, int id) {
			// // User clicked OK button
			// }
			// });
			//
			// // Create the AlertDialog
			// AlertDialog dialog = builder.create();
			// dialog.show();
			// editText4.requestFocus();
			// editText4.setFocusable(true);
			// editText4.setFocusableInTouchMode(true);
			// return;
			//
			// }

			/*
			 * 
			 * Age validation ends here
			 */

		}

		RadioGroup radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
		int radioButtonID = radiogroup.getCheckedRadioButtonId();
		// View radioButton = radiogroup.findViewById(radioButtonID);
		// int createGender = radiogroup.indexOfChild(radioButton);
		if (radioButtonID == -1) {
			// 1. Instantiate an AlertDialog.Builder with its
			// constructor
			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			// 2. Chain together various setter methods to set the
			// dialog
			// characteristics
			builder.setMessage(R.string.gendererror).setTitle(R.string.error);

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
			radiogroup.requestFocus();
			radiogroup.setFocusable(true);
			radiogroup.setFocusableInTouchMode(true);
			return;

		}

		EditText editText5 = (EditText) findViewById(R.id.createUsername);
		final String uname = editText5.getText().toString();
		if (uname.isEmpty() == true) {
			// 1. Instantiate an AlertDialog.Builder with its constructor
			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			// 2. Chain together various setter methods to set the dialog
			// characteristics
			builder.setMessage(R.string.blankusername).setTitle(R.string.error);

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
			editText5.requestFocus();
			editText5.setFocusable(true);
			editText5.setFocusableInTouchMode(true);
			return;

		}

		EditText editText6 = (EditText) findViewById(R.id.createPassword);
		String pswd = editText6.getText().toString();
		if (pswd.isEmpty() == true) {
			// 1. Instantiate an AlertDialog.Builder with its constructor
			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			// 2. Chain together various setter methods to set the dialog
			// characteristics
			builder.setMessage(R.string.blankpassword).setTitle(R.string.error);

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
			editText6.requestFocus();
			editText6.setFocusable(true);
			editText6.setFocusableInTouchMode(true);
			return;

		}
		EditText editText7 = (EditText) findViewById(R.id.createConfirmPassword);
		String confirmpswd = editText7.getText().toString();
		if (confirmpswd.isEmpty() == true) {
			// 1. Instantiate an AlertDialog.Builder with its constructor
			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			// 2. Chain together various setter methods to set the dialog
			// characteristics
			builder.setMessage(R.string.blankconfirmpassword).setTitle(
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
			editText7.requestFocus();
			editText7.setFocusable(true);
			editText7.setFocusableInTouchMode(true);
			return;

		}
		if (pswd.equals(confirmpswd) != true) {
			// 1. Instantiate an AlertDialog.Builder with its constructor
			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			// 2. Chain together various setter methods to set the dialog
			// characteristics
			builder.setMessage(R.string.passwordmismatch).setTitle(
					R.string.error);

			// Add the buttons
			builder.setPositiveButton(R.string.ok,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							// User clicked OK button
							((EditText) findViewById(R.id.createPassword))
									.setText("");
							((EditText) findViewById(R.id.createConfirmPassword))
									.setText("");
							((EditText) findViewById(R.id.createPassword))
									.requestFocus();
							((EditText) findViewById(R.id.createPassword))
									.setFocusable(true);
							((EditText) findViewById(R.id.createPassword))
									.setFocusableInTouchMode(true);
						}
					});

			// Create the AlertDialog
			AlertDialog dialog = builder.create();
			dialog.show();
			return;
		}

		// Adding value of gender radio button into a variable
		String gender = "";
		RadioButton rb = (RadioButton) findViewById(R.id.radioMale);
		if (rb.isChecked() == true) {
			gender = "Male";
		} else {
			gender = "Female";
		}

		// Adding values of spinner into a variable

		Spinner spinner = (Spinner) findViewById(R.id.createDesignation);
		String designation = spinner.getSelectedItem().toString();

		Spinner spinner2 = (Spinner) findViewById(R.id.createType);
		final String type = spinner2.getSelectedItem().toString();

		// Displaying all the records in logcat
		// Log.d("Activity", "-------Create Account Activity-----");
		// Log.d("First Name", fname);
		// Log.d("Last Name", lname);
		// Log.d("Age", Integer.toString(empage));
		// Log.d("Gender", gender);
		// Log.d("Designation", designation);
		// Log.d("Type", type);
		// Log.d("Username", uname);
		// Log.d("Password", pswd);
		// Log.d("Activity End", "-------Create Account Activity Ends-----");

		// end of login method

		EmployeeCRUD crud = new EmployeeCRUD();

		Cursor c1 = crud.showEmployeeDetails(getBaseContext(), uname);

		// Log.d("rowId", String.valueOf(rowId));
		// Create employee added dialog box and refresh the page
		if (c1.getCount() == 0) {
			crud.insertEmployee(getBaseContext(), fname, lname, empage, gender,
					designation, type, uname, pswd);
			// 1. Instantiate an AlertDialog.Builder with its constructor
			AlertDialog.Builder builder1 = new AlertDialog.Builder(this);

			// 2. Chain together various setter methods to set the dialog
			// characteristics
			builder1.setMessage(R.string.employeeadded).setTitle(
					R.string.success);

			// Add the buttons
			builder1.setPositiveButton(R.string.ok,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							// User clicked OK button
							finish();

							if (type.equals("User")) {
								Intent intent = new Intent(
										CreateAccountActivity.this,
										UserActivity.class);

								intent.putExtra("username", uname);

								startActivity(intent);

								return;

							}
							Intent intent = new Intent(getBaseContext(),
									ShowEmployeesActivity.class);

							startActivity(intent);
						}
					});

			// Create the AlertDialog
			AlertDialog dialog1 = builder1.create();
			dialog1.show();
			return;
		} else {

			// 1. Instantiate an AlertDialog.Builder with its constructor
			AlertDialog.Builder builder1 = new AlertDialog.Builder(this);

			// 2. Chain together various setter methods to set the dialog
			// characteristics
			builder1.setMessage(R.string.usernameexists).setTitle(
					R.string.success);

			// Add the buttons
			builder1.setPositiveButton(R.string.ok,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							// User clicked OK button

						}
					});

			// Create the AlertDialog
			AlertDialog dialog1 = builder1.create();
			dialog1.show();
		}
	}

	public void resetForm(View view) {

		// 1. Instantiate an AlertDialog.Builder with its constructor
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		// 2. Chain together various setter methods to set the dialog
		// characteristics
		builder.setMessage(R.string.suretoreset).setTitle(R.string.error);

		// Add the buttons
		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						// User clicked OK button
						finish();
						Intent intent = new Intent(getBaseContext(),
								CreateAccountActivity.class);

						startActivity(intent);

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

}
