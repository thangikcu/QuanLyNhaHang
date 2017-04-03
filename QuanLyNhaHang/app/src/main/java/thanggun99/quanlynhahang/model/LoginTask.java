package thanggun99.quanlynhahang.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import thanggun99.quanlynhahang.App;
import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.model.entity.Admin;
import thanggun99.quanlynhahang.util.Utils;
import thanggun99.quanlynhahang.view.dialog.NotifiDialog;

/**
 * Created by Thanggun99 on 19/03/2017.
 */

public class LoginTask {
    private static final String AUTO = "AUTO";
    public static final String NOT_AUTO = "NOT_AUTO";

    public static final String FAIL = "FAIL";
    public static final String OTHER = "OTHER";
    public static final String SUCCESS = "SUCCESS";

    private OnFinishLoginListener onFinishLoginListener;

    private Admin admin;
    private ProgressDialog progressDialog;
    private NotifiDialog notifiDialog;

    public LoginTask(Context context) {
        notifiDialog = new NotifiDialog(context);

        progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(Utils.getStringByRes(R.string.loading));

    }

    public void loginAuto() {
        if (Utils.isConnectingToInternet()) {
            admin = new Admin();
            if (admin.isGhiNhoDangNhap()) {
                admin.setTenDangNhap(App.getPreferences().getString(Admin.USERNAME, null));
                admin.setMatKhau(App.getPreferences().getString(Admin.PASSWORD, null));
                admin.setKieuDangNhap(AUTO);

                new LoginAsyncTask().execute();
            }

        } else {
            notifiDialog.notifi(Utils.getStringByRes(R.string.kiem_tra_ket_noi_mang));
        }
    }

    public void login(Admin admin) {
        if (Utils.isConnectingToInternet()) {
            this.admin = admin;
            new LoginAsyncTask().execute();
        } else {
            notifiDialog.notifi(Utils.getStringByRes(R.string.kiem_tra_ket_noi_mang));
        }
    }

    public Admin getAdmin() {
        return admin;
    }


    private class LoginAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.equals(SUCCESS)) {
                onFinishLoginListener.onLoginSuccess();
            } else if (s.equals(OTHER)) {
                onFinishLoginListener.onOtherLogin();
            } else {
                onFinishLoginListener.onLoginFail();
            }
            progressDialog.dismiss();
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

    public void setOnFinishLoginListener(OnFinishLoginListener onFinishLoginListener) {
        this.onFinishLoginListener = onFinishLoginListener;
    }


    public interface OnFinishLoginListener {
        void onLoginSuccess();

        void onLoginFail();

        void onOtherLogin();
    }
}
