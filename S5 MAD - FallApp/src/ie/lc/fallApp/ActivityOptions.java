


package ie.lc.fallApp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;





public class ActivityOptions extends Activity
{
	private Spinner planetSpinner;
	
	
	
	
	
	protected void onCreate( Bundle bundle ) {
		super.onCreate( bundle );
		setContentView( R.layout.activity_options );
		setupComponents();
		setupActions();
	}
	
	
	
	
	
	public boolean onCreateOptionsMenu( Menu menu )	{
		getMenuInflater().inflate( R.menu.options, menu );
		return true;
	}
	
	
	
	
	
	private void setupComponents() {
		planetSpinner = (Spinner) findViewById( R.id.planetSpinner );
		
		ArrayAdapter<Planet> planetAdapter = new ArrayAdapter<Planet>(
			this,
			android.R.layout.simple_spinner_dropdown_item,
			Planet.innerPlanets
		);
		
		planetSpinner.setAdapter( planetAdapter );
		
		
		// Select active planet by default.  More consistent and intuitive.
		Bundle intentExtras  = getIntent().getExtras();
		Planet currentPlanet = (Planet) intentExtras.getSerializable( Common.planetSelectIdentifier );
		
		for (int i=0; i<planetAdapter.getCount(); i++) {
			if (planetAdapter.getItem(i).equals(currentPlanet)) {
				planetSpinner.setSelection( i, false );
				break;
			}
		}
	}
	
	
	
	
	
	private void setupActions() {
		Button okayButton   = (Button) findViewById( R.id.okayButton   );
		Button cancelButton = (Button) findViewById( R.id.cancelButton );
		
		okayButton.setOnClickListener( new OnClickListener() {
			public void onClick( View v ) {
				Intent intent = getIntent();
				intent.putExtra( Common.planetSelectIdentifier, (Planet) planetSpinner.getSelectedItem() );
				setResult( RESULT_OK, intent );
				finish();
			}
		});
		
		
		cancelButton.setOnClickListener( new OnClickListener() {
			public void onClick( View v ) {
				setResult( RESULT_CANCELED );
				finish();
			}
		});
	}
}


























































