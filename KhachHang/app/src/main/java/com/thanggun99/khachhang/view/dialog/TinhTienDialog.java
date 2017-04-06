package com.thanggun99.khachhang.view.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.thanggun99.khachhang.R;
import com.thanggun99.khachhang.model.entity.HoaDon;
import com.thanggun99.khachhang.presenter.KhachHangPresenter;
import com.thanggun99.khachhang.util.Utils;

import butterknife.BindView;

/**
 * Created by Thanggun99 on 12/12/2016.
 */

public class TinhTienDialog extends BaseDialog {

    @BindView(R.id.tv_tien_mon)
    TextView tvTienMon;
    @BindView(R.id.tv_so_luong)
    TextView tvSoLuong;
    @BindView(R.id.tv_tien_giam_gia)
    TextView tvTienGiamGia;
    @BindView(R.id.tv_giam_gia)
    TextView tvGiamGia;
    @BindView(R.id.tv_tong_tien)
    TextView tvTongTien;
    private KhachHangPresenter khachHangPresenter;
    private int tongtien;

    public TinhTienDialog(Context context, KhachHangPresenter khachHangPresenter) {
        super(context, R.layout.dialog_tinh_tien);
        this.khachHangPresenter = khachHangPresenter;

        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.btn_ok) {
            khachHangPresenter.tinhTien();
            dismiss();
        }
    }

    public void setContent(HoaDon hoaDon) {
        if (hoaDon.getMonOrderList().size() > 0) {
            tvTitle.setText(hoaDon.getTenBan());

            this.tongtien = hoaDon.getTongTien();
            tvTienMon.setText(Utils.formatMoney(hoaDon.getTienMon()));
            tvSoLuong.setText("(" + hoaDon.getSoMon() + ")");
            tvTongTien.setText(Utils.formatMoney(hoaDon.getTongTien()));
            if (hoaDon.getGiamGia() > 0) {
                tvTienGiamGia.setText(Utils.formatMoney(hoaDon.getTienGiamGia()));
                tvGiamGia.setText("(" + hoaDon.getGiamGia() + "%)");
            } else {
                tvTienGiamGia.setText("...");
                tvGiamGia.setText("");
            }
            show();
        }
    }
}
