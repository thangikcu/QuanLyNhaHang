package com.thanggun99.khachhang.view.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thanggun99.khachhang.R;
import com.thanggun99.khachhang.adapter.TabsAdapter;
import com.thanggun99.khachhang.presenter.KhachHangPresenter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment implements KhachHangPresenter.HomeView{
    private KhachHangPresenter khachHangPresenter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabsAdapter tabsAdapter;
    private ThucDonFragment thucDonFragment;
    private TinTucFragment tinTucFragment;

    public HomeFragment(KhachHangPresenter khachHangPresenter) {
        this.khachHangPresenter = khachHangPresenter;
        khachHangPresenter.setHomeView(this);
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        findViews(view);
        initComponets();
        setEvents();
        super.onViewCreated(view, savedInstanceState);
    }

    private void initComponets() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(thucDonFragment = new ThucDonFragment(khachHangPresenter));
        fragments.add(tinTucFragment = new TinTucFragment());
        tabsAdapter = new TabsAdapter(getActivity().getSupportFragmentManager(), fragments);
    }

    private void setEvents() {
        viewPager.setAdapter(tabsAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void findViews(View view) {
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.view_pg);
    }

    @Override
    public void showOnUnLogin() {
        viewPager.setCurrentItem(0);
    }
}
