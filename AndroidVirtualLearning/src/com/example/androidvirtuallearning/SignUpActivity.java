package com.example.androidvirtuallearning;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends Activity {

	EditText txtStudentID, txtLastname, txtFirstname, txtMI, txtUsername, txtPassword, txtRePassword;
	Button btnSave;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		
		txtStudentID = (EditText)findViewById(R.id.txtStudentID);
		txtLastname = (EditText)findViewById(R.id.txtLastname);
		txtLastname = (EditText)findViewById(R.id.txtLastname);
		txtFirstname = (EditText)findViewById(R.id.txtFirstname);
		txtMI = (EditText)findViewById(R.id.txtMI);
		txtUsername = (EditText)findViewById(R.id.txtUsername);
		txtPassword = (EditText)findViewById(R.id.txtPassword);
		txtRePassword = (EditText)findViewById(R.id.txtRePassword);
		btnSave = (Button)findViewById(R.id.btnSave);
		
		btnSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up, menu);
		return true;
	}

}
