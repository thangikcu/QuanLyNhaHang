package com.thanggun99.khachhang.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.thanggun99.khachhang.R;

/**
 * Created by Thanggun99 on 18/02/2017.
 */

public class NotifiDialog extends BaseDialog {
    private TextView tvNotifi;

    public NotifiDialog(@NonNull Context context) {
        super(context);
        getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        setContentView(R.layout.dialog_notifi);
        setCancelable(true);

        tvNotifi = (TextView) findViewById(R.id.tv_notifi);
        btnOk = (Button) findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        dismiss();
    }

    public void notifi(String message) {
        tvNotifi.setText(message);
        show();
    }
}
