package com.thanggun99.khachhang.view.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.WindowManager;
import android.widget.TextView;

import com.thanggun99.khachhang.R;

import butterknife.BindView;

/**
 * Created by Thanggun99 on 18/02/2017.
 */

public class NotifiDialog extends BaseDialog {
    @BindView(R.id.tv_notifi)
    TextView tvNotifi;

    public NotifiDialog(@NonNull Context context) {
        super(context, R.layout.dialog_notifi);
        getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        setCancelable(true);

        btnCancel.setOnClickListener(this);
    }

    public void notifi(String message) {
        tvNotifi.setText(message);
        show();
    }
}
