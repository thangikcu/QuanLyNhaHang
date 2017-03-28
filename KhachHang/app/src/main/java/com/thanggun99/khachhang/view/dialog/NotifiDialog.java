package com.thanggun99.khachhang.view.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
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
        btnCancle = (Button) findViewById(R.id.btn_cancel);
        btnCancle.setOnClickListener(this);
    }

    public void notifi(String message) {
        tvNotifi.setText(message);
        show();
    }
}
