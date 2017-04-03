package thanggun99.quanlynhahang.view.fragment;


import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.model.Database;
import thanggun99.quanlynhahang.model.entity.HoaDon;
import thanggun99.quanlynhahang.util.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class ThongKeFragment extends BaseFragment {
    private TextView tvSoLuongKhachHang, tvSoLuongHoaDonTinhTien, tvSoLuongHoaDonChuaTinhTien,
            tvTongTien, tvSoLuongMon, tvSoLuongNhomMon, tvSoLuongDatBan;
    private Database database;
    private ArrayList<HoaDon> hoaDonList;

    public ThongKeFragment(Database database) {
        super(R.layout.fragment_thong_ke);
        this.database = database;

        hoaDonList = database.getHoaDonTinhTienList();
    }

    @Override
    public void findViews(View view) {
        tvSoLuongHoaDonTinhTien = (TextView) view.findViewById(R.id.tv_so_luong_hoa_don_tinh_tien);
        tvSoLuongHoaDonChuaTinhTien = (TextView) view.findViewById(R.id.tv_so_luong_hoa_don_chua_tinh_tien);
        tvSoLuongKhachHang = (TextView) view.findViewById(R.id.tv_so_luong_khach_hang);
        tvTongTien = (TextView) view.findViewById(R.id.tv_tong_tien);
        tvSoLuongMon = (TextView) view.findViewById(R.id.tv_so_luong_mon);
        tvSoLuongNhomMon = (TextView) view.findViewById(R.id.tv_so_luong_nhom_mon);
        tvSoLuongDatBan = (TextView) view.findViewById(R.id.tv_so_luong_ban_dat_truoc);

    }

    @Override
    public void initComponents() {

    }

    @Override
    public void setEvents() {
        showInfo();
    }

    public void showInfo() {
        tvSoLuongHoaDonChuaTinhTien.setText(database.getHoaDonChuaTinhTienList().size() + " hoá đơn");
        tvSoLuongHoaDonTinhTien.setText(hoaDonList.size() + " hoá đơn");
        tvSoLuongKhachHang.setText(database.getKhachHangList().size() + " khách hàng");
        tvSoLuongMon.setText(database.getMonList().size() + " món");
        tvSoLuongNhomMon.setText(database.getNhomMonList().size() + " nhóm");
        tvSoLuongDatBan.setText((database.getDatBanChuaSetBanList().size() + database.getDatBanChuaTinhTienList().size()) + " đặt trước");

        int tongTien = 0;
        for (HoaDon hoaDon : hoaDonList) {
            tongTien += hoaDon.getTongTien();
        }
        tvTongTien.setText(Utils.formatMoney(tongTien));
    }
}
