package com.thanggun99.khachhang.view.fragment;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TableRow;
import android.widget.TextView;

import com.thanggun99.khachhang.R;
import com.thanggun99.khachhang.adapter.MonOrderAdapter;
import com.thanggun99.khachhang.model.entity.DatBan;
import com.thanggun99.khachhang.model.entity.HoaDon;
import com.thanggun99.khachhang.model.entity.MonOrder;
import com.thanggun99.khachhang.presenter.KhachHangPresenter;
import com.thanggun99.khachhang.util.Utils;
import com.thanggun99.khachhang.view.dialog.ConfirmDialog;
import com.thanggun99.khachhang.view.dialog.ErrorDialog;
import com.thanggun99.khachhang.view.dialog.OrderMonDialog;
import com.thanggun99.khachhang.view.dialog.ThongTinDatBanDialog;
import com.thanggun99.khachhang.view.dialog.TinhTienDialog;

import java.util.Calendar;


@SuppressLint("ValidFragment")
public class ThongTinPhucVuFragment extends BaseFragment implements View.OnClickListener, KhachHangPresenter.ThongTinPhucVuView {

    private KhachHangPresenter khachHangPresenter;

    private EditText edtYeuCau;
    private static EditText edtGioDen;
    private TimePicker timePicker;
    private LinearLayout lnThongTinDatBan, lnDatBan, lnPhucVu;
    private TextView tvGioDen, tvYeuCau, tvTenBan, tvTrangThai, tvTongTien;
    private ImageButton btnThucDon, btnBack;
    private Button btnDatBan, btnEditDatBan, btnHuyDatBan, btnTinhTien, btnSale;
    private TableRow tbrGioDen;
    private RecyclerView monOrderRecyclerView;
    private MonOrderAdapter monOrderAdapter;
    private Animation animationAlpha;
    private PopupMenu popupMenu;
    private ThongTinDatBanDialog thongTinDatBanDialog;
    private OrderMonDialog orderMonDialog;
    private ErrorDialog errorDialog;
    private TinhTienDialog tinhTienDialog;


    public ThongTinPhucVuFragment(KhachHangPresenter khachHangPresenter) {
        super(R.layout.fragment_thong_tin_phuc_vu);
        this.khachHangPresenter = khachHangPresenter;

    }

    @Override
    public void findViews(View view) {
        tbrGioDen = (TableRow) view.findViewById(R.id.tbr_gio_den);

        tvTenBan = (TextView) view.findViewById(R.id.tv_ten_ban);
        tvTrangThai = (TextView) view.findViewById(R.id.tv_trang_thai);
        btnThucDon = (ImageButton) view.findViewById(R.id.btn_thuc_don);
        tvGioDen = (TextView) view.findViewById(R.id.tv_gio_den);

        lnDatBan = (LinearLayout) view.findViewById(R.id.ln_dat_ban);
        edtGioDen = (EditText) lnDatBan.findViewById(R.id.edt_gio_den);
        edtYeuCau = (EditText) lnDatBan.findViewById(R.id.edt_yeu_cau);
        btnDatBan = (Button) lnDatBan.findViewById(R.id.btn_dat_ban);
        btnBack = (ImageButton) lnDatBan.findViewById(R.id.btn_back);

        lnThongTinDatBan = (LinearLayout) view.findViewById(R.id.ln_thong_tin_dat_ban);
        tvYeuCau = (TextView) lnThongTinDatBan.findViewById(R.id.tv_yeu_cau);
        btnHuyDatBan = (Button) lnThongTinDatBan.findViewById(R.id.btn_huy_dat_ban);
        btnEditDatBan = (Button) lnThongTinDatBan.findViewById(R.id.btn_edit_dat_ban);


        lnPhucVu = (LinearLayout) view.findViewById(R.id.ln_phuc_vu);
        monOrderRecyclerView = (RecyclerView) lnPhucVu.findViewById(R.id.recyclerview_mon_order);
        btnSale = (Button) lnPhucVu.findViewById(R.id.btn_sale);
        btnTinhTien = (Button) lnPhucVu.findViewById(R.id.btn_tinh_tien);
        tvTongTien = (TextView) lnPhucVu.findViewById(R.id.tv_tong_tien);
    }

    @Override
    public void initComponents() {
        timePicker = new TimePicker();
        thongTinDatBanDialog = new ThongTinDatBanDialog(getContext());
        orderMonDialog = new OrderMonDialog(getContext(), khachHangPresenter);

        errorDialog = new ErrorDialog(getContext(), khachHangPresenter);
        tinhTienDialog = new TinhTienDialog(getContext(), khachHangPresenter);

        animationAlpha = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);

    }

    @Override
    public void setEvents() {
        lnDatBan.setVisibility(View.GONE);
        tvTenBan.setVisibility(View.GONE);
        tbrGioDen.setVisibility(View.GONE);
        lnPhucVu.setVisibility(View.GONE);
        lnThongTinDatBan.setVisibility(View.GONE);
        btnBack.setVisibility(View.GONE);

        monOrderRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        btnThucDon.setOnClickListener(this);
        btnEditDatBan.setOnClickListener(this);
        btnHuyDatBan.setOnClickListener(this);
        btnTinhTien.setOnClickListener(this);
        btnDatBan.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        tvYeuCau.setMovementMethod(new ScrollingMovementMethod());

        edtGioDen.setOnClickListener(this);
        edtGioDen.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    if (!timePicker.isAdded()) {

                        timePicker.show(getActivity().getSupportFragmentManager(), "timePicker");
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
                    case R.id.btn_huy:
                        khachHangPresenter.onClickHuyBan();
                        return true;
                    case R.id.btn_info_dat_ban:
                        khachHangPresenter.onClickThongTinDatBan();
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });

        khachHangPresenter.setThongTinPhucVuView(this);
        khachHangPresenter.getThongTinPhucVu();

    }

    @Override
    public void showGetDatasFailDialog() {
        errorDialog.cancel();
        errorDialog.show();
    }

    @Override
    public void showOrderMonDialog(MonOrder monOrder) {
        orderMonDialog.setContent(monOrder);
    }

    @Override
    public void showThongTinDatBanDialog(DatBan datBan) {
        thongTinDatBanDialog.setContent(datBan);
    }

    @Override
    public void showLayoutThongTinDatBan(DatBan datBan) {

        tvGioDen.setText(datBan.getGioDen());
        tvYeuCau.setText(datBan.getYeuCau());

        if (!TextUtils.isEmpty(datBan.getTenBan())) {
            tvTenBan.setVisibility(View.VISIBLE);
            tvTenBan.setText(datBan.getTenBan());
        } else {
            tvTenBan.setVisibility(View.GONE);
        }

        tvTrangThai.setText(Utils.getStringByRes(R.string.dat_truoc));

        lnThongTinDatBan.setVisibility(View.VISIBLE);
        tbrGioDen.setVisibility(View.VISIBLE);
        lnDatBan.setVisibility(View.GONE);
        lnPhucVu.setVisibility(View.GONE);
    }

    @Override
    public void showLayoutDatBan() {
        btnDatBan.setText(Utils.getStringByRes(R.string.dat_ban));
        edtGioDen.setText("");
        edtYeuCau.setText("");

        tvTrangThai.setText(Utils.getStringByRes(R.string.trong));

        lnDatBan.setVisibility(View.VISIBLE);
        tvTenBan.setVisibility(View.GONE);
        tbrGioDen.setVisibility(View.GONE);
        lnPhucVu.setVisibility(View.GONE);
        lnThongTinDatBan.setVisibility(View.GONE);
        btnBack.setVisibility(View.GONE);

    }

    @Override
    public void showLayoutPhucVu(HoaDon hoaDon) {
        monOrderAdapter = new MonOrderAdapter(getContext(), khachHangPresenter);
        monOrderRecyclerView.setAdapter(monOrderAdapter);

        tvTrangThai.setText(Utils.getStringByRes(R.string.dang_phuc_vu));

        if (hoaDon.getDatBan() != null)
            popupMenu.getMenu().findItem(R.id.btn_info_dat_ban).setVisible(true);
        else popupMenu.getMenu().findItem(R.id.btn_info_dat_ban).setVisible(false);

        tvTenBan.setText(hoaDon.getTenBan());
        tvGioDen.setText(hoaDon.getGioDen());
        if (hoaDon.getGiamGia() > 0) {
            btnSale.setVisibility(View.VISIBLE);
            btnSale.setText(hoaDon.getStringGiamGia());
        } else {
            btnSale.setVisibility(View.GONE);
        }

        lnPhucVu.setVisibility(View.VISIBLE);
        tvTenBan.setVisibility(View.VISIBLE);
        tbrGioDen.setVisibility(View.VISIBLE);
        lnDatBan.setVisibility(View.GONE);
        lnThongTinDatBan.setVisibility(View.GONE);

    }

    @Override
    public void showGiamGia(int giamGia) {
        if (giamGia > 0) {
            btnSale.setVisibility(View.VISIBLE);
            btnSale.setText(giamGia + "%");
        } else {
            btnSale.setVisibility(View.GONE);
        }
    }

    @Override
    public void showTongTien(int tongTien) {
        tvTongTien.setText(Utils.formatMoney(tongTien));
        tvTongTien.startAnimation(animationAlpha);
    }

    @Override
    public void notifyListMonOrderChange() {
        monOrderAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edt_gio_den:
                if (!timePicker.isAdded()) {

                    timePicker.show(getActivity().getSupportFragmentManager(), "timePicker");
                }
                break;
            case R.id.btn_dat_ban:
                if (chekForm()) {
                    DatBan datBan = new DatBan();
                    datBan.setGioDen(edtGioDen.getText().toString().trim());
                    if (!TextUtils.isEmpty(edtYeuCau.getText())) {

                        datBan.setYeuCau(edtYeuCau.getText().toString().trim());
                    }
                    if (btnDatBan.getText().toString().equals(Utils.getStringByRes(R.string.dat_ban))) {

                        khachHangPresenter.onClickDatBan(datBan);
                    } else {
                        khachHangPresenter.UpdateDatBan(datBan);
                    }
                }
                break;
            case R.id.btn_edit_dat_ban:
                khachHangPresenter.onClickEditDatBan();
                break;
            case R.id.btn_huy_dat_ban:
                ConfirmDialog confirmDialog = new ConfirmDialog(getContext());
                confirmDialog.showConfirm(Utils.getStringByRes(R.string.ban_co_dong_y_huy_dat_ban));
                confirmDialog.setOnOkListener(new ConfirmDialog.OnClickOkListener() {
                    @Override
                    public void onClickOk() {
                        khachHangPresenter.onClickHuyDatBan();
                    }
                });
                break;
            case R.id.btn_thuc_don:
                khachHangPresenter.showThucDonLayout();
                break;
            case R.id.btn_back:
                khachHangPresenter.backToThongTinDatBan();
                break;
            case R.id.tv_ten_ban:
                if (tvTrangThai.getText().equals(Utils.getStringByRes(R.string.dang_phuc_vu)))
                    popupMenu.show();
                break;
            case R.id.btn_tinh_tien:
                khachHangPresenter.onClickTinhTien();
                break;
            default:
                break;
        }
    }

    @Override
    public void setContentView(DatBan datBan) {
        btnBack.setVisibility(View.VISIBLE);
        btnDatBan.setText(Utils.getStringByRes(R.string.cap_nhat));
        edtYeuCau.setText(datBan.getYeuCau());
        edtGioDen.setText(datBan.getGioDen());
    }

    @Override
    public void showTinhTienDialog(HoaDon hoaDon) {
        tinhTienDialog.setContent(hoaDon);
    }

    @Override
    public void showError() {
        Utils.notifi(Utils.getStringByRes(R.string.da_xay_ra_loi));
    }

    private boolean chekForm() {
        boolean cancle = false;
        View focusView = null;

        if (TextUtils.isEmpty(edtGioDen.getText().toString()) || !edtGioDen.getText().toString().contains("-")) {
            edtGioDen.setError(getString(R.string.nhap_gio_den));
            focusView = edtGioDen;
            cancle = true;
        }

        if (cancle) {
            focusView.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void showOnSuccess() {
        Utils.notifi(Utils.getStringByRes(R.string.dat_ban_thanh_cong));
        edtGioDen.setText("");
        edtYeuCau.setText("");
    }

    @Override
    public void showOnUpdateSuccess() {
        Utils.notifi(Utils.getStringByRes(R.string.cap_nhat_dat_ban_thanh_cong));
        edtGioDen.setText("");
        edtYeuCau.setText("");
    }

    @Override
    public void showUpdateDatBanError() {
        Utils.notifi(Utils.getStringByRes(R.string.cap_nhat_dat_ban_that_bai));
    }

    @Override
    public void showDatBanError() {
        Utils.notifi(Utils.getStringByRes(R.string.dat_ban_that_bai));
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
