package com.example.dungeonbuddy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.example.dungeonbuddy.Stats;

import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.*;
import android.content.*;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Timer;



public class BuddyScreen extends Activity {
	private DungeonBuddy buddyHelper;
	private DungeonMonster randMonster;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buddyHelper = new DungeonBuddy(this);
        setContentView(R.layout.buddy_screen);
        
        Bundle bn = getIntent().getExtras();

        buddyHelper = bn.getParcelable("load");
        
        try {
            String destPath = "/data/data/" + getPackageName() +
                "/databases";
            File f = new File(destPath);
            if (!f.exists()) {            	
            	f.mkdirs();
                f.createNewFile();
            	
            	//---copy the db from the assets folder into 
            	// the databases folder---
                CopyDB(getBaseContext().getAssets().open("mydb"),
                    new FileOutputStream(destPath + "/MyDB"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        TextView t = (TextView)findViewById(R.id.textView1);
        t.setText(buddyHelper.getDName());
        
        updateTime();
        
        // Update clock and check for evolve conditions
        Timer timeCheck = new Timer();
        //final Handler handler = new Handler();
        /*
        TimerTask task = new TimerTask() {
        	public void run() { 
        		updateTime();
        	}
        };
        timeCheck.scheduleAtFixedRate(task, 1000, 1000);*/
        
        bn.clear();
    }
    
    public void updateTime() {
    	Calendar calendar = Calendar.getInstance();
		TextView t = (TextView)findViewById(R.id.textView2);
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    	t.setText((String) (sdf.format(calendar.getTime())));
    }
    
    public void isEvolve(){
    	if(buddyHelper.getNextAge() == Calendar.getInstance().getTime()){
    		buddyHelper.evolveBuddy();
    		
    		ImageView imageViewBuddy = (ImageView)findViewById(R.id.imageView1);
	    	imageViewBuddy.setImageResource(R.drawable.kenny2);
    	}
    }

    public void levelUp(int choice){
    	switch(choice){
    	case 0: //might
    		buddyHelper.levelMight();
    		break;
    	case 1: //magic
    		buddyHelper.levelMagic();
    		break;
    	case 2: //marksmen
    		buddyHelper.levelMarksman();
    		break;
    	}
    }
    
	public void loadStats(View v)
    {
    	Intent intent = new Intent();
    	intent.putExtra("stats", buddyHelper);
    	intent.setClass(this, Stats.class);
    	startActivity(intent);
    }
	
	public void loadStore(View v)
    {
    	Intent intent = new Intent();
    	intent.setClass(this, Store.class);
    	startActivity(intent);
    }
	
	public void openActivityDialog(View v) 
	{
		new AlertDialog.Builder(this)
	    .setTitle("Select the skill to level up!")
	    
	    .setPositiveButton("Might", new DialogInterface.OnClickListener() 
	    {
	        public void onClick(DialogInterface dialog, int whichButton) 
	        {	            
	        	levelUp(0);
	        }
	    }).setNeutralButton("Magic", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) 
	        {
	        	levelUp(1);
	        }
	    }).setNegativeButton("Marksman", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) 
	        {
	        	levelUp(2);
	        }
	    }).show();
	}
	
	@SuppressLint("NewApi")
	public class ActivityDialogFragment extends DialogFragment 
	{
		//The skill the user wants to level up
		int skillChoice;
		
		@SuppressLint("NewApi")
		public Dialog OnCreateDialog(Bundle savedInstanceState) 
		{		
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			// Set the dialog title
			builder.setTitle(R.string.attributeString)
			// Specify the list array, the items to be selected by default (null for none),
			// and the listener through which to receive callbacks when items are selected
	           .setSingleChoiceItems(R.array.attributeArray, -1, new DialogInterface.OnClickListener() 
	           {
	        	   public void onClick(DialogInterface dialog, int which) 
	        	   {
						skillChoice = which;				
	        	   }
	           	})
	           	// Set the action buttons
	           	.setPositiveButton(R.string.addString, new DialogInterface.OnClickListener() 
	           	{
	               	public void onClick(DialogInterface dialog, int id)
	               	{
	            	   	//On add, increase the proper skill
	            	   	levelUp(skillChoice);	            	   
	               	}
	           	})
	           	.setNegativeButton(R.string.cancelString, new DialogInterface.OnClickListener() 
	           	{
	               	public void onClick(DialogInterface dialog, int id) 
	               	{
	            	   	//Close the dialog box
	            	   	dialog.dismiss();
	               	}
	           	});
	    
	    	return builder.create();
		}
	}
	
	public void activityClick(View V) 
	{
		randMonster = new DungeonMonster();
		//The skill the user wants to level up
		int skillChoice;
		
		randMonster.randoRoll();
		
		new AlertDialog.Builder(BuddyScreen.this)
	    .setTitle("You are fighting a " + randMonster.getDMname())
	    .setPositiveButton("Use Might", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	        	int mChoice = randMonster.getRoll();
	        	int skillSum;
	        	
	        	if (randMonster.getDMMM(mChoice) == 2)
	        		skillSum = buddyHelper.getDMight()+5;
	        	else
	        		skillSum = buddyHelper.getDMight();
	        	
	        	if (skillSum > randMonster.getDMMM(mChoice))
	        	{
	        		new AlertDialog.Builder(BuddyScreen.this)
	        	    .setTitle("You have defeated the beast! 10 Coins Get!")
	            	.show();
	        		buddyHelper.setDWallet(buddyHelper.getDWallet()+10);
	        	}
	        	else
	        	{
	        		new AlertDialog.Builder(BuddyScreen.this)
	        	    .setTitle("You have been defeated!")
	            	.show();
	        	}
	        }
	     })
	     .setNeutralButton("Use Magic", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	        	int mChoice = randMonster.getRoll();
	        	int skillSum;
	        	
	        	if (randMonster.getDMMM(mChoice) == 1)
	        		skillSum = buddyHelper.getDMagic()+5;
	        	else
	        		skillSum = buddyHelper.getDMagic();
	        	
	        	if (skillSum > randMonster.getDMMM(mChoice))
	        	{
	        		new AlertDialog.Builder(BuddyScreen.this)
	        		.setTitle("You have defeated the beast! 10 Coins Get!")
	            	.show();
	        		buddyHelper.setDWallet(buddyHelper.getDWallet()+10);
	        	}
	        	else
	        	{
	        		new AlertDialog.Builder(BuddyScreen.this)
	        	    .setTitle("You have been defeated!")
	            	.show();
	        	}
	        }
	     })
	     .setNegativeButton("Use Marksmen", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	        	int mChoice = randMonster.getRoll();
	        	int skillSum;
	        	
	        	if (randMonster.getDMMM(mChoice) == 0)
	        		skillSum = buddyHelper.getDMarksman()+5;
	        	else
	        		skillSum = buddyHelper.getDMarksman();
	        	
	        	if (skillSum > randMonster.getDMMM(mChoice))
	        	{
	        		new AlertDialog.Builder(BuddyScreen.this)
	        		.setTitle("You have defeated the beast! 10 Coins Get!")
	            	.show();
	        		buddyHelper.setDWallet(buddyHelper.getDWallet()+10);
	        	}
	        	else
	        	{
	        		new AlertDialog.Builder(BuddyScreen.this)
	        	    .setTitle("You have been defeated!")
	            	.show();
	        	}
	        }
	        
	    }).show();
	}
	
	public void saveClick(View V)
	{
		
		new AlertDialog.Builder(BuddyScreen.this)
	    .setTitle("Choose a save")
	    .setPositiveButton("Save Slot 1", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	        	buddyHelper.saveBuddy(getBaseContext(), 1);
	        }
	     })
	     .setNeutralButton("Save Slot 2", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	        	buddyHelper.saveBuddy(getBaseContext(), 2);
	        }
	     })
	     .setNegativeButton("Save Slot 3", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	        	buddyHelper.saveBuddy(getBaseContext(), 3);
	        }
	        
	    }).show();
	}
	
    public void CopyDB(InputStream inputStream, 
    OutputStream outputStream) throws IOException {
        //---copy 1K bytes at a time---
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        inputStream.close();
        outputStream.close();
    }
}