package com.thanggun99.khachhang.view.fragment;


import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thanggun99.khachhang.R;
import com.thanggun99.khachhang.model.entity.KhachHang;
import com.thanggun99.khachhang.presenter.KhachHangPresenter;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class MyProfileFragment extends BaseFragment {
    @BindView(R.id.tv_ho_ten)
    TextView tvHoTen;
    @BindView(R.id.tv_ten_dang_nhap)
    TextView tvTenDangNhap;
    @BindView(R.id.tv_so_dien_thoai)
    TextView tvSoDienThoai;
    @BindView(R.id.tv_dia_chi)
    TextView tvDiaChi;
    @BindView(R.id.layout_thong_tin_dat_ban)
    LinearLayout layoutThongTinDatBan;

    private KhachHang khachHang;

    private KhachHangPresenter khachHangPresenter;

    public MyProfileFragment(KhachHangPresenter khachHangPresenter) {
        super(R.layout.fragment_my_profile);
        this.khachHangPresenter = khachHangPresenter;
        khachHang = khachHangPresenter.getKhachHang();
    }

    @Override
    public void initComponents() {

    }

    @Override
    public void setEvents() {
        tvHoTen.setText(khachHang.getTenKhachHang());
        tvTenDangNhap.setText(khachHang.getTenDangNhap());
        tvSoDienThoai.setText(khachHang.getSoDienThoai());
        tvDiaChi.setText(khachHang.getDiaChi());
    }
}
