package com.thanggun99.khachhang.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thanggun99.khachhang.LoginTask;
import com.thanggun99.khachhang.R;
import com.thanggun99.khachhang.dialog.OtherPeopleLoginDialog;
import com.thanggun99.khachhang.service.MyFirebaseMessagingService;
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
    private OtherPeopleLoginDialog otherPeopleLoginDialog;
    private boolean isLogin = false;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("thanggg", "on receiver");
            if (intent.getAction() == MyFirebaseMessagingService.LOGOUT_ACTION){
                if (isLogin){
                    otherPeopleLoginDialog.show();
                    logout();
                }
            }
        }
    };

    private void logout() {
        isLogin = false;
        lnLogin.setVisibility(View.VISIBLE);
        lnThucDon.setVisibility(View.INVISIBLE);
        edtPassword.setText("");
        edtUsername.requestFocus();
        ckbGhiNho.setChecked(false);
        loginTask.huyGhiNhoDangNhap();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thuc_don, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        otherPeopleLoginDialog = new OtherPeopleLoginDialog(getContext());
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
            loginTask.loginAuto();
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
                    logout();
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(broadcastReceiver, new IntentFilter(MyFirebaseMessagingService.LOGOUT_ACTION));
        super.onResume();
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(broadcastReceiver);
        super.onPause();
    }

    @Override
    public void onLoginSuccess() {
        isLogin = true;
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
