package com.thanggun99.khachhang.view.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.thanggun99.khachhang.R;

/**
 * Created by Thanggun99 on 06/03/2017.
 */

public class ConfirmDialog extends BaseDialog {
    private TextView tvNotifi;
    private OnClickOkListener onClickOkListener;

    public ConfirmDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_confirm);
        setCancelable(true);

        tvNotifi = (TextView) findViewById(R.id.tv_notifi);
        btnCancle = (Button) findViewById(R.id.btn_cancel);
        btnCancle.setOnClickListener(this);

        btnOk = (Button) findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.btn_ok) {
            onClickOkListener.onClickOk();
            dismiss();
        }
    }

    public void showConfirm(String message){
        tvNotifi.setText(message);
        show();
    }

    public void setOnOkListener(OnClickOkListener onClickOkListener) {
        this.onClickOkListener = onClickOkListener;
    }

    public interface OnClickOkListener {
        void onClickOk();
    }

}
