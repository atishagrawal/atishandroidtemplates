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
import android.widget.EditText;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public String strPassword = "";

	public void buttonNSALogo(View view) {
		// TODO Auto-generated method stub

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		LayoutInflater inflater = getLayoutInflater();

		final View inflatedView = inflater.inflate(
				R.layout.custom_alert_layout, null);

		builder.setView(inflatedView).setPositiveButton("PROCEED",
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

						EditText edPassword = (EditText) inflatedView
								.findViewById(R.id.edDecryptKey);

						String strPassword = edPassword.getText().toString();
						String password = "password";

						if (strPassword.equals(password)) {

							startActivity(new Intent(MainActivity.this,
									LoggedIn.class));

						} else {
							startActivity(new Intent(MainActivity.this,
									Intrusion.class));
						}

					}
				});
		builder.create();
		builder.show();
	}
}
