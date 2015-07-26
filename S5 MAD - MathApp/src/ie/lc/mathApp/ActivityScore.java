


package ie.lc.mathApp;

import ie.lc.R;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;





public class ActivityScore extends ActivityCommonMenu
{
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_score );
		setupActions();
	}
	
	
	
	
	
	protected void onResume() {
		super.onResume();
		updateUI();
	}
	
	
	
	
	
	protected void menuResetScore() {
		super.menuResetScore();
		updateUI();
	}
	
	
	
	
	
	private void setupActions() {
		final Button 	   buttBack      = (Button)       findViewById( R.id.scoreButtBack      );
		final ToggleButton toggleUseless = (ToggleButton) findViewById( R.id.scoreToggleUseless );
		
		
		buttBack.setOnClickListener( new OnClickListener() {
			public void onClick( View v ) {
				finish();
			}
		});
		
		
		toggleUseless.setOnClickListener( new OnClickListener() {
			public void onClick( View v ) {
				if (toggleUseless.isChecked())
					 showToastMessage( "Huh.  Didn't do anything..." );
				else showToastMessage( "Nope.  Not a thing!"		 );
			}
		});
    }
	
	
	
	
	
	private void updateUI() {
		final TextView textRate     = (TextView) findViewById( R.id.scoreTextRate     );
		final TextView textFraction = (TextView) findViewById( R.id.scoreTextFraction );
		
		int percent = Score.getSuccessPercent();
		int good    = Score.getSuccesses();
		int total   = Score.getAttempts();
		
		textRate    .setText( "" + percent + "%" );
		textFraction.setText( "" + good + " / " + total );
    }
}



















































