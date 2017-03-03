package thanggun99.quanlynhahang.presenter.phucvu;

import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;

import thanggun99.quanlynhahang.model.entity.Ban;
import thanggun99.quanlynhahang.model.entity.DatTruoc;
import thanggun99.quanlynhahang.model.entity.HoaDon;
import thanggun99.quanlynhahang.model.entity.NhomMon;
import thanggun99.quanlynhahang.model.entity.ThucDon;
import thanggun99.quanlynhahang.model.phucvu.MainPhucVuInteractor;

/**
 * Created by Thanggun99 on 04/12/2016.
 */

public class PhucVuPresenter implements MainPhucVuInteractor.OnMainPVFinishProgress {
    private PhucVuView phucVuView;
    private MainPhucVuInteractor mainPhucVuInteractor;

    public PhucVuPresenter(PhucVuView phucVuView, Context context) {
        this.phucVuView = phucVuView;
        mainPhucVuInteractor = new MainPhucVuInteractor(this, context);
    }

    public void loadDatas() {
        if (mainPhucVuInteractor.checkConnect()){
            phucVuView.showProgress();
            mainPhucVuInteractor.loadDatas();
        }
        else {
            phucVuView.showDialogConnectFail();
        }
    }

    public void onClickDatBan(DatTruoc datTruoc) {
        if (mainPhucVuInteractor.checkConnect()){
            mainPhucVuInteractor.datBan(datTruoc);
        }
        else {
            phucVuView.showDialogConnectFail();
        }
    }

    @Override
    public void onChangeNhomMon(NhomMon nhomMon) {
        phucVuView.showNhomMon(nhomMon);
    }

    @Override
    public void onFinishGetDatas(ArrayList<Ban> listBan, ArrayList<ThucDon> listThucDon, ArrayList<NhomMon> listNhomMon) {
        phucVuView.hideProgress();
        phucVuView.showDatas(listBan, listThucDon, listNhomMon);
    }

    @Override
    public void onGetDatasFail() {
        phucVuView.showDialogGetDatasFail();
    }

    @Override
    public void onFinishHuyBan(Ban ban) {
        phucVuView.showBanTrong(ban);
    }

    @Override
    public void onFinishGetThongTinBanTrong(Ban currentBan) {
        if (currentBan != null) phucVuView.showBanTrong(currentBan);
    }

    @Override
    public void onFinishGetThongTinBanDatTruoc(DatTruoc currentDatTruoc) {
        if (currentDatTruoc != null) phucVuView.showBanDatTruoc(currentDatTruoc);
    }

    @Override
    public void onFinishGetThongTinBanPV(HoaDon currentHoaDon) {
        if (currentHoaDon != null) phucVuView.showBanPhucVu(currentHoaDon);
    }

    @Override
    public void onChangeGiamGia(int giamGia) {
        phucVuView.showGiamGia(giamGia);
    }

    @Override
    public void onFinishHuyDatBan(Ban ban) {
        if (ban != null) phucVuView.showBanTrong(ban);
    }

    @Override
    public void onFinishDatBan(DatTruoc datTruoc) {
        if (datTruoc != null) phucVuView.showBanDatTruoc(datTruoc);
    }

    @Override
    public void onChangeTongTien(int tongTien) {
        phucVuView.showTongTien(tongTien);
    }

    public void onClickNhomMon(int position) {
        mainPhucVuInteractor.getNhomMon(position);
    }

    public void findThucDon(String keyword) {
        if (!TextUtils.isEmpty(keyword)) mainPhucVuInteractor.findThucDon(keyword);
    }

    public void onClickSale() {
        mainPhucVuInteractor.showDialogSale();
    }

    public void onClickHuyBan() {
        if (mainPhucVuInteractor.checkConnect()){
            mainPhucVuInteractor.huyBan();
        }
        else {
            phucVuView.showDialogConnectFail();
        }
    }

    public void onClickTinhTien() {
        mainPhucVuInteractor.showDialogTinhTien();
    }

    public void onDestroy() {
        mainPhucVuInteractor.destroy();
    }

    public void onClickSuaDatBan() {
        DatTruoc datTruoc = mainPhucVuInteractor.getCurrentDatTruoc();
        if (datTruoc != null) phucVuView.showFormUpdateDatBan(datTruoc);
    }

    public void onClickCapNhatDatBan(DatTruoc datTruoc) {
        if (mainPhucVuInteractor.checkConnect()){
            mainPhucVuInteractor.updateDatBan(datTruoc);
        }
        else {
            phucVuView.showDialogConnectFail();
        }
    }

    public void onClickThongTinDatBan() {
        mainPhucVuInteractor.showDialogThongTinDatBan();
    }

    public void getThongTinbanAtPosition(int position) {
        mainPhucVuInteractor.getThongTinbanAtPosition(position);
    }

    public void orderThucDon(int soLuong) {
        mainPhucVuInteractor.orderThucDon(soLuong);
    }

    /*this is interface for phucVuView Phuc vu*/
    public interface PhucVuView {
        void showProgress();

        void hideProgress();

        void showDialogConnectFail();

        void showBanTrong(Ban ban);

        void showBanPhucVu(HoaDon hoaDon);

        void showTongTien(int tongTien);

        void showNhomMon(NhomMon nhomMon);

        void showGiamGia(int giamGia);

        void showBanDatTruoc(DatTruoc datTruoc);

        void showFormUpdateDatBan(DatTruoc datTruoc);

        void showDialogGetDatasFail();

        void showDatas(ArrayList<Ban> listBan, ArrayList<ThucDon> listThucDon, ArrayList<NhomMon> listNhomMon);
    }
}
