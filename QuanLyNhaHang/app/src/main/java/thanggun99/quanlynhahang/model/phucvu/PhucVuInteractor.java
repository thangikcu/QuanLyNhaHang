package thanggun99.quanlynhahang.model.phucvu;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Date;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.model.entity.Ban;
import thanggun99.quanlynhahang.model.entity.DatBan;
import thanggun99.quanlynhahang.model.entity.HoaDon;
import thanggun99.quanlynhahang.model.entity.NhomMon;
import thanggun99.quanlynhahang.model.entity.ThucDon;
import thanggun99.quanlynhahang.model.entity.ThucDonOrder;
import thanggun99.quanlynhahang.util.Utils;

public class PhucVuInteractor {

    private OnPhucVuInteractorFinishListener onPhucVuInteractorFinishListener;

    //manager
    private BanManager banManager;
    private ThucDonManager thucDonManager;
    private HoaDonManager hoaDonManager;
    private NhomMonManager nhomMonManager;
    private DatBanManager datBanManager;

    //currentItem
    private Ban currentBan;
    private HoaDon currentHoaDon;
    private DatBan currentDatBan;
    private ThucDonOrder currentThucDonOrder;
    private ThucDon currentThucDon;


    //constructor
    public PhucVuInteractor(OnPhucVuInteractorFinishListener onPhucVuInteractorFinishListener) {

        banManager = new BanManager();
        hoaDonManager = new HoaDonManager();
        thucDonManager = new ThucDonManager();
        nhomMonManager = new NhomMonManager();
        datBanManager = new DatBanManager();

        this.onPhucVuInteractorFinishListener = onPhucVuInteractorFinishListener;
    }


    //Task
    public void loadDatas() {
        class GetDatasTask extends AsyncTask<Void, Void, Boolean> {
            @Override
            protected void onPreExecute() {
                onPhucVuInteractorFinishListener.onStartTask();
                super.onPreExecute();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                boolean taskOk;
                taskOk = nhomMonManager.loadListNhomMon();
                if (taskOk) taskOk = thucDonManager.loadListThucDon();
                if (taskOk) taskOk = banManager.loadListBan();
                if (taskOk) taskOk = datBanManager.loadListDatBan();
                if (taskOk) taskOk = hoaDonManager.loadListHoaDon();
                return taskOk;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    onPhucVuInteractorFinishListener.onFinishGetDatas(banManager.getListBan(),
                            thucDonManager.getListThucDon(), nhomMonManager.getListNhomMon());
                } else {
                    onPhucVuInteractorFinishListener.onGetDatasFail();
                }
                onPhucVuInteractorFinishListener.onFinishTask(aBoolean, null);
            }
        }
        new GetDatasTask().execute();
    }

    public void getThongTinbanAtPosition(int position) {
        currentBan = banManager.getBanAt(position);

        if (currentBan != null) {
            if (currentBan.getTrangThai() == 2) {

                currentHoaDon = hoaDonManager.getHoaDonByMaBan(currentBan.getMaBan());

                if (currentHoaDon != null && currentHoaDon.getDatBan() != null) {

                    currentDatBan = currentHoaDon.getDatBan();
                } else {

                    currentDatBan = null;
                }

                onPhucVuInteractorFinishListener.onFinishGetThongTinBanPV(currentHoaDon);
            } else if (currentBan.getTrangThai() == 1) {

                currentDatBan = datBanManager.getDatBanByMaBan(currentBan.getMaBan());
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

        if (currentBan.getTrangThai() == 2) {
            for (int i = 0; i < currentHoaDon.getThucDonOrders().size(); i++) {
                if (currentHoaDon.getThucDonOrders().get(i).getMaMon() == currentThucDon.getMaMon()) {
                    currentThucDonOrder = currentHoaDon.getThucDonOrders().get(i);
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

        private ThucDonOrder orderUpdate;

        public UpdateThucDonOrderTask(int soLuong) {
            this.soLuong = soLuong;
            orderUpdate = new ThucDonOrder();
        }

        @Override
        protected void onPreExecute() {
            onPhucVuInteractorFinishListener.onStartTask();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            orderUpdate.setMaChitietHD(currentThucDonOrder.getMaChitietHD());
            orderUpdate.setSoLuong(currentThucDonOrder.getSoLuong() + soLuong);
            return hoaDonManager.updateThucDonOrder(orderUpdate);
        }
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                currentThucDonOrder.setSoLuong(orderUpdate.getSoLuong());
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
            return hoaDonManager.themThucDonOrder(currentHoaDon, thucDonOrder);
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
            if (currentDatBan != null && currentDatBan.getBan() == currentBan)
                hoaDonNew.setDatBan(currentDatBan);
            hoaDonNew.setGioDen(date);
            hoaDonNew.setBan(currentBan);
        }

        @Override
        protected void onPreExecute() {
            onPhucVuInteractorFinishListener.onStartTask();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return hoaDonManager.taoMoiHoaDon(hoaDonNew, thucDonOrder);
        }
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                currentHoaDon = hoaDonNew;
                currentBan.setTrangThai(2);

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
                return datBanManager.datBan(datBan);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    currentBan.setTrangThai(1);
                    currentDatBan = datBan;
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
                return hoaDonManager.deleteThucDonOrder(currentThucDonOrder.getMaChitietHD());
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    currentHoaDon.getThucDonOrders().remove(currentThucDonOrder);
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
                HoaDon hoaDon = new HoaDon();
                hoaDon.setGiamGia(presentSale);
                hoaDon.setMaHoaDon(currentHoaDon.getMaHoaDon());
                return hoaDonManager.saleHoaDon(hoaDon);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    currentHoaDon.setGiamGia(presentSale);
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
                return hoaDonManager.tinhTien(currentHoaDon);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    currentBan.setTrangThai(0);
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
                datBanUpdate.setMaDatBan(currentDatBan.getMaDatBan());
                return datBanManager.updateDatBan(datBanUpdate);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    currentDatBan.setTenKhachHang(datBanUpdate.getTenKhachHang());
                    currentDatBan.setSoDienThoai(datBanUpdate.getSoDienThoai());
                    currentDatBan.setGioDen(datBanUpdate.getGioDen());
                    currentDatBan.setYeuCau(datBanUpdate.getYeuCau());
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
            return hoaDonManager.deleteHoaDon(currentHoaDon);
        }
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                if (currentHoaDon.getDatBan() != null) {

                    datBanManager.deleteDatBan(currentHoaDon.getDatBan().getMaDatBan());
                }

                currentHoaDon = null;
                currentThucDon = null;
                currentDatBan = null;
                currentThucDonOrder = null;
                currentBan.setTrangThai(0);
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
            return datBanManager.huyDatBan(currentDatBan);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                currentBan.setTrangThai(0);
                currentDatBan = null;
                onPhucVuInteractorFinishListener.onFinishHuyBan(currentBan);
            }
            onPhucVuInteractorFinishListener.onFinishTask(aBoolean, String.format(Utils.getStringByRes(R.string.pv_notify_huy_ban), currentBan.getTenBan()));
        }
    }




    public void destroy() {
        banManager = null;
        datBanManager = null;
        hoaDonManager = null;
        nhomMonManager = null;
        thucDonManager = null;
    }

    //set and get
    public NhomMon getNhomMonAt(int position) {
        return nhomMonManager.getNhomMonAt(position);
    }

    public DatBan getCurrentDatBan() {
        return currentDatBan;
    }

    public ArrayList<ThucDon> getListThucDonByTenMon(String keyword) {
        return thucDonManager.getListThucDonByTenMon(keyword);
    }

    public boolean checkConnect() {
        return Utils.isConnectingToInternet();
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
        return thucDonManager.getListThucDonByMaLoai(maLoai);
    }



    //interface PhucVuPresenter implement
    public interface OnPhucVuInteractorFinishListener {
        void onFinishGetThongTinBanTrong(Ban ban);

        void onFinishGetThongTinBanDatBan(DatBan datBan);

        void onFinishGetThongTinBanPV(HoaDon hoaDon);

        void onFinishHuyBan(Ban ban);

        void onFinishDatBan(DatBan datBan);

        void onFinishOrderUpdateThucDon(int tongTien);

        void onGetDatasFail();

        void onFinishGetDatas(ArrayList<Ban> listBan, ArrayList<ThucDon> listThucDon, ArrayList<NhomMon> listNhomMon);

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
