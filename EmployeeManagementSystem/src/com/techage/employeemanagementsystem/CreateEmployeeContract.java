package com.techage.employeemanagementsystem;

import android.provider.BaseColumns;

public class CreateEmployeeContract {

	// To prevent someone from accidentally instantiating the contract class,
	// give it an empty constructor.
	public CreateEmployeeContract() {
	}

	/* Inner class that defines the table contents */
	public static abstract class CreateEmployee implements BaseColumns {

		public static final String TABLE_NAME = "employee";
		public static final String COLUMN_NAME_EMP_FNAME = "empfname";
		public static final String COLUMN_NAME_EMP_LNAME = "emplname";
		public static final String COLUMN_NAME_EMP_AGE = "age";
		public static final String COLUMN_NAME_EMP_GENDER = "gender";
		public static final String COLUMN_NAME_EMP_DESIGNATION = "designation";
		public static final String COLUMN_NAME_EMP_TYPE = "type";
		public static final String COLUMN_NAME_EMP_USERNAME = "username";
		public static final String COLUMN_NAME_EMP_PASSWORD = "password";

	}

}
