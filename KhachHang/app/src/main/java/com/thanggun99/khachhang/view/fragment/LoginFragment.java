package com.thanggun99.khachhang.view.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thanggun99.khachhang.R;
import com.thanggun99.khachhang.model.entity.KhachHang;
import com.thanggun99.khachhang.presenter.KhachHangPresenter;
import com.thanggun99.khachhang.util.Utils;
import com.thanggun99.khachhang.view.activity.RegisterActivity;


@SuppressLint("ValidFragment")
public class LoginFragment extends BaseFragment implements View.OnClickListener, KhachHangPresenter.LoginView {
    private KhachHangPresenter khachHangPresenter;
    private LinearLayout lnLogin;
    private TextView tvLoginError, tvRegister;
    private CheckBox ckbGhiNho;
    private EditText edtUsername, edtPassword;
    private Button btnLogin;

    public LoginFragment(KhachHangPresenter khachHangPresenter) {
        super(R.layout.fragment_login);
        this.khachHangPresenter = khachHangPresenter;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void findViews(View view) {
        lnLogin = (LinearLayout) view.findViewById(R.id.ln_login);
        btnLogin = (Button) lnLogin.findViewById(R.id.btn_login);
        tvRegister = (TextView) lnLogin.findViewById(R.id.tv_register);
        edtPassword = (EditText) lnLogin.findViewById(R.id.edt_password);
        edtUsername = (EditText) lnLogin.findViewById(R.id.edt_username);
        ckbGhiNho = (CheckBox) lnLogin.findViewById(R.id.ckb_ghi_nho);
        tvLoginError = (TextView) lnLogin.findViewById(R.id.tv_error_login);
    }

    @Override
    public void initComponents() {
    }

    @Override
    public void setEvents() {
        btnLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        khachHangPresenter.setLoginView(this);

    }


    private boolean checkForm() {
        View focusView = null;
        boolean cancel = false;

        if (TextUtils.isEmpty(edtPassword.getText()) || edtPassword.getText().length() < 6) {
            edtPassword.setError(Utils.getStringByRes(R.string.nhap_mat_khau));
            focusView = edtPassword;
            cancel = true;
        }
        if (TextUtils.isEmpty(edtUsername.getText())) {
            edtUsername.setError(Utils.getStringByRes(R.string.nhap_ten_dang_nhap));
            focusView = edtUsername;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void showFormLogin() {
        edtPassword.setText("");
        edtUsername.requestFocus();
        ckbGhiNho.setChecked(false);
    }

    @Override
    public void showloginFail() {
        tvLoginError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (checkForm()) {
                    tvLoginError.setVisibility(View.GONE);
                    KhachHang khachHang = new KhachHang();
                    khachHang.setTenDangNhap(edtUsername.getText().toString().trim());
                    khachHang.setMatKhau(edtPassword.getText().toString().trim());
                    khachHang.setKieuDangNhap("notAuto");
                    khachHang.setGhiNho(ckbGhiNho.isChecked());
                    khachHangPresenter.login(khachHang);
                }
                break;
            case R.id.tv_register:
                startActivityForResult(new Intent(getContext(), RegisterActivity.class), 1);
                break;
            default:
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == 1) {
                edtUsername.setText(data.getStringExtra(RegisterActivity.USERNAME));
                edtPassword.setText("");
                edtPassword.requestFocus();
                tvLoginError.setVisibility(View.GONE);
                Utils.notifi(Utils.getStringByRes(R.string.dang_ky_thanh_cong));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
