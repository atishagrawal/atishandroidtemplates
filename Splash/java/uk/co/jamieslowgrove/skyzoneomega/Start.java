package uk.co.jamieslowgrove.skyzoneomega; // the package name of the code

import java.util.Random;// setting up the codes that can be used in this code
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;

public class Start extends Activity implements OnTouchListener{ //starts the activity with ability to use the on touch listener
    /** Called when the activity is first created. */

	AnimationSurface AnimationSurfaceView; //set up the variables that need to be used
	float x, y;
	int screenwidth, screenheight, time, arrowshot, timespressedfire;
	Bitmap background, powerup, fireshootbutton, lightningcloud, sbird, cloud, coin, chariot, chariota, chariotb, chariotc, chariotd, chariote, chariotf, badguya, badguyb, badguyc, badguyd, badguye, badguyf, arrow, myarrow, firearrow1, firearrow2, pausebutton, shootbutton, scoreback, health, losthealth;
	MediaPlayer sound;
		
    @Override
public void onCreate(Bundle savedInstanceState) { // when the activity is fist started run this
    super.onCreate(savedInstanceState);
	
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , //full screen
    		WindowManager.LayoutParams.FLAG_FULLSCREEN);
    
    DisplayMetrics metrics = new DisplayMetrics(); // find out the screens with and height and saves them as variables
    getWindowManager().getDefaultDisplay().getMetrics(metrics);
    screenwidth = metrics.widthPixels; 
    screenheight = metrics.heightPixels;
    
    time = 100; // sets the time for the speed to 100 when the game starts
    timespressedfire=0; // sets the button pressed to not pressed
    Bitmap chariotsheet = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.chariotandguy));//load the bitmap image of the player
    Bitmap.Config cnfg = Bitmap.Config.ARGB_8888; // set the bitmap configuration to ARGB on
    int[] pixels; //create an undefined integer array
    pixels = new int[717*436]; // defign the array to be the size of the amount of pixels wanted from the bitmap, load from the image the section to use, scale this to the size wanted and store back in the bitmap file
    chariotsheet.getPixels (pixels, 0, 717, 0, 0, 717, 436);
	chariota = Bitmap.createBitmap(pixels, 717, 436, cnfg);
	chariota = Bitmap.createScaledBitmap(chariota, (screenwidth/5), (screenheight/5), true);
    chariot = chariota;
    AnimationSurfaceView = new AnimationSurface(this); // set up a new animation that can use the touch sreen
    AnimationSurfaceView.setOnTouchListener(this);
    setContentView(AnimationSurfaceView);
    
}
	@Override
	protected void onPause() { // if the game is paused, pause the animation
		// TODO Auto-generated method stub
		super.onPause();
		AnimationSurfaceView.pause();
	}

	@Override
	protected void onResume() {// if the game is resumed, resume the animation
		// TODO Auto-generated method stub
		super.onResume();
		AnimationSurfaceView.resume();
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) { // if the player is pressed on the screen then the position of the play will move to the new place it is dragged and if the fire button is pressed then set the pressed value to 1
		// TODO Auto-generated method stub
		if(x <= (event.getX() + (chariota.getWidth()/2)) && x >= (event.getX() - (chariota.getWidth()/2)) && y <= (event.getY() + (chariota.getHeight()/2)) && y >= (event.getY() - (chariota.getHeight()/2))){
			x = event.getX();
			y = event.getY();}
		else if (event.getY()<=screenheight && event.getY()>=screenheight-shootbutton.getHeight() && event.getX()<=screenwidth && event.getX()>=screenwidth-shootbutton.getWidth()){
			timespressedfire = 1;
		}else{}
		return true;
	} 
	//--------------------------------------------------------------------------------------------------------
	
	public class AnimationSurface extends SurfaceView implements Runnable{ // start the animation class

		SurfaceHolder Holder; //sets up the variables
		Thread animationThread = null; //fills with nothing
		boolean isRunning = false; // sets if it running to false
		
		
		public AnimationSurface(Context context) { // sets up the animation as a surface to help the game run
			// TODO Auto-generated constructor stub
			super(context);
			Holder = getHolder();		
		}
		
		public void pause(){ // when the animation is paused then it is nolonger running so the thread is destroyed to end the game
			isRunning = false;
			while(true){
				try {
					animationThread.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}animationThread = null;
			finish();
		}
		
		public void resume(){ //when the animation is resumed then the animation is set to running and is started
			isRunning = true;
			animationThread = new Thread(this);
			animationThread.start();
		}
		Thread timer = new Thread(){ //makes a timer so that the game speed can be regulated
			public void run(){
				try{
					sleep(time);
				} catch (InterruptedException e){
					e.printStackTrace();
				}finally{
				}
			}
		};
		
//------------------------------------------------------------------------------------------------------------------------
		int a,i,count; //sets all of the varibles that are gonig to be used throughout all of the voids bellow
		int score;
		float[] myarrowspeed = new float[11];
		float[] myarrowheight = new float[11];
		Bitmap.Config cnfg = Bitmap.Config.ARGB_8888;
		Bitmap current;
		Intent lost = new Intent("uk.co.jamieslowgrove.skyzoneomega.LOST"); //sets the intent to load when you lose
		Bundle scorevalues = new Bundle(); // sets up a bundle to store the save into so that it can be moved to lose activity
		Random num = new Random(); //set up random numbers
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			initalise(); //opens the initalise void
			float cloudx = screenwidth; // sets the varibales to be used in the animation void
			float cloudx2 = 0;
			float lcloudx = -10000;
			float sbirdspeed =-10000;
			float arrowspeed =-1;
			float healthspeed =-10000;
			float badguyspeed =-10000;
			int birdvalue = -1;
			int badguyvalue = -1;
			int lcloudvalue = -1;
			int position = 0;
			int position2 = 0;
			int position3 = 0;
			int position4 = 0;
			int healthvalue = 3;
			int healthpackvalue =-1;
			boolean healthonscreen = false;
			boolean badguyonscreen = false;
			boolean birdonscreen = false;
			Paint text = new Paint();
			text.setTextSize(screenheight/15);
			Rect screen = new Rect();
			screen.set(0, 0, screenwidth, screenheight);
			String scoretext;
			a=score=count=arrowshot=0;
			
			while(isRunning){ // when  is running is true
				if(!Holder.getSurface().isValid()) //if the holder is valid then carry on
					continue;
				Canvas canvas = Holder.lockCanvas(); // lock the canvas so that the images can be set to the canvas
				canvas.drawBitmap(background, null, screen, null); // draw the background
				
				
				chariot(); // open the chariot void
				
				
				if (cloudx <= 0 - (canvas.getWidth())){ // if the 1st cloud is of the screen set it back to the far side of the screen else make it move accross by 10
			    	cloudx = canvas.getWidth();
    			}else{
    				cloudx = cloudx -10;
    			}
				

				
				if (cloudx2 <= 0 - (canvas.getWidth())){ // if the 2nd cloud is of the screen set it back to the far side of the screen else make it move accross by 10
			    	cloudx2 = canvas.getWidth();
    			}else{
    				cloudx2 = cloudx2 -10;
    			}
				
				canvas.drawBitmap(cloud,cloudx,(0-(canvas.getHeight()/7)),null); //draw the clouds to the canvas at the top and the bottom of the screen
				canvas.drawBitmap(cloud,cloudx2,(0-canvas.getHeight()/7),null);
				canvas.drawBitmap(cloud,cloudx,(canvas.getHeight()-(canvas.getHeight()/9)),null);
				canvas.drawBitmap(cloud,cloudx2,(canvas.getHeight()-(canvas.getHeight()/9)),null);
				
				
				switch (healthvalue){ // depending on the current player value display a different number of health on the screen
				case 3:
					canvas.drawBitmap(health,canvas.getWidth() - (pausebutton.getWidth()+(health.getWidth()*2)),canvas.getHeight()/100,null);
					canvas.drawBitmap(health,canvas.getWidth() - (pausebutton.getWidth()+(health.getWidth()*3)),canvas.getHeight()/100,null);
					canvas.drawBitmap(health,canvas.getWidth() - (pausebutton.getWidth()+(health.getWidth()*4)),canvas.getHeight()/100,null);
					break;
				case 2:
					canvas.drawBitmap(health,canvas.getWidth() - (pausebutton.getWidth()+(health.getWidth()*2)),canvas.getHeight()/100,null);
					canvas.drawBitmap(health,canvas.getWidth() - (pausebutton.getWidth()+(health.getWidth()*3)),canvas.getHeight()/100,null);
					canvas.drawBitmap(losthealth,canvas.getWidth() - (pausebutton.getWidth()+(health.getWidth()*4)),canvas.getHeight()/100,null);
					break;
				case 1:
					canvas.drawBitmap(health,canvas.getWidth() - (pausebutton.getWidth()+(health.getWidth()*2)),canvas.getHeight()/100,null);
					canvas.drawBitmap(losthealth,canvas.getWidth() - (pausebutton.getWidth()+(health.getWidth()*3)),canvas.getHeight()/100,null);
					canvas.drawBitmap(losthealth,canvas.getWidth() - (pausebutton.getWidth()+(health.getWidth()*4)),canvas.getHeight()/100,null);
					break;
				}
				
				
				badguy(); // open the badguy void
				
				
				current = Bitmap.createScaledBitmap(current, (canvas.getWidth()/12), (canvas.getHeight()/6), true);	//set the size of the enemy to be scaled with the screen			
				if (badguyspeed <= 0 - current.getWidth()){ // if the baguy goes off the screen reset the values about it so a new one can be made
					badguyspeed = canvas.getWidth();
					badguyvalue =-1;
					badguyonscreen = true;
				}else{
					badguyspeed = badguyspeed - 15; // else move the bad guy across by 15
				}
				if (badguyvalue ==-1){ // if there is no badguy then set new random y axis as long that it is not in the clouds
					badguyvalue = 0;
				position = num.nextInt(canvas.getHeight());
				while (position<(canvas.getHeight()/9) || position >((canvas.getHeight() -(canvas.getHeight()/9)))-current.getHeight()){
					position = num.nextInt(canvas.getHeight());}
				}
				if (badguyonscreen == true){ // when the baduy is true (not hit by the players arrow) draw to the canvas
				canvas.drawBitmap(current, badguyspeed, position, null);}
				else{}

				
				if (healthvalue<3){ // when the player has lost health allow a helath pack apear on the screen
				if (healthspeed <= 0 - health.getWidth()){ // when of the screen reset the values
					healthspeed = canvas.getWidth();
					healthpackvalue =-1;
					healthonscreen = false;
				}else{
					healthspeed = healthspeed - 10; //else move across by 10
				}
				if (healthpackvalue==-1 && healthonscreen == false){  // if there is no heath pack set new random y that is not in the clouds and allow a helth pack appear
					healthpackvalue = 0;
					healthonscreen = true;
					position4 = num.nextInt(canvas.getHeight());
				while (position4<(canvas.getHeight()/9) || position4 >((canvas.getHeight() -(canvas.getHeight()/9)))-current.getHeight()){
					position4 = num.nextInt(canvas.getHeight());}
				}
				if (healthpackvalue == 0){ // if there is a health pack draw to the canvas
				canvas.drawBitmap(health, healthspeed, position4, null);}
				else{}}
				else{}
				

				if (i==3 && a==0 && badguyonscreen == true){ //if there is a badguy that is not hit and there is no enemy arrow set a arrow to be fired
					canvas.drawBitmap(arrow, badguyspeed-(current.getWidth()/2), position + ((current.getHeight()/32)*8), null);
					arrowspeed = badguyspeed-(current.getWidth()/2);
					a=1;
				}else if (a==1){ // if ther has been an arrow fired then move accross by 60, when it goes off the screen the arrow is reset and another can be fired
					arrowspeed = arrowspeed - 60;
					canvas.drawBitmap(arrow, arrowspeed , position + ((current.getHeight()/32)*8), null);
					if(arrowspeed <= 0 - arrow.getWidth()){
					a=0;}
					else{}					
				}
				else{}
				
				
				if (a==1){ // if there is an enemy arrow 
				if (arrowspeed >= x && arrowspeed <= x +(chariot.getWidth()) && (position + ((current.getHeight()/32)*8)) >= y - (chariot.getHeight()) && (position + ((current.getHeight()/32)*8)) <= y ){ // if player is hit by an arrow
					healthvalue = healthvalue - 1; //take away 1 life
					a=0;
					if (healthvalue > 0){// if health above 0
						switch (healthvalue){
						case 2:// if 2 lives left
							sound= MediaPlayer.create(Start.this, R.raw.hitsound); // play hit once sound
							break;
						case 1:// if 1 life left
							sound= MediaPlayer.create(Start.this, R.raw.hit2sound); // play hit twice sound
							break;}
						}
					else{// if 0 lives left
						healthvalue=0;
						sound= MediaPlayer.create(Start.this, R.raw.deathsound); // play death sound
					}
					sound.start();
					if(healthvalue==0){// if lost all heath do game over and move current score to lost
						scorevalues.putInt("score", score);
						lost.putExtras(scorevalues);
						startActivity(lost);
					}
				}
				else{}
				
				
				if(birdonscreen == true && sbirdspeed >= x && sbirdspeed <= x +(chariot.getWidth()) && (position2 + ((current.getHeight()/32)*8)) >= y - (chariot.getHeight()) && (position2 + ((current.getHeight()/32)*8)) <= y ){ // if player is hit by a bird
					healthvalue = healthvalue - 1;//take away 1 life
					sbirdspeed = canvas.getWidth();
					birdvalue = -1;
					if (healthvalue > 0){// if health above 0
						switch (healthvalue){
						case 2:// if 2 lives left
							sound= MediaPlayer.create(Start.this, R.raw.hitsound); // play hit once sound
							break;
						case 1:// if 1 lives left
							sound= MediaPlayer.create(Start.this, R.raw.hit2sound); // play hit twice sound
							break;}
					}
					else{// if 0 lives left
						healthvalue=0;
						sound= MediaPlayer.create(Start.this, R.raw.deathsound); // play death sound
					}
					sound.start();
					if(healthvalue==0){// if lost all heath do game over and move current score to lost
						scorevalues.putInt("score", score);
						lost.putExtras(scorevalues);
						startActivity(lost);
					}
				}
				else{}
				
				
				if(lcloudx >= x && lcloudx <= x +(chariot.getWidth()) && (position3 + ((current.getHeight()/32)*8)) >= y - (chariot.getHeight()) && (position3 + ((current.getHeight()/32)*8)) <= y ){ // if player is hit by a lightning cloud
					healthvalue = healthvalue - 1;//take away 1 life
					if (healthvalue > 0){ // if health above 0
						switch (healthvalue){ 
						case 2: // if 2 lives left
							sound= MediaPlayer.create(Start.this, R.raw.hitsound); // play hit once sound
							break;
						case 1:// if 1 life left
							sound= MediaPlayer.create(Start.this, R.raw.hit2sound); // play hit twice sound
							break;}
					}
					else{ //if 0 lives left
						healthvalue=0;
						sound= MediaPlayer.create(Start.this, R.raw.deathsound); // play death sound
					}
					sound.start();
					if(healthvalue==0){ // if lost all heath do game over and move current score to lost
						scorevalues.putInt("score", score);
						lost.putExtras(scorevalues);
						startActivity(lost);
					}
				}}
				else{}
				
				
				if (arrowshot==1){ // if the player manages to hit a bird with an arrow the bird is removed and the player gains 100 to there score
					if(birdonscreen==true && myarrowheight[1] >= position2 && myarrowheight[1] <= position2 + sbird.getHeight() && sbirdspeed <= myarrowspeed[1] && sbirdspeed >= myarrowspeed[1] -(myarrow.getWidth())){
						birdonscreen = false;
						arrowshot = 0;
						timespressedfire = 0;
						score = score + 100;}
					else{}
				}else{}
				

				if(badguyonscreen==true && myarrowheight[1] >= position && myarrowheight[1] <= position + current.getHeight() && badguyspeed <= myarrowspeed[1] && badguyspeed >= myarrowspeed[1] -(myarrow.getWidth())){// if the players arrow hits the badhuy, badguy disapears and player arrow restes allowing new arrow to fire, and giving 200 points to the players score
					badguyonscreen = false;
					arrowshot = 0;
					timespressedfire = 0;
					score = score + 200;}
				else{}
				
				
				if (healthvalue <3 && healthpackvalue == 0 && healthspeed >= x && healthspeed <= x +(chariot.getWidth()) && (position4 + ((current.getHeight()/32)*8)) >= y - (chariot.getHeight()) && (position4 + ((current.getHeight()/32)*8)) <= y ){ // if the player hits a health pack give them a life
					sound= MediaPlayer.create(Start.this, R.raw.healthup); // play healthup sound
					healthvalue = healthvalue + 1;
					healthpackvalue =-1;
					sound.start();}
				
				
				if (sbirdspeed <= 0 - (sbird.getWidth()+1)){ //if the bird is off the screen reset values
					sbirdspeed = canvas.getWidth();
					birdonscreen = true;
					birdvalue = -1;}
			    else{
				sbirdspeed = sbirdspeed - 20;} // if the bird is still on the screen -20 to x so it moves
				
				if (birdvalue==-1){ //if the number of birds is set to -1 then create a new random y position
					birdvalue = 0;
					position2 = num.nextInt(canvas.getHeight());
				while (position2<(canvas.getHeight()/9) || position2 >((canvas.getHeight() - (canvas.getHeight()/9)) - sbird.getHeight())){//check if in the clouds, if so create a new y position untill it is not in the clouds
					position2 = num.nextInt(canvas.getHeight());}}
				
				if (lcloudx <= 0 - (lightningcloud.getWidth()+1)){ // if the lightning cloud is off the screen reset values
			    	lcloudx = canvas.getWidth();
			    	lcloudvalue=-1;
    			}else{
    				lcloudx = lcloudx -10; // if the lightning cloud is still on the screen -10 from x so it moves
    			}
				
				if (lcloudvalue==-1){  //if the number of lightning  clouds is set to -1 then create a new random y position
					lcloudvalue = 0;
					position3 = num.nextInt(canvas.getHeight());
				while (position3<(canvas.getHeight()/9) ||  position3 >((canvas.getHeight() - (canvas.getHeight()/9)) - lightningcloud.getHeight())){ //check if in the clouds, if so create a new y position untill it is not in the clouds
					position3 = num.nextInt(canvas.getHeight());}}
				
				if (birdonscreen == true){ // if the bird has not been hit by the players arrow draw to canvas
				canvas.drawBitmap(sbird, sbirdspeed, position2, null);}
				else{}
				
				canvas.drawBitmap(lightningcloud, lcloudx, position3, null); // draw the lightning cloud to the canvas

				
				if(y>(canvas.getHeight()-(canvas.getHeight()/9)-(chariot.getHeight()/2))){ // check if the player is not in the out of bound clouds if so place outside of the clouds
			    	y = (((canvas.getHeight()-(canvas.getHeight()/9)))-(chariot.getHeight()/2));
			    	canvas.drawBitmap(chariot, x-(chariot.getWidth()/2), y-(chariot.getHeight()/2), null);
			    }else if ( y<(canvas.getHeight()/9)+(chariot.getHeight()/2)){
			    	y = (((canvas.getHeight()/9))+(chariot.getHeight()/2));
			    	canvas.drawBitmap(chariot, x-(chariot.getWidth()/2), y-(chariot.getHeight()/2), null);
			    }else{
			    	canvas.drawBitmap(chariot, x-(chariot.getWidth()/2), y-(chariot.getHeight()/2), null);
			    }
				
				
				if (timespressedfire ==1 ){ // if player has pressed fire button
				if (arrowshot != 1){ //if there is no arrow currently
					sound= MediaPlayer.create(Start.this, R.raw.crossbow_shot); // play shoot sound
					sound.start(); 
					count = 4; // player animation value is now = to 4
					myarrowspeed[1] = x +(chariot.getWidth()/2); // set the x and y values to the position of the chariot, only were the arrow would come from on the chariot
					myarrowheight[1]= y -(chariot.getHeight()/2)+ ((chariot.getHeight()/32)*6);
					arrowshot = 1;} // there is now an arrow out
				else{ // if there is an arrow on the canvas
					if (myarrowspeed[1] >= canvas.getWidth() + myarrow.getWidth()){ //if arrow is off screen reset values to 0
						arrowshot = 0;
						timespressedfire = 0;
					}else{
						myarrowspeed[1] = myarrowspeed[1] + 60; // if arrow still on screen add 60 to x so that it moves across screen
					}
				}}
				
				
				switch(arrowshot){ //check if an arrow has been shot by the player, if so draw arrow to canvas
				case 0:
					break;
				case 1:
					canvas.drawBitmap(myarrow, myarrowspeed[1] , myarrowheight[1], null);
					}
				

			    canvas.drawBitmap(shootbutton, canvas.getWidth()-shootbutton.getWidth(), canvas.getHeight()-(shootbutton.getHeight()), null);// Draw shootbutton to screen
				scoretext = Integer.toString(score); // converts the value of score to a string and saves it in the score text variable
		    	canvas.drawBitmap(scoreback, 0, canvas.getHeight()/100, null);// Draw scorebackground to canvas
				canvas.drawText(scoretext, (canvas.getWidth()/40), canvas.getHeight()/15, text);// Draw Score onto the scorebackground 
				Holder.unlockCanvasAndPost(canvas); // places canvas on screen
				score++; // add 1 to the score
				speed(); // goto speed then return back here
				timer.run(); // run the timer routine so that the loop will pause for the current time set
				}
		}
		
		public void badguy(){ //This decides on what badguy image to display, allowing the enemy sprite to be animated.
			switch (i){ //checks the i value for the current set
			case 0:
				current = badguyd;
				i++; // i = 1
				break;
			case 1:
				current = badguya;
				i++; // i = 2
				break;
			case 2:
				current = badguyb;
				i++; // i = 3
				break;
			case 3:
				current = badguyc;
				i++; // i = 4
				break;
			case 4:
				current = badguya;
				if(a==0){ // if an arrow has not been fired
					i=0; // i = 0
				}else{ // if an arrow has been fired
				i++;} // i = 5
				break;
			case 5:
				current = badguye;
				i++; // i = 6
				break;
			case 6:
				current = badguyf;
				i++; // i = 7
				break;
			case 7:
				current = badguye;
				if (a==1){ // if another arrow has been fired
				i=4;} // i = 4
				else{ // if no other arrow has been fired
				i=0;} // i = 0
				break;				
			}			
		} 
		
		public void initalise(){ // This is where most of the variables are loaded so that the game does not have to load it every time it loops, stopping the game from taking a long period of time to run.
			int[] pixels; // This sets up a array for the pixles to be stored for this void
			
			//These get the sprite sheets that are going to be used and save it as bitmaps in this void:
		    Bitmap badguy = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.badguy)); 
			Bitmap buttons = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.buttons));
			Bitmap weapons = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.weapons));
			Bitmap powerorbs = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.powerorbs));
			Bitmap chariotsheet = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.chariotandguy));
			
			//These get the sprites that are going to be used and sav it as bitmaps in this class:
			sbird = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.stymphalianbird));
			lightningcloud = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.lightningcloud));
			background = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.background));
		    cloud = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.cloud));
		    
		    pixels = new int[106*39];//This sets the size of the pixle array to the number of pixles in the spritesheet
			weapons.getPixels (pixels, 0, 106, 322, 0, 106, 39); // This opens the image and stores its pixles as numbers in the pixles array fro the section of the image that I want
			arrow = Bitmap.createBitmap(pixels, 106, 39, cnfg);	// This saves these pixee values back into a bitmap image for use in the game and save it in to the class	
			weapons.getPixels (pixels, 0, 106, 0, 0, 106, 39);
			myarrow = Bitmap.createBitmap(pixels, 106, 39, cnfg);
			
			pixels = new int[140*115];
			buttons.getPixels (pixels, 0, 115, 345, 0, 115, 140);
			scoreback = Bitmap.createBitmap(pixels, 115, 140, cnfg);
			buttons.getPixels (pixels, 0, 115, 0, 0, 115, 140);
			pausebutton = Bitmap.createBitmap(pixels, 115, 140, cnfg);
			buttons.getPixels (pixels, 0, 115, 115, 0, 115, 140);
			shootbutton = Bitmap.createBitmap(pixels, 115, 140, cnfg);
			
			pixels = new int[100*100];
			powerorbs.getPixels (pixels, 0, 100, 0, 0, 100, 100);
			health = Bitmap.createBitmap(pixels, 100, 100, cnfg);	
			powerorbs.getPixels (pixels, 0, 100, 100, 0, 100, 100);
			losthealth = Bitmap.createBitmap(pixels, 100, 100, cnfg);
			
			pixels = new int[218*320];
			badguy.getPixels (pixels, 0, 218, 0, 0, 218, 320);
			badguya = Bitmap.createBitmap(pixels, 218, 320, cnfg);
			badguy.getPixels (pixels, 0, 218, 218, 0, 217, 320);
			badguyb = Bitmap.createBitmap(pixels, 218, 320, cnfg);
			badguy.getPixels (pixels, 0, 218, 436, 0, 218, 320);
			badguyc = Bitmap.createBitmap(pixels, 218, 320, cnfg);
			badguy.getPixels (pixels, 0, 218, 654, 0, 218, 320);
			badguyd = Bitmap.createBitmap(pixels, 218, 320, cnfg);
			badguy.getPixels (pixels, 0, 218, 872, 0, 218, 320);
			badguye = Bitmap.createBitmap(pixels, 218, 320, cnfg);
			badguy.getPixels (pixels, 0, 218, 1090, 0, 214, 320);
			badguyf = Bitmap.createBitmap(pixels, 218, 320, cnfg);
			
			pixels = new int[717*436];
			chariotsheet.getPixels (pixels, 0, 717, 717, 0, 717, 436);
			chariotb = Bitmap.createBitmap(pixels, 717, 436, cnfg);
			chariotsheet.getPixels (pixels, 0, 717, 1434, 0, 717, 436);
			chariotc = Bitmap.createBitmap(pixels, 717, 436, cnfg);
			chariotsheet.getPixels (pixels, 0, 717, 2151, 0, 717, 436);
			chariotd = Bitmap.createBitmap(pixels, 717, 436, cnfg);
			chariotsheet.getPixels (pixels, 0, 717, 2868, 0, 717, 436);
			chariote = Bitmap.createBitmap(pixels, 717, 436, cnfg);
			chariotsheet.getPixels (pixels, 0, 717, 3585, 0, 717, 436);
			chariotf = Bitmap.createBitmap(pixels, 717, 436, cnfg);
			
			//This takes the images that I have know created and scales them down to the size that I want them to be for my game and saves them to the class:
			chariotb = Bitmap.createScaledBitmap(chariotb, (screenwidth/5), (screenheight/5), true);
			chariotc = Bitmap.createScaledBitmap(chariotc, (screenwidth/5), (screenheight/5), true);
			chariotd = Bitmap.createScaledBitmap(chariotd, (screenwidth/5), (screenheight/5), true);
			chariote = Bitmap.createScaledBitmap(chariote, (screenwidth/5), (screenheight/5), true);
			chariotf = Bitmap.createScaledBitmap(chariotf, (screenwidth/5), (screenheight/5), true);
			scoreback = Bitmap.createScaledBitmap(scoreback, (screenwidth/25)*10, (screenheight/15), true); 
			arrow = Bitmap.createScaledBitmap(arrow, (screenwidth/20), (screenheight/24), true);
			myarrow = Bitmap.createScaledBitmap(myarrow, (screenwidth/20), (screenheight/24), true);
			pausebutton = Bitmap.createScaledBitmap(pausebutton, (screenwidth/10), (screenheight/8), true);
			shootbutton = Bitmap.createScaledBitmap(shootbutton, (screenwidth/5), (screenheight/8), true);
			health = Bitmap.createScaledBitmap(health, (screenwidth/25), (screenheight/15), true);
			losthealth = Bitmap.createScaledBitmap(losthealth, (screenwidth/25), (screenheight/15), true);
			sbird = Bitmap.createScaledBitmap(sbird, (screenwidth/15), (screenheight/15), true);
			lightningcloud = Bitmap.createScaledBitmap(lightningcloud, (screenwidth/4), (screenheight/8), true);
			cloud = Bitmap.createScaledBitmap(cloud, screenwidth*2, (screenheight/4), true);
			
			// This sets teh inital x and Y positions for the player
			x = (chariot.getWidth()/2);
			y = (screenheight/2);
		}

		public void chariot(){ //This decides on what player image to display, allowing the player sprite to be animated.
			switch (count){ //checks the count value for the current set
			case 0:
				chariot = chariota;
				count++; //count = 1
				break;
			case 1:
				chariot = chariotb;
				count++; //count = 2
				break;
			case 2:
				chariot = chariotc;
				count++; //count = 3
				break;
			case 3:
				chariot = chariotb;
				count=0; //count = 0
				break;
			case 4: // this can only be reached if an arrow is fired, in the code higher the count is then set to 4.
				chariot = chariota;
				count++; //count = 5
				break;
			case 5:
				chariot = chariote;
				count++; //count = 6
				break;
			case 6:
				chariot = chariotf;
				count++; //count = 7
				break;
			case 7:
				chariot = chariotd;
				count=0; //count = 0 ergo it goes back to the begining as it has shot the arrow
				break;		
				}
			}

		public void speed(){  //This is to set the current game run speed depending on what value the score is.
			switch (score/10){ // This takes the score and divides it by 10 for that value that it will check against
			case 1: //score = 10
				time = 95;
				break;
			case 2: //score = 20
				time = 90;
				break;
			case 4: //score = 40
				time = 85;
				break;
			case 7: //score = 70
				time = 80;
				break;
			case 10: //score = 100
				time = 75;
				break;
			case 15: //score = 150		
				time = 70;
				break;
			case 20: //score = 200
				time = 65;
				break;
			case 25: //score = 250
				time = 60;
				break;
			case 30: //score = 300
				time = 55;
				break;
			case 35: //score = 350
				time = 50;
				break;
			case 40: //score = 400
				time = 45;
				break;
			case 50: //score = 500
				time = 40;
				break;
			case 60: //score = 600
				time = 35;
				break;
			case 70: //score = 700
				time = 30;
				break;
			case 80: //score = 800
				time = 25;
				break;
			case 90: //score = 900
				time = 20;
				break;
			case 100: //score = 1000
				time = 15;
				break;
			case 150: //score = 1500
				time = 10;
				break;
			case 200: //score = 2000
				time = 5;
				break;
			case 300: //score = 3000
				time = 0;
				break;	
			}
		}
	}

}