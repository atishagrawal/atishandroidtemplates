package uk.co.jamieslowgrove.skyzoneomega;// the package name of the code

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;// setting up the codes that can be used in this code
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class Scores extends Activity { //starts the acitvity
    /** Called when the activity is first created. */

	TextView scorestitle, firstplacescore, secondplacescore, thirdplacescore, fourthplacescore, fithplacescore;
	ImageView coin1, coin2; // creates the varibales to be used in the code
	String filename,loadedscorecurrent;
	
	    @Override
    public void onCreate(Bundle savedInstanceState) {//void that starts when activity starts
        super.onCreate(savedInstanceState);

		//full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , //sets it to full screen
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.scores);//sets the layout to the XML file
        initalise(); //goes to the initalise void
        filename = "scores_file_1";  // loads the scores to the screen using the scoreload void
        scoreload();
        firstplacescore.setText(loadedscorecurrent);
        
        filename = "scores_file_2";
        scoreload();
        secondplacescore.setText(loadedscorecurrent);
        
        filename = "scores_file_3";
        scoreload();
        thirdplacescore.setText(loadedscorecurrent);
        
        filename = "scores_file_4";
        scoreload();
        fourthplacescore.setText(loadedscorecurrent);
        
        filename = "scores_file_5";
        scoreload();
        fithplacescore.setText(loadedscorecurrent);
        
        }
	    private void initalise(){//sets the variables of the XML to the varibales of the Java
	    	scorestitle = (TextView) findViewById(R.id.scores); 
	    	firstplacescore = (TextView) findViewById(R.id.firstplacescore);
	    	secondplacescore = (TextView) findViewById(R.id.secondplacescore); 
	    	thirdplacescore = (TextView) findViewById(R.id.thirdplacescore);
	    	fourthplacescore = (TextView) findViewById(R.id.fourthplacescore);
	    	fithplacescore = (TextView) findViewById(R.id.fithplacescore);
	    	coin1 = (ImageView) findViewById(R.id.ivCoin1);
	    	coin2 = (ImageView) findViewById(R.id.ivCoin2);
	    }
	    private void scoreload(){ // loads the data from the file
	    	int bytenum;
	    	int current = 0;
	    	current = current + 0;
	    	StringBuffer fileContent = new StringBuffer("");
	    	FileInputStream ScoresIn = null;
	    	try{
	    	ScoresIn = openFileInput(filename);}
	    	catch (FileNotFoundException e){
	    		// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	bytenum = (ScoresIn.toString()).length();
	    	byte[] buffer = new byte[bytenum];
	    	try {
				while ((current = ScoresIn.read(buffer)) != -1) {
					fileContent.append(new String(buffer));
				}
				String data = new String(fileContent);
				loadedscorecurrent = data;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	try {
    			ScoresIn.close();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}}
		@Override
		protected void onPause() { // if paused end the activty
			// TODO Auto-generated method stub
			super.onPause();
			finish();
		}
}