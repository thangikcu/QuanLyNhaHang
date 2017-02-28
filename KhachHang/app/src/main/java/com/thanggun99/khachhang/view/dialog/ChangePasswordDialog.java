package com.thanggun99.khachhang.view.dialog;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.thanggun99.khachhang.R;

/**
 * Created by Thanggun99 on 28/02/2017.
 */

public class ChangePasswordDialog extends BaseDialog {
    EditText edtPassword, edtNewPassword, edtRePassword;

    public ChangePasswordDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_change_password);
        setCancelable(true);

        edtPassword = (EditText) findViewById(R.id.edt_password);
        edtNewPassword = (EditText) findViewById(R.id.edt_new_password);
        edtRePassword = (EditText) findViewById(R.id.edt_re_password);


        findViewById(R.id.btn_ok).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.btn_ok){

        }
    }
}
