package com.thanggun99.khachhang.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.thanggun99.khachhang.R;

/**
 * Created by Thanggun99 on 18/02/2017.
 */

public class OtherPeopleLoginDialog extends BaseDialog {

    public OtherPeopleLoginDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_other_people_login);
        setCancelable(true);

        btnOk = (Button) findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        dismiss();
    }
}
