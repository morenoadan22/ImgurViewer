package com.moreno.imgurviewer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by adan on 1/12/16.
 * <br />
 * <br />
 * Adapter class used to contain fragment references and allow for
 * transitions between fragments
 *
 */
public class LargeImageAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public LargeImageAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }

}
