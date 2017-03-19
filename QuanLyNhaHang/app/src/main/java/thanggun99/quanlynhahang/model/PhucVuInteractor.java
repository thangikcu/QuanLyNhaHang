package thanggun99.quanlynhahang.model;

import android.os.AsyncTask;
import android.text.TextUtils;

import java.util.Date;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.model.entity.Ban;
import thanggun99.quanlynhahang.model.entity.DatBan;
import thanggun99.quanlynhahang.model.entity.HoaDon;
import thanggun99.quanlynhahang.model.entity.ThucDon;
import thanggun99.quanlynhahang.model.entity.ThucDonOrder;
import thanggun99.quanlynhahang.util.Utils;

public class PhucVuInteractor {
    private Database database;

    private OnPhucVuInteractorFinishListener onPhucVuInteractorFinishListener;

    //currentItem
    private Ban currentBan;
    private HoaDon currentHoaDon;
    private DatBan currentDatBan;
    private ThucDonOrder currentThucDonOrder;
    private ThucDon currentThucDon;
    private DatBan currentDatBanChuaSetBan;


    //constructor
    public PhucVuInteractor(OnPhucVuInteractorFinishListener onPhucVuInteractorFinishListener, Database database) {

        this.database = database;
        this.onPhucVuInteractorFinishListener = onPhucVuInteractorFinishListener;
    }


    public void getThongTinBan(Ban ban) {
        currentBan = ban;

        if (currentBan != null) {
            if (currentBan.getTrangThai() == 2) {

                currentHoaDon = database.getHoaDonChuaTinhTienByMaBan(currentBan.getMaBan());

                if (currentHoaDon != null && currentHoaDon.getDatBan() != null) {

                    currentDatBan = currentHoaDon.getDatBan();
                } else {

                    currentDatBan = null;
                }

                onPhucVuInteractorFinishListener.onFinishGetThongTinBanPV(currentHoaDon);
            } else if (currentBan.getTrangThai() == 1) {

                currentDatBan = database.getDatBanChuaTinhTienByMaBan(currentBan.getMaBan());
                currentHoaDon = null;
                onPhucVuInteractorFinishListener.onFinishGetThongTinBanDatBan(currentDatBan);
            } else {

                currentHoaDon = null;
                currentDatBan = null;
                onPhucVuInteractorFinishListener.onFinishGetThongTinBanTrong(currentBan);
            }
        }
    }

    public void orderThucDon(int soLuong) {
        ThucDonOrder thucDonOrder = new ThucDonOrder();
        thucDonOrder.setMaMon(currentThucDon.getMaMon());
        thucDonOrder.setSoLuong(soLuong);
        thucDonOrder.setThucDon(database.getThucDonByMaMon(currentThucDon.getMaMon()));

        if (currentBan.getTrangThai() == 2) {
            for (ThucDonOrder tdOrder : currentHoaDon.getThucDonOrders()) {
                if (tdOrder.getMaMon() == currentThucDon.getMaMon()) {
                    currentThucDonOrder = tdOrder;
                    new UpdateThucDonOrderTask(soLuong).execute();
                    return;
                }
            }
            new ThemThucDonOrderTask(thucDonOrder).execute();
        } else {
            new TaoMoiHoaDonTask(thucDonOrder).execute();
        }
    }

    public void updateDatBanService(DatBan datBanUpdate) {
        DatBan datBan;
        if (database.getDatBanChuaSetBanByMa(datBanUpdate.getMaDatBan()) != null) {
            datBan = database.getDatBanChuaSetBanByMa(datBanUpdate.getMaDatBan());
            this.currentDatBanChuaSetBan = datBan;
        } else {
            datBan = database.getDatBanChuaTinhTienByMa(datBanUpdate.getMaDatBan());
            if (currentDatBan.equals(datBan)) {
                currentDatBan = datBan;
            }
        }
        datBan.setYeuCau(datBanUpdate.getYeuCau());
        datBan.setGioDen(datBanUpdate.getGioDen());
        if (!TextUtils.isEmpty(datBanUpdate.getSoDienThoai())) {
            datBan.setTenKhachHang(datBanUpdate.getTenKhachHang());
            datBan.setSoDienThoai(datBanUpdate.getSoDienThoai());
        }

    }

    public void logout() {
        database.getAdmin().huyGhiNhoDangNhap();
    }


    private class UpdateThucDonOrderTask extends AsyncTask<Void, Void, Boolean> {
        private int soLuong;

        public UpdateThucDonOrderTask(int soLuong) {
            this.soLuong = soLuong;
        }

        @Override
        protected void onPreExecute() {
            onPhucVuInteractorFinishListener.onStartTask();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            delay(500);
            return currentThucDonOrder.updateThucDonOrder(soLuong);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                onPhucVuInteractorFinishListener.onFinishOrderUpdateThucDon(currentHoaDon.getTongTien());
            }
            onPhucVuInteractorFinishListener.onFinishTask(aBoolean, String.format(Utils.getStringByRes(R.string.pv_notify_order_thucdon),
                    currentBan.getTenBan(), soLuong, currentThucDonOrder.getTenMon()));
        }

    }

    private void delay(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class ThemThucDonOrderTask extends AsyncTask<Void, Void, Boolean> {

        private ThucDonOrder thucDonOrder;

        public ThemThucDonOrderTask(ThucDonOrder thucDonOrder) {
            this.thucDonOrder = thucDonOrder;
        }

        @Override
        protected void onPreExecute() {
            onPhucVuInteractorFinishListener.onStartTask();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            delay(500);
            return currentHoaDon.themThucDonOrder(thucDonOrder);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                onPhucVuInteractorFinishListener.onFinishOrderAddThucDon(currentHoaDon.getTongTien());
            }
            onPhucVuInteractorFinishListener.onFinishTask(aBoolean, String.format(Utils.getStringByRes(R.string.pv_notify_order_thucdon),
                    currentBan.getTenBan(), thucDonOrder.getSoLuong(), currentThucDon.getTenMon()));
        }

    }

    private class TaoMoiHoaDonTask extends AsyncTask<Void, Void, Boolean> {
        private ThucDonOrder thucDonOrder;

        private HoaDon hoaDonNew;

        public TaoMoiHoaDonTask(ThucDonOrder thucDonOrder) {
            this.thucDonOrder = thucDonOrder;

            hoaDonNew = new HoaDon();
            String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            if (currentDatBan != null)
                hoaDonNew.setDatBan(currentDatBan);
            hoaDonNew.setGioDen(Utils.formatDate(date));
            hoaDonNew.setBan(currentBan);
        }

        @Override
        protected void onPreExecute() {
            onPhucVuInteractorFinishListener.onStartTask();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            delay(500);
            return hoaDonNew.taoMoiHoaDon(thucDonOrder);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                currentHoaDon = hoaDonNew;
                database.addhoaDonChuaTinhTien(currentHoaDon);
                onPhucVuInteractorFinishListener.onFinishOrderCreateHoaDon(currentHoaDon);
            }
            onPhucVuInteractorFinishListener.onFinishTask(aBoolean, String.format(Utils.getStringByRes(R.string.pv_notify_hoa_don_moi),
                    currentBan.getTenBan(), thucDonOrder.getSoLuong(), currentThucDon.getTenMon()));
        }

    }

    public void datBanChuaSetBan(final DatBan datBan) {

        class DatBanChuaSetBanTask extends AsyncTask<Void, Void, Boolean> {

            @Override
            protected void onPreExecute() {
                onPhucVuInteractorFinishListener.onStartTask();
                super.onPreExecute();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                delay(500);
                return datBan.datBan();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    database.addDatBanChuaSetBan(datBan);
                    onPhucVuInteractorFinishListener.onFinishDatBanChuaSetBan();
                } else {
                    onPhucVuInteractorFinishListener.onDatBanChuaSetBanFail();
                }
                onPhucVuInteractorFinishListener.onFinishTask(false, null);
            }
        }
        new DatBanChuaSetBanTask().execute();

    }

    public void khachDatBanVaoBan(final DatBan datBan) {

        class KhachDatBanVaoBanTask extends AsyncTask<Void, Void, Boolean> {

            @Override
            protected void onPreExecute() {
                onPhucVuInteractorFinishListener.onStartTask();
                super.onPreExecute();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                delay(500);
                return datBan.KhachVaoBan();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    currentBan = datBan.getBan();
                    currentDatBan = datBan;
                    currentDatBan.setTrangThai(1);
                    currentDatBanChuaSetBan = null;
                    database.onKhachDatBanVaoBan(currentDatBan);
                    onPhucVuInteractorFinishListener.onFinishKhachDatBanVaoBan();
                } else {
                    currentDatBanChuaSetBan.setBan(null);
                    onPhucVuInteractorFinishListener.onKhachDatBanVaoBanFail();
                }
                onPhucVuInteractorFinishListener.onFinishTask(aBoolean,
                        String.format(Utils.getStringByRes(R.string.pv_notify_dat_ban), currentBan.getTenBan()));
            }
        }
        new KhachDatBanVaoBanTask().execute();

    }

    public void datBanSetBan(final DatBan datBan) {
        class DatBanSetBanTask extends AsyncTask<Void, Void, Boolean> {

            @Override
            protected void onPreExecute() {
                onPhucVuInteractorFinishListener.onStartTask();
                super.onPreExecute();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                delay(500);
                return datBan.datBan();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    currentDatBan = datBan;
                    database.addDatBanChuaTinhTien(currentDatBan);
                    onPhucVuInteractorFinishListener.onFinishDatBanSetBan();
                }
                onPhucVuInteractorFinishListener.onFinishTask(aBoolean,
                        String.format(Utils.getStringByRes(R.string.pv_notify_dat_ban), currentBan.getTenBan()));
            }
        }
        new DatBanSetBanTask().execute();
    }

    public void deleteThucDonOrder() {
        class DeleteThucDonOrderTask extends AsyncTask<Void, Void, Boolean> {
            private String tenMon;

            @Override
            protected void onPreExecute() {
                tenMon = currentThucDonOrder.getTenMon();
                onPhucVuInteractorFinishListener.onStartTask();
                super.onPreExecute();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                delay(500);
                return currentHoaDon.deleteThucDonOrder(currentThucDonOrder.getMaChitietHD());
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    currentThucDonOrder = null;
                    onPhucVuInteractorFinishListener.onFinishDeleteThucDonOrder(currentHoaDon.getTongTien());
                }
                onPhucVuInteractorFinishListener.onFinishTask(aBoolean, String.format(Utils.getStringByRes(R.string.pv_notify_delete_thucdon_order),
                        currentBan.getTenBan(), tenMon));
            }
        }
        new DeleteThucDonOrderTask().execute();
    }

    public void saleHoaDon(final int presentSale) {
        class SaleHoaDonTask extends AsyncTask<Void, Void, Boolean> {

            @Override
            protected void onPreExecute() {
                onPhucVuInteractorFinishListener.onStartTask();
                super.onPreExecute();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                delay(500);
                return currentHoaDon.saleHoaDon(presentSale);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    onPhucVuInteractorFinishListener.onFinishSale(currentHoaDon);
                }
                onPhucVuInteractorFinishListener.onFinishTask(
                        aBoolean, String.format(Utils.getStringByRes(R.string.pv_notify_sale), currentBan.getTenBan(), currentHoaDon.getGiamGia()));
            }
        }
        new SaleHoaDonTask().execute();
    }

    public void tinhTien() {
        class TinhTienTask extends AsyncTask<Void, Void, Boolean> {
            private int tongTien;

            @Override
            protected void onPreExecute() {
                tongTien = currentHoaDon.getTongTien();
                onPhucVuInteractorFinishListener.onStartTask();
                super.onPreExecute();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                delay(500);
                return currentHoaDon.tinhTien();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    database.onTinhTienHoaDon(currentHoaDon);

                    currentHoaDon = null;
                    currentThucDon = null;
                    currentDatBan = null;
                    currentThucDonOrder = null;
                    onPhucVuInteractorFinishListener.onFinishTinhTien(currentBan);
                }
                onPhucVuInteractorFinishListener.onFinishTask(
                        aBoolean, String.format(Utils.getStringByRes(R.string.pv_notify_tinh_tien),
                                currentBan.getTenBan(), Utils.formatMoney(tongTien)));
            }
        }
        new TinhTienTask().execute();
    }

    public void updateDatBanChuaSetBan(final DatBan datBan) {

        class UpdateDatBanChuaSetBanTask extends AsyncTask<Void, Void, Boolean> {
            @Override
            protected void onPreExecute() {
                onPhucVuInteractorFinishListener.onStartTask();
                super.onPreExecute();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                delay(500);
                if (currentDatBanChuaSetBan != null) {
                    return currentDatBanChuaSetBan.updateDatBan(datBan);

                } else {
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    onPhucVuInteractorFinishListener.onFinishUpdateDatBanChuaSetBan();
                } else {
                    onPhucVuInteractorFinishListener.onUpdateDatBanChuaSetBanFail();
                }
                onPhucVuInteractorFinishListener.onFinishTask(
                        false, null);
            }
        }
        new UpdateDatBanChuaSetBanTask().execute();

    }

    public void updateDatBanSetBan(final DatBan datBanUpdate) {
        class UpdateDatBanSetBanTask extends AsyncTask<Void, Void, Boolean> {
            @Override
            protected void onPreExecute() {
                onPhucVuInteractorFinishListener.onStartTask();
                super.onPreExecute();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                delay(500);
                return currentDatBan.updateDatBan(datBanUpdate);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    onPhucVuInteractorFinishListener.onFinishUpdateDatBanSetBan();
                }
                onPhucVuInteractorFinishListener.onFinishTask(
                        aBoolean, String.format(Utils.getStringByRes(R.string.pv_notify_update_dat_ban), currentBan.getTenBan()));
            }
        }
        new UpdateDatBanSetBanTask().execute();
    }

    public void huyBan() {
        if (currentBan.getTrangThai() == 2) {
            new HuyBanTask().execute();
        } else {
            new HuyDatBanSetBanTask().execute();
        }
    }

    private class HuyBanTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            onPhucVuInteractorFinishListener.onStartTask();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            delay(500);
            return currentHoaDon.deleteHoaDon();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                database.onHuyBan(currentHoaDon);

                currentHoaDon = null;
                currentThucDon = null;
                currentDatBan = null;
                currentThucDonOrder = null;
                onPhucVuInteractorFinishListener.onFinishHuyBan(currentBan);
            }
            onPhucVuInteractorFinishListener.onFinishTask(
                    aBoolean, String.format(Utils.getStringByRes(R.string.pv_notify_huy_ban), currentBan.getTenBan()));
        }

    }

    public void huyDatBanChuaSetBan() {
        class HuyDatBanChuaSetBanTask extends AsyncTask<Void, Void, Boolean> {

            @Override
            protected void onPreExecute() {
                onPhucVuInteractorFinishListener.onStartTask();
                super.onPreExecute();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                delay(500);
                return currentDatBanChuaSetBan.huyDatBan();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    database.removeDatBanChuaSetBan(currentDatBanChuaSetBan);
                    currentDatBanChuaSetBan = null;
                    onPhucVuInteractorFinishListener.onFinishHuyDatBanChuaSetBan();
                } else {
                    onPhucVuInteractorFinishListener.onHuyDatBanChuaSetBanFail();
                }
                onPhucVuInteractorFinishListener.onFinishTask(false, null);
            }
        }

        new HuyDatBanChuaSetBanTask().execute();
    }


    private class HuyDatBanSetBanTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            onPhucVuInteractorFinishListener.onStartTask();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            delay(500);
            return currentDatBan.huyDatBan();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                database.removeDatBanChuaTinhTien(currentDatBan);
                currentDatBan = null;
                onPhucVuInteractorFinishListener.onFinishHuyBan(currentBan);
            }
            onPhucVuInteractorFinishListener.onFinishTask(aBoolean, String.format(Utils.getStringByRes(R.string.pv_notify_huy_ban), currentBan.getTenBan()));
        }
    }

    //set and get
    public DatBan getCurrentDatBan() {
        return currentDatBan;
    }

    public void setcurrentThucDon(ThucDon currentThucDon) {
        this.currentThucDon = currentThucDon;
    }

    public Ban getcurrentBan() {
        return currentBan;
    }

    public ThucDonOrder getCurrentThucDonOrder() {
        return currentThucDonOrder;
    }

    public HoaDon getcurrentHoaDon() {
        return currentHoaDon;
    }

    public void setCurrentThucDonOrder(ThucDonOrder currentThucDonOrder) {
        this.currentThucDonOrder = currentThucDonOrder;
    }

    public Database getDatabase() {
        return database;
    }

    public void setCurrentDatBanChuaSetBan(DatBan currentDatBanChuaSetBan) {
        this.currentDatBanChuaSetBan = currentDatBanChuaSetBan;
    }

    public DatBan getCurrentDatBanChuaSetBan() {
        return currentDatBanChuaSetBan;
    }


    //interface PhucVuPresenter implement
    public interface OnPhucVuInteractorFinishListener {
        void onFinishGetThongTinBanTrong(Ban ban);

        void onFinishGetThongTinBanDatBan(DatBan datBan);

        void onFinishGetThongTinBanPV(HoaDon hoaDon);

        void onFinishHuyBan(Ban ban);

        void onFinishDatBanSetBan();

        void onFinishOrderUpdateThucDon(int tongTien);

        void onFinishOrderAddThucDon(int tongTien);

        void onFinishOrderCreateHoaDon(HoaDon currentHoaDon);

        void onFinishDeleteThucDonOrder(int tongTien);

        void onFinishSale(HoaDon tongTien);

        void onFinishTinhTien(Ban ban);

        void onFinishUpdateDatBanSetBan();

        void onFinishTask(Boolean isSuccess, String message);

        void onStartTask();

        void onFinishDatBanChuaSetBan();

        void onDatBanChuaSetBanFail();

        void onFinishHuyDatBanChuaSetBan();

        void onHuyDatBanChuaSetBanFail();

        void onFinishUpdateDatBanChuaSetBan();

        void onUpdateDatBanChuaSetBanFail();

        void onFinishKhachDatBanVaoBan();

        void onKhachDatBanVaoBanFail();
    }
}
