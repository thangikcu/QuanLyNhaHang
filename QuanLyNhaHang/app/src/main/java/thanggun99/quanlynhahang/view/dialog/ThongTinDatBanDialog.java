package thanggun99.quanlynhahang.view.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.model.entity.DatTruoc;

/**
 * Created by Thanggun99 on 24/12/2016.
 */

public class ThongTinDatBanDialog extends BaseDialog {
    private TextView tvTenKhachHang, tvSoDienThoai, tvKhoangGioDen, tvGhiChu;

    public ThongTinDatBanDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_thong_tin_dat_ban);

        tvTenKhachHang = (TextView) findViewById(R.id.tv_ten_khach_hang);
        tvSoDienThoai = (TextView) findViewById(R.id.tv_so_dien_thoai);
        tvKhoangGioDen = (TextView) findViewById(R.id.tv_khoang_gio_den);
        tvGhiChu = (TextView) findViewById(R.id.tv_ghi_chu);
    }

    public void setContent(DatTruoc datTruoc) {
        if (datTruoc != null) {
            tvTenKhachHang.setText(datTruoc.getTenKhachHang());
            tvSoDienThoai.setText(datTruoc.getSoDienThoai());
            tvKhoangGioDen.setText(datTruoc.getGioDen());
            tvGhiChu.setText(datTruoc.getGhiChu());
            show();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }
}
