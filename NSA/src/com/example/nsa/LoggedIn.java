package com.example.nsa;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class LoggedIn extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logged_in);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.logged_in, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void buttonNSALogo(View view) {
		// TODO Auto-generated method stub

		showAlertSearchAndAbort();

	}

	private void showAlertSearchAndAbort() {
		// Showing the alertbox

		AlertDialog.Builder builderProceed = new AlertDialog.Builder(this);
		LayoutInflater inflaterProceed = getLayoutInflater();
		builderProceed
				.setView(
						inflaterProceed.inflate(R.layout.custom_alert_proceed,
								null))
				.setPositiveButton("SEARCH", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated
						// method stub

						showSearchDialog();

					}
				}).setNegativeButton("ADD", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated
						// method stub
						startActivity(new Intent(LoggedIn.this, AddAgent.class));

					}
				});
		builderProceed.create();
		builderProceed.show();

	}

	private void showSearchDialog() {
		//

		AlertDialog.Builder builderProceed = new AlertDialog.Builder(this);
		LayoutInflater inflaterProceed = getLayoutInflater();
		builderProceed.setView(
				inflaterProceed.inflate(R.layout.custom_alert_proceed, null))
				.setPositiveButton("OK", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				});
		builderProceed.create();
		builderProceed.show();

	}
}
