package thanggun99.quanlynhahang.model.phucvu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.FrameLayout;

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
import thanggun99.quanlynhahang.view.adapter.BanAdapter;
import thanggun99.quanlynhahang.view.adapter.NhomMonAdapter;
import thanggun99.quanlynhahang.view.adapter.ThucDonAdapter;
import thanggun99.quanlynhahang.view.adapter.ThucDonOrderAdapter;
import thanggun99.quanlynhahang.view.dialog.DeleteThucDonOrderDialog;
import thanggun99.quanlynhahang.view.dialog.ErrorDialog;
import thanggun99.quanlynhahang.view.dialog.OrderThucDonDialog;
import thanggun99.quanlynhahang.view.dialog.SaleDialog;
import thanggun99.quanlynhahang.view.dialog.TinhTienDialog;


/**
 * Created by Thanggun99 on 05/12/2016.
 */

public class MainPhucVuManager {

    private BanManager banManager;
    private ThucDonManager thucDonManager;
    private HoaDonManager hoaDonManager;
    private NhomMonManager nhomMonManager;
    private DatTruocManager datTruocManager;
    private OnMainPVFinishProgress onMainPVFinishProgress;
    private Ban currentBan;
    private ThucDonOrder currentThucDonOrder;
    private HoaDon currentHoaDon;
    private DatTruoc currentDatTruoc;
    private BanAdapter banAdapter;
    private ThucDonOrderAdapter thucDonOrderAdapter;
    private int positionBan, postionthucDonOrder;
    private DeleteThucDonOrderDialog deleteThucDonOrderDialog;
    private OrderThucDonDialog orderThucDonDialog;
    private TinhTienDialog tinhTienDialog;
    private ErrorDialog errorDialog;
    private SaleDialog saleDialog;
    private Context context;
    private ThucDonAdapter thucDonAdapter;
    private NhomMonAdapter nhomMonAdapter;
    private View view;
    private ProgressDialog progressDialog;

    public MainPhucVuManager(OnMainPVFinishProgress onMainPVFinishProgress, Context context) {
        this.context = context;
        view = ((Activity) context).getWindow().getDecorView().findViewById(R.id.drawer_layout);

        errorDialog = new ErrorDialog(context, this);
        tinhTienDialog = new TinhTienDialog(context, this);

        banManager = new BanManager();
        hoaDonManager = new HoaDonManager();
        thucDonManager = new ThucDonManager();
        nhomMonManager = new NhomMonManager();
        datTruocManager = new DatTruocManager();

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(Utils.getStringByRes(R.string.loading));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        this.onMainPVFinishProgress = onMainPVFinishProgress;
    }

    public void loadDatas() {
        class GetDatasTask extends AsyncTask<Void, Void, Boolean> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.show();
            }

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
                    deleteThucDonOrderDialog = new DeleteThucDonOrderDialog(context, MainPhucVuManager.this);
                    orderThucDonDialog = new OrderThucDonDialog(context, MainPhucVuManager.this);
                    saleDialog = new SaleDialog(context, MainPhucVuManager.this);

                    nhomMonAdapter = new NhomMonAdapter(nhomMonManager.getListNhomMon());

                    thucDonAdapter = new ThucDonAdapter(thucDonManager.getListThucDon());
                    thucDonAdapter.setOnItemclickListener(new OnItemclickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            ThucDon thucDon = thucDonAdapter.getItem(position);

                            if (currentHoaDon != null && currentBan.getMaBan() == currentHoaDon.getBan().getMaBan()) {
                                for (int i = 0; i < currentHoaDon.getThucDonOrders().size(); i++) {
                                    if (currentHoaDon.getThucDonOrders().get(i).getMaMon() == thucDon.getMaMon()) {
                                        currentThucDonOrder = currentHoaDon.getThucDonOrders().get(i);
                                        orderThucDonDialog.setContentUpdate(currentBan.getTenBan(), currentThucDonOrder);
                                        return;
                                    }
                                }
                                orderThucDonDialog.setContentThem(currentHoaDon, thucDon);
                            } else {
                                orderThucDonDialog.setContentTaoMoi(currentBan, thucDon);
                            }
                        }
                    });

                    banAdapter = new BanAdapter(banManager.getListBan());
                    banAdapter.setOnItemclickListener(new OnItemclickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            getThongTinbanAtPosition(position);
                        }
                    });

                    thucDonOrderAdapter = new ThucDonOrderAdapter();
                    thucDonOrderAdapter.setOnItemclickListener(new OnItemclickListener() {
                        @Override
                        public void onItemClick(View view, final int position) {
                            postionthucDonOrder = position;
                            currentThucDonOrder = thucDonOrderAdapter.getItem(position);

                            if (view.getId() == R.id.btn_delete_mon_order) {
                                deleteThucDonOrderDialog.setContent(currentThucDonOrder.getMaChitietHD(), currentBan.getTenBan(), currentThucDonOrder.getTenMon());

                            } else {
                                orderThucDonDialog.setContentUpdate(currentBan.getTenBan(), currentThucDonOrder);
                            }
                        }
                    });

                    getThongTinbanAtPosition(0);
                    onMainPVFinishProgress.onFinishGetDatas(banAdapter, thucDonOrderAdapter, thucDonAdapter, nhomMonAdapter);
                } else {
                    errorDialog.show();
                }
                progressDialog.dismiss();
            }
        }
        new GetDatasTask().execute();
    }

    private void getThongTinbanAtPosition(int position) {
        this.currentBan = banManager.getBanAt(position);
        this.positionBan = position;

        if (currentBan.getTrangThai() == 2) {
            currentHoaDon = hoaDonManager.getHoaDonByMaBan(currentBan.getMaBan());
            thucDonOrderAdapter.changeData(currentHoaDon.getThucDonOrders());

            onMainPVFinishProgress.onFinishGetThongTinBanPV(currentHoaDon);
        }else if (currentBan.getTrangThai() == 1){
            currentDatTruoc = datTruocManager.getDatTruocByMaBan(currentBan.getMaBan());
            onMainPVFinishProgress.onFinishGetThongTinBanDatTruoc(currentDatTruoc);
        }else {
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
                    currentDatTruoc = datTruoc;
                    banAdapter.notifyItemChanged(positionBan);
                    onMainPVFinishProgress.onFinishDatBan(currentDatTruoc);
                }
                showSnackbar(aBoolean, String.format(Utils.getStringByRes(R.string.pv_notify_dat_ban), currentBan.getTenBan()));
                progressDialog.dismiss();
            }
        }
        new DatBanTask().execute();
    }

    public void updateThucDonOrder(final ThucDonOrder orderUpdate) {
        class UpdateThucDonOrderTask extends AsyncTask<Void, Void, Boolean> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.show();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                return hoaDonManager.updateThucDonOrder(orderUpdate);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    currentThucDonOrder.setSoLuong(orderUpdate.getSoLuong());
                    thucDonOrderAdapter.notifyItemChanged(postionthucDonOrder);
                    onMainPVFinishProgress.onChangeTongTien(currentHoaDon.getTongTien());
                    orderThucDonDialog.hide();
                }
                showSnackbar(aBoolean, String.format(Utils.getStringByRes(R.string.pv_notify_order_thucdon), currentBan.getTenBan(), orderUpdate.getSoLuong(), orderUpdate.getTenMon()));
                progressDialog.dismiss();
            }
        }
        new UpdateThucDonOrderTask().execute();
    }

    public void deleteThucDonOrder(final int maChiTietHD) {
        class DeleteThucDonOrderTask extends AsyncTask<Void, Void, Boolean> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.show();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                return hoaDonManager.deleteThucDonOrder(maChiTietHD);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    currentHoaDon.getThucDonOrders().remove(postionthucDonOrder);
                    thucDonOrderAdapter.notifyItemRemoved(postionthucDonOrder);
                    onMainPVFinishProgress.onChangeTongTien(currentHoaDon.getTongTien());
                    deleteThucDonOrderDialog.hide();
                }
                showSnackbar(aBoolean, String.format(Utils.getStringByRes(R.string.pv_notify_delete_thucdon_order), currentBan.getTenBan(), currentThucDonOrder.getTenMon()));
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

    public void findThucDon(String keyword) {
        thucDonAdapter.changeData(thucDonManager.getListThucDonByTenMon(keyword));
    }

    public void themThucDonOrder(final ThucDonOrder thucDonOrderNew) {
        class ThemThucDonOrderTask extends AsyncTask<Void, Void, Boolean> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.show();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                return hoaDonManager.themThucDonOrder(currentHoaDon, thucDonOrderNew);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    thucDonOrderAdapter.notifyItemInserted(0);
                    onMainPVFinishProgress.onChangeTongTien(currentHoaDon.getTongTien());
                    orderThucDonDialog.hide();
                }
                showSnackbar(aBoolean, String.format(Utils.getStringByRes(R.string.pv_notify_order_thucdon), currentBan.getTenBan(), thucDonOrderNew.getSoLuong(), thucDonOrderNew.getTenMon()));
                progressDialog.dismiss();
            }
        }
        new ThemThucDonOrderTask().execute();
    }

    public void taoMoiHoaDon(final HoaDon hoaDonNew, final ThucDonOrder thucDonOrderNew) {
        class TaoMoiHoaDonTask extends AsyncTask<Void, Void, Boolean> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.show();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                return hoaDonManager.taoMoiHoaDon(hoaDonNew, thucDonOrderNew);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    currentHoaDon = hoaDonNew;

                    thucDonOrderAdapter.changeData(hoaDonNew.getThucDonOrders());

                    currentBan = hoaDonNew.getBan();
                    banAdapter.notifyItemChanged(positionBan);

                    onMainPVFinishProgress.onFinishGetThongTinBanPV(hoaDonNew);
                    orderThucDonDialog.hide();
                }
                showSnackbar(aBoolean, String.format(Utils.getStringByRes(R.string.pv_notify_hoa_don_moi), currentBan.getTenBan(), thucDonOrderNew.getSoLuong(), thucDonOrderNew.getTenMon()));
                progressDialog.dismiss();
            }
        }
        new TaoMoiHoaDonTask().execute();
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
                currentHoaDon = null;
                currentBan.setTrangThai(0);
                banAdapter.notifyItemChanged(positionBan);
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
                int tongTien = 0;
                if (aBoolean) {
                    currentBan.setTrangThai(0);
                    banAdapter.notifyItemChanged(positionBan);
                    onMainPVFinishProgress.onFinishHuyBan(currentBan);
                    tongTien = currentHoaDon.getTongTien();
                    currentHoaDon = null;
                    tinhTienDialog.dismiss();
                }
                showSnackbar(aBoolean, String.format(Utils.getStringByRes(R.string.pv_notify_tinh_tien), currentBan.getTenBan(), Utils.formatMoney(tongTien)));
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
                banAdapter.notifyItemChanged(positionBan);
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
        void onFinishGetDatas(BanAdapter banAdapter, ThucDonOrderAdapter thucDonOrderAdapter, ThucDonAdapter thucDonAdapter, NhomMonAdapter nhomMonAdapter);

        void onFinishGetThongTinBanTrong(Ban ban);

        void onFinishGetThongTinBanDatTruoc(DatTruoc datTruoc);

        void onFinishGetThongTinBanPV(HoaDon hoaDon);

        void onFinishHuyDatBan(Ban ban);

        void onFinishHuyBan(Ban ban);

        void onFinishDatBan(DatTruoc datTruoc);

        void onChangeTongTien(int tongTien);

        void onChangeGiamGia(int giamGia);

        void onChangeNhomMon(NhomMon nhomMon);
    }
}
