package com.techage.techsuitemobile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class SplashActivity extends Activity {
	private static int SPLASH_TIME_OUT = 3000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		TextView t1 = (TextView) findViewById(R.id.tech_suite_textview);
		Typeface face = Typeface.createFromAsset(getAssets(),
				"fonts/KaushanScript-Regular.otf");
		t1.setTypeface(face);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {

				Intent mainActivity = new Intent(SplashActivity.this,
						MainActivity.class);
				startActivity(mainActivity);

				finish();
			}
		}, SPLASH_TIME_OUT);
	}

};
