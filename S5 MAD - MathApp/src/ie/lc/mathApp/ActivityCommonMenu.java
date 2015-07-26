


package ie.lc.mathApp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.*;
import android.widget.Toast;





public abstract class ActivityCommonMenu extends Activity
{
	private static final int MENU_GROUP_SCORE = 0;
	private static final int MENU_GROUP_OTHER = 1;
	
	private static final int MENU_SCORE_VIEW  = 0;
	private static final int MENU_SCORE_RESET = 1;
	private static final int MENU_WEBSITE     = 2;
	private static final int MENU_RANDOM      = 3;
	
	
	
	
	
	public boolean onCreateOptionsMenu( Menu menu ) {
		menu.add( MENU_GROUP_SCORE, MENU_SCORE_VIEW,  0, "View score"      );
		menu.add( MENU_GROUP_SCORE, MENU_SCORE_RESET, 0, "Reset score"     );
		menu.add( MENU_GROUP_OTHER, MENU_WEBSITE,     0, "Wolfram Alpha"   );
		menu.add( MENU_GROUP_OTHER, MENU_RANDOM,      0, "Random activity" );
		
		return true;
	}
	
	
	
	
	
	public boolean onOptionsItemSelected( MenuItem item ) {
		switch (item.getItemId()) {
			case MENU_SCORE_VIEW :  menuShowScore();            break;
			case MENU_SCORE_RESET:  menuResetScore();           break;
			case MENU_WEBSITE    :  menuShowWebsite();          break;
			case MENU_RANDOM     :  menuShowRandomActivity();   break;
		}
		
		return true;
	}
	
	
	
	
	
	/**
	 * Shows a fancy message.
	 * @param str
	 */
	protected void showToastMessage( String str ) {
		Toast.makeText( this, str, Toast.LENGTH_SHORT ).show();
	}
	
	
	
	
	
	protected void menuResetScore() {
		showToastMessage( "Score reset." );
		Score.reset();
	}





	protected void menuShowScore() {
		Intent intent = new Intent( this, ActivityScore.class );
		startActivity( intent );
	}
	
	
	
	
	
	protected void menuShowWebsite() {
		Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse("http://wolframalpha.com") );
		startActivity( intent );
	}
	
	
	
	
	
	protected void menuShowRandomActivity() {
		switch (Util.randomIntRange( 0, 3 )) {
			case 0:  startActivity( new Intent(this, ActivityArithmetic.class) );  break;
			case 1:  startActivity( new Intent(this, ActivitySqrt      .class) );  break;
			case 2:  startActivity( new Intent(this, ActivityWave      .class) );  break;
		}
	}
}
