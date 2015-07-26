package com.techage.harendra;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void loginMethod(View view) {

		Toast.makeText(
				this,
				((EditText) findViewById(R.id.edUsername)).getText().toString(),
				Toast.LENGTH_LONG).show();

	}

}
