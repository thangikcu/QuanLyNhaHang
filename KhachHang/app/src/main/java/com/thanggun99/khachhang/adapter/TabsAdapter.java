package com.thanggun99.khachhang.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.thanggun99.khachhang.fragment.ThucDonFragment;
import com.thanggun99.khachhang.fragment.TinTucFragment;

import java.util.ArrayList;

/**
 * Created by Thanggun99 on 16/02/2017.
 */

public class TabsAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> fragments;

    public TabsAdapter(FragmentManager fm) {
        super(fm);
        initsFragments();
    }

    private void initsFragments() {
        fragments = new ArrayList<>();
        fragments.add(new ThucDonFragment());
        fragments.add(new TinTucFragment());
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
                return "THỰC ĐƠN";
            case 1:
                return "TIN TỨC";
        }
        return null;
    }

}
