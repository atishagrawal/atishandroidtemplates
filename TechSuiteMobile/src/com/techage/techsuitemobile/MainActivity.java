package com.techage.techsuitemobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_twitter_login);

		// TextView e1 = (TextView) findViewById(R.id.password_edit_text);
		// Typeface face = Typeface.createFromAsset(getAssets(),
		// "fonts/AlexBrush-Regular.ttf");
		// e1.setTypeface(face);

	}

	public void Register(View view) {

		Intent registerActivity = new Intent(MainActivity.this,
				RegisterActivity.class);
		startActivity(registerActivity);
	}

	public void onLogin(View view) {

		Intent socialNetworkLogin = new Intent(MainActivity.this,
				LoginActivity.class);
		startActivity(socialNetworkLogin);
	}
}
