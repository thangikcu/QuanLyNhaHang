package thanggun99.quanlynhahang.presenter;

import android.text.TextUtils;

import java.util.ArrayList;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.interfaces.ShowOnMain;
import thanggun99.quanlynhahang.model.Database;
import thanggun99.quanlynhahang.model.PhucVuInteractor;
import thanggun99.quanlynhahang.model.entity.Ban;
import thanggun99.quanlynhahang.model.entity.DatBan;
import thanggun99.quanlynhahang.model.entity.HoaDon;
import thanggun99.quanlynhahang.model.entity.NhomMon;
import thanggun99.quanlynhahang.model.entity.ThucDon;
import thanggun99.quanlynhahang.model.entity.ThucDonOrder;
import thanggun99.quanlynhahang.util.Utils;

public class PhucVuPresenter implements PhucVuInteractor.OnPhucVuInteractorFinishListener {
    private ShowOnMain showOnMain;
    private PhucVuView phucVuView;
    private DatBanView datBanView;
    private PhucVuInteractor phucVuInteractor;

    public PhucVuPresenter(ShowOnMain showOnMain, Database database) {
        this.showOnMain = showOnMain;
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


    //receive broadcast
    public void khachHangDatBanService(DatBan datBan) {
        datBan.setKhachHang(getDatabase().getKhachHangByMa(datBan.getKhachHang().getMaKhachHang()));
        getDatabase().addDatBanChuaSetBan(datBan);
        if (datBanView != null) {
            datBanView.clearFormDatBan();
            phucVuInteractor.setCurrentDatBanChuaSetBan(datBan);
            datBanView.showThongTinDatBanChuaSetBan(phucVuInteractor.getCurrentDatBanChuaSetBan(),
                    getDatabase().getBanList());
            datBanView.notifyAddListDatBanChuaSetBan();
        }
    }

    public void khachHangHuyDatBanService(int maDatBan) {
        DatBan datBan = getDatabase().getDatBanChuaSetBanByMa(maDatBan);
        getDatabase().removeDatBanChuaSetBan(datBan);
        if (datBanView != null) {
            datBanView.notifyRemoveListDatBanChuaSetBan(datBan);
            datBanView.clearFormDatBan();
        }
    }

    public void updateDatBanService(DatBan datBanUpdate) {
        phucVuInteractor.updateDatBanService(datBanUpdate);

        if (datBanView != null) {
            datBanView.clearFormDatBan();
            datBanView.showThongTinDatBanChuaSetBan(phucVuInteractor.getCurrentDatBanChuaSetBan(),
                    getDatabase().getBanList());
            datBanView.notifyUpdateListDatBanChuaSetBan(phucVuInteractor.getCurrentDatBanChuaSetBan());
        }
    }

    //on click ban
    public void onClickBan(Ban ban) {
        phucVuInteractor.getThongTinBan(ban);
    }

    @Override
    public void onFinishGetThongTinBanTrong(Ban currentBan) {
        if (currentBan != null)
            phucVuView.showBanTrong(currentBan);
    }

    @Override
    public void onFinishGetThongTinBanDatBan(DatBan currentDatBan) {
        if (currentDatBan != null) {
            phucVuView.showBanDatBan(currentDatBan);
        }

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

    //dat ban chua set ban
    public void onClickDatBanChuaSetBan(DatBan datBan) {
        if (checkConnect()) {

            phucVuInteractor.datBanChuaSetBan(datBan);
        }
    }

    @Override
    public void onFinishDatBanChuaSetBan() {
        datBanView.notifyAddListDatBanChuaSetBan();
        datBanView.clearFormDatBan();
    }

    @Override
    public void onDatBanChuaSetBanFail() {
        datBanView.showSnackbar(Utils.getStringByRes(R.string.dat_ban_that_bai));
    }


    //dat ban set ban
    public void onClickDatBanSetBan(DatBan datBan) {
        if (checkConnect()) {
            datBan.setBan(phucVuInteractor.getcurrentBan());
            phucVuInteractor.datBanSetBan(datBan);
        }
    }

    @Override
    public void onFinishDatBanSetBan() {
        phucVuView.notifyUpdateListBan(phucVuInteractor.getcurrentBan());
        phucVuView.showBanDatBan(phucVuInteractor.getCurrentDatBan());
    }


    //on click nhom mon
    public void onClickNhomMon(NhomMon nhomMon) {
        phucVuView.clearTimKiem();
        phucVuView.showNhomMon(nhomMon);
        phucVuView.notifyChangeListThucDon(getDatabase().getListThucDonByMaLoai(nhomMon.getMaLoai()));

    }


    // tim thuc don theo ten
    public void findThucDon(String keyWord) {
        if (!TextUtils.isEmpty(keyWord)) {
            phucVuView.notifyChangeListThucDon(getDatabase().getListThucDonByTenMon(keyWord));
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


    //huy dat ban chua set ban
    public void onClickDeleteDatBanChuaSetBan(DatBan datBan) {
        phucVuInteractor.setCurrentDatBanChuaSetBan(datBan);
        datBanView.showConfirmDialog();
    }

    public void huyDatBanChuaSetBan() {
        if (checkConnect()) {
            phucVuInteractor.huyDatBanChuaSetBan();

        }
    }

    @Override
    public void onFinishHuyDatBanChuaSetBan() {
        datBanView.clearFormDatBan();
        datBanView.notifyRemoveListDatBanChuaSetBan(null);
    }

    @Override
    public void onHuyDatBanChuaSetBanFail() {
        datBanView.showSnackbar(Utils.getStringByRes(R.string.huy_dat_ban_that_bai));
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


    //view dat ban chua set ban
    public void onClickDatBanChuaSetBanItem(DatBan datBan) {
        phucVuInteractor.setCurrentDatBanChuaSetBan(datBan);
        datBanView.showThongTinDatBanChuaSetBan(datBan, getDatabase().getBanList());
    }

    //khach vao ban
    public void khachDatBanVaoBan(Ban ban) {
        if (checkConnect()) {
            DatBan datBan = phucVuInteractor.getCurrentDatBanChuaSetBan();
            datBan.setBan(ban);
            phucVuInteractor.khachDatBanVaoBan(datBan);
        }
    }

    @Override
    public void onFinishKhachDatBanVaoBan() {
        datBanView.clearFormDatBan();
        datBanView.notifyRemoveListDatBanChuaSetBan(null);
        showOnMain.showPhucVu();
        onFinishDatBanSetBan();
    }

    @Override
    public void onKhachDatBanVaoBanFail() {
        datBanView.showSnackbar(Utils.getStringByRes(R.string.da_xay_ra_loi));
    }



    //sua dat ban chua set ban
    public void onClickUpdateDatBanChuaSetBan(DatBan datBan) {
        phucVuInteractor.setCurrentDatBanChuaSetBan(datBan);
        datBanView.fillFormUpdateDatBanChuaSetBan(datBan);
    }


    public void updateDatBanChuaSetBan(DatBan datBan) {
        if (checkConnect()) {

            phucVuInteractor.updateDatBanChuaSetBan(datBan);
        }
    }

    @Override
    public void onFinishUpdateDatBanChuaSetBan() {
        datBanView.clearFormDatBan();
        datBanView.showThongTinDatBanChuaSetBan(phucVuInteractor.getCurrentDatBanChuaSetBan(),
                getDatabase().getBanList());
        datBanView.notifyUpdateListDatBanChuaSetBan(phucVuInteractor.getCurrentDatBanChuaSetBan());
    }

    @Override
    public void onUpdateDatBanChuaSetBanFail() {
        datBanView.showSnackbar(Utils.getStringByRes(R.string.cap_nhat_dat_ban_that_bai));
    }

    //sua dat ban da set ban
    public void onClickUpdateDatBanSetBan() {
        phucVuView.showFormUpdateDatBanSetBan(phucVuInteractor.getCurrentDatBan());
    }

    public void updateDatBanSetBan(DatBan datBan) {
        if (checkConnect()) {

            phucVuInteractor.updateDatBanSetBan(datBan);
        }

    }

    @Override
    public void onFinishUpdateDatBanSetBan() {
        onFinishGetThongTinBanDatBan(phucVuInteractor.getCurrentDatBan());
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
            phucVuView.closeThucDonLayout();
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
        phucVuView.showConfirmDialog(phucVuInteractor.getcurrentBan().getTenBan(), thucDonOrder.getTenMon());

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
        return phucVuInteractor.getDatabase();
    }

    public void getThongTinBanAt(int position) {
        Ban ban = getDatabase().getBanList().get(position);
        ban.setSelected(1);
        phucVuInteractor.getThongTinBan(ban);
    }

    public void findDatBan(String keyWord) {
        datBanView.notifyChangeListDatBanChuaTinhTien(getDatabase().getListDatBanChuaSetBanByTenKhachHang(keyWord));
    }

    public void setDatBanView(DatBanView datBanView) {
        this.datBanView = datBanView;
    }




    /*this is interface for phucVuView Phuc vu*/
    public interface PhucVuView {

        void showBanTrong(Ban ban);

        void showBanPhucVu(HoaDon hoaDon);

        void showTongTien(int tongTien);

        void showNhomMon(NhomMon nhomMon);

        void showGiamGia(int giamGia);

        void showBanDatBan(DatBan datBan);

        void showFormUpdateDatBanSetBan(DatBan datBan);

        void showOrderThucDonDialog(String tenBan, ThucDon thucDon);

        void notifyAddListThucDonOrder();

        void notifyUpDateListThucDonOrder(ThucDonOrder currentThucDonOrder);

        void notifyUpdateListBan(Ban ban);

        void showConfirmDialog(String tenBan, String tenMon);

        void notifyRemoveListThucDonOrder();

        void showThongTinDatBanDialog(DatBan datBan);

        void notifyChangeListThucDon(ArrayList<ThucDon> thucDons);

        void showSaleDialog(HoaDon hoaDon);

        void showTinhTienDialog(HoaDon hoaDon);

        void showSnackbar(Boolean isError, String message);

        void clearTimKiem();

        void closeThucDonLayout();

    }

    public interface DatBanView {
        void notifyChangeListDatBanChuaTinhTien(ArrayList<DatBan> datBanList);

        void notifyAddListDatBanChuaSetBan();

        void clearFormDatBan();

        void showSnackbar(String message);

        void showConfirmDialog();

        void notifyRemoveListDatBanChuaSetBan(DatBan datBan);

        void fillFormUpdateDatBanChuaSetBan(DatBan datBan);

        void notifyUpdateListDatBanChuaSetBan(DatBan datBan);

        void showThongTinDatBanChuaSetBan(DatBan datBan, ArrayList<Ban> banList);

    }
}
