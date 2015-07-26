package com.example.androidvirtuallearning;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class SubjectActivity extends Activity implements OnItemClickListener{

	TextView lblSubjectName;
	ListView listDownloadables;
	Button btnSubjectInfo;
	
	private String[] downloadables = {"Full-text Lectures", "Videos", "Images", "PDFs", "Quizes"};
	
	private DataManager sharedData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subject);
		
		sharedData = DataManager.getInstance();
		
		lblSubjectName = (TextView)findViewById(R.id.lblSubjectName);
		listDownloadables = (ListView)findViewById(R.id.listDownloadables);
		btnSubjectInfo = (Button)findViewById(R.id.btnSubjectInfo);
		
		lblSubjectName.setText("" + sharedData.getCurrentSubject().getCode());
		
		listDownloadables.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, downloadables));
		listDownloadables.setOnItemClickListener(this);
		
		final Intent subjectInfoView = new Intent(this, SubjectInfoActivity.class);
		
		
		btnSubjectInfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				startActivity(subjectInfoView);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.subject, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		sharedData = DataManager.getInstance();
		
		switch(arg2)
		{
			case 0:
				
				sharedData.setDlcType(DLCType.Text);
				
				break;
			case 1:
				
				sharedData.setDlcType(DLCType.Video);
					
				break;
			case 2:
				
				sharedData.setDlcType(DLCType.Image);
				
				break;
			case 3:
				
				sharedData.setDlcType(DLCType.PDF);
				
				break;
			case 4:
				
				sharedData.setDlcType(DLCType.Quiz);
				
				break;
			default:
				
				sharedData.setDlcType(DLCType.Text);
				
				break;
		}
		
		
		System.out.println("DLC Type Selected: " + sharedData.getDlcType().toString());
		
		final Intent DLCListView = new Intent(this, DLCListingsActivity.class);
		
		startActivity(DLCListView);
	}

}
