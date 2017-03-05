package thanggun99.quanlynhahang.presenter.phucvu;

import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;

import thanggun99.quanlynhahang.model.entity.Ban;
import thanggun99.quanlynhahang.model.entity.DatBan;
import thanggun99.quanlynhahang.model.entity.HoaDon;
import thanggun99.quanlynhahang.model.entity.NhomMon;
import thanggun99.quanlynhahang.model.entity.ThucDon;
import thanggun99.quanlynhahang.model.entity.ThucDonOrder;
import thanggun99.quanlynhahang.model.phucvu.PhucVuInteractor;

public class PhucVuPresenter implements PhucVuInteractor.OnPhucVuInteractorFinishListener {
    private PhucVuView phucVuView;
    private PhucVuInteractor phucVuInteractor;

    public PhucVuPresenter(PhucVuView phucVuView, Context context) {
        this.phucVuView = phucVuView;
        phucVuInteractor = new PhucVuInteractor(this);
    }


    //Asynctask
    @Override
    public void onStartTask() {
        phucVuView.showProgress();
    }

    @Override
    public void onFinishTask(Boolean isSuccess, String message) {
        phucVuView.hideProgress();
        phucVuView.showSnackbar(isSuccess, message);
    }


    //getdatas
    public void loadDatas() {
        if (phucVuInteractor.checkConnect()) {
            phucVuInteractor.loadDatas();
        } else {

            phucVuView.showGetDatasFailDialog();
            phucVuView.showConnectFailDialog();
        }
    }

    @Override
    public void onFinishGetDatas(ArrayList<Ban> listBan, ArrayList<ThucDon> listThucDon, ArrayList<NhomMon> listNhomMon) {
        phucVuView.hideGetDatasFailDialog();
        phucVuView.showDatas(listBan, listThucDon, listNhomMon);
    }

    @Override
    public void onGetDatasFail() {
        phucVuView.showGetDatasFailDialog();
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


    //on destroy
    public void onDestroy() {
        phucVuInteractor.destroy();
    }


    //datban
    public void onClickDatBan(DatBan datBan) {
        if (phucVuInteractor.checkConnect()) {
            phucVuInteractor.datBan(datBan);
        } else {
            phucVuView.showConnectFailDialog();
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
        if (phucVuInteractor.checkConnect()) {
            phucVuInteractor.saleHoaDon(sale);
        } else {
            phucVuView.showConnectFailDialog();
        }
    }

    @Override
    public void onFinishSale(HoaDon hoaDon) {
        phucVuView.hideSaleDialog();
        phucVuView.showTongTien(hoaDon.getTongTien());
        phucVuView.showGiamGia(hoaDon.getGiamGia());
    }


    //huyban
    public void onClickHuyBan() {
        if (phucVuInteractor.checkConnect()) {
            phucVuInteractor.huyBan();
        } else {
            phucVuView.showConnectFailDialog();
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
        if (phucVuInteractor.checkConnect()) {
            phucVuInteractor.tinhTien();
        } else {
            phucVuView.showConnectFailDialog();
        }
    }

    @Override
    public void onFinishTinhTien(Ban ban) {
        phucVuView.hideTinhTienDialog();
        onFinishHuyBan(ban);
    }


    //suaban
    public void onClickUpdateDatBan() {
        phucVuView.showFormUpdateDatBan(phucVuInteractor.getCurrentDatBan());
    }

    public void onClickUpdateDatBan(DatBan datBan) {
        if (phucVuInteractor.checkConnect()) {
            phucVuInteractor.updateDatBan(datBan);
        } else {
            phucVuView.showConnectFailDialog();
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
        if (phucVuInteractor.checkConnect()) {
            phucVuInteractor.orderThucDon(soLuong);
        } else {
            phucVuView.showConnectFailDialog();
        }
    }

    @Override
    public void onFinishOrderUpdateThucDon(int tongTien) {
        phucVuView.hideOrderThucDonDialog();
        phucVuView.showTongTien(tongTien);
        phucVuView.notifyUpDateListThucDonOrder(phucVuInteractor.getCurrentThucDonOrder());
    }

    @Override
    public void onFinishOrderAddThucDon(int tongTien) {
        phucVuView.hideOrderThucDonDialog();
        phucVuView.showTongTien(tongTien);
        phucVuView.notifyAddListThucDonOrder();
    }

    @Override
    public void onFinishOrderCreateHoaDon(HoaDon currentHoaDon) {
        phucVuView.hideOrderThucDonDialog();
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
        if (phucVuInteractor.checkConnect()) {
            phucVuInteractor.deleteThucDonOrder();
        } else {
            phucVuView.showConnectFailDialog();
        }
    }

    @Override
    public void onFinishDeleteThucDonOrder(int tongTien) {
        phucVuView.hideDeleteThucDonOrderDialog();
        phucVuView.notifyRemoveListThucDonOrder();

    }


    //thong tin dat ban
    public void onClickThongTinDatBan() {
        phucVuView.showThongTinDatBanDialog(phucVuInteractor.getcurrentHoaDon().getDatBan());
    }


    /*this is interface for phucVuView Phuc vu*/
    public interface PhucVuView {
        void showProgress();

        void hideProgress();

        void showConnectFailDialog();

        void showBanTrong(Ban ban);

        void showBanPhucVu(HoaDon hoaDon);

        void showTongTien(int tongTien);

        void showNhomMon(NhomMon nhomMon);

        void showGiamGia(int giamGia);

        void showBanDatBan(DatBan datBan);

        void showFormUpdateDatBan(DatBan datBan);

        void showGetDatasFailDialog();

        void showDatas(ArrayList<Ban> listBan, ArrayList<ThucDon> listThucDon, ArrayList<NhomMon> listNhomMon);

        void showOrderThucDonDialog(String tenBan, ThucDon thucDon);

        void hideOrderThucDonDialog();

        void notifyAddListThucDonOrder();

        void notifyUpDateListThucDonOrder(ThucDonOrder thucDonOrder);

        void notifyUpdateListBan(Ban ban);

        void showDeleteThucDonOrderDialog(String tenBan, String tenMon);

        void hideDeleteThucDonOrderDialog();

        void notifyRemoveListThucDonOrder();

        void showThongTinDatBanDialog(DatBan datBan);

        void notifyChangeListThucDon(ArrayList<ThucDon> thucDons);

        void showSaleDialog(HoaDon hoaDon);

        void hideSaleDialog();

        void showTinhTienDialog(HoaDon hoaDon);

        void hideGetDatasFailDialog();

        void showSnackbar(Boolean isError, String message);

        void hideTinhTienDialog();

    }
}
