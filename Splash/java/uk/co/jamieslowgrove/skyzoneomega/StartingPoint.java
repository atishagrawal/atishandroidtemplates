package uk.co.jamieslowgrove.skyzoneomega; // the package name of the code

import java.io.FileInputStream;// setting up the codes that can be used in this code
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class StartingPoint extends Activity { //starts the acitvity
    /** Called when the activity is first created. */

	Button start, scores, credits; // creates the varibles to be used in the code
	TextView mcsite;
	ImageView mclogo, szologo, coin1, coin2,  coin3, coin4, coin5, coin6;
    @Override
    public void onCreate(Bundle savedInstanceState) { //void that starts when activity starts
        super.onCreate(savedInstanceState);
        
        //full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , //sets it to full screen
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        filecheck();// starts the filecheck void   
        
        setContentView(R.layout.main); //sets the layout to the XML file
        
        start = (Button) findViewById(R.id.bStart); //sets the variables of the XML to the varibales of the Java
        scores = (Button) findViewById(R.id.bScores);
        credits = (Button) findViewById(R.id.bCredits);
        szologo = (ImageView) findViewById(R.id.ivSZOlogo);
        coin1 = (ImageView) findViewById(R.id.ivCoin1);
        coin2 = (ImageView) findViewById(R.id.ivCoin2);
        coin3 = (ImageView) findViewById(R.id.ivCoin3);
        coin4 = (ImageView) findViewById(R.id.ivCoin4);
        coin5 = (ImageView) findViewById(R.id.ivCoin5);
        coin6 = (ImageView) findViewById(R.id.ivCoin6);
        start.setOnClickListener(new View.OnClickListener() {
        	
        	public void onClick(View v) { // if start pressed then start the game
				// TODO Auto-generated method stub
        		Intent openStart = new Intent("uk.co.jamieslowgrove.skyzoneomega.START");
        		startActivity(openStart);
			}
        });
        scores.setOnClickListener(new View.OnClickListener() {
        	
        	public void onClick(View v) { //if scores pressed open scores
				// TODO Auto-generated method stub
        		Intent openScores = new Intent("uk.co.jamieslowgrove.skyzoneomega.SCORES");
				startActivity(openScores);
			}
        });
        credits.setOnClickListener(new View.OnClickListener() {
        	
        	public void onClick(View v) { //if credits are pressed open the credits
				// TODO Auto-generated method stub
        		Intent openCredits = new Intent("uk.co.jamieslowgrove.skyzoneomega.CREDITS");
				startActivity(openCredits);
			}
        });
        
        
    }	private void filecheck(){ // checks if the score file exists, if not then it creates a new blank score file
    	String filename = "scores_file_1";
    	String filename2 = "scores_file_2";
    	String filename3 = "scores_file_3";
    	String filename4 = "scores_file_4";
    	String filename5 = "scores_file_5";
    	String blankscore ="-";
    	FileOutputStream ScoreOut;
    	FileInputStream ScoresIn;
    	try{
    	ScoresIn = openFileInput(filename);
    		try {
    			ScoresIn.close();
    		} catch (IOException e3) {
    			// TODO Auto-generated catch block
    			e3.printStackTrace();
		}}
    	catch (FileNotFoundException e){
    		try {
    			ScoreOut = openFileOutput(filename, Context.MODE_PRIVATE);
    			try {
    				ScoreOut.write(blankscore.getBytes());
    			} catch (IOException e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}
    			try {
    				ScoreOut.close();
    			} catch (IOException e2) {
    				// TODO Auto-generated catch block
    				e2.printStackTrace();
    			}
    		} catch (FileNotFoundException er) {
    			// TODO Auto-generated catch block
    			er.printStackTrace();				
		}}
    	try{
        	ScoresIn = openFileInput(filename2);
        		try {
        			ScoresIn.close();
        		} catch (IOException e3) {
        			// TODO Auto-generated catch block
        			e3.printStackTrace();
    		}}
        	catch (FileNotFoundException e){
        		try {
        			ScoreOut = openFileOutput(filename2, Context.MODE_PRIVATE);
        			try {
        				ScoreOut.write(blankscore.getBytes());
        			} catch (IOException e1) {
        				// TODO Auto-generated catch block
        				e1.printStackTrace();
        			}
        			try {
        				ScoreOut.close();
        			} catch (IOException e2) {
        				// TODO Auto-generated catch block
        				e2.printStackTrace();
        			}
        		} catch (FileNotFoundException er) {
        			// TODO Auto-generated catch block
        			er.printStackTrace();				
    		}}
    	try{
        	ScoresIn = openFileInput(filename3);
        		try {
        			ScoresIn.close();
        		} catch (IOException e3) {
        			// TODO Auto-generated catch block
        			e3.printStackTrace();
    		}}
        	catch (FileNotFoundException e){
        		try {
        			ScoreOut = openFileOutput(filename3, Context.MODE_PRIVATE);
        			try {
        				ScoreOut.write(blankscore.getBytes());
        			} catch (IOException e1) {
        				// TODO Auto-generated catch block
        				e1.printStackTrace();
        			}
        			try {
        				ScoreOut.close();
        			} catch (IOException e2) {
        				// TODO Auto-generated catch block
        				e2.printStackTrace();
        			}
        		} catch (FileNotFoundException er) {
        			// TODO Auto-generated catch block
        			er.printStackTrace();				
    		}}
    	try{
        	ScoresIn = openFileInput(filename4);
        		try {
        			ScoresIn.close();
        		} catch (IOException e3) {
        			// TODO Auto-generated catch block
        			e3.printStackTrace();
    		}}
        	catch (FileNotFoundException e){
        		try {
        			ScoreOut = openFileOutput(filename4, Context.MODE_PRIVATE);
        			try {
        				ScoreOut.write(blankscore.getBytes());
        			} catch (IOException e1) {
        				// TODO Auto-generated catch block
        				e1.printStackTrace();
        			}
        			try {
        				ScoreOut.close();
        			} catch (IOException e2) {
        				// TODO Auto-generated catch block
        				e2.printStackTrace();
        			}
        		} catch (FileNotFoundException er) {
        			// TODO Auto-generated catch block
        			er.printStackTrace();				
    		}}
    	try{
        	ScoresIn = openFileInput(filename5);
        		try {
        			ScoresIn.close();
        		} catch (IOException e3) {
        			// TODO Auto-generated catch block
        			e3.printStackTrace();
    		}}
        	catch (FileNotFoundException e){
        		try {
        			ScoreOut = openFileOutput(filename5, Context.MODE_PRIVATE);
        			try {
        				ScoreOut.write(blankscore.getBytes());
        			} catch (IOException e1) {
        				// TODO Auto-generated catch block
        				e1.printStackTrace();
        			}
        			try {
        				ScoreOut.close();
        			} catch (IOException e2) {
        				// TODO Auto-generated catch block
        				e2.printStackTrace();
        			}
        		} catch (FileNotFoundException er) {
        			// TODO Auto-generated catch block
        			er.printStackTrace();				
    		}}
    	}
}