package thanggun99.quanlynhahang.model.phucvu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.Date;

import thanggun99.quanlynhahang.App;
import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.interfaces.OnItemclickListener;
import thanggun99.quanlynhahang.model.entity.Ban;
import thanggun99.quanlynhahang.model.entity.DatTruoc;
import thanggun99.quanlynhahang.model.entity.HoaDon;
import thanggun99.quanlynhahang.model.entity.NhomMon;
import thanggun99.quanlynhahang.model.entity.ThucDon;
import thanggun99.quanlynhahang.model.entity.ThucDonOrder;
import thanggun99.quanlynhahang.util.Utils;
import thanggun99.quanlynhahang.adapter.BanAdapter;
import thanggun99.quanlynhahang.adapter.NhomMonAdapter;
import thanggun99.quanlynhahang.adapter.ThucDonAdapter;
import thanggun99.quanlynhahang.adapter.ThucDonOrderAdapter;
import thanggun99.quanlynhahang.view.dialog.DeleteThucDonOrderDialog;
import thanggun99.quanlynhahang.view.dialog.ErrorDialog;
import thanggun99.quanlynhahang.view.dialog.OrderThucDonDialog;
import thanggun99.quanlynhahang.view.dialog.SaleDialog;
import thanggun99.quanlynhahang.view.dialog.ThongTinDatBanDialog;
import thanggun99.quanlynhahang.view.dialog.TinhTienDialog;


/**
 * Created by Thanggun99 on 05/12/2016.
 */

public class MainPhucVuInteractor {

    private Context context;
    private OnMainPVFinishProgress onMainPVFinishProgress;
    private View view;

    private BanManager banManager;
    private ThucDonManager thucDonManager;
    private HoaDonManager hoaDonManager;
    private NhomMonManager nhomMonManager;
    private DatTruocManager datTruocManager;

    //currentItem
    private Ban currentBan;
    private HoaDon currentHoaDon;
    private DatTruoc currentDatTruoc;

    private ThucDonOrder currentThucDonOrder;
    private ThucDon currentThucDon;

    public MainPhucVuInteractor(OnMainPVFinishProgress onMainPVFinishProgress, Context context) {
        this.context = context;
        view = ((Activity) context).getWindow().getDecorView().findViewById(R.id.drawer_layout);

        banManager = new BanManager();
        hoaDonManager = new HoaDonManager();
        thucDonManager = new ThucDonManager();
        nhomMonManager = new NhomMonManager();
        datTruocManager = new DatTruocManager();

        this.onMainPVFinishProgress = onMainPVFinishProgress;
    }

    public void loadDatas() {
        class GetDatasTask extends AsyncTask<Void, Void, Boolean> {
            @Override
            protected Boolean doInBackground(Void... params) {
                boolean taskOk;
                taskOk = nhomMonManager.loadListNhomMon();
                if (taskOk) taskOk = thucDonManager.loadListThucDon();
                if (taskOk) taskOk = banManager.loadListBan();
                if (taskOk) taskOk = datTruocManager.loadListDatTruoc();
                if (taskOk) taskOk = hoaDonManager.loadListHoaDon();
                return taskOk;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    onMainPVFinishProgress.onFinishGetDatas(banManager.getListBan(),
                            thucDonManager.getListThucDon(), nhomMonManager.getListNhomMon());
                } else {
                    onMainPVFinishProgress.onGetDatasFail();
                }
            }
        }
        new GetDatasTask().execute();
    }

    public void destroy() {
        banManager = null;
        datTruocManager = null;
        hoaDonManager = null;
        nhomMonManager = null;
        thucDonManager = null;
    }

    public void orderThucDon(int soLuong) {
        ThucDonOrder thucDonOrder = new ThucDonOrder();
        thucDonOrder.setMaMon(currentThucDon.getMaMon());
        thucDonOrder.setSoLuong(soLuong);

        if (currentBan.getTrangThai() == 2) {
            for (int i = 0; i < currentHoaDon.getThucDonOrders().size(); i++) {
                if (currentHoaDon.getThucDonOrders().get(i).getMaMon() == currentThucDon.getMaMon()) {
                    currentThucDonOrder = currentHoaDon.getThucDonOrders().get(i);
                    UpdateThucDonOrderTask updateThucDonOrderTask = new UpdateThucDonOrderTask(soLuong);
                    updateThucDonOrderTask.execute();
                    return;
                }
            }
            ThemThucDonOrderTask themThucDonOrderTask = new ThemThucDonOrderTask(thucDonOrder);
            themThucDonOrderTask.execute();
        } else {
            TaoMoiHoaDonTask taoMoiHoaDonTask = new TaoMoiHoaDonTask(thucDonOrder);
            taoMoiHoaDonTask.execute();
        }
    }

    public void updateDatBan(final DatTruoc datTruocUpdate) {
        class UpdateDatBanTask extends AsyncTask<Void, Void, Boolean> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.show();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                datTruocUpdate.setMaDatTruoc(currentDatTruoc.getMaDatTruoc());
                return datTruocManager.updateDatBan(datTruocUpdate);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    currentDatTruoc.setTenKhachHang(datTruocUpdate.getTenKhachHang());
                    currentDatTruoc.setSoDienThoai(datTruocUpdate.getSoDienThoai());
                    currentDatTruoc.setGioDen(datTruocUpdate.getGioDen());
                    currentDatTruoc.setGhiChu(datTruocUpdate.getGhiChu());
                    onMainPVFinishProgress.onFinishGetThongTinBanDatTruoc(currentDatTruoc);
                }
                showSnackbar(aBoolean, String.format(Utils.getStringByRes(R.string.pv_notify_update_dat_ban), currentBan.getTenBan()));
                progressDialog.dismiss();
            }
        }
        new UpdateDatBanTask().execute();
    }

    public boolean checkConnect() {
        return Utils.isConnectingToInternet();
    }

    public void showDialogThongTinDatBan() {
        thongTinDatBanDialog.setContent(currentHoaDon.getDatTruoc());
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
            super.onPreExecute();
            progressDialog.show();
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
                thucDonOrderAdapter.updateThucDonOrder(currentThucDonOrder);
                onMainPVFinishProgress.onChangeTongTien(currentHoaDon.getTongTien());
                orderThucDonDialog.hide();
            }
            showSnackbar(aBoolean, String.format(Utils.getStringByRes(R.string.pv_notify_order_thucdon),
                    currentBan.getTenBan(), soLuong, currentThucDonOrder.getTenMon()));
            progressDialog.dismiss();
        }
    }

    private class ThemThucDonOrderTask extends AsyncTask<Void, Void, Boolean> {
        private ThucDonOrder thucDonOrder;

        public ThemThucDonOrderTask(ThucDonOrder thucDonOrder) {
            this.thucDonOrder = thucDonOrder;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return hoaDonManager.themThucDonOrder(currentHoaDon, thucDonOrder);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                thucDonOrderAdapter.notifyItemInserted(0);
                onMainPVFinishProgress.onChangeTongTien(currentHoaDon.getTongTien());
                orderThucDonDialog.hide();
            }
            showSnackbar(aBoolean, String.format(Utils.getStringByRes(R.string.pv_notify_order_thucdon),
                    currentBan.getTenBan(), thucDonOrder.getSoLuong(), currentThucDon.getTenMon()));
            progressDialog.dismiss();
        }
    }

    private class TaoMoiHoaDonTask extends AsyncTask<Void, Void, Boolean> {
        private ThucDonOrder thucDonOrder;
        private HoaDon hoaDonNew;

        public TaoMoiHoaDonTask(ThucDonOrder thucDonOrder) {
            this.thucDonOrder = thucDonOrder;

            hoaDonNew = new HoaDon();
            String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            if (currentDatTruoc != null && currentDatTruoc.getBan() == currentBan)
                hoaDonNew.setDatTruoc(currentDatTruoc);
            hoaDonNew.setGioDen(date);
            hoaDonNew.setBan(currentBan);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return hoaDonManager.taoMoiHoaDon(hoaDonNew, thucDonOrder);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                currentHoaDon = hoaDonNew;
                thucDonOrderAdapter.changeData(hoaDonNew.getThucDonOrders());

                currentBan.setTrangThai(2);
                banAdapter.updateBan(currentBan);

                onMainPVFinishProgress.onFinishGetThongTinBanPV(hoaDonNew);
                orderThucDonDialog.hide();
            }
            showSnackbar(aBoolean, String.format(Utils.getStringByRes(R.string.pv_notify_hoa_don_moi),
                    currentBan.getTenBan(), thucDonOrder.getSoLuong(), currentThucDon.getTenMon()));
            progressDialog.dismiss();
        }
    }

    public void getThongTinbanAtPosition(int position) {
        currentBan = banManager.getBanAt(position);

        if (currentBan.getTrangThai() == 2) {
            currentHoaDon = hoaDonManager.getHoaDonByMaBan(currentBan.getMaBan());
            if (currentHoaDon.getDatTruoc() != null) {
                currentDatTruoc = currentHoaDon.getDatTruoc();
            } else {
                currentDatTruoc = null;
            }
            onMainPVFinishProgress.onFinishGetThongTinBanPV(currentHoaDon);
        } else if (currentBan.getTrangThai() == 1) {
            currentDatTruoc = datTruocManager.getDatTruocByMaBan(currentBan.getMaBan());
            currentHoaDon = null;
            onMainPVFinishProgress.onFinishGetThongTinBanDatTruoc(currentDatTruoc);
        } else {
            currentHoaDon = null;
            currentDatTruoc = null;
            onMainPVFinishProgress.onFinishGetThongTinBanTrong(currentBan);
        }
    }

    public void datBan(final DatTruoc datTruoc) {
        class DatBanTask extends AsyncTask<Void, Void, Boolean> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.show();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                datTruoc.setBan(currentBan);
                return datTruocManager.datBan(datTruoc);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    currentBan.setTrangThai(1);
                    currentDatTruoc = datTruoc;
                    banAdapter.updateBan(currentBan);
                    onMainPVFinishProgress.onFinishDatBan(currentDatTruoc);
                }
                showSnackbar(aBoolean, String.format(Utils.getStringByRes(R.string.pv_notify_dat_ban), currentBan.getTenBan()));
                progressDialog.dismiss();
            }
        }
        new DatBanTask().execute();
    }

    public void deleteThucDonOrder() {
        class DeleteThucDonOrderTask extends AsyncTask<Void, Void, Boolean> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.show();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                return hoaDonManager.deleteThucDonOrder(currentThucDonOrder.getMaChitietHD());
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                showSnackbar(aBoolean, String.format(Utils.getStringByRes(R.string.pv_notify_delete_thucdon_order),
                        currentBan.getTenBan(), currentThucDonOrder.getTenMon()));

                if (aBoolean) {
                    thucDonOrderAdapter.deleteThucDonOrder(currentThucDonOrder);
                    currentHoaDon.getThucDonOrders().remove(currentThucDonOrder);
                    currentThucDonOrder = null;
                    onMainPVFinishProgress.onChangeTongTien(currentHoaDon.getTongTien());
                    deleteThucDonOrderDialog.hide();
                }
                progressDialog.dismiss();
            }
        }
        new DeleteThucDonOrderTask().execute();
    }

    public void getNhomMon(int position) {
        NhomMon nhomMon = nhomMonManager.getNhomMonAt(position);
        thucDonAdapter.changeData(thucDonManager.getListThucDonByMaLoai(nhomMon.getMaLoai()));
        onMainPVFinishProgress.onChangeNhomMon(nhomMon);
    }

    public DatTruoc getCurrentDatTruoc() {
        return currentDatTruoc;
    }

    public void findThucDon(String keyword) {
        thucDonAdapter.changeData(thucDonManager.getListThucDonByTenMon(keyword));
    }

    public void showDialogSale() {
        saleDialog.setContent(currentHoaDon);
    }

    public void saleHoaDon(final int presentSale) {
        class SaleHoaDonTask extends AsyncTask<Void, Void, Boolean> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.show();
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
                    onMainPVFinishProgress.onChangeTongTien(currentHoaDon.getTongTien());
                    onMainPVFinishProgress.onChangeGiamGia(currentHoaDon.getGiamGia());
                    saleDialog.dismiss();
                }
                showSnackbar(aBoolean, String.format(Utils.getStringByRes(R.string.pv_notify_sale), currentBan.getTenBan(), currentHoaDon.getGiamGia()));
                progressDialog.dismiss();
            }
        }
        new SaleHoaDonTask().execute();
    }

    public void huyBan() {
        if (currentBan.getTrangThai() == 2) {
            new HuyBanTask().execute();
        } else {
            new HuyDatBanTask().execute();
        }
    }

    public void showDialogTinhTien() {
        tinhTienDialog.setContent(currentHoaDon);
    }

    private class HuyBanTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return hoaDonManager.deleteHoaDon(currentHoaDon);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                if (currentHoaDon.getDatTruoc() != null)
                    datTruocManager.deleteDatTruoc(currentHoaDon.getDatTruoc().getMaDatTruoc());
                currentHoaDon = null;
                currentThucDon = null;
                currentDatTruoc = null;
                currentThucDonOrder = null;
                currentBan.setTrangThai(0);
                banAdapter.updateBan(currentBan);
                onMainPVFinishProgress.onFinishHuyBan(currentBan);
            }
            showSnackbar(aBoolean, String.format(Utils.getStringByRes(R.string.pv_notify_huy_ban), currentBan.getTenBan()));
            progressDialog.dismiss();
        }
    }

    public void tinhTien() {
        class TinhTienTask extends AsyncTask<Void, Void, Boolean> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.show();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                return hoaDonManager.tinhTien(currentHoaDon);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                showSnackbar(aBoolean, String.format(Utils.getStringByRes(R.string.pv_notify_tinh_tien),
                        currentBan.getTenBan(), Utils.formatMoney(currentHoaDon.getTongTien())));

                if (aBoolean) {
                    currentBan.setTrangThai(0);
                    banAdapter.updateBan(currentBan);
                    onMainPVFinishProgress.onFinishHuyBan(currentBan);
                    currentHoaDon = null;
                    currentThucDon = null;
                    currentDatTruoc = null;
                    currentThucDonOrder = null;
                    tinhTienDialog.dismiss();
                }
                progressDialog.dismiss();

            }
        }
        new TinhTienTask().execute();
    }

    private class HuyDatBanTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return datTruocManager.huyDatBan(currentDatTruoc);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                currentBan.setTrangThai(0);
                banAdapter.updateBan(currentBan);
                currentDatTruoc = null;
                onMainPVFinishProgress.onFinishHuyDatBan(currentBan);
            }
            showSnackbar(aBoolean, String.format(Utils.getStringByRes(R.string.pv_notify_huy_ban), currentBan.getTenBan()));
            progressDialog.dismiss();
        }
    }

    private void showSnackbar(boolean error, String message) {
        if (!error) {
            message = Utils.getStringByRes(R.string.error);
        }
        final Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        View viewSnackbar = snackbar.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) viewSnackbar.getLayoutParams();
        params.width = App.getContext().getResources().getDisplayMetrics().widthPixels / 2;
        viewSnackbar.setLayoutParams(params);
        viewSnackbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    public interface OnMainPVFinishProgress {
        void onFinishGetThongTinBanTrong(Ban ban);

        void onFinishGetThongTinBanDatTruoc(DatTruoc datTruoc);

        void onFinishGetThongTinBanPV(HoaDon hoaDon);

        void onFinishHuyDatBan(Ban ban);

        void onFinishHuyBan(Ban ban);

        void onFinishDatBan(DatTruoc datTruoc);

        void onChangeTongTien(int tongTien);

        void onChangeGiamGia(int giamGia);

        void onChangeNhomMon(NhomMon nhomMon);

        void onGetDatasFail();

        void onFinishGetDatas(ArrayList<Ban> listBan, ArrayList<ThucDon> listThucDon, ArrayList<NhomMon> listNhomMon);
    }
}
