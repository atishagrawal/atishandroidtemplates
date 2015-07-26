package com.techage.techsuite.DBUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.techage.techsuite.DBUtils.DatabaseContract.FacebookDetails;

public class DatabaseHelper extends SQLiteOpenHelper {
	// If you change the database schema, you must increment the database
	// version.
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "FacebookApp.db";

	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	private static final String SQL_CREATE_FACEBOOK_DETAILS = "CREATE TABLE "
			+ FacebookDetails.TABLE_NAME + " (" + FacebookDetails._ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ FacebookDetails.COLUMN_NAME_ACCESS_TOKEN + TEXT_TYPE + " )";

	private static final String SQL_DELETE_FACEBOOK_DETAILS = "DROP TABLE IF EXISTS "
			+ FacebookDetails.TABLE_NAME;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_FACEBOOK_DETAILS);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// This database is only a cache for online data, so its upgrade policy
		// is
		// to simply to discard the data and start over
		db.execSQL(SQL_DELETE_FACEBOOK_DETAILS);
		onCreate(db);
	}

	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}
}