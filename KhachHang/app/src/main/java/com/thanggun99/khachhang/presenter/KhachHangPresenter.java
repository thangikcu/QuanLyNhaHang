package com.thanggun99.khachhang.presenter;


import com.thanggun99.khachhang.model.KhachHangInteractor;
import com.thanggun99.khachhang.model.entity.KhachHang;

/**
 * Created by Thanggun99 on 28/02/2017.
 */

public class KhachHangPresenter implements KhachHangInteractor.OnKhachHangFinishedListener {
    private ThucDonView thucDonView;
    private KhachHangInteractor khachHangInteractor;
    private MainView mainView;

    public KhachHangPresenter(MainView mainView) {
        khachHangInteractor = new KhachHangInteractor(this);
        this.mainView = mainView;
    }

    public void loginAuto() {
        if (khachHangInteractor.khachHang.isGhiNhoDangNhap()) {
            thucDonView.showProgress();
            khachHangInteractor.loginAuto();
        }
    }

    @Override
    public void onLoginSuccess() {
        thucDonView.showThucDon();
        mainView.showViewOnlogin();
        thucDonView.hideProgress();
    }

    @Override
    public void onOtherLogin() {
        thucDonView.showOtherLogin();
        thucDonView.hideProgress();
    }

    @Override
    public void onLoginFail() {
        thucDonView.showloginFail();
        thucDonView.hideProgress();
    }

    public void login(KhachHang khachHang) {
        thucDonView.showProgress();
        khachHangInteractor.login(khachHang);
    }

    public void setThucDonView(ThucDonView thucDonView) {
        this.thucDonView = thucDonView;
    }

    public void logout() {
        khachHangInteractor.logout();
        thucDonView.showFormLogin();
        mainView.showViewOnUnlogin();
    }

    public void onDestroy() {
        mainView = null;
        thucDonView = null;
    }

    public interface ThucDonView {
        void showProgress();

        void hideProgress();

        void showOtherLogin();

        void showloginFail();

        void showThucDon();

        void showFormLogin();
    }

    public interface MainView {
        void showViewOnUnlogin();

        void showViewOnlogin();
    }
}
