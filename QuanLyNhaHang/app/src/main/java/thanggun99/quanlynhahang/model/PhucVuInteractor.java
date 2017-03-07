package thanggun99.quanlynhahang.model;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Date;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.model.Database;
import thanggun99.quanlynhahang.model.entity.Ban;
import thanggun99.quanlynhahang.model.entity.DatBan;
import thanggun99.quanlynhahang.model.entity.HoaDon;
import thanggun99.quanlynhahang.model.entity.NhomMon;
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


    //constructor
    public PhucVuInteractor(OnPhucVuInteractorFinishListener onPhucVuInteractorFinishListener, Database database) {

        this.database = database;
        this.onPhucVuInteractorFinishListener = onPhucVuInteractorFinishListener;
    }


    public void getThongTinbanAtPosition(int position) {
        currentBan = database.getBanAt(position);

        if (currentBan != null) {
            if (currentBan.getTrangThai() == 2) {

                currentHoaDon = database.getHoaDonByMaBan(currentBan.getMaBan());

                if (currentHoaDon != null && currentHoaDon.getDatBan() != null) {

                    currentDatBan = currentHoaDon.getDatBan();
                } else {

                    currentDatBan = null;
                }

                onPhucVuInteractorFinishListener.onFinishGetThongTinBanPV(currentHoaDon);
            } else if (currentBan.getTrangThai() == 1) {

                currentDatBan = database.getDatBanByMaBan(currentBan.getMaBan());
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

    public void datBan(final DatBan datBan) {
        class DatBanTask extends AsyncTask<Void, Void, Boolean> {

            @Override
            protected void onPreExecute() {
                onPhucVuInteractorFinishListener.onStartTask();
                super.onPreExecute();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                datBan.setBan(currentBan);
                return datBan.datBan();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    currentDatBan = datBan;
                    database.addDatBan(currentDatBan);
                    onPhucVuInteractorFinishListener.onFinishDatBan(currentDatBan);
                }
                onPhucVuInteractorFinishListener.onFinishTask(aBoolean, String.format(Utils.getStringByRes(R.string.pv_notify_dat_ban), currentBan.getTenBan()));
            }
        }
        new DatBanTask().execute();
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
                return currentHoaDon.deleteThucDonOrder(currentThucDonOrder.getMaChitietHD());
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    currentThucDonOrder = null;
                    onPhucVuInteractorFinishListener.onFinishDeleteThucDonOrder(currentHoaDon.getTongTien());
                }
                onPhucVuInteractorFinishListener.onFinishTask(aBoolean,String.format(Utils.getStringByRes(R.string.pv_notify_delete_thucdon_order),
                        currentBan.getTenBan(), tenMon) );
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
                return currentHoaDon.tinhTien();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    database.addHoaDonTinhTien(currentHoaDon);
                    database.removeHoaDonChuaTinhTien(currentHoaDon);
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

    public void updateDatBan(final DatBan datBanUpdate) {
        class UpdateDatBanTask extends AsyncTask<Void, Void, Boolean> {
            @Override
            protected void onPreExecute() {
                onPhucVuInteractorFinishListener.onStartTask();
                super.onPreExecute();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                return currentDatBan.updateDatBan(datBanUpdate);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    onPhucVuInteractorFinishListener.onFinishUpdateDatBan(currentDatBan);
                }
                onPhucVuInteractorFinishListener.onFinishTask(
                        aBoolean, String.format(Utils.getStringByRes(R.string.pv_notify_update_dat_ban), currentBan.getTenBan()));
            }
        }
        new UpdateDatBanTask().execute();
    }

    public void huyBan() {
        if (currentBan.getTrangThai() == 2) {
            new HuyBanTask().execute();
        } else {
            new HuyDatBanTask().execute();
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
            return currentHoaDon.deleteHoaDon();
        }
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                if (currentHoaDon.getDatBan() != null) {

                    database.removeDatBan(currentDatBan);
                }
                database.removeHoaDonChuaTinhTien(currentHoaDon);
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

    private class HuyDatBanTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            onPhucVuInteractorFinishListener.onStartTask();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return currentDatBan.huyDatBan();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                database.removeDatBan(currentDatBan);
                currentDatBan = null;
                onPhucVuInteractorFinishListener.onFinishHuyBan(currentBan);
            }
            onPhucVuInteractorFinishListener.onFinishTask(aBoolean, String.format(Utils.getStringByRes(R.string.pv_notify_huy_ban), currentBan.getTenBan()));
        }
    }

    //set and get
    public NhomMon getNhomMonAt(int position) {
        return database.getNhomMonAt(position);
    }

    public DatBan getCurrentDatBan() {
        return currentDatBan;
    }

    public ArrayList<ThucDon> getListThucDonByTenMon(String keyword) {
        return database.getListThucDonByTenMon(keyword);
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

    public ArrayList<ThucDon> getListThucDonByMaLoai(int maLoai) {
        return database.getListThucDonByMaLoai(maLoai);
    }



    //interface PhucVuPresenter implement
    public interface OnPhucVuInteractorFinishListener {
        void onFinishGetThongTinBanTrong(Ban ban);

        void onFinishGetThongTinBanDatBan(DatBan datBan);

        void onFinishGetThongTinBanPV(HoaDon hoaDon);

        void onFinishHuyBan(Ban ban);

        void onFinishDatBan(DatBan datBan);

        void onFinishOrderUpdateThucDon(int tongTien);

        void onFinishOrderAddThucDon(int tongTien);

        void onFinishOrderCreateHoaDon(HoaDon currentHoaDon);

        void onFinishDeleteThucDonOrder(int tongTien);

        void onFinishSale(HoaDon tongTien);

        void onFinishTinhTien(Ban ban);

        void onFinishUpdateDatBan(DatBan datBan);

        void onFinishTask(Boolean isSuccess, String message);

        void onStartTask();
    }
}