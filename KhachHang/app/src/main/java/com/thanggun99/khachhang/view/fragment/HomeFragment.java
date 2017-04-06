package com.thanggun99.khachhang.view.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressLint("ValidFragment")
public class HomeFragment extends BaseFragment implements KhachHangPresenter.HomeView {
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.view_pg)
    ViewPager viewPg;
    Unbinder unbinder;
    private KhachHangPresenter khachHangPresenter;
    private TabsAdapter tabsAdapter;
    private ThucDonFragment thucDonFragment;
    private TinTucFragment tinTucFragment;

    public HomeFragment(KhachHangPresenter khachHangPresenter) {
        super(R.layout.fragment_home);
        this.khachHangPresenter = khachHangPresenter;
        // Required empty public constructor
    }

    @Override
    public void setEvents() {
        khachHangPresenter.setHomeView(this);
        viewPg.setAdapter(tabsAdapter);
        tabs.setupWithViewPager(viewPg);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 1) {
                    khachHangPresenter.loadTinTucList();
                } else if (tab.getPosition() == 0) {
                    khachHangPresenter.loadThucDonList();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void initComponents() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(thucDonFragment = new ThucDonFragment(khachHangPresenter));
        fragments.add(tinTucFragment = new TinTucFragment(khachHangPresenter));
        tabsAdapter = new TabsAdapter(getActivity().getSupportFragmentManager(), fragments);

    }

    @Override
    public void showTabThucDon() {
        viewPg.setCurrentItem(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
