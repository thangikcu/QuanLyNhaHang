package com.thanggun99.khachhang.presenter;


import com.thanggun99.khachhang.model.Database;
import com.thanggun99.khachhang.model.KhachHangInteractor;
import com.thanggun99.khachhang.model.entity.DatBan;
import com.thanggun99.khachhang.model.entity.HoaDon;
import com.thanggun99.khachhang.model.entity.KhachHang;
import com.thanggun99.khachhang.model.entity.NhomMon;
import com.thanggun99.khachhang.model.entity.Mon;
import com.thanggun99.khachhang.model.entity.TinTuc;
import com.thanggun99.khachhang.util.Utils;

import java.util.ArrayList;

/**
 * Created by Thanggun99 on 28/02/2017.
 */

public class KhachHangPresenter implements KhachHangInteractor.OnKhachHangFinishedListener {
    private ThucDonView thucDonView;
    private LoginView loginView;
    private TinTucView tinTucView;
    private KhachHangInteractor khachHangInteractor;
    private MainView mainView;
    private ChangepasswordView changepasswordView;
    private HomeView homeView;
    private FeedbackView feedbackView;
    private ThongTinPhucVuView thongTinPhucVuView;

    public KhachHangPresenter(MainView mainView) {
        khachHangInteractor = new KhachHangInteractor(this);
        this.mainView = mainView;
    }

    @Override
    public void onStartTask() {
        mainView.showProgress();
    }

    @Override
    public void onFinishTask() {
        mainView.hideProgress();
    }

    private boolean checkConnect() {
        if (Utils.isConnectingToInternet()) {
            return true;
        } else {
            mainView.showDialogConnectFail();
            return false;
        }
    }

    //on receive broadcast
    public void updateThongTinKhachHang(KhachHang khachHangUpdate) {
        khachHangInteractor.updateThongTinKhachHang(khachHangUpdate);
        if (thongTinPhucVuView != null) {
            thongTinPhucVuView.showLayoutThongTinDatBan(khachHangInteractor.getCurrentDatBan());
        }
    }

    public void deleteDatBan() {
        khachHangInteractor.deleteDatBan();
        if (thongTinPhucVuView != null) {
            thongTinPhucVuView.showLayoutDatBan();
        }
    }


    public void loginAuto() {
        if (khachHangInteractor.isGhiNhoDangNhap()) {

            mainView.showLoginFragment();

            if (checkConnect()) {

                khachHangInteractor.loginAuto();
            }
        } else {
            mainView.showHomeFragment();
        }
    }

    @Override
    public void onLoginSuccess(KhachHang khachHang) {
        mainView.showViewOnlogin(khachHang);
    }

    @Override
    public void onGetThucDonSuccess() {
        thucDonView.showThucDon();
    }

    @Override
    public void onGetThucDonFail() {
        thucDonView.showErrorGetThucDonFail();
    }

    @Override
    public void onOtherLogin() {
        mainView.showOtherLogin();
    }

    @Override
    public void onLoginFail() {
        loginView.showloginFail();
    }

    public void login(KhachHang khachHang) {
        if (checkConnect()) {
            khachHangInteractor.login(khachHang);
        }
    }

    public void setThucDonView(ThucDonView thucDonView) {
        this.thucDonView = thucDonView;
    }

    public void logout() {
        khachHangInteractor.logout();
        loginView.showFormLogin();
        mainView.showViewOnUnlogin();
        mainView.setNullFragments();
        homeView.showTabThucDon();
    }

    @Override
    public void onChangePasswordSucess() {
        changepasswordView.showOnsuccess();
    }

    @Override
    public void onChangePasswordFail() {
        changepasswordView.showOnFail();
    }

    @Override
    public void onSentFeedbackFail() {
        feedbackView.showOnFail();
    }

    @Override
    public void onSentFeedbackSuccess() {
        feedbackView.showOnsuccess();
    }

    @Override
    public void passwordWrong() {
        changepasswordView.showPasswordWrong();
    }

    public void onDestroy() {
        mainView = null;
        thucDonView = null;
    }

    public void changePassWord(String password, String newPassword) {
        if (checkConnect()) {
            khachHangInteractor.changePassword(password, newPassword);
        }
    }

    public void setChangepasswordView(ChangepasswordView changepasswordView) {
        this.changepasswordView = changepasswordView;
    }

    public void setHomeView(HomeView homeView) {
        this.homeView = homeView;
    }

    public void setFeedbackView(FeedbackView feedbackView) {
        this.feedbackView = feedbackView;
    }

    public void sentFeedback(String title, String content) {
        if (checkConnect()) {
            khachHangInteractor.sentFeedback(title, content);
        }
    }

    //dat ban
    public void onClickDatBan(DatBan datBan) {
        if (checkConnect()) {
            khachHangInteractor.datBan(datBan);
        }
    }

    @Override
    public void onDatBanSuccess() {
        thongTinPhucVuView.showOnSuccess();
        thongTinPhucVuView.showLayoutThongTinDatBan(khachHangInteractor.getCurrentDatBan());
    }

    @Override
    public void onDatBanFail() {
        thongTinPhucVuView.showDatBanError();
    }


    public void setThongTinPhucVuView(ThongTinPhucVuView thongTinPhucVuView) {
        this.thongTinPhucVuView = thongTinPhucVuView;
    }


    //get thong tin phuc vu
    public void getThongTinPhucVu() {
        khachHangInteractor.getThongTinPhucVu();
    }

    @Override
    public void onGetThongTinPhucVuFail() {
        thongTinPhucVuView.showGetDatasFailDialog();
    }

    @Override
    public void onFinishGetThongTinKhachHangPhucVu() {
        thongTinPhucVuView.showLayoutPhucVu(khachHangInteractor.getKhachHang().getCurrentHoaDon());
        thongTinPhucVuView.showTongTien(khachHangInteractor.getKhachHang().getCurrentHoaDon().getTongTien());
    }

    @Override
    public void onFinishGetThongTinKhachHangDatBan() {
        thongTinPhucVuView.showLayoutThongTinDatBan(khachHangInteractor.getKhachHang().getCurrentDatBan());
    }

    @Override
    public void onFinishGetThongTinKhachHangNone() {
        thongTinPhucVuView.showLayoutDatBan();
    }


    //huy dat ban
    public void onClickHuyDatBan() {
        if (checkConnect()) {
            khachHangInteractor.huyDatBan();
        }
    }

    @Override
    public void onFinishHuyDatBan() {
        thongTinPhucVuView.showLayoutDatBan();
    }

    @Override
    public void onHuyDatBanFail() {
        thongTinPhucVuView.showError();
    }

    public void onClickEditDatBan() {
        thongTinPhucVuView.showLayoutDatBan();
        thongTinPhucVuView.setContentView(khachHangInteractor.getCurrentDatBan());
    }

    public void UpdateDatBan(DatBan datBan) {
        if (checkConnect()) {
            khachHangInteractor.updateDatBan(datBan);
        }
    }

    @Override
    public void onUpdateDatBanSuccess() {
        thongTinPhucVuView.showOnUpdateSuccess();
        thongTinPhucVuView.showLayoutThongTinDatBan(khachHangInteractor.getCurrentDatBan());
    }

    @Override
    public void onUpdateDatBanFail() {
        thongTinPhucVuView.showUpdateDatBanError();
    }

    public void backToThongTinDatBan() {
        thongTinPhucVuView.showLayoutThongTinDatBan(khachHangInteractor.getCurrentDatBan());
    }

    public void loadTinTucList() {
        if (checkConnect()) {
            if (getDatabase().getTinTucList() == null) {

                khachHangInteractor.loadTinTucList();
            }
        }
    }

    @Override
    public void onGetTinTucSuccess() {
        tinTucView.showTinTucList();
    }

    @Override
    public void onGetTinTucFail() {
        tinTucView.showErrorLoadTinTuc();
    }

    public void setTinTucView(TinTucView tinTucView) {
        this.tinTucView = tinTucView;
    }

    public ArrayList<TinTuc> getTinTucList() {
        return getDatabase().getTinTucList();
    }

    public KhachHang getKhachHang() {

        return khachHangInteractor.getKhachHang();
    }

    public Database getDatabase() {
        return khachHangInteractor.getDatabase();
    }

    public void onClickNhomMon(NhomMon nhomMon) {
        thucDonView.notifyChangeListThucDon(getDatabase().getListThucDonByMaLoai(nhomMon.getMaLoai()));
    }

    public void loadThucDonList() {
        if (checkConnect()) {
            if (getDatabase().getMonList() == null) {

                khachHangInteractor.loadThucDonList();
            }
        }

    }

    public void setLoginView(LoginView loginView) {
        this.loginView = loginView;
    }

    public void showThucDonLayout() {
        mainView.showHomeFragment();
        homeView.showTabThucDon();
    }

    public void onClickHuyBan() {

    }

    public void onClickThongTinDatBan() {
        thongTinPhucVuView.showThongTinDatBanDialog(getKhachHang().getCurrentHoaDon().getDatBan());
    }

    public interface ThucDonView {

        void showThucDon();

        void showErrorGetThucDonFail();

        void notifyChangeListThucDon(ArrayList<Mon> listMonByMaLoai);
    }

    public interface TinTucView {
        void showTinTucList();

        void showErrorLoadTinTuc();
    }

    public interface LoginView {

        void showloginFail();

        void showFormLogin();
    }

    public interface MainView {
        void showViewOnUnlogin();

        void showViewOnlogin(KhachHang khachHang);

        void showDialogConnectFail();

        void setNullFragments();

        void hideProgress();

        void showProgress();

        void showOtherLogin();

        void showLoginFragment();

        void showHomeFragment();

    }

    public interface ChangepasswordView {

        void showOnsuccess();

        void showPasswordWrong();

        void showOnFail();

    }

    public interface HomeView {
        void showTabThucDon();
    }

    public interface FeedbackView {

        void showOnsuccess();

        void showOnFail();
    }

    public interface ThongTinPhucVuView {

        void showOnSuccess();

        void showGetDatasFailDialog();

        void showLayoutThongTinDatBan(DatBan datBan);

        void showLayoutDatBan();

        void showError();

        void setContentView(DatBan datBan);

        void showOnUpdateSuccess();

        void showLayoutPhucVu(HoaDon currentHoaDon);

        void showUpdateDatBanError();

        void showDatBanError();

        void showTongTien(int tongTien);

        void showThongTinDatBanDialog(DatBan datBan);
    }
}
