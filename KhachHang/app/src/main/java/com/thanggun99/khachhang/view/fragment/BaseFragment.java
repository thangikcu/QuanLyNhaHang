package com.thanggun99.khachhang.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.thanggun99.khachhang.App;
import com.thanggun99.khachhang.R;
import com.thanggun99.khachhang.interfaces.CommondActionForView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Thanggun99 on 16/03/2017.
 */
public abstract class BaseFragment extends Fragment implements CommondActionForView {
    protected View view;
    private int layoutResource;
    private Animation zoomAnimation;
    private Unbinder unbinder;

    public BaseFragment(int layoutResource) {
        this.layoutResource = layoutResource;
        zoomAnimation = AnimationUtils.loadAnimation(App.getContext(), R.anim.zoom);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            view.startAnimation(zoomAnimation);
        } else {
            view.clearAnimation();
        }
        super.onHiddenChanged(hidden);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.view = inflater.inflate(layoutResource, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        view.startAnimation(zoomAnimation);
        super.onViewCreated(view, savedInstanceState);
        initComponents();
        setEvents();
    }

    @Override
    public abstract void initComponents();

    @Override
    public abstract void setEvents();
}
