package com.thanggun99.khachhang.view.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.thanggun99.khachhang.R;
import com.thanggun99.khachhang.presenter.KhachHangPresenter;

public class ErrorDialog extends BaseDialog{

    private final Context context;
    private final KhachHangPresenter khachHangPresenter;

    public ErrorDialog(Context context, KhachHangPresenter khachHangPresenter) {
        super(context);
        this.context = context;
        this.khachHangPresenter = khachHangPresenter;
        setContentView(R.layout.dialog_error);
        setCancelable(false);

        btnOk = (Button) findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        khachHangPresenter.reLoadThongTinPhucVu();
        dismiss();
    }
}
