package edu.gettysburg.pokersquares;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SplashScreen extends Activity {
	private Button buttonNewGame;
	private TextView textViewPokerSquares;
	private String userName;
	private EditText editTextUserName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro_main);

		// Initialize Buttons, TextView, and the EditText
		buttonNewGame = (Button) findViewById(R.id.buttonNewGame);
		editTextUserName = (EditText) findViewById(R.id.editTextUserName);
		textViewPokerSquares = (TextView) findViewById(R.id.textViewPokerSquares);

		// Set black shadows around text
		buttonNewGame.setShadowLayer(10, 0, 0, Color.BLACK);
		textViewPokerSquares.setShadowLayer(7, 0, 0, Color.BLACK);

		// If user wants a new game, get their userName from the EditText and
		// switchIntents
		buttonNewGame.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				userName = editTextUserName.getText().toString();
				switchIntent();
			}
		});

		// If the user wants to continue, get their data and send it over to the
		// primary Intent
	}

	/**
	 * Switch Intents properly, and package the userName as an extra for the
	 * bundle
	 */
	public void switchIntent() {
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("userName", userName);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
