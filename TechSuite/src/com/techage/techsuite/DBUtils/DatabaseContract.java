package com.techage.techsuite.DBUtils;

import android.provider.BaseColumns;

public class DatabaseContract {

	public DatabaseContract() {
		// TODO Auto-generated constructor stub
	}

	public static abstract class FacebookDetails implements BaseColumns {
		public static final String TABLE_NAME = "facebookDetails";
		public static final String COLUMN_NAME_ACCESS_TOKEN = "access_token";
	}

}
