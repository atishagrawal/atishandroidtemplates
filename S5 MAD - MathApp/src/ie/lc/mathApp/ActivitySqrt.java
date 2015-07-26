


package ie.lc.mathApp;

import ie.lc.R;
import android.os.Bundle;
import android.view.Menu;
import android.widget.*;





public class ActivitySqrt extends ActivityGameBase
{
	private double gameRoot;
	private double gameLow;
	private double gameHigh;
	private double gameUserInput;

	
	
	
	
	protected void onSaveInstanceState( Bundle out ) {
		super.onSaveInstanceState( out );
		out.putDouble( "gameRoot",      gameRoot      );
		out.putDouble( "gameLow",       gameLow       );
		out.putDouble( "gameHigh",      gameHigh      );
		out.putDouble( "gameUserInput", gameUserInput );
	}
	
	
	
	
	
	protected void onRestoreInstanceState( Bundle in ) {
		super.onRestoreInstanceState( in );
		gameRoot      = in.getDouble( "gameRoot"      );
		gameLow       = in.getDouble( "gameLow"       );
		gameHigh      = in.getDouble( "gameHigh"      );
		gameUserInput = in.getDouble( "gameUserInput" );
		updateUI();
	}





	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState, R.layout.activity_sqrt );
	}
    
    
    
    
    
    protected void setupGameActions() {
		setupAnswerActions(
			R.id.sqrtFieldAnswer,
			R.id.sqrtButtAnswer
		);
		
		setupSeekBar();
	}
	
	
	
	
	
	private void setupSeekBar() {
		final SeekBar seek = (SeekBar) findViewById( R.id.sqrtSeek );
		
		seek.setMax( 1024 );
		
		seek.setOnSeekBarChangeListener( new SeekBarAdapter() {
			public void onProgressChanged( SeekBar seekBar, int progress, boolean fromUser ) {
				onSeek( progress / (double) seek.getMax() );
			}
		});
	}
	
	
	
	
	
	private void onSeek( double frac ) {
		EditText fieldAnswer = (EditText) findViewById( R.id.sqrtFieldAnswer );
		double   value       = Geo.lerp( gameLow, gameHigh, frac );
		
	    gameUserInput = Geo.roundOnThresh( value, 0.2 );
		fieldAnswer.setText( formatDecimal(gameUserInput) );
	}
	
	
	
	
	
	protected void setupNavActions() {
		setupNavActions(
			R.id.sqrtButtWave,	     ActivityWave      .class,
			R.id.sqrtButtArithmetic, ActivityArithmetic.class
		);
	}
	
	
	
	
	
	protected void updateUI() {
		double square = gameRoot * gameRoot;
		
		EditText fieldAnswer  = (EditText) findViewById( R.id.sqrtFieldAnswer   );
		TextView textQuestion = (TextView) findViewById( R.id.sqrtTextQuestion  );
		TextView textLow      = (TextView) findViewById( R.id.sqrtTextSeekLeft  );
		TextView textHigh     = (TextView) findViewById( R.id.sqrtTextSeekRight );
		
		textQuestion.setText( "Root of " + (int) square + " = ?" );
		textLow     .setText( "" + (int) gameLow  );
		textHigh    .setText( "" + (int) gameHigh );
		fieldAnswer .setText( "" );
	}
	
	
	
	
	
	protected void gameReset() {
       gameRoot = Util.randomIntRange( 2, 12+1 );
       gameLow  = gameRoot - Util.randomIntRange( 0, 8 );
       gameHigh = gameRoot + Util.randomIntRange( 1, 8 );
       
       updateUI();
	}
	
	
	
	
	
	protected boolean isAnswerCorrect() {
		return gameUserInput == gameRoot;
	}
}
















































