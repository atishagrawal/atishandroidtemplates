package uk.co.jamieslowgrove.skyzoneomega; // the package name of the code

import android.app.Activity;// setting up the codes that can be used in this code
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Splash extends Activity{ //starts the acitvity
	
	MediaPlayer splashsound; // creates the varibales to be used in the code
	@Override
	protected void onCreate(Bundle savedInstanceState) {//void that starts when activity starts
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,//sets it to full screen
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    
		setContentView(R.layout.splash); //sets the layout to the XML file
		
		splashsound= MediaPlayer.create(Splash.this, R.raw.splashsound);
		splashsound.start(); //start the splash sound
		
		Thread timer = new Thread(){ // wait 3000 miliseconds before the activity starts
			public void run(){
				try{
					sleep(3000);
				} catch (InterruptedException e){
					e.printStackTrace();
				}finally{
					Intent opentStartingPoint = new Intent("uk.co.jamieslowgrove.skyzoneomega.STARTINGPOINT"); //open the main menu
					startActivity(opentStartingPoint);
				}
			}
		};
		timer.start();
	}

	@Override
	protected void onPause() { // if paused end the activty
		// TODO Auto-generated method stub
		super.onPause();
		splashsound.release(); // stop the sound
		finish();
	}
	
}
