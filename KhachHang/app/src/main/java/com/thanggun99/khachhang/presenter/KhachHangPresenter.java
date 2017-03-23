package com.thanggun99.khachhang.presenter;


import com.thanggun99.khachhang.model.KhachHangInteractor;
import com.thanggun99.khachhang.model.entity.DatBan;
import com.thanggun99.khachhang.model.entity.KhachHang;
import com.thanggun99.khachhang.util.Utils;

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
        if (datBanView != null) {
            datBanView.showLayoutThongTinDatBan(khachHangInteractor.getCurrentDatBan());
        }
    }

    public void deleteDatBan() {
        khachHangInteractor.deleteDatBan();
        if (datBanView != null) {
            datBanView.showLayoutDatBan();
        }
    }


    public void loginAuto() {
        if (checkConnect()) {
            if (khachHangInteractor.isGhiNhoDangNhap()) {
                khachHangInteractor.loginAuto();
            }
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
        if (checkConnect()) {
            khachHangInteractor.login(khachHang);
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
        if (checkConnect()) {
            khachHangInteractor.getInfoDatBan();
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
        if (checkConnect()) {
            khachHangInteractor.huyDatBan();
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
        if (checkConnect()) {
            khachHangInteractor.updateDatBan(datBan);
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
