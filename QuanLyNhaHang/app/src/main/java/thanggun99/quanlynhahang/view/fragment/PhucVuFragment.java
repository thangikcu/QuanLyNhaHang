package thanggun99.quanlynhahang.view.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import thanggun99.quanlynhahang.App;
import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.adapter.BanAdapter;
import thanggun99.quanlynhahang.adapter.NhomMonAdapter;
import thanggun99.quanlynhahang.adapter.ThucDonAdapter;
import thanggun99.quanlynhahang.adapter.ThucDonOrderAdapter;
import thanggun99.quanlynhahang.model.entity.Ban;
import thanggun99.quanlynhahang.model.entity.DatBan;
import thanggun99.quanlynhahang.model.entity.HoaDon;
import thanggun99.quanlynhahang.model.entity.NhomMon;
import thanggun99.quanlynhahang.model.entity.ThucDon;
import thanggun99.quanlynhahang.model.entity.ThucDonOrder;
import thanggun99.quanlynhahang.presenter.PhucVuPresenter;
import thanggun99.quanlynhahang.util.TimePicker;
import thanggun99.quanlynhahang.util.Utils;
import thanggun99.quanlynhahang.view.dialog.ConfirmDialog;
import thanggun99.quanlynhahang.view.dialog.OrderThucDonDialog;
import thanggun99.quanlynhahang.view.dialog.SaleDialog;
import thanggun99.quanlynhahang.view.dialog.ThongTinDatBanDialog;
import thanggun99.quanlynhahang.view.dialog.TinhTienDialog;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static thanggun99.quanlynhahang.R.string.error;

@SuppressLint("ValidFragment")
public class PhucVuFragment extends BaseFragment implements PhucVuPresenter.PhucVuView, View.OnClickListener, TimePicker.OnFinishPickTimeListener {

    private RecyclerView listViewBan, listViewThucDonOrder;
    private ImageButton btnThucDon;
    private Button btnSale, btnDatBan, btnCancel, btnTinhTien;
    private ListView listViewNhomMon;
    private RecyclerView listViewThucDon;
    private TableRow tableRow;
    private android.widget.SearchView edtTimKiemMon;
    private TextView tvTenBan, tvTrangThai, tvTongTien, tvGioDen, tvTenLoai, tvTenKhachHang,
            tvSoDienThoai, tvKhoangGioDen, tvYeuCau;
    private DrawerLayout drawerLayout;
    private LinearLayout layoutThongTinBan;
    private LinearLayout layoutThucDon;
    private LinearLayout layoutThongTinDatBan;
    private ScrollView scrLayoutDatBan;
    private LinearLayout layoutDatBan;
    private PhucVuPresenter phucVuPresenter;
    private PopupMenu popupMenu;
    private EditText edtTenKhachHang, edtSoDienThoai, edtYeuCau, edtGioDen;

    //adapter
    private BanAdapter banAdapter;
    private ThucDonOrderAdapter thucDonOrderAdapter;
    private ThucDonAdapter thucDonAdapter;
    private NhomMonAdapter nhomMonAdapter;

    //dialog
    private ConfirmDialog confirmDialog;
    private ThongTinDatBanDialog thongTinDatBanDialog;
    private OrderThucDonDialog orderThucDonDialog;
    private TinhTienDialog tinhTienDialog;
    private SaleDialog saleDialog;

    //animationAlpha
    private Animation animationAlpha;
    private Animation animationZoom;

    private TimePicker timePicker;

    public PhucVuFragment(PhucVuPresenter phucVuPresenter) {
        super(R.layout.fragment_phuc_vu);
        this.phucVuPresenter = phucVuPresenter;
        phucVuPresenter.setPhucVuView(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        phucVuPresenter.getThongTinBanAt(0);
    }

    @Override
    public void onDestroy() {

        if (confirmDialog != null) confirmDialog.cancel();
        if (orderThucDonDialog != null) orderThucDonDialog.cancel();
        if (saleDialog != null) saleDialog.cancel();
        if (tinhTienDialog != null) tinhTienDialog.cancel();
        if (thongTinDatBanDialog != null) thongTinDatBanDialog.cancel();

        super.onDestroy();
    }

    @Override
    public void findViews(View view) {
        layoutThongTinDatBan = (LinearLayout) view.findViewById(R.id.layout_thong_tin_dat_ban);
        tvTenKhachHang = (TextView) layoutThongTinDatBan.findViewById(R.id.tv_ten_khach_hang);
        tvSoDienThoai = (TextView) layoutThongTinDatBan.findViewById(R.id.tv_so_dien_thoai);
        tvKhoangGioDen = (TextView) layoutThongTinDatBan.findViewById(R.id.tv_gio_den);
        tvYeuCau = (TextView) layoutThongTinDatBan.findViewById(R.id.tv_yeu_cau);

        layoutThongTinBan = (LinearLayout) view.findViewById(R.id.ln_thong_tin_ban);
        btnSale = (Button) layoutThongTinBan.findViewById(R.id.btn_sale);
        btnTinhTien = (Button) layoutThongTinBan.findViewById(R.id.btn_tinh_tien);
        tvGioDen = (TextView) layoutThongTinBan.findViewById(R.id.tv_gio_den);
        tvTongTien = (TextView) layoutThongTinBan.findViewById(R.id.tv_tong_tien);
        listViewThucDonOrder = (RecyclerView) layoutThongTinBan.findViewById(R.id.list_thuc_don_order);

        btnThucDon = (ImageButton) view.findViewById(R.id.btn_thuc_don);
        listViewBan = (RecyclerView) view.findViewById(R.id.list_ban);
        tvTenBan = (TextView) view.findViewById(R.id.tv_title);
        tvTrangThai = (TextView) view.findViewById(R.id.tv_trang_thai);
        drawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);

        layoutThucDon = (LinearLayout) view.findViewById(R.id.layout_thuc_don);
        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) layoutThucDon.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels / 2;
        layoutThucDon.setLayoutParams(params);
        listViewNhomMon = (ListView) view.findViewById(R.id.list_nhom_mon);
        tableRow = (TableRow) layoutThucDon.findViewById(R.id.tbr);
        listViewThucDon = (RecyclerView) layoutThucDon.findViewById(R.id.list_thuc_don);
        tvTenLoai = (TextView) layoutThucDon.findViewById(R.id.tv_title);
        edtTimKiemMon = (android.widget.SearchView) layoutThucDon.findViewById(R.id.edt_tim_kiem_mon);


        layoutDatBan = (LinearLayout) view.findViewById(R.id.layout_dat_ban_chua_set_ban);
        scrLayoutDatBan = (ScrollView) layoutDatBan.findViewById(R.id.scroll_layout_dat_ban);
        btnDatBan = (Button) layoutDatBan.findViewById(R.id.btn_dat_ban);
        btnCancel = (Button) layoutDatBan.findViewById(R.id.btn_cancel);
        edtTenKhachHang = (EditText) layoutDatBan.findViewById(R.id.edt_ten_khach_hang);
        edtSoDienThoai = (EditText) layoutDatBan.findViewById(R.id.edt_so_dien_thoai);
        edtGioDen = (EditText) layoutDatBan.findViewById(R.id.edt_gio_den);
        edtYeuCau = (EditText) layoutDatBan.findViewById(R.id.edt_ghi_chu);
    }

    @Override
    public void initComponents() {
        timePicker = new TimePicker();
        timePicker.setOnFinishPickTimeListener(this);
        //initDialogs
        tinhTienDialog = new TinhTienDialog(getContext(), phucVuPresenter);
        thongTinDatBanDialog = new ThongTinDatBanDialog(getContext());
        confirmDialog = new ConfirmDialog(getContext());
        orderThucDonDialog = new OrderThucDonDialog(getContext(), phucVuPresenter);
        saleDialog = new SaleDialog(getContext(), phucVuPresenter);

        //initAdapter
        banAdapter = new BanAdapter(phucVuPresenter);
        nhomMonAdapter = new NhomMonAdapter(phucVuPresenter);
        thucDonAdapter = new ThucDonAdapter(phucVuPresenter);
        thucDonOrderAdapter = new ThucDonOrderAdapter(phucVuPresenter);

        //initAnimation
        animationAlpha = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
        animationZoom = AnimationUtils.loadAnimation(getContext(), R.anim.zoom);

    }

    @Override
    public void setEvents() {
        listViewBan.setAdapter(banAdapter);
        listViewBan.setLayoutManager(new GridLayoutManager(getContext(), 4));

        listViewNhomMon.setAdapter(nhomMonAdapter);

        listViewThucDon.setAdapter(thucDonAdapter);
        listViewThucDon.setLayoutManager(new LinearLayoutManager(getContext()));

        listViewThucDonOrder.setAdapter(thucDonOrderAdapter);
        listViewThucDonOrder.setLayoutManager(new LinearLayoutManager(getContext()));

        tvYeuCau.setMovementMethod(new ScrollingMovementMethod());
        tvTenLoai.setMovementMethod(new ScrollingMovementMethod());
        edtYeuCau.setMovementMethod(new ScrollingMovementMethod());
        tvTenBan.setMovementMethod(new ScrollingMovementMethod());

        btnThucDon.setOnClickListener(this);
        btnSale.setOnClickListener(this);
        btnTinhTien.setOnClickListener(this);
        btnDatBan.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        btnCancel.setVisibility(GONE);

        edtGioDen.setOnClickListener(this);
        edtGioDen.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    timePicker.show(getActivity().getSupportFragmentManager(), "timePicker");
            }
        });

        edtTimKiemMon.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                edtTimKiemMon.onActionViewCollapsed();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                phucVuPresenter.findThucDon(newText);
                return true;
            }
        });

        edtTimKiemMon.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    tvTenLoai.clearAnimation();
                    tvTenLoai.setVisibility(GONE);

                } else {
                    edtTimKiemMon.onActionViewCollapsed();
                    tvTenLoai.setText(getResources().getString(R.string.thuc_don));
                    tvTenLoai.setVisibility(VISIBLE);
                }
            }
        });

        tvTenBan.setOnClickListener(this);
        popupMenu = new PopupMenu(getContext(), tvTenBan);
        popupMenu.getMenuInflater().inflate(R.menu.ban_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.btn_cancel:
                        phucVuPresenter.onClickHuyBan();
                        return true;
                    case R.id.btn_info_dat_ban:
                        phucVuPresenter.onClickThongTinDatBan();
                        return true;
                    case R.id.btn_update_dat_ban:
                        phucVuPresenter.onClickUpdateDatBanSetBan();
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void clearTimKiem() {
        if (edtTimKiemMon.hasFocus()) edtTimKiemMon.onActionViewCollapsed();
    }

    @Override
    public void showThongTinDatBanDialog(DatBan datBan) {
        thongTinDatBanDialog.setContent(datBan);
    }

    @Override
    public void showSnackbar(Boolean isSuccess, String message) {
        if (!TextUtils.isEmpty(message)) {
            if (!isSuccess) {
                message = Utils.getStringByRes(error);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edt_gio_den:
                timePicker.show(getActivity().getSupportFragmentManager(), "timePicker");
                break;
            case R.id.btn_tinh_tien:
                phucVuPresenter.onClickTinhTien();
                break;
            case R.id.tv_title:
                if (!tvTrangThai.getText().equals(Utils.getStringByRes(R.string.trong)))
                    popupMenu.show();
                break;
            case R.id.btn_sale:
                phucVuPresenter.onClickSale();
                break;
            case R.id.btn_thuc_don:
                drawerLayout.openDrawer(GravityCompat.END);
                break;
            case R.id.btn_dat_ban:
                if (chekForm()) {
                    DatBan datBan = new DatBan();
                    datBan.setTenKhachHang(edtTenKhachHang.getText().toString().trim());
                    datBan.setSoDienThoai(edtSoDienThoai.getText().toString().trim());
                    datBan.setGioDen(edtGioDen.getText().toString().trim());
                    datBan.setYeuCau(edtYeuCau.getText().toString().trim());
                    if (btnDatBan.getText().equals(Utils.getStringByRes(R.string.dat_ban)))
                        phucVuPresenter.onClickDatBanSetBan(datBan);
                    else phucVuPresenter.updateDatBanSetBan(datBan);
                }
                break;
            case R.id.btn_cancel:
                showLayoutThongTinDatBan();
                break;
            default:
                break;
        }
    }

    @Override
    public void closeThucDonLayout() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        }
    }

    @Override
    public void showTinhTienDialog(HoaDon hoaDon) {
        tinhTienDialog.setContent(hoaDon);
    }

    @Override
    public void showSaleDialog(HoaDon hoaDon) {
        saleDialog.setContent(hoaDon);
    }

    public void clearForm() {
        btnDatBan.setText(Utils.getStringByRes(R.string.dat_ban));
        btnCancel.setVisibility(GONE);
        scrLayoutDatBan.scrollTo(0, 0);

        edtTenKhachHang.clearFocus();
        edtSoDienThoai.clearFocus();
        edtGioDen.clearFocus();
        edtYeuCau.clearFocus();

        edtTenKhachHang.setError(null);
        edtSoDienThoai.setError(null);
        edtGioDen.setError(null);
        edtYeuCau.setError(null);

        edtTenKhachHang.setText(null);
        edtSoDienThoai.setText(null);
        edtGioDen.setText(null);
        edtYeuCau.setText(null);
    }

    private boolean chekForm() {
        boolean cancle = false;
        View focusView = null;

        if (TextUtils.isEmpty(edtGioDen.getText().toString()) || !edtGioDen.getText().toString().contains("-")) {
            edtGioDen.setError(getString(R.string.nhap_gio_den));
            focusView = edtGioDen;
            cancle = true;
        }
        if (TextUtils.isEmpty(edtSoDienThoai.getText().toString()) || edtSoDienThoai.length() < 9) {
            edtSoDienThoai.setError(getString(R.string.nhap_so_dien_thoai));
            focusView = edtSoDienThoai;
            cancle = true;
        }
        if (TextUtils.isEmpty(edtTenKhachHang.getText().toString())) {
            edtTenKhachHang.setError(getString(R.string.nhap_ten_khach_hang));
            focusView = edtTenKhachHang;
            cancle = true;
        }

        if (cancle) {
            focusView.requestFocus();
            return false;
        } else {

            return true;
        }
    }

    @Override
    public void showFormUpdateDatBanSetBan(DatBan datBan) {
        btnDatBan.setText(Utils.getStringByRes(R.string.cap_nhat));
        btnCancel.setVisibility(VISIBLE);

        showLayoutDatBan();

        edtTenKhachHang.setText(datBan.getTenKhachHang());
        edtSoDienThoai.setText(datBan.getSoDienThoai());
        edtGioDen.setText(datBan.getGioDen());
        edtYeuCau.setText(datBan.getYeuCau());
    }

    @Override
    public void showTongTien(int tongTien) {
        tvTongTien.setText(Utils.formatMoney(tongTien));
        tvTongTien.startAnimation(animationAlpha);

    }

    @Override
    public void showGiamGia(int giamGia) {
        if (giamGia > 0) {

            btnSale.setText(giamGia + "%");
        } else {
            btnSale.setText(Utils.getStringByRes(R.string.sale));
        }
        btnSale.startAnimation(animationAlpha);
    }

    @Override
    public void showConfirmDialog(String tenBan, String tenMon) {
        confirmDialog.setContent(tenBan, String.format(Utils.getStringByRes(R.string.huy_order), tenMon));
        confirmDialog.setOnClickOkListener(new ConfirmDialog.OnClickOkListener() {
            @Override
            public void onClickOk() {
                phucVuPresenter.deleteThucDonOrder();
                confirmDialog.dismiss();
            }
        });
    }

    @Override
    public void showOrderThucDonDialog(String tenBan, ThucDon thucDon) {
        orderThucDonDialog.setContent(tenBan, thucDon);
    }

    @Override
    public void notifyAddListThucDonOrder() {
        listViewThucDonOrder.scrollToPosition(0);
        thucDonOrderAdapter.notifyItemInserted(0);

    }

    @Override
    public void notifyRemoveListThucDonOrder() {
        thucDonOrderAdapter.deleteThucDonOrder();
    }

    @Override
    public void notifyUpdateListBan(Ban ban) {
        banAdapter.updateBan(ban);
    }

    @Override
    public void notifyChangeSelectBan(Ban ban) {
        banAdapter.changeSelectBan(ban);

    }

    @Override
    public void notifyUpDateListThucDonOrder(ThucDonOrder currentThucDonOrder) {
        listViewThucDonOrder.scrollToPosition(thucDonOrderAdapter.getPositionOf(currentThucDonOrder));
        thucDonOrderAdapter.updateThucDonOrder(currentThucDonOrder);

    }

    @Override
    public void showNhomMon(NhomMon nhomMon) {
        tvTenLoai.setText(nhomMon.getTenLoai());
        tvTenLoai.startAnimation(animationAlpha);

        tableRow.setBackgroundColor(nhomMon.getMauSac());
    }

    @Override
    public void notifyChangeListThucDon(ArrayList<ThucDon> thucDons) {
        thucDonAdapter.changeData(thucDons);
    }

    public void showBan(Ban ban) {

        tvTenBan.setText(ban.getTenBan());
        tvTrangThai.setText(ban.getStringTrangThai());

        tvTenBan.startAnimation(animationZoom);
        tvTrangThai.startAnimation(animationAlpha);
    }

    @Override
    public void showBanDatBan(DatBan currentDatBan) {

        showBan(currentDatBan.getBan());

        tvTenKhachHang.setText(currentDatBan.getTenKhachHang());
        tvSoDienThoai.setText(currentDatBan.getSoDienThoai());
        tvKhoangGioDen.setText(currentDatBan.getGioDen());
        tvYeuCau.setText(currentDatBan.getYeuCau());
        tvYeuCau.scrollTo(0, 0);

        popupMenu.getMenu().findItem(R.id.btn_update_dat_ban).setVisible(true);
        popupMenu.getMenu().findItem(R.id.btn_info_dat_ban).setVisible(false);

        showLayoutThongTinDatBan();
    }

    private void showLayoutThongTinDatBan() {
        layoutDatBan.clearAnimation();

        layoutThongTinBan.setVisibility(GONE);
        layoutDatBan.setVisibility(GONE);

        layoutThongTinDatBan.setVisibility(VISIBLE);

        layoutThongTinDatBan.startAnimation(animationZoom);
    }

    @Override
    public void showBanTrong(Ban currentBan) {
        clearForm();

        showBan(currentBan);
        btnDatBan.setText(Utils.getStringByRes(R.string.dat_ban));

        showLayoutDatBan();
    }

    private void showLayoutDatBan() {
        layoutThongTinDatBan.clearAnimation();
        layoutDatBan.clearAnimation();

        layoutThongTinBan.setVisibility(GONE);
        layoutThongTinDatBan.setVisibility(GONE);

        layoutDatBan.setVisibility(VISIBLE);

        layoutDatBan.startAnimation(animationZoom);
    }

    @Override
    public void showBanPhucVu(HoaDon hoaDon) {
        showBan(hoaDon.getBan());
        showTongTien(hoaDon.getTongTien());
        showGiamGia(hoaDon.getGiamGia());

        clearItemThucDonOrderAnimation(thucDonOrderAdapter.getItemCount());
        thucDonOrderAdapter.changeData(hoaDon.getThucDonOrders());

        tvGioDen.setText(hoaDon.getGioDen());
        tvGioDen.startAnimation(animationAlpha);

        popupMenu.getMenu().findItem(R.id.btn_update_dat_ban).setVisible(false);
        if (hoaDon.getDatBan() != null)
            popupMenu.getMenu().findItem(R.id.btn_info_dat_ban).setVisible(true);
        else popupMenu.getMenu().findItem(R.id.btn_info_dat_ban).setVisible(false);


        showLayoutThongTinBanPV();
    }

    private void showLayoutThongTinBanPV() {
        layoutDatBan.clearAnimation();
        layoutThongTinDatBan.clearAnimation();

        layoutThongTinBan.setVisibility(VISIBLE);
        layoutDatBan.setVisibility(GONE);
        layoutThongTinDatBan.setVisibility(GONE);
    }

    private void clearItemThucDonOrderAnimation(int itemCount) {
        View view;
        for (int i = 0; i < itemCount; i++) {
            view = listViewThucDonOrder.getChildAt(i);
            if (view != null) {
                view.clearAnimation();
            }
        }
    }

    @Override
    public void onFinishPickTime(String date) {
        edtGioDen.setText(date);
        edtGioDen.setError(null);
    }

}
