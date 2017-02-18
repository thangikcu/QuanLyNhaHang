package com.thanggun99.khachhang;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.google.firebase.iid.FirebaseInstanceId;
import com.thanggun99.khachhang.util.API;
import com.thanggun99.khachhang.util.Utils;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Thanggun99 on 17/02/2017.
 */

public class LoginTask {
    public static String USERNAME = "USERNAME";
    public static String PASSWORD = "PASSWORD";

    private OnLoginListenner onLoginListenner;
    private ProgressDialog progressDialog;
    private SharedPreferences preferences;


    public void setLoginListenner(OnLoginListenner onLoginListenner) {
        this.onLoginListenner = onLoginListenner;
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

    public void loginAuto(){
        new LoginAsynTask().execute(preferences.getString(USERNAME, null), preferences.getString(PASSWORD, null), "auto");
    }

    public void login(String username, String password) {
        new LoginAsynTask().execute(username, password, "login");
    }

    private class LoginAsynTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                onLoginListenner.onLoginSuccess();
            } else {
                onLoginListenner.onLoginError();
            }
            progressDialog.dismiss();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            Map<String, String> infoLogin = new HashMap<>();
            infoLogin.put("username", params[0]);
            infoLogin.put("password", params[1]);
            infoLogin.put("mode", params[2]);
            infoLogin.put("token", FirebaseInstanceId.getInstance().getToken());

            String s = API.callService(API.LOGIN_URL, null, infoLogin);

            if (!TextUtils.isEmpty(s) && s.contains("success")) {
                return true;
            } else return false;
        }
    }

    public interface OnLoginListenner {
        void onLoginSuccess();

        void onLoginError();
    }
}
