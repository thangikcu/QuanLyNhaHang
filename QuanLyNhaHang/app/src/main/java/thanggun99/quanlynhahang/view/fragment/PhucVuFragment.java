package thanggun99.quanlynhahang.view.fragment;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import thanggun99.quanlynhahang.App;
import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.adapter.BanAdapter;
import thanggun99.quanlynhahang.adapter.NhomMonAdapter;
import thanggun99.quanlynhahang.adapter.ThucDonAdapter;
import thanggun99.quanlynhahang.adapter.ThucDonOrderAdapter;
import thanggun99.quanlynhahang.interfaces.OnItemclickListener;
import thanggun99.quanlynhahang.model.Database;
import thanggun99.quanlynhahang.model.entity.Ban;
import thanggun99.quanlynhahang.model.entity.DatBan;
import thanggun99.quanlynhahang.model.entity.HoaDon;
import thanggun99.quanlynhahang.model.entity.NhomMon;
import thanggun99.quanlynhahang.model.entity.ThucDon;
import thanggun99.quanlynhahang.model.entity.ThucDonOrder;
import thanggun99.quanlynhahang.presenter.PhucVuPresenter;
import thanggun99.quanlynhahang.util.Utils;
import thanggun99.quanlynhahang.view.dialog.DeleteThucDonOrderDialog;
import thanggun99.quanlynhahang.view.dialog.OrderThucDonDialog;
import thanggun99.quanlynhahang.view.dialog.SaleDialog;
import thanggun99.quanlynhahang.view.dialog.ThongTinDatBanDialog;
import thanggun99.quanlynhahang.view.dialog.TinhTienDialog;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static thanggun99.quanlynhahang.R.string.error;

@SuppressLint("ValidFragment")
public class PhucVuFragment extends Fragment implements PhucVuPresenter.PhucVuView, View.OnClickListener {
    private Database database;

    private RecyclerView listViewBan, listViewThucDonOrder;
    private Button btnThucDon, btnSale, btnDatBan, btnTinhTien;
    private ListView listViewNhomMon;
    private RecyclerView listViewThucDon;
    private TableRow tableRow;
    private android.widget.SearchView edtTimKiemMon;
    private TextView tvTenBan, tvTrangThai, tvTongTien, tvGioDen, tvTenLoai, tvTenKhachHang,
            tvSoDienThoai, tvKhoangGioDen, tvYeuCau;
    private DrawerLayout drawerLayout;
    private LinearLayout layoutThongTinBan, layoutThucDon, layoutThongTinDatBan, layoutDatBan;
    private PhucVuPresenter phucVuPresenter;
    private PopupMenu popupMenu;
    private EditText edtTenKhachHang, edtSoDienThoai, edtYeuCau;
    private TimePicker timePicker;

    //adapter
    private BanAdapter banAdapter;
    private ThucDonOrderAdapter thucDonOrderAdapter;
    private ThucDonAdapter thucDonAdapter;
    private NhomMonAdapter nhomMonAdapter;

    //dialog
    private DeleteThucDonOrderDialog deleteThucDonOrderDialog;
    private ThongTinDatBanDialog thongTinDatBanDialog;
    private OrderThucDonDialog orderThucDonDialog;
    private TinhTienDialog tinhTienDialog;
    private SaleDialog saleDialog;

    static EditText edtGioDen;
    private View view;

    public PhucVuFragment(PhucVuPresenter phucVuPresenter) {
        database = phucVuPresenter.getDatabase();
        this.phucVuPresenter = phucVuPresenter;
        phucVuPresenter.setPhucVuView(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_phuc_vu, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViews(view);
        initComponents();
        setEvents();
        showContent();

    }

     private void initComponents() {
        timePicker = new TimePicker();
        //initDialogs
        tinhTienDialog = new TinhTienDialog(getContext(), phucVuPresenter);
        thongTinDatBanDialog = new ThongTinDatBanDialog(getContext());
        deleteThucDonOrderDialog = new DeleteThucDonOrderDialog(getContext(), phucVuPresenter);
        orderThucDonDialog = new OrderThucDonDialog(getContext(), phucVuPresenter);
        saleDialog = new SaleDialog(getContext(), phucVuPresenter);

    }

    private void setEvents() {
        btnThucDon.setOnClickListener(this);
        btnSale.setOnClickListener(this);
        btnTinhTien.setOnClickListener(this);
        btnDatBan.setOnClickListener(this);

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
        popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.btn_cancle:
                        phucVuPresenter.onClickHuyBan();
                        return true;
                    case R.id.btn_info_dat_ban:
                        phucVuPresenter.onClickThongTinDatBan();
                        return true;
                    case R.id.btn_update_dat_ban:
                        phucVuPresenter.onClickUpdateDatBan();
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });
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

    public void showContent() {
        setDatas(database.getBanList(), database.getThucDonList(), database.getNhomMonList());
    }

    @Override
    public void onDestroy() {

        if (deleteThucDonOrderDialog != null) deleteThucDonOrderDialog.cancel();
        if (orderThucDonDialog != null) orderThucDonDialog.cancel();
        if (saleDialog != null) saleDialog.cancel();
        if (tinhTienDialog != null) tinhTienDialog.cancel();

        super.onDestroy();
    }

    private void findViews(View view) {
        this.view = view;
        layoutThongTinDatBan = (LinearLayout) view.findViewById(R.id.layout_thong_tin_dat_ban);
        tvTenKhachHang = (TextView) layoutThongTinDatBan.findViewById(R.id.tv_ten_khach_hang);
        tvSoDienThoai = (TextView) layoutThongTinDatBan.findViewById(R.id.tv_so_dien_thoai);
        tvKhoangGioDen = (TextView) layoutThongTinDatBan.findViewById(R.id.tv_khoang_gio_den);
        tvYeuCau = (TextView) layoutThongTinDatBan.findViewById(R.id.tv_yeu_cau);

        layoutThongTinBan = (LinearLayout) view.findViewById(R.id.ln_thong_tin_ban);
        btnSale = (Button) layoutThongTinBan.findViewById(R.id.btn_sale);
        btnTinhTien = (Button) layoutThongTinBan.findViewById(R.id.btn_tinh_tien);
        tvGioDen = (TextView) layoutThongTinBan.findViewById(R.id.tv_gio_den);
        tvTongTien = (TextView) layoutThongTinBan.findViewById(R.id.tv_tong_tien);
        listViewThucDonOrder = (RecyclerView) layoutThongTinBan.findViewById(R.id.list_thuc_don_order);

        btnThucDon = (Button) view.findViewById(R.id.btn_thuc_don);
        listViewBan = (RecyclerView) view.findViewById(R.id.list_ban);
        tvTenBan = (TextView) view.findViewById(R.id.tv_ten_ban);
        tvTrangThai = (TextView) view.findViewById(R.id.tv_trang_thai);
        drawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);

        layoutThucDon = (LinearLayout) view.findViewById(R.id.layout_thuc_don);
        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) layoutThucDon.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels / 2;
        layoutThucDon.setLayoutParams(params);
        listViewNhomMon = (ListView) view.findViewById(R.id.list_nhom_mon);
        tableRow = (TableRow) layoutThucDon.findViewById(R.id.tbr);
        listViewThucDon = (RecyclerView) layoutThucDon.findViewById(R.id.list_thuc_don);
        tvTenLoai = (TextView) layoutThucDon.findViewById(R.id.tv_ten_loai);
        edtTimKiemMon = (android.widget.SearchView) layoutThucDon.findViewById(R.id.edt_tim_kiem_mon);

        layoutDatBan = (LinearLayout) view.findViewById(R.id.layout_dat_ban);
        btnDatBan = (Button) layoutDatBan.findViewById(R.id.btn_dat_ban);
        edtTenKhachHang = (EditText) layoutDatBan.findViewById(R.id.edt_ten_khach_hang);
        edtSoDienThoai = (EditText) layoutDatBan.findViewById(R.id.edt_so_dien_thoai);
        edtGioDen = (EditText) layoutDatBan.findViewById(R.id.edt_gio_den);
        edtYeuCau = (EditText) layoutDatBan.findViewById(R.id.edt_ghi_chu);
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
            case R.id.tv_ten_ban:
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
                        phucVuPresenter.onClickDatBan(datBan);
                    else phucVuPresenter.onClickUpdateDatBan(datBan);
                }
                break;
            default:
                break;
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
    public void showFormUpdateDatBan(DatBan datBan) {
        btnDatBan.setText(Utils.getStringByRes(R.string.cap_nhat));
        layoutDatBan.setVisibility(VISIBLE);
        layoutThongTinDatBan.setVisibility(GONE);

        edtTenKhachHang.setText(datBan.getTenKhachHang());
        edtSoDienThoai.setText(datBan.getSoDienThoai());
        edtGioDen.setText(datBan.getGioDen());
        edtYeuCau.setText(datBan.getYeuCau());
    }

    @Override
    public void showTongTien(int tongTien) {
        tvTongTien.setText(Utils.formatMoney(tongTien));
    }

    @Override
    public void showGiamGia(int giamGia) {
        if (giamGia > 0) btnSale.setText(giamGia + "%");
        else btnSale.setText(Utils.getStringByRes(R.string.sale));
    }

    public void setDatas(ArrayList<Ban> listBan, ArrayList<ThucDon> listThucDon, ArrayList<NhomMon> listNhomMon) {

        banAdapter = new BanAdapter(listBan);
        listViewBan.setAdapter(banAdapter);
        listViewBan.setLayoutManager(new GridLayoutManager(getContext(), 4));
        banAdapter.setOnItemclickListener(new OnItemclickListener() {
            @Override
            public void onItemClick(View view, int position) {
                phucVuPresenter.getThongTinbanAtPosition(position);
            }
        });

        nhomMonAdapter = new NhomMonAdapter(listNhomMon);
        listViewNhomMon.setAdapter(nhomMonAdapter);
        listViewNhomMon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (edtTimKiemMon.hasFocus()) edtTimKiemMon.onActionViewCollapsed();

                phucVuPresenter.onClickNhomMon(position);
            }
        });

        thucDonAdapter = new ThucDonAdapter(listThucDon);
        listViewThucDon.setAdapter(thucDonAdapter);
        listViewThucDon.setLayoutManager(new LinearLayoutManager(getContext()));
        thucDonAdapter.setOnItemclickListener(new OnItemclickListener() {
            @Override
            public void onItemClick(View view, int position) {
                phucVuPresenter.onClickThucdon(thucDonAdapter.getItem(position));
            }
        });

        thucDonOrderAdapter = new ThucDonOrderAdapter();
        listViewThucDonOrder.setAdapter(thucDonOrderAdapter);
        listViewThucDonOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        thucDonOrderAdapter.setOnItemclickListener(new OnItemclickListener() {
            @Override
            public void onItemClick(View view, final int position) {

                if (view.getId() == R.id.btn_delete_mon_order) {
                    phucVuPresenter.onClickDeleteMonOrder(thucDonOrderAdapter.getItem(position));

                } else {
                    phucVuPresenter.onClickThucdonOrder(thucDonOrderAdapter.getItem(position));
                }
            }
        });
        phucVuPresenter.getThongTinbanAtPosition(0);
    }

    @Override
    public void notifyChangeListThucDon(ArrayList<ThucDon> thucDons) {
        thucDonAdapter.changeData(thucDons);
    }

    @Override
    public void showDeleteThucDonOrderDialog(String tenBan, String tenMon) {
        deleteThucDonOrderDialog.setContent(tenBan, tenMon);
    }

    @Override
    public void showOrderThucDonDialog(String tenBan, ThucDon thucDon) {
        orderThucDonDialog.setContent(tenBan, thucDon);
    }

    @Override
    public void notifyAddListThucDonOrder() {
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
    public void notifyUpDateListThucDonOrder(ThucDonOrder thucDonOrder) {
        thucDonOrderAdapter.updateThucDonOrder(thucDonOrder);

    }

    @Override
    public void showNhomMon(NhomMon nhomMon) {
        tvTenLoai.setText(nhomMon.getTenLoai());
        tableRow.setBackgroundColor(nhomMon.getMauSac());
    }

    @Override
    public void showBanDatBan(DatBan currentDatBan) {
        tvTenBan.setText(currentDatBan.getBan().getTenBan());
        tvTrangThai.setText(currentDatBan.getBan().getStringTrangThai());

        tvTenKhachHang.setText(currentDatBan.getTenKhachHang());
        tvSoDienThoai.setText(currentDatBan.getSoDienThoai());
        tvKhoangGioDen.setText(currentDatBan.getGioDen());
        tvYeuCau.setText(currentDatBan.getYeuCau());

        popupMenu.getMenu().findItem(R.id.btn_update_dat_ban).setVisible(true);
        popupMenu.getMenu().findItem(R.id.btn_info_dat_ban).setVisible(false);
        layoutThongTinBan.setVisibility(GONE);
        layoutDatBan.setVisibility(GONE);
        layoutThongTinDatBan.setVisibility(VISIBLE);
    }

    @Override
    public void showBanTrong(Ban currentBan) {
        clearForm();
        tvTenBan.setText(currentBan.getTenBan());
        tvTrangThai.setText(currentBan.getStringTrangThai());
        btnDatBan.setText(Utils.getStringByRes(R.string.dat_ban));

        layoutThongTinBan.setVisibility(GONE);
        layoutDatBan.setVisibility(VISIBLE);
        layoutThongTinDatBan.setVisibility(GONE);
    }

    @Override
    public void showBanPhucVu(HoaDon hoaDon) {
        thucDonOrderAdapter.changeData(hoaDon.getThucDonOrders());

        tvTenBan.setText(hoaDon.getBan().getTenBan());
        tvTrangThai.setText(hoaDon.getBan().getStringTrangThai());

        tvGioDen.setText(hoaDon.getGioDen());
        tvTongTien.setText(Utils.formatMoney(hoaDon.getTongTien()));
        btnSale.setText(hoaDon.getStringGiamGia());

        popupMenu.getMenu().findItem(R.id.btn_update_dat_ban).setVisible(false);
        if (hoaDon.getDatBan() != null)
            popupMenu.getMenu().findItem(R.id.btn_info_dat_ban).setVisible(true);
        else popupMenu.getMenu().findItem(R.id.btn_info_dat_ban).setVisible(false);

        layoutThongTinBan.setVisibility(VISIBLE);
        layoutDatBan.setVisibility(GONE);
        layoutThongTinDatBan.setVisibility(GONE);
    }

public static class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute, false);
    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        edtGioDen.setText(Utils.formatDate(year + "-" + (month + 1) + "-" + day + " " + hourOfDay + ":" + minute + ":" + "00"));
        edtGioDen.setError(null);
    }
}
}
