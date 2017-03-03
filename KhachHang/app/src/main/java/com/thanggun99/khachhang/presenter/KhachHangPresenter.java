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
    private ChangepasswordView changepasswordView;
    private HomeView homeView;
    private FeedbackView feedbackView;

    public KhachHangPresenter(MainView mainView) {
        khachHangInteractor = new KhachHangInteractor(this);
        this.mainView = mainView;
    }

    public void loginAuto() {
        if (khachHangInteractor.checkConnect()) {
            if (khachHangInteractor.isGhiNhoDangNhap()) {
                thucDonView.showProgress();
                khachHangInteractor.loginAuto();
            }
        } else {
            mainView.showDialogConnectFail();
        }

    }

    @Override
    public void onLoginSuccess(KhachHang khachHang) {
        thucDonView.showThucDon();
        mainView.showViewOnlogin(khachHang);
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
        if (khachHangInteractor.checkConnect()) {
            thucDonView.showProgress();
            khachHangInteractor.login(khachHang);
        } else {
            mainView.showDialogConnectFail();
        }

    }

    public void setThucDonView(ThucDonView thucDonView) {
        this.thucDonView = thucDonView;
    }

    public void logout() {
        khachHangInteractor.logout();
        thucDonView.showFormLogin();
        mainView.showViewOnUnlogin();
        homeView.showOnUnLogin();
    }

    @Override
    public void onChangePasswordSucess() {
        changepasswordView.hideProgress();
        changepasswordView.showOnsuccess();
    }

    @Override
    public void onChangePasswordFail() {
        changepasswordView.hideProgress();
        changepasswordView.showOnFail();
    }

    @Override
    public void onSentFeedbackFail() {
        feedbackView.hideProgress();
        feedbackView.showOnFail();
    }

    @Override
    public void onSentFeedbackSuccess() {
        feedbackView.hideProgress();
        feedbackView.showOnsuccess();
    }

    @Override
    public void passwordWrong() {
        changepasswordView.hideProgress();
        changepasswordView.showPasswordWrong();
    }

    public void onDestroy() {
        mainView = null;
        thucDonView = null;
    }

    public void changePassWord(String password, String newPassword) {
        if (khachHangInteractor.checkConnect()) {
            changepasswordView.showProgress();
            khachHangInteractor.changePassword(password, newPassword);
        } else {
            mainView.showDialogConnectFail();
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
        if (khachHangInteractor.checkConnect()) {
            feedbackView.showProgress();
            khachHangInteractor.sentFeedback(title, content);
        } else {
            mainView.showDialogConnectFail();
        }
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

        void showViewOnlogin(KhachHang khachHang);

        void showDialogConnectFail();

    }

    public interface ChangepasswordView {
        void showProgress();

        void hideProgress();

        void showOnsuccess();

        void showPasswordWrong();

        void showOnFail();

    }

    public interface HomeView {
        void showOnUnLogin();
    }

    public interface FeedbackView {
        void showProgress();

        void hideProgress();

        void showOnsuccess();

        void showOnFail();
    }
}
