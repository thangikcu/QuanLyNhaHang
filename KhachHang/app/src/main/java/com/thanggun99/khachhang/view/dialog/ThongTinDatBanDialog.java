package com.thanggun99.khachhang.view.dialog;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.thanggun99.khachhang.R;
import com.thanggun99.khachhang.model.entity.DatBan;


public class ThongTinDatBanDialog extends BaseDialog {
    private TextView tvKhoangGioDen, tvYeuCau;

    public ThongTinDatBanDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_thong_tin_dat_ban);

        tvKhoangGioDen = (TextView) findViewById(R.id.tv_gio_den);
        tvYeuCau = (TextView) findViewById(R.id.tv_yeu_cau);
        tvYeuCau.setMovementMethod(new ScrollingMovementMethod());
    }

    public void setContent(DatBan datBan) {
        if (datBan != null) {
            tvKhoangGioDen.setText(datBan.getGioDen());
            tvYeuCau.setText(datBan.getYeuCau());
            show();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }
}
