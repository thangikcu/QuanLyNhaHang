package com.thanggun99.khachhang.view.dialog;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.thanggun99.khachhang.R;
import com.thanggun99.khachhang.model.entity.DatBan;

import butterknife.BindView;


public class ThongTinDatBanDialog extends BaseDialog {
    @BindView(R.id.tv_gio_den)
    TextView tvGioDen;
    @BindView(R.id.tv_yeu_cau)
    TextView tvYeuCau;

    public ThongTinDatBanDialog(Context context) {
        super(context, R.layout.dialog_thong_tin_dat_ban);

        tvYeuCau.setMovementMethod(new ScrollingMovementMethod());
    }

    public void setContent(DatBan datBan) {
        if (datBan != null) {
            tvGioDen.setText(datBan.getGioDen());
            tvYeuCau.setText(datBan.getYeuCau());
            show();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }
}
