package com.thanggun99.khachhang.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thanggun99.khachhang.R;

/**
 * Created by Thanggun99 on 18/02/2017.
 */

public abstract class BaseDialog extends Dialog implements View.OnClickListener{
    protected Button btnOk, btnCancle;
    protected TextView tvTitle;

    public BaseDialog(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_cancel) dismiss();
    }
}
