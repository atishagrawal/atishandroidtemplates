package com.techage.employeemanagementsystem;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.techage.employeemanagementsystem.CreateEmployeeContract.CreateEmployee;

public class CreateEmployeeHelper extends SQLiteOpenHelper {

	// If you change the database schema, you must increment the database
	// version.
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "employee.db";
	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";

	private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
			+ CreateEmployee.TABLE_NAME + " ("

			+ CreateEmployee.COLUMN_NAME_EMP_FNAME + TEXT_TYPE + COMMA_SEP
			+ CreateEmployee.COLUMN_NAME_EMP_LNAME + TEXT_TYPE + COMMA_SEP
			+ CreateEmployee.COLUMN_NAME_EMP_AGE + " INTEGER " + COMMA_SEP
			+ CreateEmployee.COLUMN_NAME_EMP_GENDER + TEXT_TYPE + COMMA_SEP
			+ CreateEmployee.COLUMN_NAME_EMP_DESIGNATION + TEXT_TYPE
			+ COMMA_SEP + CreateEmployee.COLUMN_NAME_EMP_TYPE + TEXT_TYPE
			+ COMMA_SEP + CreateEmployee.COLUMN_NAME_EMP_USERNAME + TEXT_TYPE
			+ COMMA_SEP + CreateEmployee.COLUMN_NAME_EMP_PASSWORD + TEXT_TYPE
			+ " )";

	private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
			+ CreateEmployee.TABLE_NAME;

	public CreateEmployeeHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(SQL_CREATE_ENTRIES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// This database is only a cache for online data, so its upgrade policy
		// is
		// to simply to discard the data and start over
		db.execSQL(SQL_DELETE_ENTRIES);
		onCreate(db);
	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}

}
