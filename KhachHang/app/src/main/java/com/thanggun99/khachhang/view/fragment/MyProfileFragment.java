package com.thanggun99.khachhang.view.fragment;


import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.thanggun99.khachhang.R;
import com.thanggun99.khachhang.model.entity.KhachHang;
import com.thanggun99.khachhang.presenter.KhachHangPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class MyProfileFragment extends BaseFragment {
    private KhachHang khachHang;
    private TextView tvHoten, tvTenDangNhap, tvSoDienThoai, tvDiaChi;

    private KhachHangPresenter khachHangPresenter;

    public MyProfileFragment(KhachHangPresenter khachHangPresenter) {
        super(R.layout.fragment_my_profile);
        this.khachHangPresenter = khachHangPresenter;
        khachHang = khachHangPresenter.getKhachHang();
    }


    @Override
    public void findViews(View view) {
        tvHoten = (TextView) view.findViewById(R.id.tv_ho_ten);
        tvTenDangNhap = (TextView) view.findViewById(R.id.tv_ten_dang_nhap);
        tvSoDienThoai = (TextView) view.findViewById(R.id.tv_so_dien_thoai);
        tvDiaChi = (TextView) view.findViewById(R.id.tv_dia_chi);
    }

    @Override
    public void initComponents() {

    }

    @Override
    public void setEvents() {
        tvHoten.setText(khachHang.getTenKhachHang());
        tvTenDangNhap.setText(khachHang.getTenDangNhap());
        tvSoDienThoai.setText(khachHang.getSoDienThoai());
        tvDiaChi.setText(khachHang.getDiaChi());
    }

}
