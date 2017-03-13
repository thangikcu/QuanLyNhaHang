package com.thanggun99.khachhang.view.fragment;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thanggun99.khachhang.R;
import com.thanggun99.khachhang.model.entity.DatBan;
import com.thanggun99.khachhang.presenter.KhachHangPresenter;
import com.thanggun99.khachhang.util.Utils;
import com.thanggun99.khachhang.view.dialog.ConfirmDialog;

import java.util.Calendar;


@SuppressLint("ValidFragment")
public class DatBanFragment extends Fragment implements View.OnClickListener, KhachHangPresenter.DatBanView {
    private KhachHangPresenter khachHangPresenter;
    private EditText edtYeuCau;
    private static EditText edtGioDen;
    private TimePicker timePicker;
    private TextView tvError;
    private LinearLayout lnThongTinDatBan, lnDatBan;
    private TextView tvGioDen, tvYeuCau, tvTitle;
    private Button btnDatBan;
    private ImageButton btnBack;

    public DatBanFragment(KhachHangPresenter khachHangPresenter) {
        this.khachHangPresenter = khachHangPresenter;
        khachHangPresenter.setDatBanView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dat_ban, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        timePicker = new TimePicker();

        lnDatBan = (LinearLayout) view.findViewById(R.id.ln_dat_ban);
        lnThongTinDatBan = (LinearLayout) view.findViewById(R.id.ln_thong_tin_dat_ban);
        tvGioDen = (TextView) view.findViewById(R.id.tv_gio_den);
        tvYeuCau = (TextView) view.findViewById(R.id.tv_yeu_cau);

        tvYeuCau.setMovementMethod(new ScrollingMovementMethod());

        edtGioDen = (EditText) view.findViewById(R.id.edt_gio_den);
        edtGioDen.setOnClickListener(this);
        edtGioDen.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    timePicker.show(getActivity().getSupportFragmentManager(), "timePicker");
            }
        });

        edtYeuCau = (EditText) view.findViewById(R.id.edt_yeu_cau);
        edtYeuCau.requestFocus();

        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvError = (TextView) view.findViewById(R.id.tv_error);

        btnDatBan = (Button) view.findViewById(R.id.btn_dat_ban);
        btnDatBan.setOnClickListener(this);

        btnBack = (ImageButton) view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);

        view.findViewById(R.id.btn_huy_dat_ban).setOnClickListener(this);
        view.findViewById(R.id.btn_edit_dat_ban).setOnClickListener(this);

        khachHangPresenter.getInfoDatBan();

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void showLayoutThongTinDatBan(DatBan datBan) {
        btnBack.setVisibility(View.GONE);
        lnThongTinDatBan.setVisibility(View.VISIBLE);
        lnDatBan.setVisibility(View.GONE);

        tvYeuCau.setText(datBan.getYeuCau());
        tvGioDen.setText(datBan.getGioDen());
    }

    @Override
    public void showLayoutDatBan() {
        tvTitle.setText(Utils.getStringByRes(R.string.dat_ban));
        btnDatBan.setText(Utils.getStringByRes(R.string.dat_ban));
        edtYeuCau.requestFocus();
        edtGioDen.setText("");
        edtYeuCau.setText("");
        lnThongTinDatBan.setVisibility(View.GONE);
        lnDatBan.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edt_gio_den:
                timePicker.show(getActivity().getSupportFragmentManager(), "timePicker");
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
            case R.id.btn_back:
                khachHangPresenter.backToThongTinDatBan();
                break;
            default:
                break;
        }
    }

    @Override
    public void setContentView(DatBan datBan) {
        btnBack.setVisibility(View.VISIBLE);
        tvTitle.setText(Utils.getStringByRes(R.string.cap_nhat_dat_ban));
        btnDatBan.setText(Utils.getStringByRes(R.string.cap_nhat));
        edtYeuCau.setText(datBan.getYeuCau());
        edtGioDen.setText(datBan.getGioDen());
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
        tvError.setVisibility(View.GONE);
    }

    @Override
    public void showOnUpdateSuccess() {
        Utils.notifi(Utils.getStringByRes(R.string.cap_nhat_dat_ban_thanh_cong));
        edtGioDen.setText("");
        edtYeuCau.setText("");
        tvError.setVisibility(View.GONE);
    }

    @Override
    public void showOnFail() {
        tvError.setVisibility(View.VISIBLE);
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
