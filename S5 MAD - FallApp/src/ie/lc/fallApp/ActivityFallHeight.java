package ie.lc.fallApp;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ActivityFallHeight extends Activity {
	private double persistentRandomNumber;
	private static final String persistentRandomNumberKey = "prn";

	private final int planetRequestCode = 1337;
	private final Interlock fieldInterlock = new Interlock();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fall);
		setupActions();

		persistentRandomNumber = Math.random() * 10.0;
	}

	protected void onSaveInstanceState(Bundle out) {
		out.putDouble(persistentRandomNumberKey, persistentRandomNumber);
		showToastMessage("Saved instance variable: " + persistentRandomNumber);
	}

	protected void onRestoreInstanceState(Bundle in) {
		persistentRandomNumber = in.getDouble(persistentRandomNumberKey);
		showToastMessage("Restored instance variable: "
				+ persistentRandomNumber);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == planetRequestCode && resultCode == RESULT_OK) {
			Bundle extras = data.getExtras();
			Planet selectedPlanet = (Planet) extras
					.getSerializable(Common.planetSelectIdentifier);
			Planet activePlanet = Physics.getActivePlanet();

			if (!selectedPlanet.equals(activePlanet)) {
				Physics.setActivePlanet(selectedPlanet);
				showToastMessage(getString(R.string.planetChanged) + " "
						+ selectedPlanet + ".");
				updateDisplayedHeightFromVelocity();
			}
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.fall_height, menu);
		return true;
	}

	/** Used by computeAndShow */
	private interface ComputeCallback {
		double execute(String textInput) throws NumberFormatException;
	}

	private void computeAndShow(EditText targetField, String textInput,
			ComputeCallback comp) {
		if (!fieldInterlock.isLocked()) {
			fieldInterlock.setLocked(true);
			String textOutput = "";

			try {
				double num = comp.execute(textInput);
				textOutput = formatTwoDecimalPlaces(num);
			} catch (Exception ex) {
				// Ducked
			}

			targetField.setText(textOutput);
			fieldInterlock.setLocked(false);
		}
	}

	private void setupActions() {
		final EditText velocityField = (EditText) findViewById(R.id.velocityField);
		final EditText heightField = (EditText) findViewById(R.id.heightField);
		final Button optionsButton = (Button) findViewById(R.id.optionsButton);
		final Button showBigButton = (Button) findViewById(R.id.showBigButton);

		velocityField.addTextChangedListener(new TextWatcherAdapter() {
			public void afterTextChanged(Editable e) {
				computeAndShow(heightField, e.toString(),
						new ComputeCallback() {
							public double execute(String textInput)
									throws NumberFormatException {
								double vKmh = Double.parseDouble(textInput);
								double vMps = Physics
										.kilometresPerHourToMetresPerSec(vKmh);
								return Physics
										.computeEquivalentFallHeight(vMps);
							}
						});
			}
		});

		heightField.addTextChangedListener(new TextWatcherAdapter() {
			public void afterTextChanged(Editable e) {
				computeAndShow(velocityField, e.toString(),
						new ComputeCallback() {
							public double execute(String textInput)
									throws NumberFormatException {
								double height = Double.parseDouble(textInput);
								double vMps = Physics
										.computeEquivalentVelocity(height);
								return Physics
										.metresPerSecToKilometresPerHour(vMps);
							}
						});
			}
		});

		optionsButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(ActivityFallHeight.this,
						ActivityOptions.class);
				intent.putExtra(Common.planetSelectIdentifier,
						Physics.getActivePlanet());
				startActivityForResult(intent, planetRequestCode);
			}
		});

		showBigButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(ActivityFallHeight.this,
						ActivityDisplay.class);
				intent.putExtra(Common.displayHeightIdentifier, heightField
						.getText().toString());
				startActivity(intent);
			}
		});
	}

	private void updateDisplayedHeightFromVelocity() {
		final EditText velocityField = (EditText) findViewById(R.id.velocityField);
		velocityField.setText(velocityField.getText()); // Triggers event.
	}

	private static String formatTwoDecimalPlaces(double num) {
		DecimalFormat f = new DecimalFormat("0.00");
		return f.format(num);
	}

	private void showToastMessage(String str) {
		// Toast.makeText( this, str, Toast.LENGTH_SHORT ).show();
	}
}
