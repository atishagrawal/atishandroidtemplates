package com.ec327.chatterbox.chatterbox;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;

public class Mainscreen extends ListActivity {

    /* This is the Constructor in context of Java for the Android app. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainscreen);

        //Connects the thread screen with the cloud for thread updates and sync.

        TabHost forums = (TabHost) findViewById(R.id.mainTabs);
        forums.setup();

        //Creates the format each tab button will show and displays the Episode tab.
        TabHost.TabSpec spec1 = forums.newTabSpec("tab1");
        spec1.setContent(R.id.main_episode);
        spec1.setIndicator("Episode");
        forums.addTab(spec1);

        //Creates the format each tab button will show and displays the Seasons tab.
        TabHost.TabSpec spec2 = forums.newTabSpec("tab2");
        spec2.setContent(R.id.main_season);
        spec2.setIndicator("Season");
        forums.addTab(spec2);

        //Creates the format each tab button will show and displays the Series tab.
        TabHost.TabSpec spec3 = forums.newTabSpec("tab3");
        spec3.setContent(R.id.main_series);
        spec3.setIndicator("Series");
        forums.addTab(spec3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mainscreen, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_addShows) {
            toAddShows();
            return true;
        } else if (id == R.id.action_myActivity) {
            toMyActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void toAddShows() {
        Intent intent = new Intent(this, AddShows.class);
        startActivity(intent);
    }

    private void toMyActivity() {
        Intent intent = new Intent(this,MyActivity.class);
        startActivity(intent);
    }
}
