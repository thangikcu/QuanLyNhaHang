package thanggun99.quanlynhahang.view.dialog;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.model.entity.DatBan;

/**
 * Created by Thanggun99 on 24/12/2016.
 */

public class ThongTinDatBanDialog extends BaseDialog {
    private TextView tvTenKhachHang, tvSoDienThoai, tvKhoangGioDen, tvYeuCau;

    public ThongTinDatBanDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_thong_tin_dat_ban);

        tvTenKhachHang = (TextView) findViewById(R.id.tv_ten_khach_hang);
        tvSoDienThoai = (TextView) findViewById(R.id.tv_so_dien_thoai);
        tvKhoangGioDen = (TextView) findViewById(R.id.tv_gio_den);
        tvYeuCau = (TextView) findViewById(R.id.tv_yeu_cau);
        tvYeuCau.setMovementMethod(new ScrollingMovementMethod());
    }

    public void setContent(DatBan datBan) {
        if (datBan != null) {
            tvTenKhachHang.setText(datBan.getTenKhachHang());
            tvSoDienThoai.setText(datBan.getSoDienThoai());
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
