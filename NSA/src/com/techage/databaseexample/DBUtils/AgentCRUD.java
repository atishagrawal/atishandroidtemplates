package com.techage.databaseexample.DBUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.techage.databaseexample.DBUtils.DatabaseContract.Agent;

public class AgentCRUD {

	private DatabaseHelper databaseHelper = null;

	public AgentCRUD(Context context) {

		databaseHelper = new DatabaseHelper(context);

	}

	public void insertAgent(ContentValues contentValues) {
		// Inserting the employee

		if (databaseHelper != null) {

			SQLiteDatabase database = databaseHelper.getWritableDatabase();
			database.insert(Agent.TABLE_NAME, null, contentValues);
			database.close();

		}

	}

	public Cursor getAgent() {
		// Returning the employee

		if (databaseHelper != null) {

			SQLiteDatabase database = databaseHelper.getReadableDatabase();

			Cursor cursor = null;

			cursor = database.query(Agent.TABLE_NAME, null, null, null, null,
					null, null);

			return cursor;

		}
		return null;

	}

}
