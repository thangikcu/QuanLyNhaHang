package thanggun99.quanlynhahang.view.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.model.entity.HoaDon;
import thanggun99.quanlynhahang.presenter.PhucVuPresenter;
import thanggun99.quanlynhahang.util.Utils;

/**
 * Created by Thanggun99 on 12/12/2016.
 */

public class TinhTienDialog extends BaseDialog {
    private PhucVuPresenter phucVuPresenter;
    private TextView tvTienMon, tvSoLuong, tvTienGiamGia, tvGiamGia, tvTongTien, tvTienTraLai;
    private SearchView edtTienKhachDua;
    private int tongtien;

    public TinhTienDialog(Context context, PhucVuPresenter phucVuPresenter) {
        super(context);
        setContentView(R.layout.dialog_tinh_tien);

        this.phucVuPresenter = phucVuPresenter;

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTienMon = (TextView) findViewById(R.id.tv_tien_mon);
        tvSoLuong = (TextView) findViewById(R.id.tv_so_luong);
        tvGiamGia = (TextView) findViewById(R.id.tv_giam_gia);
        tvTienGiamGia = (TextView) findViewById(R.id.tv_tien_giam_gia);
        tvTongTien = (TextView) findViewById(R.id.tv_tong_tien);
        tvTienTraLai = (TextView) findViewById(R.id.tv_tien_tra_lai);

        btnOk = (Button) findViewById(R.id.btn_ok);
        btnCancle = (Button) findViewById(R.id.btn_cancel);
        btnOk.setOnClickListener(this);
        btnCancle.setOnClickListener(this);

        edtTienKhachDua = (SearchView) findViewById(R.id.edt_tien_khach_dua);
        edtTienKhachDua.setIconifiedByDefault(false);
        edtTienKhachDua.setIconified(false);
        int icSearchID = context.getResources().getIdentifier("android:id/search_mag_icon", null, null);
        ImageView imageView = (ImageView) edtTienKhachDua.findViewById(icSearchID);
        imageView.setVisibility(View.GONE);
        edtTienKhachDua.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText) && newText.length() < 10) {
                    try {
                        int tienKhachDua = Integer.parseInt(newText);

                        if (tienKhachDua > tongtien) {
                            tvTienTraLai.setText(Utils.formatMoney(tienKhachDua - tongtien));
                            return true;
                        } else tvTienTraLai.setText("");

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }

                } else tvTienTraLai.setText("");
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.btn_ok) {
            phucVuPresenter.tinhTien();
            dismiss();
        }
    }

    public void setContent(HoaDon hoaDon) {
        if (hoaDon.getMonOrderList().size() > 0) {
            this.tongtien = hoaDon.getTongTien();
            edtTienKhachDua.setQuery("", false);
            tvTitle.setText(hoaDon.getBan().getTenBan());
            tvTienMon.setText(Utils.formatMoney(hoaDon.getTienMon()));
            tvSoLuong.setText("(" + hoaDon.getSoMon() + ")");
            tvTongTien.setText(Utils.formatMoney(hoaDon.getTongTien()));
            if (hoaDon.getGiamGia() > 0) {
                tvTienGiamGia.setText(Utils.formatMoney(hoaDon.getTienGiamGia()));
                tvGiamGia.setText("(" + hoaDon.getGiamGia() + "%)");
            } else {
                tvTienGiamGia.setText("...");
                tvGiamGia.setText("");
            }
            show();
        }
    }
}
