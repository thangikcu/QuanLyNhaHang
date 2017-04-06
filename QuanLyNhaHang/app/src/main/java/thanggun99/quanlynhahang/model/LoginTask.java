package thanggun99.quanlynhahang.model;

import android.os.AsyncTask;
import android.text.TextUtils;

import thanggun99.quanlynhahang.App;
import thanggun99.quanlynhahang.model.entity.Admin;

import static thanggun99.quanlynhahang.model.entity.Admin.USERNAME;

/**
 * Created by Thanggun99 on 19/03/2017.
 */

public class LoginTask {
    private static final String AUTO = "AUTO";
    public static final String NOT_AUTO = "NOT_AUTO";

    public static final String FAIL = "FAIL";
    public static final String OTHER = "OTHER";
    public static final String SUCCESS = "SUCCESS";

    private OnLoginListener onLoginListener;

    private Admin admin;

    public void loginAuto() {
        if (isGhiNhoDangNhap()) {
            admin = new Admin();
            admin.setTenDangNhap(App.getPreferences().getString(USERNAME, null));
            admin.setMatKhau(App.getPreferences().getString(Admin.PASSWORD, null));
            admin.setKieuDangNhap(AUTO);

            new LoginAsyncTask().execute();
        }
    }


    public boolean isGhiNhoDangNhap() {
        return !TextUtils.isEmpty(App.getPreferences().getString(USERNAME, null));
    }

    public void login(Admin admin) {

        this.admin = admin;
        new LoginAsyncTask().execute();
    }

    public Admin getAdmin() {
        return admin;
    }

    private class LoginAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            onLoginListener.onStartTask();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.equals(SUCCESS)) {
                onLoginListener.onLoginSuccess();
            } else if (s.equals(OTHER)) {
                onLoginListener.onOtherLogin();
            } else {
                onLoginListener.onLoginFail();
            }
            onLoginListener.onFinishTask();
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(Void... params) {
            delay(2000);
            return admin.login();
        }
    }

    private void delay(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setOnLoginListener(OnLoginListener onLoginListener) {
        this.onLoginListener = onLoginListener;
    }


    public interface OnLoginListener {
        void onStartTask();

        void onFinishTask();

        void onLoginSuccess();

        void onLoginFail();

        void onOtherLogin();
    }
}
