package com.techage.databaseexample.DBUtils;

import android.provider.BaseColumns;

public class DatabaseContract {

	// To prevent someone from accidentally instantiating the contract class,
	// give it an empty constructor.
	public DatabaseContract() {
		// Empty Constructor
	}

	/* Inner class that defines the table contents */
	public static abstract class Agent implements BaseColumns {
		public static final String TABLE_NAME = "agent";

		public static final String COLUMN_NAME_AGENT_NAME = "agent_name";
		public static final String COLUMN_NAME_AGENT_CODE_NAME = "agent_code_name";
		public static final String COLUMN_NAME_AGENT_ADDRESS = "employee_address";
		public static final String COLUMN_NAME_AGENT_EMAIL = "employee_email";
		public static final String COLUMN_NAME_TIME_STAMP = "time_stamp";
	}

	/* Inner class that defines the table contents */
	public static abstract class Department implements BaseColumns {
		public static final String TABLE_NAME = "department";

		public static final String COLUMN_NAME_DEPARTMENT_NAME = "department_name";
	}

}
