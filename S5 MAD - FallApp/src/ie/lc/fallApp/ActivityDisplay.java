


package ie.lc.fallApp;

import android.os.Bundle;
import android.app.Activity;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;





public class ActivityDisplay extends Activity
{
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_display );
		setupComponents();
		setupActions();
	}
	
	
	
	
	
	public boolean onCreateOptionsMenu( Menu menu ) {
		getMenuInflater().inflate( R.menu.display, menu );
		return true;
	}
	
	
	
	
	
	private void setupComponents() {
		TextView bigText = (TextView) findViewById( R.id.bigText );
		Bundle   data    = getIntent().getExtras();
		
		if (data.containsKey( Common.displayHeightIdentifier )) {
			String text = data.getString( Common.displayHeightIdentifier );
			bigText.setText( text );
		}
	}
	
	
	
	
	
	private void setupActions()	{
		Button backButton = (Button) findViewById( R.id.backButton );
		
		backButton.setOnClickListener( new OnClickListener() {
			public void onClick( View v ) {
				finish();
			}
		});
	}
}
