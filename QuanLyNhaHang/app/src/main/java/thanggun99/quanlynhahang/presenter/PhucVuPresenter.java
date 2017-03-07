package thanggun99.quanlynhahang.presenter;

import android.text.TextUtils;

import java.util.ArrayList;

import thanggun99.quanlynhahang.interfaces.ShowOnMain;
import thanggun99.quanlynhahang.model.Database;
import thanggun99.quanlynhahang.model.entity.Ban;
import thanggun99.quanlynhahang.model.entity.DatBan;
import thanggun99.quanlynhahang.model.entity.HoaDon;
import thanggun99.quanlynhahang.model.entity.NhomMon;
import thanggun99.quanlynhahang.model.entity.ThucDon;
import thanggun99.quanlynhahang.model.entity.ThucDonOrder;
import thanggun99.quanlynhahang.model.PhucVuInteractor;
import thanggun99.quanlynhahang.util.Utils;

public class PhucVuPresenter implements PhucVuInteractor.OnPhucVuInteractorFinishListener {
    private ShowOnMain showOnMain;
    private PhucVuView phucVuView;
    private PhucVuInteractor phucVuInteractor;
    private Database database;

    public PhucVuPresenter(ShowOnMain showOnMain, Database database) {
        this.showOnMain = showOnMain;
        this.database = database;
        phucVuInteractor = new PhucVuInteractor(this, database);
    }

    //Asynctask
    @Override
    public void onStartTask() {
        showOnMain.showProgress();
    }

    @Override
    public void onFinishTask(Boolean isSuccess, String message) {
        showOnMain.hideProgress();
        phucVuView.showSnackbar(isSuccess, message);
    }


    //on click ban
    public void getThongTinbanAtPosition(int position) {
        phucVuInteractor.getThongTinbanAtPosition(position);
    }

    @Override
    public void onFinishGetThongTinBanTrong(Ban currentBan) {
        if (currentBan != null)
            phucVuView.showBanTrong(currentBan);
    }

    @Override
    public void onFinishGetThongTinBanDatBan(DatBan currentDatBan) {
        if (currentDatBan != null)
            phucVuView.showBanDatBan(currentDatBan);
    }

    @Override
    public void onFinishGetThongTinBanPV(HoaDon currentHoaDon) {
        if (currentHoaDon != null)
            phucVuView.showBanPhucVu(currentHoaDon);
    }


    //check connection
    private boolean checkConnect() {
        if (Utils.isConnectingToInternet()) {
            return true;
        } else {
            showOnMain.showConnectFailDialog();
            return false;
        }
    }

    //datban
    public void onClickDatBan(DatBan datBan) {
        if (checkConnect()) {

            phucVuInteractor.datBan(datBan);
        }
    }

    @Override
    public void onFinishDatBan(DatBan datBan) {
        phucVuView.notifyUpdateListBan(phucVuInteractor.getcurrentBan());
        phucVuView.showBanDatBan(datBan);
    }


    //on click nhom mon
    public void onClickNhomMon(int position) {
        NhomMon nhomMon = phucVuInteractor.getNhomMonAt(position);
        phucVuView.showNhomMon(nhomMon);
        phucVuView.notifyChangeListThucDon(phucVuInteractor.getListThucDonByMaLoai(nhomMon.getMaLoai()));

    }


    // tim thuc don theo ten
    public void findThucDon(String keyword) {
        if (!TextUtils.isEmpty(keyword)) {
            phucVuView.notifyChangeListThucDon(phucVuInteractor.getListThucDonByTenMon(keyword));
        }
    }


    //sale
    public void onClickSale() {
        phucVuView.showSaleDialog(phucVuInteractor.getcurrentHoaDon());
    }

    public void saleHoaDon(int sale) {
        if (checkConnect()) {
            phucVuInteractor.saleHoaDon(sale);
        }
    }

    @Override
    public void onFinishSale(HoaDon hoaDon) {
        phucVuView.showTongTien(hoaDon.getTongTien());
        phucVuView.showGiamGia(hoaDon.getGiamGia());
    }


    //huyban
    public void onClickHuyBan() {
        if (checkConnect()) {
            phucVuInteractor.huyBan();

        }

    }

    @Override
    public void onFinishHuyBan(Ban ban) {
        phucVuView.notifyUpdateListBan(ban);
        phucVuView.showBanTrong(ban);
    }


    //Tính tiền
    public void onClickTinhTien() {
        phucVuView.showTinhTienDialog(phucVuInteractor.getcurrentHoaDon());

    }

    public void tinhTien() {
        if (checkConnect()) {

            phucVuInteractor.tinhTien();
        }

    }

    @Override
    public void onFinishTinhTien(Ban ban) {
        onFinishHuyBan(ban);
    }


    //suaban
    public void onClickUpdateDatBan() {
        phucVuView.showFormUpdateDatBan(phucVuInteractor.getCurrentDatBan());
    }

    public void onClickUpdateDatBan(DatBan datBan) {
        if (checkConnect()) {

            phucVuInteractor.updateDatBan(datBan);
        }

    }

    @Override
    public void onFinishUpdateDatBan(DatBan datBan) {
        onFinishGetThongTinBanDatBan(datBan);
    }


    //order thuc don
    public void onClickThucdonOrder(ThucDonOrder thucDonOrder) {
        phucVuInteractor.setcurrentThucDon(thucDonOrder);
        phucVuInteractor.setCurrentThucDonOrder(thucDonOrder);
        phucVuView.showOrderThucDonDialog(phucVuInteractor.getcurrentBan().getTenBan(), thucDonOrder);
    }

    public void onClickThucdon(ThucDon thucDon) {
        phucVuInteractor.setcurrentThucDon(thucDon);
        phucVuView.showOrderThucDonDialog(phucVuInteractor.getcurrentBan().getTenBan(), thucDon);
    }

    public void orderThucDon(int soLuong) {
        if (checkConnect()) {

            phucVuInteractor.orderThucDon(soLuong);
        }
    }

    @Override
    public void onFinishOrderUpdateThucDon(int tongTien) {
        phucVuView.showTongTien(tongTien);
        phucVuView.notifyUpDateListThucDonOrder(phucVuInteractor.getCurrentThucDonOrder());
    }

    @Override
    public void onFinishOrderAddThucDon(int tongTien) {
        phucVuView.showTongTien(tongTien);
        phucVuView.notifyAddListThucDonOrder();
    }

    @Override
    public void onFinishOrderCreateHoaDon(HoaDon currentHoaDon) {
        phucVuView.showBanPhucVu(currentHoaDon);
        phucVuView.notifyUpdateListBan(currentHoaDon.getBan());

    }


    //delete thuc don order
    public void onClickDeleteMonOrder(ThucDonOrder thucDonOrder) {
        phucVuInteractor.setcurrentThucDon(thucDonOrder);
        phucVuInteractor.setCurrentThucDonOrder(thucDonOrder);
        phucVuView.showDeleteThucDonOrderDialog(phucVuInteractor.getcurrentBan().getTenBan(), thucDonOrder.getTenMon());

    }

    public void deleteThucDonOrder() {
        if (checkConnect()) {
            phucVuInteractor.deleteThucDonOrder();
        }
    }

    @Override
    public void onFinishDeleteThucDonOrder(int tongTien) {
        phucVuView.showTongTien(tongTien);
        phucVuView.notifyRemoveListThucDonOrder();
    }


    //thong tin dat ban
    public void onClickThongTinDatBan() {
        phucVuView.showThongTinDatBanDialog(phucVuInteractor.getcurrentHoaDon().getDatBan());
    }

    public void setPhucVuView(PhucVuView phucVuView) {
        this.phucVuView = phucVuView;
    }

    public Database getDatabase() {
        return database;
    }


    /*this is interface for phucVuView Phuc vu*/
    public interface PhucVuView {

        void showBanTrong(Ban ban);

        void showBanPhucVu(HoaDon hoaDon);

        void showTongTien(int tongTien);

        void showNhomMon(NhomMon nhomMon);

        void showGiamGia(int giamGia);

        void showBanDatBan(DatBan datBan);

        void showFormUpdateDatBan(DatBan datBan);

        void showOrderThucDonDialog(String tenBan, ThucDon thucDon);

        void notifyAddListThucDonOrder();

        void notifyUpDateListThucDonOrder(ThucDonOrder thucDonOrder);

        void notifyUpdateListBan(Ban ban);

        void showDeleteThucDonOrderDialog(String tenBan, String tenMon);

        void notifyRemoveListThucDonOrder();

        void showThongTinDatBanDialog(DatBan datBan);

        void notifyChangeListThucDon(ArrayList<ThucDon> thucDons);

        void showSaleDialog(HoaDon hoaDon);

        void showTinhTienDialog(HoaDon hoaDon);

        void showSnackbar(Boolean isError, String message);

    }
}
