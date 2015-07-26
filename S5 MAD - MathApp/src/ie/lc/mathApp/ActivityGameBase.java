



package ie.lc.mathApp;

import ie.lc.R;
import java.text.DecimalFormat;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;





public abstract class ActivityGameBase extends ActivityCommonMenu
{
	protected void onCreate( Bundle savedInstanceState, int layoutID ) {
		super.onCreate( savedInstanceState );
		setContentView( layoutID );
		initialise();
	}
	
	
	
	
	
	protected void onResume() {
		super.onResume();
		Score.load();
	}
	
	
	
	
	
	/**
	 * Called directly after onCreate.
	 */
	protected void initialise() {
		setupGameActions();
		setupNavActions();
		gameReset();
	}
	
	
	
	
	
	/**
	 * Configure components used in the main game.
	 * Called first in initialise()
	 */
	protected abstract void setupGameActions();
	
	
	
	
	
	/**
	 * Configure components used to navigate between activities.
	 * Called in initialise()
	 */
	protected abstract void setupNavActions();
	
	
	
	
	
	/**
	 * Reset the game state.
	 * Called in initialise() and onAnswer().
	 */
	protected abstract void gameReset();
	
	
	
	
	
	/**
	 * Evaluates whether answer is correct.  Used by onAnswer().
	 */
	protected abstract boolean isAnswerCorrect();
	
	
	
	
	
	/**
	 * Configure the common answer field.
	 * Not called automatically.  Do it in setupGameActions().
	 */
	protected void setupAnswerActions( int fieldID, int buttID ) {
		final EditText fieldAnswer  = (EditText) findViewById( fieldID );
		final Button   buttAnswer   = (Button)   findViewById( buttID  );
		
		
		fieldAnswer.addTextChangedListener( new TextWatcherAdapter() {
			public void afterTextChanged( Editable ed ) {
				buttAnswer.setClickable( (ed.length() > 0) );
			};
		});
		
		
		buttAnswer.setClickable( false );
		buttAnswer.setOnClickListener( new OnClickListener() {
			public void onClick( View v ) {
				onAnswer();
			}
		});
	}
	
	
	
	
	
	/**
	 * Configure common nav parameters by specifiying resources and activity classes.
	 * Not called automatically.
	 */
	protected void setupNavActions( int buttIdLeft,  final Class<? extends ActivityGameBase> typeLeft,
									int buttIdRight, final Class<? extends ActivityGameBase> typeRight ) {
		Button buttLeft  = (Button) findViewById( buttIdLeft  );
		Button buttRight = (Button) findViewById( buttIdRight );
		
		
		buttLeft.setOnClickListener( new OnClickListener() {
			public void onClick( View v ) {
				Intent intent = new Intent( ActivityGameBase.this, typeLeft );
				startActivity( intent );
			}
		});
		
		
		buttRight.setOnClickListener( new OnClickListener() {
			public void onClick( View v ) {
				Intent intent = new Intent( ActivityGameBase.this, typeRight );
				startActivity( intent );
			}
		});
	}
	
	
	
	
	
	/**
	 * Handles text notification, scoring and game reset.
	 * Should be called in response to the answer button.
	 */
	protected void onAnswer() {
		if (isAnswerCorrect()) {
			showToastMessage( "Right! :D" );
			Score.onCorrectAnswer();
			generateScoreNotification();
		}
		else {
			showToastMessage( "Wrong :(" );
			Score.onWrongAnswer();
		}
		
		gameReset();
	}
	
	
	
	
	
	/**
	 * Notify user when they have a score that's a multiple of ten. 
	 */
	private void generateScoreNotification() {
		int suc = Score.getSuccesses();
		int mod = suc % 10;
		
		if (suc <  0
		||  mod != 0)
			return;
		
		String title = "MathApp Score";
		String text  = "MathApp score reached " + suc + " points!";
		
		Intent        onTrigger = new Intent( this, ActivityScore.class );
		PendingIntent pending   = PendingIntent.getActivity( this, 0, onTrigger, Intent.FLAG_ACTIVITY_NEW_TASK );
		
		int    nResID = R.drawable.ic_launcher;
		String nText  = title;
		long   nTime  = System.currentTimeMillis();
		
		Notification notification = new Notification( nResID, nText, nTime );
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.setLatestEventInfo( this, title, text, pending );
		
		NotificationManager manager = (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE );
		manager.notify( 0, notification );
	}
	
	
	
	
	
	/**
	 * Format as appropriate for the app.
	 */
	protected String formatDecimal( double num ) {
		DecimalFormat f = new DecimalFormat( "0.00" );
		return f.format( num );
	}
}

























