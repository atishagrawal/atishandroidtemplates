package com.example.androidvirtuallearning;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.TextView;

public class StudentHomeActivity extends Activity implements OnItemClickListener {

	TextView lblWelcome;
	ImageView imgUser;
	ListView listSubjects;
	Button btnAddSubject, btnEditUser, btnSignOut;
	
	
	private DataManager sharedData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student_home);
		
		sharedData = DataManager.getInstance();

		lblWelcome = (TextView)findViewById(R.id.lblWelcome);
		imgUser = (ImageView)findViewById(R.id.imgUser);
		listSubjects = (ListView)findViewById(R.id.listSubjects);
		
		btnAddSubject = (Button)findViewById(R.id.btnAddSubject);
		btnEditUser = (Button)findViewById(R.id.btnEditUser);
		btnSignOut= (Button)findViewById(R.id.btnSignOut);
		
		listSubjects.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sharedData.getStudent().getSubjectCodes()));
		listSubjects.setOnItemClickListener(this);
		
		
		
		final Intent addSubjectView = new Intent(this, AddSubjectActivity.class);
		final Intent editUserView = new Intent(this, EditUserActivity.class);
		final Intent loginView = new Intent(this, MainActivity.class);
		
		btnAddSubject.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				startActivity(addSubjectView);
			}
		});
		
		btnEditUser.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				startActivity(editUserView);
			}
		});
		
		
		btnSignOut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				startActivity(loginView);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.student_home, menu);
		return true;
	}

	
	@Override
	protected void onResume() {
		
		super.onResume();
		
		lblWelcome.setText("Welcome " + sharedData.getStudent().getFirstname());
		
		imgUser.setImageBitmap(sharedData.getStudent().getUserImage());
		imgUser.setScaleType(ScaleType.FIT_XY);
	}

	@Override
	public void onBackPressed() {
	    // do nothing.
		// This will disable the back button on the device so the user cannot return to the Login Page
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		sharedData.setCurrentSubject(sharedData.getStudent().getSubjects().get(arg2));
		
		final Intent subjectView = new Intent(this, SubjectActivity.class);
		
		startActivity(subjectView);
	}
}
