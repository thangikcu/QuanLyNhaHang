package com.thanggun99.khachhang.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thanggun99.khachhang.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatBanFragment extends Fragment {


    public DatBanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dat_ban, container, false);
    }

}
