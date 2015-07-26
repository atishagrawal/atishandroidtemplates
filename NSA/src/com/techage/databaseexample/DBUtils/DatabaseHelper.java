package com.techage.databaseexample.DBUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.techage.databaseexample.DBUtils.DatabaseContract.Agent;
import com.techage.databaseexample.DBUtils.DatabaseContract.Department;

public class DatabaseHelper extends SQLiteOpenHelper {

	// If you change the database schema, you must increment the database
	// version.
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "AgentData.db";

	/**
	 * 
	 * Creating the table sql queries
	 */

	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";

	private static final String SQL_CREATE_AGENT = "CREATE TABLE "
			+ Agent.TABLE_NAME + " (" + Agent._ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"
			+ Agent.COLUMN_NAME_AGENT_NAME + TEXT_TYPE + COMMA_SEP
			+ Agent.COLUMN_NAME_AGENT_CODE_NAME + TEXT_TYPE + COMMA_SEP
			+ Agent.COLUMN_NAME_AGENT_ADDRESS + TEXT_TYPE + COMMA_SEP
			+ Agent.COLUMN_NAME_AGENT_EMAIL + TEXT_TYPE + COMMA_SEP
			+ Agent.COLUMN_NAME_TIME_STAMP
			+ " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" + " )";

	private static final String SQL_DELETE_AGENT = "DROP TABLE IF EXISTS "
			+ Agent.TABLE_NAME;

	private static final String SQL_CREATE_DEPARTMENT = "CREATE TABLE "
			+ Department.TABLE_NAME + " (" + Department._ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"
			+ Department.COLUMN_NAME_DEPARTMENT_NAME + TEXT_TYPE + " )";

	private static final String SQL_DELETE_DEPARTMENT = "DROP TABLE IF EXISTS "
			+ Department.TABLE_NAME;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Creating table

		db.execSQL(SQL_CREATE_AGENT);
		db.execSQL(SQL_CREATE_DEPARTMENT);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Deleting and re-creating the tables

		db.execSQL(SQL_DELETE_AGENT);
		db.execSQL(SQL_DELETE_DEPARTMENT);

		onCreate(db);

	}

}
