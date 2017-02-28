package com.thanggun99.khachhang;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.thanggun99.khachhang.util.API;
import com.thanggun99.khachhang.util.Utils;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Thanggun99 on 17/02/2017.
 */

public class LoginTask {
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";

    private OnLoginLogoutListener onLoginLogoutListener;
    private ProgressDialog progressDialog;
    private SharedPreferences preferences;

    public void setLoginListenner(OnLoginLogoutListener onLoginLogoutListener) {
        this.onLoginLogoutListener = onLoginLogoutListener;
    }

    public LoginTask(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(Utils.getStringByRes(R.string.loading));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

    }

    public boolean isGhiNhoLogin() {
        if (preferences.getString(USERNAME, null) != null) return true;
        return false;
    }

    public void ghiNhoDangNhap(String username, String password) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USERNAME, username);
        editor.putString(PASSWORD, password);
        editor.apply();
    }

    public void huyGhiNhoDangNhap() {
        preferences.edit().clear().apply();
    }

    public void loginAuto() {
        new LoginAsynTask().execute(preferences.getString(USERNAME, null), preferences.getString(PASSWORD, null), "auto");
    }

    public void login(String username, String password) {
        new LoginAsynTask().execute(username, password, "login");
    }

    private class LoginAsynTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            if (!Utils.isConnectingToInternet()) {
                Utils.notifi(Utils.getStringByRes(R.string.kiem_tra_ket_noi_mang));
                cancel(true);
            } else {
                progressDialog.show();
            }
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            if (!TextUtils.isEmpty(s) && s.contains("success")) {
                onLoginLogoutListener.onLoginSuccess();

            }else if (!TextUtils.isEmpty(s) && s.contains("other")){
                Utils.notifi(Utils.getStringByRes(R.string.other_people_login));
            }else{
                onLoginLogoutListener.onLoginError();
            }
            progressDialog.dismiss();
        }

        @Override
        protected String doInBackground(String... params) {
            Map<String, String> infoLogin = new HashMap<>();
            infoLogin.put("username", params[0]);
            infoLogin.put("password", params[1]);
            infoLogin.put("mode", params[2]);
            infoLogin.put("token", Utils.getToken());

            String s = API.callService(API.LOGIN_URL, null, infoLogin);

            return s;
        }
    }

    public interface OnLoginLogoutListener {
        void onLoginSuccess();

        void onLogout();

        void onLoginError();
    }
}
