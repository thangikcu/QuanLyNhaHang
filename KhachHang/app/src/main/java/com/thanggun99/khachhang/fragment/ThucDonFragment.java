package com.thanggun99.khachhang.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thanggun99.khachhang.R;
import com.thanggun99.khachhang.LoginTask;
import com.thanggun99.khachhang.util.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThucDonFragment extends Fragment implements View.OnClickListener, LoginTask.OnLoginListenner {

    private LinearLayout lnLogin, lnThucDon;
    private TextView tvLoginError;
    private CheckBox ckbGhiNho;
    private EditText edtUsername, edtPassword;
    private Button btnLogin, btnLogout;
    private LoginTask loginTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thuc_don, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        loginTask = new LoginTask(getContext());
        loginTask.setLoginListenner(this);

        lnThucDon = (LinearLayout) view.findViewById(R.id.ln_thuc_don);
        btnLogout = (Button) lnThucDon.findViewById(R.id.btn_logout);

        lnLogin = (LinearLayout) view.findViewById(R.id.ln_login);
        btnLogin = (Button) lnLogin.findViewById(R.id.btn_login);
        edtPassword = (EditText) lnLogin.findViewById(R.id.edt_password);
        edtUsername = (EditText) lnLogin.findViewById(R.id.edt_username);
        ckbGhiNho = (CheckBox) lnLogin.findViewById(R.id.ckb_ghi_nho);
        tvLoginError = (TextView) lnLogin.findViewById(R.id.tv_error_login);

        setEvents();

        if (loginTask.isGhiNhoLogin()) {
            loginTask.login();
        }
    }

    private void setEvents() {
        btnLogin.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                View focusView = null;
                boolean cancel = false;
                if (TextUtils.isEmpty(edtPassword.getText())) {
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
                } else {
                    loginTask.login(edtUsername.getText().toString(), edtPassword.getText().toString());
                }
                break;
            case R.id.btn_logout:
                lnLogin.setVisibility(View.VISIBLE);
                lnThucDon.setVisibility(View.INVISIBLE);
                edtPassword.setText("");
                edtUsername.requestFocus();
                ckbGhiNho.setChecked(false);
                loginTask.huyGhiNhoDangNhap();
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoginSuccess() {
        lnThucDon.setVisibility(View.VISIBLE);
        lnLogin.setVisibility(View.INVISIBLE);
        tvLoginError.setVisibility(View.GONE);
        if (ckbGhiNho.isChecked()) {
            loginTask.ghiNhoDangNhap(edtUsername.getText().toString(), edtPassword.getText().toString());
        }
    }

    @Override
    public void onLoginError() {
        tvLoginError.setVisibility(View.VISIBLE);
    }
}
