package com.thanggun99.khachhang.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thanggun99.khachhang.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Thanggun99 on 18/02/2017.
 */

public abstract class BaseDialog extends Dialog implements View.OnClickListener{
    protected
    @Nullable
    @BindView(R.id.btn_ok)
    Button btnOk;

    protected
    @Nullable
    @BindView(R.id.btn_cancel)
    Button btnCancel;

    protected
    @Nullable
    @BindView(R.id.tv_title)
    TextView tvTitle;

    public BaseDialog(Context context, int layoutResID) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(layoutResID);
        ButterKnife.bind(this);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_cancel) dismiss();
    }
}
