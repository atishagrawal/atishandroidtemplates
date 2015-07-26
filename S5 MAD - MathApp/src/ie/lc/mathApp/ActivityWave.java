


package ie.lc.mathApp;

import ie.lc.R;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;





public class ActivityWave extends ActivityGameBase
{
	private Wave           gameWave;
	private double         time;
	private CallbackThread animThread;
	
	
	
	
	
	protected void onSaveInstanceState( Bundle out ) {
		super.onSaveInstanceState( out );
		out.putSerializable( "gameWave", gameWave );
	}
	
	
	
	
	
	protected void onRestoreInstanceState( Bundle in ) {
		super.onRestoreInstanceState( in );
		gameWave = (Wave) in.getSerializable( "gameWave" );
	}
	
	
	
	
	
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState, R.layout.activity_wave );
	}
	
	
	
	
	
	protected void onPause() {
		super.onPause();
		
		if (animThread != null)
			animThread.join();
		
		animThread = null;
	}
	
	
	
	
	
	protected void onResume() {
		super.onResume();
		createAnimationThread();
	}
    
    
    
    
    
    private void setupWaveSpinner() {
    	Spinner spin = (Spinner) findViewById( R.id.waveSpinner );
    		
		ArrayAdapter<Wave> adapter = new ArrayAdapter<Wave>(
			this,
			android.R.layout.simple_spinner_dropdown_item,
			Wave.values()
		);
		
		spin.setAdapter( adapter );
    }
    
    
    
    
    
    private void setupWaveBars() {
		for (ProgressBar bar: getWaveBars())
			bar.setMax( 1024 );
	}
    
    
    
    
    
    protected void setupGameActions() {
    	setupWaveSpinner();
    	setupWaveBars();
    	setupAnswerButton();
	}
	
	
	
	
	
    private void setupAnswerButton() {
    	final Button buttAnswer = (Button) findViewById( R.id.waveButtAnswer );
		
		buttAnswer.setOnClickListener( new OnClickListener() {
			public void onClick( View v ) {
				onAnswer();
			}
		});
    }





	protected void setupNavActions() {
		setupNavActions(
			R.id.waveButtArithmetic, ActivityArithmetic.class,
			R.id.waveButtSqrt,       ActivitySqrt      .class
		);
	}
	
	
	
	
	
	protected void gameReset() {
		gameWave = Wave.random();
	}
	
	
	
	
	
	protected boolean isAnswerCorrect() {
		Spinner spin = (Spinner) findViewById( R.id.waveSpinner );
		return (Wave) spin.getSelectedItem() == gameWave;
	}
	
	
	
	
	
	private ProgressBar[] getWaveBars() {
    	ProgressBar[] bars = {
    		(ProgressBar) findViewById( R.id.waveWave0 ),
    		(ProgressBar) findViewById( R.id.waveWave1 ),
    		(ProgressBar) findViewById( R.id.waveWave2 ),
    		(ProgressBar) findViewById( R.id.waveWave3 ),
    		(ProgressBar) findViewById( R.id.waveWave4 ),
    		(ProgressBar) findViewById( R.id.waveWave5 ),
    		(ProgressBar) findViewById( R.id.waveWave6 ),
    	};
    	
    	return bars;
    }
	
	
	
	
	
	private void updateWaveBars() {
		ProgressBar[] waves      = getWaveBars();
		double        wavelength = 60.0;
		
		for (int i=0; i<waves.length; i++) {
			double imax   = (double) (waves.length - 1);
			double frac   = (i / imax);
			double valMax = waves[i].getMax();
			double input  = time + (frac * wavelength * 0.5);
			double val    = gameWave.evaluate( input, wavelength, 0, valMax );
			
			waves[i].setProgress( (int) val );
		}
	}
	
	
	
	
	
	private void createAnimationThread() {
		if (animThread != null)
			animThread.join();
		
		animThread = new CallbackThread( 1000/60, new Callback() {
			public void execute() {
				time++;
				updateWaveBars();
			}
		});
	}
}



















































