package com.thanggun99.khachhang.presenter;


import com.thanggun99.khachhang.model.KhachHangInteractor;
import com.thanggun99.khachhang.model.entity.DatBan;
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
    private DatBanView datBanView;

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

    public void loginAuto() {
        if (khachHangInteractor.checkConnect()) {
            if (khachHangInteractor.isGhiNhoDangNhap()) {
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
    }

    @Override
    public void onOtherLogin() {
        thucDonView.showOtherLogin();
    }

    @Override
    public void onLoginFail() {
        thucDonView.showloginFail();
    }

    public void login(KhachHang khachHang) {
        if (khachHangInteractor.checkConnect()) {
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
        mainView.setNullFragments();
        homeView.showOnUnLogin();
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
        if (khachHangInteractor.checkConnect()) {
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
            khachHangInteractor.sentFeedback(title, content);
        } else {
            mainView.showDialogConnectFail();
        }
    }

    //dat ban
    public void onClickDatBan(DatBan datBan) {
        if (khachHangInteractor.checkConnect()) {
            khachHangInteractor.datBan(datBan);
        } else {
            mainView.showDialogConnectFail();
        }

    }

    @Override
    public void onDatBanSuccess() {
        datBanView.showOnSuccess();
        datBanView.showLayoutThongTinDatBan(khachHangInteractor.getCurrentDatBan());
    }

    @Override
    public void onDatBanFail() {
        datBanView.showOnFail();
    }


    public void setDatBanView(DatBanView datBanView) {
        this.datBanView = datBanView;
    }


    //get thong tin dat ban
    public void getInfoDatBan() {
        if (khachHangInteractor.checkConnect()) {
            khachHangInteractor.getInfoDatBan();
        } else {
            mainView.showDialogConnectFail();
        }
    }

    @Override
    public void onGetInfoDatBanSuccess() {
        datBanView.showLayoutThongTinDatBan(khachHangInteractor.getCurrentDatBan());
    }

    @Override
    public void onGetInfoDatBanFail() {
        datBanView.showLayoutDatBan();
    }


    //huy dat ban
    public void onClickHuyDatBan() {
        if (khachHangInteractor.checkConnect()) {
            khachHangInteractor.huyDatBan();
        } else {
            mainView.showDialogConnectFail();
        }
    }

    @Override
    public void onFinishHuyDatBan() {
        datBanView.showLayoutDatBan();
    }

    @Override
    public void onHuyDatBanFail() {
        datBanView.showError();
    }

    public void onClickEditDatBan() {
        datBanView.showLayoutDatBan();
        datBanView.setContentView(khachHangInteractor.getCurrentDatBan());
    }

    public void UpdateDatBan(DatBan datBan) {
        if (khachHangInteractor.checkConnect()) {
            khachHangInteractor.updateDatBan(datBan);
        } else {
            mainView.showDialogConnectFail();
        }
    }

    @Override
    public void onUpdateDatBanSuccess() {
        datBanView.showOnUpdateSuccess();
        datBanView.showLayoutThongTinDatBan(khachHangInteractor.getCurrentDatBan());
    }

    @Override
    public void onUpdateDatBanFail() {
        onDatBanFail();
    }

    public void backToThongTinDatBan() {
        datBanView.showLayoutThongTinDatBan(khachHangInteractor.getCurrentDatBan());
    }

    public interface ThucDonView {

        void showOtherLogin();

        void showloginFail();

        void showThucDon();

        void showFormLogin();

    }

    public interface MainView {
        void showViewOnUnlogin();

        void showViewOnlogin(KhachHang khachHang);

        void showDialogConnectFail();

        void setNullFragments();

        void hideProgress();

        void showProgress();
    }

    public interface ChangepasswordView {

        void showOnsuccess();

        void showPasswordWrong();

        void showOnFail();

    }

    public interface HomeView {
        void showOnUnLogin();
    }

    public interface FeedbackView {

        void showOnsuccess();

        void showOnFail();
    }

    public interface DatBanView {

        void showOnSuccess();

        void showOnFail();

        void showLayoutThongTinDatBan(DatBan datBan);

        void showLayoutDatBan();

        void showError();

        void setContentView(DatBan datBan);

        void showOnUpdateSuccess();

    }
}
