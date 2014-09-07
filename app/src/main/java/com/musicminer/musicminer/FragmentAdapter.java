package com.musicminer.musicminer;

import android.support.v13.app.FragmentPagerAdapter;
import android.app.Fragment;
import android.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment Adapter used to flip between fragments.
 *
 * Created by egaebel on 7/27/14.
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    //~Class Fields--------------------------------------------------------------------------------
    /**
     * List of fragments being managed by this adapter.
     */
    private List<Fragment> fragments;

    //~Constructors--------------------------------------------------------------------------------
    public FragmentAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<Fragment>();
    }

    //~Methods-------------------------------------------------------------------------------------
    /**
     * Takes a Fragment and adds it to the list of Fragments that are managed in this adapter.
     *
     * @param fragment the fragment to add.
     */
    public void addFragment(Fragment fragment) {
        fragments.add(fragment);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
}