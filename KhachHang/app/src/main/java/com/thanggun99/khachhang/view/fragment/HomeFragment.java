package com.thanggun99.khachhang.view.fragment;


import android.annotation.SuppressLint;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.thanggun99.khachhang.R;
import com.thanggun99.khachhang.adapter.TabsAdapter;
import com.thanggun99.khachhang.presenter.KhachHangPresenter;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class HomeFragment extends BaseFragment implements KhachHangPresenter.HomeView{
    private KhachHangPresenter khachHangPresenter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabsAdapter tabsAdapter;
    private ThucDonFragment thucDonFragment;
    private TinTucFragment tinTucFragment;

    public HomeFragment(KhachHangPresenter khachHangPresenter) {
        super(R.layout.fragment_home);
        this.khachHangPresenter = khachHangPresenter;
        khachHangPresenter.setHomeView(this);
        // Required empty public constructor
    }



    @Override
    public void setEvents() {
        viewPager.setAdapter(tabsAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void findViews(View view) {
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.view_pg);
    }

    @Override
    public void initComponents() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(thucDonFragment = new ThucDonFragment(khachHangPresenter));
        fragments.add(tinTucFragment = new TinTucFragment());
        tabsAdapter = new TabsAdapter(getActivity().getSupportFragmentManager(), fragments);
    }

    @Override
    public void showOnUnLogin() {
        viewPager.setCurrentItem(0);
    }
}
