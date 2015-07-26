package com.techage.employeemanagementsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.techage.employeemanagementsystem.CreateEmployeeContract.CreateEmployee;

public class EmployeeCRUD {

	public Cursor showAllData(Context context) {
		// Log.d("EmployeeCRUD", "Inside Employee CRUD");
		CreateEmployeeHelper mDbHelper = new CreateEmployeeHelper(context);
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		// db.execSQL("delete from employee");
		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = { CreateEmployee.COLUMN_NAME_EMP_FNAME,
				CreateEmployee.COLUMN_NAME_EMP_LNAME,
				CreateEmployee.COLUMN_NAME_EMP_AGE,
				CreateEmployee.COLUMN_NAME_EMP_GENDER,
				CreateEmployee.COLUMN_NAME_EMP_DESIGNATION,
				CreateEmployee.COLUMN_NAME_EMP_TYPE,
				CreateEmployee.COLUMN_NAME_EMP_USERNAME,
				CreateEmployee.COLUMN_NAME_EMP_PASSWORD,

		};

		// How you want the results sorted in the resulting Cursor
		String sortOrder = CreateEmployee.COLUMN_NAME_EMP_FNAME + " ASC";
		// Log.d("EmployeeCRUD", "Fetching data into cursor");
		Cursor c = db.query(CreateEmployee.TABLE_NAME, // The table to query
				projection, // The columns to return
				null, // The columns for the WHERE clause
				null, // The values for the WHERE clause
				null, // don't group the rows
				null, // don't filter by row groups
				sortOrder // The sort order
				);

		// db.close();
		// Log.d("CursorCount", Integer.toString(c.getCount()));
		return c;
	}

	public Cursor showEmployeeDetails(Context context, String str) {
		CreateEmployeeHelper mDbHelper = new CreateEmployeeHelper(context);
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		// Log.d("details", str);
		String[] a = { str };
		Cursor c = db.rawQuery("select * from employee where username=?", a);
		c.moveToFirst();

		return c;
	}

	public int deleteEmployeeDetails(Context context, String str) {
		CreateEmployeeHelper mDbHelper = new CreateEmployeeHelper(context);
		SQLiteDatabase db = mDbHelper.getWritableDatabase();

		// Define 'where' part of query.
		String selection = CreateEmployee.COLUMN_NAME_EMP_USERNAME + " =?";
		// Specify arguments in placeholder order.
		String[] selectionArgs = { String.valueOf(str) };
		// Issue SQL statement.
		int rows = db.delete(CreateEmployee.TABLE_NAME, selection,
				selectionArgs);

		// // Define 'where' part of query.
		// String selection = CreateEmployee.COLUMN_NAME_EMP_USERNAME +
		// " = '?'";
		// // Specify arguments in placeholder order.
		//
		// // String[] selectionArgs = { str };
		// String[] selectionArgs = { String.valueOf(str) };
		//
		// // Issue SQL statement.
		// int rows = db.delete(CreateEmployee.TABLE_NAME, selection,
		// selectionArgs);
		Log.d("inside employee crud", str + "employee deleted successfully");
		return rows;
	}

	public void insertEmployee(Context context, String fname, String lname,
			int empage, String gender, String designation, String type,
			String uname, String pswd) {

		CreateEmployeeHelper mDbHelper = new CreateEmployeeHelper(context);

		// Gets the data repository in write mode
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		// db.execSQL("delete from employee");
		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();

		values.put(CreateEmployee.COLUMN_NAME_EMP_FNAME, fname);
		values.put(CreateEmployee.COLUMN_NAME_EMP_LNAME, lname);
		values.put(CreateEmployee.COLUMN_NAME_EMP_AGE, empage);
		values.put(CreateEmployee.COLUMN_NAME_EMP_GENDER, gender);
		values.put(CreateEmployee.COLUMN_NAME_EMP_DESIGNATION, designation);
		values.put(CreateEmployee.COLUMN_NAME_EMP_TYPE, type);
		values.put(CreateEmployee.COLUMN_NAME_EMP_USERNAME, uname);
		values.put(CreateEmployee.COLUMN_NAME_EMP_PASSWORD, pswd);

		// Insert the new row, returning the primary key value of the new
		// row
		// db.execSQL("drop table employee;");

		db.insert(CreateEmployee.TABLE_NAME, null, values);
	}

}
