package com.thanggun99.khachhang.model;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.thanggun99.khachhang.App;
import com.thanggun99.khachhang.model.entity.DatBan;
import com.thanggun99.khachhang.model.entity.KhachHang;
import com.thanggun99.khachhang.util.API;

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
            protected void onPreExecute() {
                onKhachHangFinishedListener.onStartTask();
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    onKhachHangFinishedListener.onChangePasswordSucess();
                } else {
                    onKhachHangFinishedListener.onChangePasswordFail();
                }
                onKhachHangFinishedListener.onFinishTask();
                super.onPostExecute(aBoolean);
            }

            @Override
            protected Boolean doInBackground(String... params) {
                delay(500);
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

    public void sentFeedback(String title, String content) {
        class FeedbackTask extends AsyncTask<String, Void, Boolean> {

            @Override
            protected void onPreExecute() {
                onKhachHangFinishedListener.onStartTask();
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    onKhachHangFinishedListener.onSentFeedbackSuccess();
                } else {
                    onKhachHangFinishedListener.onSentFeedbackFail();
                }

                onKhachHangFinishedListener.onFinishTask();
                super.onPostExecute(aBoolean);
            }

            @Override
            protected Boolean doInBackground(String... params) {
                delay(500);
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

    public void datBan(final DatBan datBan) {
        class DatBanTask extends AsyncTask<Void, Void, Boolean> {
            @Override
            protected void onPreExecute() {
                onKhachHangFinishedListener.onStartTask();
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    onKhachHangFinishedListener.onDatBanSuccess();
                } else {
                    onKhachHangFinishedListener.onDatBanFail();
                }
                onKhachHangFinishedListener.onFinishTask();
                super.onPostExecute(aBoolean);
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                delay(500);
                return khachHang.datBan(datBan);
            }
        }
        new DatBanTask().execute();
    }


    public void getInfoDatBan() {
        class GetInfoDatBanTask extends AsyncTask<String, Void, Boolean> {
            @Override
            protected void onPreExecute() {
                onKhachHangFinishedListener.onStartTask();
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    onKhachHangFinishedListener.onGetInfoDatBanSuccess();
                } else {
                    onKhachHangFinishedListener.onGetInfoDatBanFail();
                }
                onKhachHangFinishedListener.onFinishTask();
                super.onPostExecute(aBoolean);
            }

            @Override
            protected Boolean doInBackground(String... params) {
                delay(500);
                return khachHang.getInfoDatBan();
            }
        }
        new GetInfoDatBanTask().execute();
    }


    public void huyDatBan() {
        class HuyDatBanTask extends AsyncTask<Void, Void, Boolean> {
            @Override
            protected void onPreExecute() {
                onKhachHangFinishedListener.onStartTask();
                super.onPreExecute();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                delay(500);
                return khachHang.huyDatBan();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    onKhachHangFinishedListener.onFinishHuyDatBan();
                } else {
                    onKhachHangFinishedListener.onHuyDatBanFail();
                }
                onKhachHangFinishedListener.onFinishTask();
            }

        }
        new HuyDatBanTask().execute();
    }

    public DatBan getCurrentDatBan() {
        return khachHang.getCurrentDatBan();
    }

    public void updateDatBan(final DatBan datBan) {
        class UpdateDatBanTask extends AsyncTask<Void, Void, Boolean> {
            @Override
            protected void onPreExecute() {
                onKhachHangFinishedListener.onStartTask();
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    onKhachHangFinishedListener.onUpdateDatBanSuccess();
                } else {
                    onKhachHangFinishedListener.onUpdateDatBanFail();
                }
                onKhachHangFinishedListener.onFinishTask();
                super.onPostExecute(aBoolean);
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                delay(500);
                return khachHang.updateDatBan(datBan);
            }

        }
        new UpdateDatBanTask().execute();
    }

    private class LoginAsynTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            onKhachHangFinishedListener.onStartTask();
            super.onPreExecute();
        }

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
            onKhachHangFinishedListener.onFinishTask();
        }

        @Override
        protected String doInBackground(Void... params) {
            delay(2000);
            return khachHang.login();
        }
    }

    private void delay(int milis){
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            e.printStackTrace();
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

        void onDatBanSuccess();

        void onDatBanFail();

        void onGetInfoDatBanSuccess();

        void onGetInfoDatBanFail();

        void onFinishHuyDatBan();

        void onHuyDatBanFail();

        void onUpdateDatBanSuccess();

        void onUpdateDatBanFail();

        void onStartTask();

        void onFinishTask();
    }

}
