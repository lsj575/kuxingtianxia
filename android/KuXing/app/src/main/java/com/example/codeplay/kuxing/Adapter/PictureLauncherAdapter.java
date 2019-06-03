package com.example.codeplay.kuxing.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;

public class PictureLauncherAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> fragments;
    public PictureLauncherAdapter(FragmentManager fm,ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
        Log.i("xxx","333");
    }

    @Override
    public Fragment getItem(int position) {
        Log.i("xxx","444");
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
