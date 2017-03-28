package com.thanggun99.khachhang.view.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thanggun99.khachhang.R;
import com.thanggun99.khachhang.model.entity.Mon;
import com.thanggun99.khachhang.presenter.KhachHangPresenter;

public class OrderMonDialog extends BaseDialog {
    private final Context context;
    private final KhachHangPresenter khachHangPresenter;
    private TextView tvTenMon;
    private TextView tvDVT;
    private EditText edtSoLuong;
    private ImageButton btnCong, btnTru;
    private ImageView ivMon;
    private RatingBar ratingBar;
    private TextView tvRatingPoint;

    public OrderMonDialog(Context context, KhachHangPresenter khachHangPresenter) {
        super(context);
        this.context = context;
        this.khachHangPresenter = khachHangPresenter;
        setContentView(R.layout.dialog_order_mon);

        tvRatingPoint = (TextView) findViewById(R.id.tv_point_rating);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        tvDVT = (TextView) findViewById(R.id.tv_don_vi_tinh);
        tvDVT.setMovementMethod(new ScrollingMovementMethod());
        edtSoLuong = (EditText) findViewById(R.id.edt_so_luong);
        btnCong = (ImageButton) findViewById(R.id.btn_cong);
        btnTru = (ImageButton) findViewById(R.id.btn_tru);
        btnOk = (Button) findViewById(R.id.btn_ok);
        btnCancle = (Button) findViewById(R.id.btn_cancel);
        ivMon = (ImageView) findViewById(R.id.iv_mon);
        tvTenMon = (TextView) findViewById(R.id.tv_message);
        tvTenMon.setMovementMethod(new ScrollingMovementMethod());

        btnOk.setOnClickListener(this);
        btnCong.setOnClickListener(this);
        btnTru.setOnClickListener(this);
        btnCancle.setOnClickListener(this);
    }

    public void setContent(Mon mon) {
        ratingBar.setRating(mon.getRating() / mon.getPersonRating());
        tvRatingPoint.setText(mon.getRatingPoint());
        edtSoLuong.setText("1");
        tvTenMon.setText(mon.getTenMon());
        tvDVT.setText(mon.getDonViTinh());
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
