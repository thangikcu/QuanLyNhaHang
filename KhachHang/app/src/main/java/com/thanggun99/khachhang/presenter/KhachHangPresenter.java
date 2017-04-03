package com.thanggun99.khachhang.presenter;


import com.thanggun99.khachhang.R;
import com.thanggun99.khachhang.model.Database;
import com.thanggun99.khachhang.model.KhachHangInteractor;
import com.thanggun99.khachhang.model.entity.DatBan;
import com.thanggun99.khachhang.model.entity.HoaDon;
import com.thanggun99.khachhang.model.entity.KhachHang;
import com.thanggun99.khachhang.model.entity.MonOrder;
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
    private MainView mainView;
    private HomeView homeView;
    private ThongTinPhucVuView thongTinPhucVuView;
    private ChangepasswordView changepasswordView;
    private FeedbackView feedbackView;

    private KhachHangInteractor khachHangInteractor;

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
            mainView.showConnectFailDialog();
            return false;
        }
    }

    //on receive broadcast
    public void updateThongTinKhachHangService(KhachHang khachHangUpdate) {
        khachHangInteractor.updateThongTinKhachHang(khachHangUpdate);
        if (thongTinPhucVuView != null) {
            thongTinPhucVuView.showLayoutThongTinDatBan(khachHangInteractor.getCurrentDatBan());
        }
    }

    public void deleteDatBanService() {
        khachHangInteractor.deleteDatBan();
        if (thongTinPhucVuView != null) {
            thongTinPhucVuView.showLayoutDatBan();
        }
    }

    public void taoHoaDonMoiService() {
        mainView.showThongTinPhucVuFragment();

        khachHangInteractor.deleteYeuCau();
        reLoadThongTinPhucVu();
    }

    public void giamGiaHoaDonMoiService(int giamGia) {
        mainView.showThongTinPhucVuFragment();

        khachHangInteractor.giamGiaHoaDon(giamGia);

        if (thongTinPhucVuView != null) {
            thongTinPhucVuView.showGiamGia(giamGia);
            thongTinPhucVuView.showTongTien(khachHangInteractor.getCurrentHoaDon().getTongTien());
        }

    }

    public void orderMonService() {
        mainView.showThongTinPhucVuFragment();

        khachHangInteractor.onOrderSuccess();

        if (thongTinPhucVuView != null) {
            thongTinPhucVuView.notifyListMonOrderChange();
            thongTinPhucVuView.showTongTien(khachHangInteractor.getCurrentHoaDon().getTongTien());

        }

        khachHangInteractor.deleteYeuCau();
    }

    public void tinhTienHoaDonService() {

        khachHangInteractor.onTinhTienHoaDon();
        khachHangInteractor.deleteYeuCau();

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
        mainView.showViewOnUnlogin();
        mainView.setNullFragments();
        if (loginView != null) {

            loginView.showFormLogin();
        }
        if (homeView != null) {

            homeView.showTabThucDon();
        }
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
        if (getKhachHang().getCurrentHoaDon() != null) {
            thongTinPhucVuView.showLayoutPhucVu(khachHangInteractor.getKhachHang().getCurrentHoaDon());
            thongTinPhucVuView.showTongTien(khachHangInteractor.getKhachHang().getCurrentHoaDon().getTongTien());
        } else if (getKhachHang().getCurrentDatBan() != null) {
            thongTinPhucVuView.showLayoutThongTinDatBan(khachHangInteractor.getKhachHang().getCurrentDatBan());

        } else {
            thongTinPhucVuView.showLayoutDatBan();

        }
    }

    public void reLoadThongTinPhucVu() {
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
        if (getDatabase().getMonList() == null) {
            if (checkConnect()) {
                khachHangInteractor.loadThucDonList();
            }
        } else {
            thucDonView.showThucDon();
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


    // goi mon
    public void onClickMon(Mon mon) {
        if (checkLogin()) {
            khachHangInteractor.setcurrentMon(mon);
            thucDonView.showOrderMonDialog(mon);
        } else {
            thucDonView.showRequireLoginNotify();
        }

    }

    public void onClickMonOrder(MonOrder monOrder) {
        khachHangInteractor.setcurrentMon(monOrder);
        khachHangInteractor.setCurrentMonOrder(monOrder);
        thongTinPhucVuView.showOrderMonDialog(monOrder);
    }

    public void orderMon(int soLuong) {
        if (checkConnect()) {

            khachHangInteractor.orderMon(soLuong);
        }
    }

    @Override
    public void onFinishSendYeuCau() {
        mainView.showNotify(Utils.getStringByRes(R.string.da_gui_yeu_cau));
    }

    @Override
    public void onSendYeuCauFail() {
        mainView.showNotify(Utils.getStringByRes(R.string.gui_yeu_cau_that_bai));
    }

    public boolean checkLogin() {
        return khachHangInteractor.isLogin();
    }

    public void onClickTinhTien() {
        thongTinPhucVuView.showTinhTienDialog(khachHangInteractor.getCurrentHoaDon());
    }

    public void tinhTien() {
        if (checkConnect()) {

            khachHangInteractor.tinhTien();
        }
    }


    public interface ThucDonView {

        void showThucDon();

        void showErrorGetThucDonFail();

        void notifyChangeListThucDon(ArrayList<Mon> listMonByMaLoai);

        void showOrderMonDialog(Mon mon);

        void showRequireLoginNotify();
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

        void showConnectFailDialog();

        void setNullFragments();

        void hideProgress();

        void showProgress();

        void showOtherLogin();

        void showLoginFragment();

        void showHomeFragment();

        void showThongTinPhucVuFragment();

        void showNotify(String message);
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

        void showOrderMonDialog(MonOrder monOrder);

        void showGetDatasFailDialog();

        void showGiamGia(int giamGia);

        void notifyListMonOrderChange();

        void showTinhTienDialog(HoaDon currentHoaDon);
    }
}
