package thanggun99.quanlynhahang.presenter.phucvu;

import android.content.Context;
import android.text.TextUtils;

import thanggun99.quanlynhahang.model.entity.DatTruoc;
import thanggun99.quanlynhahang.model.entity.NhomMon;
import thanggun99.quanlynhahang.view.adapter.BanAdapter;
import thanggun99.quanlynhahang.view.adapter.NhomMonAdapter;
import thanggun99.quanlynhahang.view.adapter.ThucDonAdapter;
import thanggun99.quanlynhahang.view.adapter.ThucDonOrderAdapter;
import thanggun99.quanlynhahang.model.entity.Ban;
import thanggun99.quanlynhahang.model.entity.HoaDon;
import thanggun99.quanlynhahang.model.phucvu.MainPhucVuManager;

/**
 * Created by Thanggun99 on 04/12/2016.
 */

public class PhucVuPresenter implements MainPhucVuManager.OnMainPVFinishProgress {
    private PhucVuView view;
    private MainPhucVuManager mainPhucVuManager;

    public PhucVuPresenter(PhucVuView view, Context context) {
        this.view = view;
        mainPhucVuManager = new MainPhucVuManager(this, context);
    }

    public void loadDatas() {
        mainPhucVuManager.loadDatas();
    }

    public void onClickDatBan(DatTruoc datTruoc) {
        mainPhucVuManager.datBan(datTruoc);
    }

    @Override
    public void onChangeNhomMon(NhomMon nhomMon) {
        view.showNhomMon(nhomMon);
    }

    @Override
    public void onFinishGetDatas(BanAdapter banAdapter, ThucDonOrderAdapter thucDonOrderAdapter, ThucDonAdapter thucDonAdapter, NhomMonAdapter nhomMonAdapter) {
        view.showDatas(banAdapter, thucDonOrderAdapter, thucDonAdapter, nhomMonAdapter);
    }

    @Override
    public void onFinishHuyBan(Ban ban) {
        view.showBanTrong(ban);
    }

    @Override
    public void onFinishGetThongTinBanTrong(Ban ban) {
        if (ban != null) view.showBanTrong(ban);

    }

    @Override
    public void onFinishGetThongTinBanDatTruoc(DatTruoc datTruoc) {
        if (datTruoc != null) view.showBanDatTruoc(datTruoc);
    }

    @Override
    public void onFinishGetThongTinBanPV(HoaDon hoaDon) {
        if (hoaDon != null) view.showBanPhucVu(hoaDon);
    }

    @Override
    public void onChangeGiamGia(int giamGia) {
        view.showGiamGia(giamGia);
    }

    @Override
    public void onFinishHuyDatBan(Ban ban) {
        if (ban != null) view.showBanTrong(ban);
    }

    @Override
    public void onFinishDatBan(DatTruoc datTruoc) {
        if (datTruoc != null) view.showBanDatTruoc(datTruoc);
    }

    @Override
    public void onChangeTongTien(int tongTien) {
        view.showTongTien(tongTien);
    }

    public void onClickNhomMon(int position) {
        mainPhucVuManager.getNhomMon(position);
    }

    public void findThucDon(String keyword) {
        if (!TextUtils.isEmpty(keyword)) mainPhucVuManager.findThucDon(keyword);
    }

    public void onClickSale() {
        mainPhucVuManager.showDialogSale();
    }

    public void onClickHuyBan() {
        mainPhucVuManager.huyBan();
    }

    public void onClickTinhTien() {
        mainPhucVuManager.showDialogTinhTien();
    }

    public void onDestroy() {
        mainPhucVuManager.destroy();
    }

    public void onClickSuaDatBan() {
        DatTruoc datTruoc = mainPhucVuManager.getCurrentDatTruoc();
        if (datTruoc != null) view.showFormUpdateDatBan(datTruoc);
    }

    public void onClickCapNhatDatBan(DatTruoc datTruoc) {
        mainPhucVuManager.updateDatBan(datTruoc);
    }

    public void onClickThongTinDatBan() {
        mainPhucVuManager.showDialogThongTinDatBan();
    }

    /*this is interface for view Phuc vu*/
    public interface PhucVuView {

        void showDatas(BanAdapter banAdapter, ThucDonOrderAdapter thucDonOrderAdapter, ThucDonAdapter thucDonAdapter, NhomMonAdapter nhomMonAdapter);

        void showBanTrong(Ban ban);

        void showBanPhucVu(HoaDon hoaDon);

        void showTongTien(int tongTien);

        void showNhomMon(NhomMon nhomMon);

        void showGiamGia(int giamGia);

        void showBanDatTruoc(DatTruoc datTruoc);

        void showFormUpdateDatBan(DatTruoc datTruoc);
    }
}
