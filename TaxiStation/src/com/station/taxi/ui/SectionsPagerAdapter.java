package com.station.taxi.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

import com.station.taxi.R;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the
 * sections of the app.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

	private static final int TOTAL_COUNT = 3;
	private String mServerIp;
    private StationActivity mActivity;
    private SparseArray<ItemsListFragment> mFragments = new SparseArray<ItemsListFragment>(TOTAL_COUNT);
    
    /**
     * 
     * @param activity
     * @param fm
     */
	public SectionsPagerAdapter(StationActivity activity, FragmentManager fm) {
        super(fm);
        mActivity = activity;
    }
	
	/**
	 * Set ip of the server
	 * @param ip
	 */
	public void setServerIp(String ip) {
		mServerIp = ip;
	}

    @Override
    public Fragment getItem(int i) {
    	if (mServerIp == null) {
    		throw new IllegalArgumentException("Current ip was not set");
    	}
    	ItemsListFragment f = ItemsListFragment.newInstance(mServerIp,i);
    	mFragments.put(i, f);
    	return f;
    }

    @Override
    public int getCount() {
        return TOTAL_COUNT;
    }

    /**
     * Reload all initiated fragments
     */
    public void reloadFragments() {
    	for(int i=0;i<mFragments.size(); i++) {
    		mFragments.valueAt(i).setServerIp(mServerIp);
    		mFragments.valueAt(i).reload();
    	}
    }
    
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return mActivity.getString(R.string.title_section1).toUpperCase();
            case 1: return mActivity.getString(R.string.title_section2).toUpperCase();
            case 2: return mActivity.getString(R.string.title_section3).toUpperCase();
        }
        return null;
    }
}
