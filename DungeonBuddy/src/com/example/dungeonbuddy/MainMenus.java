package com.example.dungeonbuddy;

import com.example.dungeonbuddy.BuddyScreen;
import com.example.dungeonbuddy.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

public class MainMenus extends Activity {

	String[] slotsList;
    int slotChoose = 0;
    DungeonBuddy newHolder = new DungeonBuddy(this);
    
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        RelativeLayout rl = new RelativeLayout(this);
        
        rl.setBackgroundResource(R.drawable.mainbackhdpi);
        //ContentView(rl);
        
        slotsList = getResources().getStringArray(R.array.Saveslots);
        
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
        		  this, R.array.Saveslots, android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        Spinner loads = (Spinner) findViewById(R.id.spinnerLoad);	
        loads.setAdapter( adapter );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void loadBuddy(View v)
    {
    	final Intent intent = new Intent();
        intent.setClass(this, BuddyScreen.class);
        Spinner loads = (Spinner) findViewById(R.id.spinnerLoad);
        
        if(loads.getSelectedItemId() == 0)
        {
        final EditText input = new EditText(this);
        input.setText("Name");
        
        new AlertDialog.Builder(MainMenus.this)
	    .setTitle("Please Enter Dungeon Buddy Name:")
	    .setView(input)
	    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	            
	            if(input.getText().toString().equals("") == false)
	            {

	            	newHolder.setDName(input.getText().toString());

	            	intent.putExtra("load", newHolder);
	                startActivity(intent);
	            }
	            
	        }
	    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	            // Do nothing.
	        }
	    }).show();
        }
        
        else if (loads.getSelectedItemId() != 0)
        {
        	newHolder = newHolder.loadBuddy(getBaseContext(), (int) loads.getSelectedItemId());
        	
        	new AlertDialog.Builder(MainMenus.this)
    	    .setTitle("Please Enter Dungeon Buddy Name:")
        	.show();
        	
        	intent.putExtra("load", newHolder);
            startActivity(intent);
        }
    }
    
    public void close(View v)
	{
		this.finish();
	}
}
