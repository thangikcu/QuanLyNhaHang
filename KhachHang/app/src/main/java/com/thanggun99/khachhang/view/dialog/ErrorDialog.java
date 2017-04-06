package com.thanggun99.khachhang.view.dialog;

import android.content.Context;
import android.view.View;

import com.thanggun99.khachhang.R;
import com.thanggun99.khachhang.presenter.KhachHangPresenter;

public class ErrorDialog extends BaseDialog{

    private final KhachHangPresenter khachHangPresenter;

    public ErrorDialog(Context context, KhachHangPresenter khachHangPresenter) {
        super(context, R.layout.dialog_error);
        this.khachHangPresenter = khachHangPresenter;
        setCancelable(false);

        btnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        khachHangPresenter.reLoadThongTinPhucVu();
        dismiss();
    }
}
