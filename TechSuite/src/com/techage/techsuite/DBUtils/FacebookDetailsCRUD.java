package com.techage.techsuite.DBUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.techage.techsuite.DBUtils.DatabaseContract.FacebookDetails;

public class FacebookDetailsCRUD {

	private DatabaseHelper databaseHelper = null;

	public FacebookDetailsCRUD(Context context) {
		// Instantiating The variable

		databaseHelper = new DatabaseHelper(context);
	}

	public void insertUser(ContentValues contentValues) {
		// inserting the user

		if (databaseHelper != null) {

			// Gets the data repository in write mode
			SQLiteDatabase db = databaseHelper.getWritableDatabase();

			db.insert(FacebookDetails.TABLE_NAME, null, contentValues);
		}

	}

	public Cursor getAccessTokens() {

		if (databaseHelper != null) {

			// Gets the data repository in write mode
			SQLiteDatabase db = databaseHelper.getReadableDatabase();

			Cursor cursor = null;
			cursor = db.query(FacebookDetails.TABLE_NAME,
					new String[] { FacebookDetails.COLUMN_NAME_ACCESS_TOKEN },
					null, null, null, null, null);

			return cursor;
		}

		return null;
	}

}
