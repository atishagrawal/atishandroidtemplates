package com.station.taxi.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.station.taxi.R;
import com.station.taxi.ui.ServerIpDialog.ServerIpDialogListener;
/**
 * Main application activity
 * @author alex
 *
 */
public class StationActivity extends FragmentActivity implements ServerIpDialogListener {
	private static final String PREF_KEY_IP = "ip";
	private static final String PREFS_NAME = "StationAppPrefs";

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	private String mServerIp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_station);
		// Create the adapter that will return a fragment for each of the three
		// primary sections
		// of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		
		// Restore preferences
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		mServerIp = settings.getString(PREF_KEY_IP, null);
		if (mServerIp == null) {
			showServerIpDialog();
		} else {
			setPagerAdapter(mServerIp);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_station, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item.getItemId() == R.id.menu_serverip) {
			showServerIpDialog();
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	/**
	 * Called when serverIp changed
	 */
	public void onSetServerIp(String serverIp) {
		
		if (serverIp == null || serverIp.equals("")) {
			return;
		}
		mServerIp = serverIp;
		// We need an Editor object to make preference changes.
		// All objects are from android.context.Context
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(PREF_KEY_IP, serverIp);

		// Commit the edits!
		editor.commit();
		
		setPagerAdapter(serverIp);

	}

	/**
	 * Update pager adapter
	 * @param serverIp
	 */
	private void setPagerAdapter(String serverIp) {
		// Set Section Pager Adapter
		mSectionsPagerAdapter.setServerIp(serverIp);
		if (mViewPager.getAdapter() == null) {
			mViewPager.setAdapter(mSectionsPagerAdapter);
		} else {
			mSectionsPagerAdapter.reloadFragments();
		}
	}

	/**
	 * Show fragment dialog to set server ip
	 */
	private void showServerIpDialog() {
		// DialogFragment.show() will take care of adding the fragment
		// in a transaction. We also want to remove any currently showing
		// dialog, so make our own transaction and take care of that here.
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
		if (prev != null) {
			ft.remove(prev);
		}
		ft.addToBackStack(null);

		// Create and show the dialog.
		DialogFragment newFragment = ServerIpDialog.newInstance(mServerIp);
		newFragment.show(ft, "dialog");
	}

}
