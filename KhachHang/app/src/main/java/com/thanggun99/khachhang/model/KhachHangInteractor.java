package com.thanggun99.khachhang.model;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.thanggun99.khachhang.App;
import com.thanggun99.khachhang.model.entity.KhachHang;
import com.thanggun99.khachhang.util.API;
import com.thanggun99.khachhang.util.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Thanggun99 on 28/02/2017.
 */

public class KhachHangInteractor {
    private KhachHang khachHang;
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

    public void changePassword(String password, String newPassword) {

        class ChangePasswordTask extends AsyncTask<String, Void, Boolean> {

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    onKhachHangFinishedListener.onChangePasswordSucess();
                } else {
                    onKhachHangFinishedListener.onChangePasswordFail();
                }
                super.onPostExecute(aBoolean);
            }

            @Override
            protected Boolean doInBackground(String... params) {
                return khachHang.changePassword(params[0]);
            }
        }

        if (!khachHang.getMatKhau().equals(password)) {
            onKhachHangFinishedListener.passwordWrong();
        } else {

            new ChangePasswordTask().execute(newPassword);
        }
    }

    public boolean isGhiNhoDangNhap() {
        if (khachHang.isGhiNhoDangNhap()) {
            return true;
        }
        return false;
    }

    public boolean checkConnect() {
        return Utils.isConnectingToInternet();
    }

    public void sentFeedback(String title, String content) {
        class FeedbackTask extends AsyncTask<String, Void, Boolean> {
            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    onKhachHangFinishedListener.onSentFeedbackSuccess();
                } else {
                    onKhachHangFinishedListener.onSentFeedbackFail();
                }

                super.onPostExecute(aBoolean);
            }

            @Override
            protected Boolean doInBackground(String... params) {
                Map<String, String> valuesPost = new HashMap<>();
                valuesPost.put("title", params[0]);
                valuesPost.put("content", params[1]);
                String s = API.callService(API.FEEDBACK_URL, null, valuesPost);

                if (!TextUtils.isEmpty(s) && s.contains("success")) {
                    return true;
                }
                return false;
            }
        }
        new FeedbackTask().execute(title, content);
    }

    private class LoginAsynTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPostExecute(String s) {
            if (s.equals("success")) {
                onKhachHangFinishedListener.onLoginSuccess(khachHang);
            } else if (s.equals("other")) {
                onKhachHangFinishedListener.onOtherLogin();
                khachHang = null;
            } else {
                onKhachHangFinishedListener.onLoginFail();
                khachHang = null;
            }
        }

        @Override
        protected String doInBackground(Void... params) {
            return khachHang.login();
        }
    }


    public interface OnKhachHangFinishedListener {

        void onLoginSuccess(KhachHang khachHang);

        void onOtherLogin();

        void onLoginFail();

        void passwordWrong();

        void onChangePasswordSucess();

        void onChangePasswordFail();

        void onSentFeedbackFail();

        void onSentFeedbackSuccess();
    }

}
