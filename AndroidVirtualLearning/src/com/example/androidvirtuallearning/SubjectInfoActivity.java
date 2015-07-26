package com.example.androidvirtuallearning;

import java.text.SimpleDateFormat;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SubjectInfoActivity extends Activity {

	TextView lblSubjectCode, lblSubjectName, lblClassSchedule, lblTime, lblTeacher;
	Button btnAllSubjects;
	
	private DataManager sharedData;
	
	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subject_info);
		
		sharedData = DataManager.getInstance();
		Subject currentSubject = sharedData.getCurrentSubject();
		
		lblSubjectCode = (TextView)findViewById(R.id.lblSubjectCode);
		lblSubjectName = (TextView)findViewById(R.id.lblSubjectName);
		lblClassSchedule = (TextView)findViewById(R.id.lblClassSchedule);
		lblTime = (TextView)findViewById(R.id.lblTime);
		lblTeacher = (TextView)findViewById(R.id.lblTeacher);
		btnAllSubjects = (Button)findViewById(R.id.btnAllSubjects);
		
		lblSubjectCode.setText(currentSubject.getCode());
		lblSubjectName.setText("Subject: "+currentSubject.getName());
		lblClassSchedule.setText("Class Schedule: "+currentSubject.getSchedule());
		lblTeacher.setText("Teacher: "+currentSubject.getInstructor());
		
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
		
		lblTime.setText("Time: " + sdf.format(currentSubject.getStartTime()) + " to " + sdf.format(currentSubject.getEndTime()));
		
		final Intent studentHome = new Intent(this, StudentHomeActivity.class);
		
		btnAllSubjects.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				startActivity(studentHome);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.subject_info, menu);
		return true;
	}

}
