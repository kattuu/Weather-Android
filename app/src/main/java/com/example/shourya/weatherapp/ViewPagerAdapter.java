package com.example.shourya.weatherapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by vishal on 17/02/18.
 */

class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position ==0) {
            return new HomeFragment();
        } else if (position == 1) {
            return new CurrentFragment();
        } else return new WorkFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }
}



