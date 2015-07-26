package com.example.androidvirtuallearning;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditUserActivity extends Activity {

	private EditText txtStudentID, txtLastname, txtFirstname, txtMI, txtUsername, txtPassword, txtRePassword;
	private Button btnSave, btnBrowseImage;
	
	private DataManager sharedData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_user);
		
		sharedData = DataManager.getInstance();
		
		Student student = sharedData.getStudent();
		
		txtStudentID = (EditText)findViewById(R.id.txtStudentID);
		txtLastname = (EditText)findViewById(R.id.txtLastname);
		txtFirstname = (EditText)findViewById(R.id.txtFirstname);
		txtMI = (EditText)findViewById(R.id.txtMI);
		txtUsername = (EditText)findViewById(R.id.txtUsername);
		txtPassword = (EditText)findViewById(R.id.txtPassword);
		txtRePassword = (EditText)findViewById(R.id.txtRePassword);
		btnBrowseImage = (Button)findViewById(R.id.btnBrowseImage);
		btnSave = (Button)findViewById(R.id.btnSave);
		
		
		txtStudentID.setText(student.getStudentID());
		txtLastname.setText(student.getLastname());
		txtFirstname.setText(student.getFirstname());
		txtMI.setText(student.getMiddleInitial());
		txtUsername.setText(student.getUsername());
		txtPassword.setText(student.getPassword());
		txtRePassword.setText(student.getPassword());
		
		btnBrowseImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
			}
		});
		
		btnSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(txtPassword.getText().toString() == txtRePassword.getText().toString())
				{
					sharedData.getStudent().setStudentID(txtStudentID.getText().toString());
					sharedData.getStudent().setLastname(txtLastname.getText().toString());
					sharedData.getStudent().setFirstname(txtFirstname.getText().toString());
					sharedData.getStudent().setMiddleInitial(txtMI.getText().toString());
					sharedData.getStudent().setUsername(txtUsername.getText().toString());
					sharedData.getStudent().setPassword(txtPassword.getText().toString());
				}
				else
				{
					
				}
				
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_user, menu);
		return true;
	}

}
