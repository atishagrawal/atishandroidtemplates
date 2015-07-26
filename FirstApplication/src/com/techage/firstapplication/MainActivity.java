package com.techage.firstapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//LinearLayout layoutMainActivity = (LinearLayout) findViewById(R.id.linearLayoutMainActivity);

		//layoutMainActivity.setBackground(getDrawable(R.drawable.ic_launcher));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		// if (id == R.id.action_settings) {
		// return true;
		// }
		//

//		Toast.makeText(MainActivity.this, this.getString(R.string.app_name),
//				Toast.LENGTH_SHORT).show();
//		// Creating the LayoutInflater instance
//		LayoutInflater li = getLayoutInflater();
//		// Getting the View object as defined in the customtoast.xml file
//		View layout = li.inflate(R.layout.toast_view,
//				(ViewGroup) findViewById(R.id.customToastLayout));
//
//		// Creating the Toast object
//		Toast toast = new Toast(MainActivity.this);
//		toast.setDuration(Toast.LENGTH_SHORT);
//		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//		toast.setView(layout);// setting the view of custom toast layout
//		toast.show();

		return super.onOptionsItemSelected(item);
	}
}
