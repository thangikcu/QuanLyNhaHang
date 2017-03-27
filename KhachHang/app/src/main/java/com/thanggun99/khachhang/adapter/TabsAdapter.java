package com.thanggun99.khachhang.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Thanggun99 on 16/02/2017.
 */

public class TabsAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> fragments;

    public TabsAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        if (fragments.isEmpty()) return 0;
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Thực đơn";
            case 1:
                return "Tin tức";
        }
        return null;
    }

}
