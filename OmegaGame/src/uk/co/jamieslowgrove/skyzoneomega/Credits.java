package uk.co.jamieslowgrove.skyzoneomega;// the package name of the code

import android.app.Activity;// setting up the codes that can be used in this code
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class Credits extends Activity { //starts the acitvity
    /** Called when the activity is first created. */

	TextView credmcl, credcreate, crednotes, credtitle; // creates the varibales to be used in the code
	ImageView coin1, coin2;
	    @Override
    public void onCreate(Bundle savedInstanceState) {//void that starts when activity starts
        super.onCreate(savedInstanceState);

		//full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , //sets it to full screen
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.credits); //sets the layout to the XML file
        credmcl = (TextView) findViewById(R.id.creditsmclogo);//sets the variables of the XML to the varibales of the Java
        credcreate = (TextView) findViewById(R.id.creditscreator);
        crednotes = (TextView) findViewById(R.id.creditsnotes);
        credtitle = (TextView) findViewById(R.id.credits);
        coin1 = (ImageView) findViewById(R.id.ivCoin1);
        coin2 = (ImageView) findViewById(R.id.ivCoin2);
        }

		@Override
		protected void onPause() { // if paused end the activty
			// TODO Auto-generated method stub
			super.onPause();
			finish();
		}
}