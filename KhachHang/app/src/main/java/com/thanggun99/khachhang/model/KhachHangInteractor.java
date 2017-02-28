package com.thanggun99.khachhang.model;

import android.os.AsyncTask;

import com.thanggun99.khachhang.App;
import com.thanggun99.khachhang.model.entity.KhachHang;

/**
 * Created by Thanggun99 on 28/02/2017.
 */

public class KhachHangInteractor {
    public static KhachHang khachHang;
    private OnKhachHangFinishedListener onKhachHangFinishedListener;

    public KhachHangInteractor(OnKhachHangFinishedListener onKhachHangFinishedListener) {
        this.onKhachHangFinishedListener = onKhachHangFinishedListener;
        khachHang = new KhachHang();
    }

    public void loginAuto() {
        if (khachHang.isGhiNhoDangNhap()) {
            khachHang.setTenDangNhap(App.getPreferences().getString(KhachHang.USERNAME, null));
            khachHang.setMatKhau(App.getPreferences().getString(KhachHang.PASSWORD, null));
            khachHang.setKieuDangNhap("auto");
            new LoginAsynTask().execute();
        }
    }

    public void login(KhachHang khachHang) {
        this.khachHang = khachHang;
        new LoginAsynTask().execute();
    }

    public void logout() {
        khachHang.huyGhiNhoDangNhap();
        khachHang = null;
    }

    private class LoginAsynTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.equals("success")) {
                onKhachHangFinishedListener.onLoginSuccess();
            } else if (s.equals("other")) {
                onKhachHangFinishedListener.onOtherLogin();
            } else {
                onKhachHangFinishedListener.onLoginFail();
            }
        }

        @Override
        protected String doInBackground(Void... params) {
            return khachHang.login();
        }
    }


    public interface OnKhachHangFinishedListener {
        void onLoginSuccess();
        void onOtherLogin();
        void onLoginFail();
    }
}
