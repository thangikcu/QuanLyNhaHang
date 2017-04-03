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
import thanggun99.quanlynhahang.model.entity.Mon;
import thanggun99.quanlynhahang.model.entity.MonOrder;
import thanggun99.quanlynhahang.model.entity.NhomMon;
import thanggun99.quanlynhahang.model.entity.YeuCau;
import thanggun99.quanlynhahang.util.Utils;

public class PhucVuPresenter implements PhucVuInteractor.OnPhucVuInteractorFinishListener {

    private ShowOnMain showOnMain;
    private PhucVuView phucVuView;
    private DatBanView datBanView;
    private YeuCauView yeuCauView;
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
    public void datBanService(DatBan datBan) {
        phucVuInteractor.datBanService(datBan);
        if (datBan.getBan() != null) {

            if (phucVuView != null) {
                phucVuView.notifyUpdateListBan(datBan.getBan());
                if (phucVuInteractor.getcurrentBan().getMaBan() == datBan.getBan().getMaBan()) {
                    phucVuView.showBanDatBan(datBan);
                }
            }
        } else {

            if (datBanView != null) {
                datBanView.clearFormDatBan();
                phucVuInteractor.setCurrentDatBanChuaSetBan(datBan);
                datBanView.showThongTinDatBanChuaSetBan(phucVuInteractor.getCurrentDatBanChuaSetBan(),
                        getDatabase().getBanList());
                datBanView.notifyAddListDatBanChuaSetBan();
            }
        }

    }

    public void khachVaoBanService(DatBan datBanVaoBan) {
        DatBan datBan = getDatabase().getDatBanChuaSetBanByMa(datBanVaoBan.getMaDatBan());
        if (datBan != null) {
            datBan.setBan(getDatabase().getBanByMaBan(datBanVaoBan.getBan().getMaBan()));
            datBan.getBan().setTrangThai(Ban.DA_DAT_TRUOC);
            phucVuView.notifyUpdateListBan(datBan.getBan());

            if (datBanView != null) {
                datBanView.clearFormDatBan();
                datBanView.notifyRemoveListDatBanChuaSetBan(datBan);
            }
            if (phucVuInteractor.getcurrentBan().getMaBan() == datBan.getBan().getMaBan()) {
                phucVuView.showBanDatBan(datBan);
            }

            getDatabase().onKhachDatBanVaoBan(datBan);
        }
    }

    public void huyDatBanService(int maDatBan) {
        DatBan datBan;
        if (getDatabase().getDatBanChuaSetBanByMa(maDatBan) != null) {

            datBan = getDatabase().getDatBanChuaSetBanByMa(maDatBan);
            if (datBan != null) {
                if (datBanView != null) {
                    if (datBan.getKhachHang() != null) {
                        datBan.getKhachHang().setDatBan(null);
                    }
                    datBanView.notifyRemoveListDatBanChuaSetBan(datBan);
                    datBanView.clearFormDatBan();
                }
                getDatabase().removeDatBanChuaSetBan(datBan);
            }


        } else {
            datBan = getDatabase().getDatBanChuaTinhTienByMa(maDatBan);
            if (datBan != null) {
                if (phucVuView != null) {
                    if (datBan.getKhachHang() != null) {
                        datBan.getKhachHang().setDatBan(null);
                    }
                    datBan.getBan().setTrangThai(Ban.TRONG);
                    phucVuView.notifyUpdateListBan(datBan.getBan());

                    if (phucVuInteractor.getcurrentBan().getMaBan() == datBan.getBan().getMaBan()) {
                        phucVuInteractor.setCurrentDatBan(null);
                        phucVuInteractor.setCurrentHoaDon(null);
                        phucVuView.showBanTrong(phucVuInteractor.getcurrentBan());
                    }
                }
                getDatabase().removeDatBanChuaTinhTien(datBan);
            }

        }

    }

    public void updateDatBanService(DatBan datBanUpdate) {
        phucVuInteractor.updateDatBanService(datBanUpdate);

        if (getDatabase().getDatBanChuaSetBanByMa(datBanUpdate.getMaDatBan()) != null) {
            if (datBanView != null) {
                datBanView.clearFormDatBan();
                datBanView.showThongTinDatBanChuaSetBan(phucVuInteractor.getCurrentDatBanChuaSetBan(),
                        getDatabase().getBanList());
                datBanView.notifyUpdateListDatBanChuaSetBan(phucVuInteractor.getCurrentDatBanChuaSetBan());
            }
        } else {
            if (phucVuInteractor.getCurrentDatBan() != null
                    && phucVuInteractor.getCurrentDatBan().getMaDatBan()
                    == datBanUpdate.getMaDatBan()) {

                phucVuView.showBanDatBan(phucVuInteractor.getCurrentDatBan());
            }
        }

    }

    public void khachHangYeuCauService(YeuCau yeuCauNew) {
        yeuCauNew.setKhachHang(getDatabase().getKhachHangByMa(yeuCauNew.getKhachHang().getMaKhachHang()));
        getDatabase().setDataForYeuCau(yeuCauNew);

        YeuCau yeuCau = getDatabase().getYeuCauByMaKhachHang(yeuCauNew.getKhachHang().getMaKhachHang());
        if (yeuCau != null) {

            yeuCau.update(yeuCauNew);
            yeuCauView.notifyYeuCauChange(yeuCau);

        } else {

            getDatabase().addYeuCau(yeuCauNew);
            yeuCauView.notifyAddYeuCau();
            showOnMain.showFloatButton();
            showOnMain.updateFloatButton(getDatabase().getYeuCauList().size());
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
        phucVuView.notifyChangeListMon(getDatabase().getListMonByMaLoai(nhomMon.getMaLoai()));

    }


    // tim thuc don theo ten
    public void findMon(String keyWord) {
        if (!TextUtils.isEmpty(keyWord)) {
            phucVuView.notifyChangeListMon(getDatabase().getListMonByTenMon(keyWord));
        }
    }


    //sale
    public void onClickSale() {
        phucVuView.showSaleDialog(phucVuInteractor.getCurrentHoaDon());
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
        phucVuView.showTinhTienDialog(phucVuInteractor.getCurrentHoaDon());

    }

    public void tinhTien() {
        if (checkConnect()) {

            phucVuInteractor.tinhTien();
        }
    }

    @Override
    public void onFinishTinhTien(Ban ban) {
        onFinishHuyBan(ban);
        if (yeuCauView != null) {

            yeuCauView.notifyDatachange();
        }

        showOnMain.updateFloatButton(getDatabase().getYeuCauList().size());
    }


    //view dat ban chua set ban
    public void onClickDatBanChuaSetBanItem(DatBan datBan) {
        phucVuInteractor.setCurrentDatBanChuaSetBan(datBan);
        datBanView.showThongTinDatBanChuaSetBan(datBan, getDatabase().getBanList());
    }

    //khach vao ban
    public void khachDatBanVaoBan(Ban ban) {
        if (ban.getTrangThai() == Ban.TRONG) {
            DatBan datBan = phucVuInteractor.getCurrentDatBanChuaSetBan();
            datBan.setBan(ban);

            if (checkConnect()) {

                phucVuInteractor.khachDatBanVaoBan(datBan);
            }
        } else {
            datBanView.showSnackbar(Utils.getStringByRes(R.string.chon_ban_khac));
        }

    }

    @Override
    public void onFinishKhachDatBanVaoBan() {
        datBanView.clearFormDatBan();
        datBanView.notifyRemoveListDatBanChuaSetBan(null);
        showOnMain.showPhucVu();
        phucVuView.notifyChangeSelectBan(phucVuInteractor.getcurrentBan());
        phucVuView.showBanDatBan(phucVuInteractor.getCurrentDatBan());
    }

    @Override
    public void onKhachDatBanVaoBanFail() {
        datBanView.showSnackbar(Utils.getStringByRes(R.string.da_xay_ra_loi));
    }


    //sua dat ban chua set ban
    public void onClickUpdateDatBanChuaSetBan(DatBan datBan) {
        phucVuInteractor.setCurrentDatBanChuaSetBan(datBan);
        datBanView.clearFormDatBan();
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
    public void onClickMonOrder(MonOrder monOrder) {
        phucVuInteractor.setcurrentMon(monOrder);
        phucVuInteractor.setCurrentMonOrder(monOrder);
        phucVuView.showOrderMonDialog(phucVuInteractor.getcurrentBan().getTenBan(), monOrder);
    }

    public void onClickMon(Mon mon) {
        phucVuInteractor.setcurrentMon(mon);
        phucVuView.showOrderMonDialog(phucVuInteractor.getcurrentBan().getTenBan(), mon);
    }

    public void orderMon(int soLuong) {
        if (checkConnect()) {
            phucVuView.closeThucDonLayout();
            phucVuInteractor.orderMon(soLuong);
        }
    }

    @Override
    public void onFinishOrderUpdateMon(int tongTien) {
        phucVuView.showTongTien(tongTien);
        phucVuView.notifyUpDateListMonOrder(phucVuInteractor.getCurrentMonOrder());
    }

    @Override
    public void onFinishOrderAddMon(int tongTien) {
        phucVuView.showTongTien(tongTien);
        phucVuView.notifyAddListMonOrder();
    }

    @Override
    public void onFinishOrderCreateHoaDon(HoaDon currentHoaDon) {
        phucVuView.showBanPhucVu(currentHoaDon);
        phucVuView.notifyUpdateListBan(currentHoaDon.getBan());

    }


    //delete thuc don order
    public void onClickDeleteMonOrder(MonOrder monOrder) {
        phucVuInteractor.setcurrentMon(monOrder);
        phucVuInteractor.setCurrentMonOrder(monOrder);
        phucVuView.showConfirmDialog(phucVuInteractor.getcurrentBan().getTenBan(), monOrder.getTenMon());

    }

    public void deleteMonOrder() {
        if (checkConnect()) {
            phucVuInteractor.deleteMonOrder();
        }
    }

    @Override
    public void onFinishDeleteMonOrder(int tongTien) {
        phucVuView.showTongTien(tongTien);
        phucVuView.notifyRemoveListMonOrder();
    }


    //thong tin dat ban
    public void onClickThongTinDatBan() {
        phucVuView.showThongTinDatBanDialog(phucVuInteractor.getCurrentHoaDon().getDatBan());
    }

    public void setPhucVuView(PhucVuView phucVuView) {
        this.phucVuView = phucVuView;
    }

    public Database getDatabase() {
        return phucVuInteractor.getDatabase();
    }

    public void getThongTinBanAt(int position) {
        Ban ban = getDatabase().getBanList().get(position);
        ban.setSelected(true);
        phucVuInteractor.getThongTinBan(ban);
    }

    public void findDatBan(String keyWord) {
        datBanView.notifyChangeListDatBanChuaTinhTien(getDatabase().getListDatBanChuaSetBanByTenKhachHang(keyWord));
    }

    public void onClickYeuCau(YeuCau yeuCau) {
        phucVuInteractor.setCurrentYeuCau(yeuCau);

        if (yeuCau.getType() == YeuCau.TINH_TIEN_HOA_DON) {
            showOnMain.showPhucVu();

            HoaDon hoaDon = getDatabase().getHoaDonChuaTinhTienByMa(yeuCau.getMaHoaDon());

            if (hoaDon != null) {
                Ban ban = hoaDon.getBan();
                showOnMain.hideKhachHangYeuCauDialog();

                phucVuInteractor.getThongTinBan(ban);
                phucVuView.notifyChangeSelectBan(ban);
            }

        } else {
            yeuCauView.showChiTietYeuCau(yeuCau);

            HoaDon hoaDon = getDatabase().getHoaDonChuaTinhTienByMa(yeuCau.getMaHoaDon());

            if (hoaDon != null) {
                yeuCauView.showBan(hoaDon.getBan());
            } else {

                DatBan datBan = yeuCau.getKhachHang().getDatBan();

                if (datBan != null && datBan.getBan() != null) {
                    yeuCauView.showBan(datBan.getBan());
                } else {
                    yeuCauView.setBanList(getDatabase().getBanList());
                }
            }
        }
    }

    public void khachHangOrderMon(Ban ban) {

        showOnMain.showPhucVu();

        YeuCau yeuCau = phucVuInteractor.getCurrentYeuCau();

        HoaDon hoaDon = getDatabase().getHoaDonChuaTinhTienByMa(yeuCau.getMaHoaDon());

        if (hoaDon != null) {
            ban = hoaDon.getBan();
        } else {

            DatBan datBan = yeuCau.getKhachHang().getDatBan();

            if (datBan != null && datBan.getBan() != null) {

                ban = datBan.getBan();

            }
        }
        phucVuView.notifyChangeSelectBan(ban);
        phucVuInteractor.getThongTinBan(ban);

        if (checkConnect()) {
            if (yeuCau.getType() == YeuCau.HOA_DON_MOI) {

                phucVuInteractor.taoHoaDonMoiKhachHangYeuCau();

            } else if (yeuCau.getType() == YeuCau.THEM_MON) {

                phucVuInteractor.orderMonKhachHangYeuCau();
            }
        }
    }

    @Override
    public void onFinishTaoMoiHoaDonKhachHangYeuCau() {
        yeuCauView.notifyRemoveYeuCau(phucVuInteractor.getCurrentYeuCau());

        showOnMain.updateFloatButton(getDatabase().getYeuCauList().size());

        DatBan datBan = phucVuInteractor.getCurrentYeuCau().getKhachHang().getDatBan();
        if (datBan != null) {
            if (datBan.getBan() == null) {

                if (datBanView != null) {
                    datBanView.notifyRemoveListDatBanChuaSetBan(datBan);
                    datBanView.clearFormDatBan();
                }
            }
        }
    }

    @Override
    public void onFinishOrderMonKhachHangYeuCau() {
        yeuCauView.notifyRemoveYeuCau(phucVuInteractor.getCurrentYeuCau());

        showOnMain.updateFloatButton(getDatabase().getYeuCauList().size());

        phucVuView.notifyChangeListMonOrder();
        phucVuView.showTongTien(phucVuInteractor.getCurrentHoaDon().getTongTien());
    }


    public void setDatBanView(DatBanView datBanView) {
        this.datBanView = datBanView;
    }

    public void setYeuCauView(YeuCauView yeuCauView) {
        this.yeuCauView = yeuCauView;
    }


    public interface YeuCauView {

        void notifyYeuCauChange(YeuCau yeuCau);

        void notifyAddYeuCau();

        void showChiTietYeuCau(YeuCau yeuCau);

        void setBanList(ArrayList<Ban> banList);

        void showBan(Ban ban);

        void notifyRemoveYeuCau(YeuCau yeuCau);

        void notifyDatachange();
    }


    public interface PhucVuView {

        void showBanTrong(Ban ban);

        void showBanPhucVu(HoaDon hoaDon);

        void showTongTien(int tongTien);

        void showNhomMon(NhomMon nhomMon);

        void showGiamGia(int giamGia);

        void showBanDatBan(DatBan datBan);

        void showFormUpdateDatBanSetBan(DatBan datBan);

        void showOrderMonDialog(String tenBan, Mon mon);

        void notifyAddListMonOrder();

        void notifyUpDateListMonOrder(MonOrder currentMonOrder);

        void notifyUpdateListBan(Ban ban);

        void showConfirmDialog(String tenBan, String tenMon);

        void notifyRemoveListMonOrder();

        void showThongTinDatBanDialog(DatBan datBan);

        void notifyChangeListMon(ArrayList<Mon> mons);

        void showSaleDialog(HoaDon hoaDon);

        void showTinhTienDialog(HoaDon hoaDon);

        void showSnackbar(Boolean isError, String message);

        void clearTimKiem();

        void closeThucDonLayout();

        void notifyChangeSelectBan(Ban ban);

        void showError(String message);

        void notifyChangeListMonOrder();
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
