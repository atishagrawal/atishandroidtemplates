package day.tab;


import android.app.TabActivity;


import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
/**
 * Creates the tabs to be used for each day
 * 
 * @extends TabActivity Used for creating tabs within the class
 **/
public class Timetable extends TabActivity {
	
	private static final String EXTRA_DAY = "EXTRA_DAY";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab

        // Create an Intent to launch an Activity for the tab (to be reused)
       
        intent = new Intent().setClass(this, daytab.class);
        
        
        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("Monday").setIndicator("Mon",
                          res.getDrawable(R.drawable.ic_tab_artists))
                      .setContent(intent);
        
        // Add the day to the intent
        intent.putExtra(EXTRA_DAY, "Monday");
        tabHost.addTab(spec);

        // Do the same for the other tabs
        intent = new Intent().setClass(this, daytab.class);
        spec = tabHost.newTabSpec("Tuesday").setIndicator("Tue",
                          res.getDrawable(R.drawable.ic_tab_artists))
                      .setContent(intent);
        intent.putExtra(EXTRA_DAY, "Tuesday");
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, daytab.class);
        spec = tabHost.newTabSpec("Wednesday").setIndicator("Wed",
                          res.getDrawable(R.drawable.ic_tab_artists))
                      .setContent(intent);
        intent.putExtra(EXTRA_DAY, "Wednesday");
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, daytab.class);
        spec = tabHost.newTabSpec("Thursday").setIndicator("Thur",
                          res.getDrawable(R.drawable.ic_tab_artists))
                      .setContent(intent);
        intent.putExtra(EXTRA_DAY, "Thursday");
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, daytab.class);
        spec = tabHost.newTabSpec("Friday").setIndicator("Fri",
                          res.getDrawable(R.drawable.ic_tab_artists))
                      .setContent(intent);
        intent.putExtra(EXTRA_DAY, "Friday");
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, daytab.class);
        intent.putExtra(EXTRA_DAY, "Saturday");
        spec = tabHost.newTabSpec("Saturday").setIndicator("Sat",
                          res.getDrawable(R.drawable.ic_tab_artists))
                      .setContent(intent);
        
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, daytab.class);
        spec = tabHost.newTabSpec("Sunday").setIndicator("Sun",
                          res.getDrawable(R.drawable.ic_tab_artists))
                      .setContent(intent);
        intent.putExtra(EXTRA_DAY, "Sunday");
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);
    }
}