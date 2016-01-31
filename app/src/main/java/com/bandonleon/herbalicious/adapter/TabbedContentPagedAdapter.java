package com.bandonleon.herbalicious.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bandonleon.herbalicious.fragment.PlaceholderFragment;

/**
 * Created by dombhuphaibool on 1/31/16.
 */
public class TabbedContentPagedAdapter extends FragmentStatePagerAdapter {

    public TabbedContentPagedAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            default:
                fragment = new PlaceholderFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mCount;
    }

    // @TODO... Remove this. For testing purposes only!
    private int mCount = 0;
    public void addItem() {
        mCount++;
    }
}
