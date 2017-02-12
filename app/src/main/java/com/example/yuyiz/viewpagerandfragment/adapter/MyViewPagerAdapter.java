package com.example.yuyiz.viewpagerandfragment.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by yuyiz on 2017/2/8.
 */
public class MyViewPagerAdapter extends FragmentPagerAdapter {
    private FragmentManager fragmentManager;
    private ArrayList<Fragment> fragments;

    public MyViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragmentManager = fm;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}