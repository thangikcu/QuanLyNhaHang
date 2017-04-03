package com.thanggun99.khachhang.model;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.thanggun99.khachhang.App;
import com.thanggun99.khachhang.model.entity.DatBan;
import com.thanggun99.khachhang.model.entity.HoaDon;
import com.thanggun99.khachhang.model.entity.KhachHang;
import com.thanggun99.khachhang.model.entity.Mon;
import com.thanggun99.khachhang.model.entity.MonOrder;
import com.thanggun99.khachhang.model.entity.MonYeuCau;
import com.thanggun99.khachhang.model.entity.YeuCau;
import com.thanggun99.khachhang.util.API;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Thanggun99 on 28/02/2017.
 */

public class KhachHangInteractor {
    private KhachHang khachHang;
    private Database database;
    private OnKhachHangFinishedListener onKhachHangFinishedListener;
    private Mon currentMon;
    private boolean isLogin;
    private MonOrder currentMonOrder;


    public KhachHangInteractor(OnKhachHangFinishedListener onKhachHangFinishedListener) {
        this.onKhachHangFinishedListener = onKhachHangFinishedListener;
        isLogin = false;
        database = new Database();

    }

    public void loginAuto() {
        khachHang = new KhachHang();
        khachHang.setTenDangNhap(App.getPreferences().getString(KhachHang.USERNAME, null));
        khachHang.setMatKhau(App.getPreferences().getString(KhachHang.PASSWORD, null));
        khachHang.setKieuDangNhap("auto");
        khachHang.setDatabase(database);

        new LoginAsynTask().execute();
    }

    public void login(KhachHang khachHang) {
        this.khachHang = khachHang;
        khachHang.setDatabase(database);

        new LoginAsynTask().execute();
    }

    public void logout() {
        isLogin = false;
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

        if (TextUtils.isEmpty(App.getPreferences().getString(KhachHang.USERNAME, null))) {
            return false;
        }
        return true;

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

    public void updateThongTinKhachHang(KhachHang khachHangUpdate) {
        khachHang.setTenKhachHang(khachHangUpdate.getTenKhachHang());
        khachHang.setSoDienThoai(khachHangUpdate.getSoDienThoai());
        if (getCurrentDatBan() != null) {
            DatBan datBan = getCurrentDatBan();
            datBan.setGioDen(khachHangUpdate.getCurrentDatBan().getGioDen());
            datBan.setYeuCau(khachHangUpdate.getCurrentDatBan().getYeuCau());
        }

    }

    public void deleteDatBan() {
        khachHang.setCurrentDatBan(null);
    }

    public void loadTinTucList() {
        class GetTinTucTask extends AsyncTask<Void, Void, Boolean> {
            @Override
            protected void onPreExecute() {
                onKhachHangFinishedListener.onStartTask();
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    onKhachHangFinishedListener.onGetTinTucSuccess();
                } else {
                    onKhachHangFinishedListener.onGetTinTucFail();
                }
                onKhachHangFinishedListener.onFinishTask();
                super.onPostExecute(aBoolean);
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                return database.loadTinTucList();
            }

        }
        new GetTinTucTask().execute();
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void loadThucDonList() {
        class GetThucDonTask extends AsyncTask<Void, Void, Boolean> {
            @Override
            protected void onPreExecute() {
                onKhachHangFinishedListener.onStartTask();
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    onKhachHangFinishedListener.onGetThucDonSuccess();
                } else {
                    onKhachHangFinishedListener.onGetThucDonFail();
                }
                onKhachHangFinishedListener.onFinishTask();
                super.onPostExecute(aBoolean);
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                return database.loadThucDonList();
            }

        }
        new GetThucDonTask().execute();
    }

    public Database getDatabase() {
        return database;
    }

    public void setcurrentMon(Mon currentMon) {
        this.currentMon = currentMon;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setCurrentMonOrder(MonOrder monOrder) {
        this.currentMonOrder = monOrder;
    }

    public void orderMon(int soLuong) {
        MonYeuCau monYeuCau = new MonYeuCau();
        monYeuCau.setMaMon(currentMon.getMaMon());
        monYeuCau.setSoLuong(soLuong);

        YeuCau yeuCau = khachHang.getYeuCau();
        if (khachHang.getCurrentHoaDon() == null) {
            yeuCau.setType(YeuCau.HOA_DON_MOI);
        } else {
            yeuCau.setType(YeuCau.THEM_MON);
        }

        new SendYeuCauTask(monYeuCau).execute();

    }


    public void tinhTien() {
        YeuCau yeuCau = khachHang.getYeuCau();

        yeuCau.setType(YeuCau.TINH_TIEN_HOA_DON);

        new SendYeuCauTask(null).execute();
    }

    public void onTinhTienHoaDon() {
        khachHang.setCurrentHoaDon(null);

    }

    private class SendYeuCauTask extends AsyncTask<Void, Void, Boolean> {
        private MonYeuCau monYeuCau;

        public SendYeuCauTask(MonYeuCau monYeuCau) {
            this.monYeuCau = monYeuCau;
        }

        @Override
        protected void onPreExecute() {
            onKhachHangFinishedListener.onStartTask();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                onKhachHangFinishedListener.onFinishSendYeuCau();
            } else {
                onKhachHangFinishedListener.onSendYeuCauFail();
            }
            onKhachHangFinishedListener.onFinishTask();
            super.onPostExecute(aBoolean);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            delay(500);

            return khachHang.sendYeuCau(monYeuCau);
        }

    }

    public void giamGiaHoaDon(int giamGia) {
        khachHang.getCurrentHoaDon().setGiamGia(giamGia);
    }

    public HoaDon getCurrentHoaDon() {
        return khachHang.getCurrentHoaDon();
    }

    public void deleteYeuCau() {
        khachHang.setYeuCau(new YeuCau());
    }

    public void onOrderSuccess() {
        YeuCau yeuCau = khachHang.getYeuCau();

        ArrayList<MonYeuCau> monYeuCauNewList = new ArrayList<>();
        monYeuCauNewList.addAll(yeuCau.getMonYeuCauList());

        for (MonOrder monOrder : getCurrentHoaDon().getMonOrderList()) {
            for (MonYeuCau monYeuCau : yeuCau.getMonYeuCauList()) {
                if (monOrder.getMaMon() == monYeuCau.getMaMon()) {

                    monOrder.setSoLuong(monOrder.getSoLuong() + monYeuCau.getSoLuong());
                    monYeuCauNewList.remove(monYeuCau);
                }
            }
        }

        ArrayList<MonOrder> monOrderNewList = new ArrayList<>();

        for (MonYeuCau monYeuCau : monYeuCauNewList) {
            MonOrder monOrder = new MonOrder();
            monOrder.setMon(database.getMonByMaMon(monYeuCau.getMaMon()));
            monOrder.setSoLuong(monYeuCau.getSoLuong());

            monOrderNewList.add(monOrder);
        }

        getCurrentHoaDon().getMonOrderList().addAll(0, monOrderNewList);

    }

    private class LoginAsynTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            onKhachHangFinishedListener.onStartTask();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            switch (s) {
                case KhachHang.LOGIN_SUCCESS:
                    isLogin = true;

                    onKhachHangFinishedListener.onLoginSuccess(khachHang);
                    break;
                case KhachHang.OTHER_LOGIN:
                    onKhachHangFinishedListener.onOtherLogin();
                    khachHang = null;
                    break;
                case KhachHang.FAIL:
                    onKhachHangFinishedListener.onLoginFail();
                    khachHang = null;
                    break;
                default:
                    break;
            }
            onKhachHangFinishedListener.onFinishTask();
        }

        @Override
        protected String doInBackground(Void... params) {
            delay(2000);
            return khachHang.login();
        }
    }

    public void getThongTinPhucVu() {
        class GetThongTinPhucVuTask extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                onKhachHangFinishedListener.onStartTask();
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                switch (s) {
                    case KhachHang.KH_PHUC_VU:
                        onKhachHangFinishedListener.onFinishGetThongTinKhachHangPhucVu();
                        break;
                    case KhachHang.KH_DAT_BAN:
                        onKhachHangFinishedListener.onFinishGetThongTinKhachHangDatBan();
                        break;
                    case KhachHang.KH_NONE:
                        onKhachHangFinishedListener.onFinishGetThongTinKhachHangNone();
                        break;
                    case KhachHang.FAIL:
                        onKhachHangFinishedListener.onGetThongTinPhucVuFail();
                        break;
                    default:
                        break;
                }
                onKhachHangFinishedListener.onFinishTask();

                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(String... params) {
                delay(500);
                return khachHang.getThongTinPhucVu();
            }
        }
        new GetThongTinPhucVuTask().execute();
    }


    private void delay(int milis) {
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

        void onFinishHuyDatBan();

        void onHuyDatBanFail();

        void onUpdateDatBanSuccess();

        void onUpdateDatBanFail();

        void onStartTask();

        void onFinishTask();

        void onGetTinTucSuccess();

        void onGetTinTucFail();

        void onGetThucDonSuccess();

        void onGetThucDonFail();

        void onGetThongTinPhucVuFail();

        void onFinishGetThongTinKhachHangNone();

        void onFinishGetThongTinKhachHangPhucVu();

        void onFinishGetThongTinKhachHangDatBan();

        void onFinishSendYeuCau();

        void onSendYeuCauFail();
    }

}
