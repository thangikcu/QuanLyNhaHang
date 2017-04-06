package com.thanggun99.khachhang.view.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thanggun99.khachhang.R;
import com.thanggun99.khachhang.model.entity.Mon;
import com.thanggun99.khachhang.presenter.KhachHangPresenter;

import butterknife.BindView;

public class OrderMonDialog extends BaseDialog {

    @BindView(R.id.tv_ten_mon)
    TextView tvTenMon;
    @BindView(R.id.iv_mon)
    ImageView ivMon;
    @BindView(R.id.btn_tru)
    ImageButton btnTru;
    @BindView(R.id.edt_so_luong)
    EditText edtSoLuong;
    @BindView(R.id.tv_don_vi_tinh)
    TextView tvDonViTinh;
    @BindView(R.id.btn_cong)
    ImageButton btnCong;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.tv_point_rating)
    TextView tvPointRating;

    private final KhachHangPresenter khachHangPresenter;

    public OrderMonDialog(Context context, KhachHangPresenter khachHangPresenter) {
        super(context, R.layout.dialog_order_mon);

        this.khachHangPresenter = khachHangPresenter;
        tvTenMon.setMovementMethod(new ScrollingMovementMethod());

        btnOk.setOnClickListener(this);
        btnCong.setOnClickListener(this);
        btnTru.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    public void setContent(Mon mon) {
        ratingBar.setRating(mon.getRating() / mon.getPersonRating());
        tvPointRating.setText(mon.getRatingPoint());
        edtSoLuong.setText("1");
        tvTenMon.setText(mon.getTenMon());
        tvDonViTinh.setText(mon.getDonViTinh());
        Glide.with(getContext())
                .load(mon.getHinhAnh())
                .placeholder(R.drawable.ic_food)
                .error(R.drawable.ic_food)
                .into(ivMon);
        show();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_ok:
                if (!TextUtils.isEmpty(edtSoLuong.getText())) {
                    try {
                        khachHangPresenter.orderMon(Integer.parseInt(edtSoLuong.getText().toString()));

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }

                    dismiss();
                }
                break;
            case R.id.btn_cong:
                int sl = 1;
                if (!edtSoLuong.getText().toString().isEmpty()) {
                    try {
                        sl = Integer.parseInt(edtSoLuong.getText().toString());
                        sl++;

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }

                }
                edtSoLuong.setText(sl + "");
                break;
            case R.id.btn_tru:
                int sl2 = 1;
                if (!edtSoLuong.getText().toString().isEmpty()) {
                    sl2 = Integer.parseInt(edtSoLuong.getText().toString());
                    if (sl2 > 1) sl2--;
                }
                edtSoLuong.setText(sl2 + "");
                break;
            default:
                break;
        }
    }

}
