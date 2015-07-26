package uk.co.jamieslowgrove.skyzoneomega;// the package name of the code

import java.io.FileInputStream;
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

public class Lost extends Activity { //starts the acitvity
    /** Called when the activity is first created. */
	
	int lastscore; // creates the varibales to be used in the code
	String lastscoreshow,loadedscorecurrent,loadedscore1,loadedscore2,loadedscore3,loadedscore4,loadedscore5;
	String filename;
	int Score1, Score2, Score3, Score4, Score5,Scorecurrent;
	String Score ="";
	TextView score;
	ImageView coin1, coin2, losttitle;
	Button menu, again;
	    @Override
    public void onCreate(Bundle savedInstanceState) {//void that starts when activity starts
        super.onCreate(savedInstanceState);
        lastscore = getIntent().getExtras().getInt("score");
        
		//full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , //sets it to full screen
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.lost); //sets the layout to the XML file
        initalise();
        filename = "scores_file_1"; //loads the current scores
        scoreload();
        loadedscore1 = loadedscorecurrent;
        filename = "scores_file_2";
        scoreload();
        loadedscore2 = loadedscorecurrent;
        filename = "scores_file_3";
        scoreload();
        loadedscore3 = loadedscorecurrent;
        filename = "scores_file_4";
        scoreload();
        loadedscore4 = loadedscorecurrent;
        filename = "scores_file_5";
        scoreload();
        loadedscore5 = loadedscorecurrent;
        
        checkscore(); //checks if the new score should replace any of the old scores
        
        filename = "scores_file_1"; // save the resultant scores back to the files
	    Scorecurrent = Score1;
	    savescore();
	    filename = "scores_file_2";
	    Scorecurrent = Score2;
	    savescore();
	    filename = "scores_file_3";
	    Scorecurrent = Score3;
	    savescore();
	    filename = "scores_file_4";
	    Scorecurrent = Score4;
	    savescore();
	    filename = "scores_file_5";
	    Scorecurrent = Score5;
	    savescore();
                
        lastscoreshow = Integer.toString(lastscore);
	    score.setText("Score: " + lastscoreshow); 
        
        menu.setOnClickListener(new View.OnClickListener() {
        	
        	public void onClick(View v) {
				// TODO Auto-generated method stub
        		Intent openScores = new Intent("uk.co.jamieslowgrove.skyzoneomega.STARTINGPOINT");
				startActivity(openScores);
			}
        });again.setOnClickListener(new View.OnClickListener() {
        	
        	public void onClick(View v) {
				// TODO Auto-generated method stub
        		Intent openStart = new Intent("uk.co.jamieslowgrove.skyzoneomega.START");
        		startActivity(openStart);
			}
        });
        }
	    private void checkscore() {
			// TODO Auto-generated method stub
	    	int a=0;
	    	try{
	    	Score1 = Integer.parseInt(loadedscore1);}
	    	catch (Exception e){
	    		Score1 =-1;
	    	}
	    	try{
	    	Score2 = Integer.parseInt(loadedscore2);}
	    	catch (Exception e){
	    		Score2 =-1;
	    	}
	    	try{
	    	Score3 = Integer.parseInt(loadedscore3);}
	    	catch (Exception e){
	    		Score3 =-1;
	    	}
	    	try{
	    	Score4 = Integer.parseInt(loadedscore4);}
	    	catch (Exception e){
	    		Score4 =-1;
	    	}
	    	try{
	    	Score5 = Integer.parseInt(loadedscore5);}
	    	catch (Exception e){
	    		Score5 =-1;
	    	}
	    	if (lastscore > Score1){
		    	a=1;
		    	Scorecurrent = Score1;
		    	Score1 = lastscore;		    	
		    }else if (lastscore < Score1 && lastscore > Score2){
		    	a=2;
		    	Scorecurrent = Score2;
		    	Score2 = lastscore;
			}else if (lastscore < Score2 && lastscore > Score3){
				a=3;
		    	Scorecurrent = Score3;
				Score3 = lastscore;
			}else if (lastscore < Score3 && lastscore > Score4){
				a=4;
		    	Scorecurrent = Score4;
				Score4 = lastscore;
			}else if (lastscore < Score4 && lastscore > Score5){
			    Score5 = lastscore;}
			else{}
	    	switch (a){
	    	case(1):
	    		Score5 = Score4;
		    	Score4 = Score3;
		    	Score3 = Score2;
		    	Score2 = Scorecurrent;
	    		break;
	    	case(2):
	    		Score5 = Score4;
		    	Score4 = Score3;
		    	Score3 = Scorecurrent;
	    		break;
	    	case(3):
	    		Score5 = Score4;
		    	Score4 = Scorecurrent;
	    		break;
	    	case(4):
	    		Score5 = Scorecurrent;
	    		break;
	    	}
		    }
	    private void savescore() {
			// TODO Auto-generated method stub
		FileOutputStream ScoresOut = null;
		if (Scorecurrent != -1){ 
		Score = Integer.toString(Scorecurrent);}
		else {
			Score = "-";}
		try {
			ScoresOut = openFileOutput(filename, Context.MODE_PRIVATE);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ScoresOut.write(Score.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ScoresOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	    private void initalise(){
	    	menu = (Button) findViewById(R.id.bMenu);
	        again = (Button) findViewById(R.id.bAgain);
	    	score = (TextView) findViewById(R.id.tvScore);
	    	losttitle = (ImageView) findViewById(R.id.ivLost);
	    	coin1 = (ImageView) findViewById(R.id.ivCoin1);
	    	coin2 = (ImageView) findViewById(R.id.ivCoin2);
	    }
	    private void scoreload(){
	    	
	    	StringBuffer fileContent = new StringBuffer("");
	    	FileInputStream ScoresIn = null;
	    	try{
	    	ScoresIn = openFileInput(filename);}
	    	catch (FileNotFoundException e){
	    		// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	int ch;
	    	try {
				while((ch = ScoresIn.read())!=-1){
					fileContent.append((char)ch);
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	    	
			loadedscorecurrent = fileContent.toString();	    	
	    	try {
    			ScoresIn.close();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}}
	    
		@Override
		protected void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			finish();
		}
}