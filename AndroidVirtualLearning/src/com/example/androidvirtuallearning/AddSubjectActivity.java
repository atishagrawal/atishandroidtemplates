package com.example.androidvirtuallearning;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddSubjectActivity extends Activity {

	TextView lblPasskey;
	EditText txtPasskey;
	Button btnAdd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_subject);
		
		lblPasskey = (TextView)findViewById(R.id.lblPasskey);
		txtPasskey = (EditText)findViewById(R.id.txtPasskey);
		btnAdd = (Button)findViewById(R.id.btnAdd);
		
		
		btnAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_subject, menu);
		return true;
	}

}
