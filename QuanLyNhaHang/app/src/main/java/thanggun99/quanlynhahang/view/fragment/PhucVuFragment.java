package thanggun99.quanlynhahang.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TableRow;
import android.widget.TextView;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.model.entity.Ban;
import thanggun99.quanlynhahang.model.entity.HoaDon;
import thanggun99.quanlynhahang.model.entity.NhomMon;
import thanggun99.quanlynhahang.presenter.phucvu.PhucVuPresenter;
import thanggun99.quanlynhahang.util.Utils;
import thanggun99.quanlynhahang.view.adapter.BanAdapter;
import thanggun99.quanlynhahang.view.adapter.NhomMonAdapter;
import thanggun99.quanlynhahang.view.adapter.ThucDonAdapter;
import thanggun99.quanlynhahang.view.adapter.ThucDonOrderAdapter;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


/**
 * A simple {@link Fragment} subclass.
 */
public class PhucVuFragment extends Fragment implements PhucVuPresenter.PhucVuView, View.OnClickListener {
    private RecyclerView listViewBan, listViewThucDonOrder;
    private Button btnThucDon, btnSale, btnDatBan, btnTinhTien;
    private View view;
    private ListView listViewNhomMon;
    private RecyclerView listViewThucDon;
    private TableRow tableRow;
    private android.widget.SearchView edtTimKiemMon;
    private TextView tvTenBan, tvTrangThai, tvTongTien, tvGioDen, tvTenLoai;
    private DrawerLayout drawerLayout;
    private LinearLayout layoutThongTinBan, layoutThucDon;
    private PhucVuPresenter phucVuPresenter;
    private PopupMenu popupMenu;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sell, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        phucVuPresenter = new PhucVuPresenter(this, getContext());
        findViews();
        setEvents();
        phucVuPresenter.loadDatas();
    }

    private void setEvents() {
        btnThucDon.setOnClickListener(this);
        btnSale.setOnClickListener(this);
        btnTinhTien.setOnClickListener(this);
        btnDatBan.setOnClickListener(this);

        edtTimKiemMon.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                edtTimKiemMon.onActionViewCollapsed();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                phucVuPresenter.findThucDon(newText);
                return true;
            }
        });

        edtTimKiemMon.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    tvTenLoai.setVisibility(GONE);
                } else {
                    edtTimKiemMon.onActionViewCollapsed();
                    tvTenLoai.setText(getResources().getString(R.string.thuc_don));
                    tvTenLoai.setVisibility(VISIBLE);
                }
            }
        });

        tvTenBan.setOnClickListener(this);
        popupMenu = new PopupMenu(getContext(), tvTenBan);
        popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.btn_cancle) {
                    phucVuPresenter.onClickHuyBan();
                    return true;
                }
                return false;
            }
        });
    }

    private void findViews() {
        btnThucDon = (Button) view.findViewById(R.id.btn_thuc_don);
        btnSale = (Button) view.findViewById(R.id.btn_sale);
        btnDatBan = (Button) view.findViewById(R.id.btn_dat_ban);
        btnTinhTien = (Button) view.findViewById(R.id.btn_tinh_tien);
        tvGioDen = (TextView) view.findViewById(R.id.tv_gio_den);
        tvTongTien = (TextView) view.findViewById(R.id.tv_tong_tien);
        layoutThongTinBan = (LinearLayout) view.findViewById(R.id.ln_thong_tin_ban);
        listViewBan = (RecyclerView) view.findViewById(R.id.list_ban);
        listViewThucDonOrder = (RecyclerView) view.findViewById(R.id.list_thuc_don_order);
        tvTenBan = (TextView) view.findViewById(R.id.tv_ten_ban);
        tvTrangThai = (TextView) view.findViewById(R.id.tv_trang_thai);
        drawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        layoutThucDon = (LinearLayout) view.findViewById(R.id.layout_thuc_don);
        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) layoutThucDon.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels / 2;
        layoutThucDon.setLayoutParams(params);
        listViewNhomMon = (ListView) view.findViewById(R.id.list_nhom_mon);
        tableRow = (TableRow) view.findViewById(R.id.tbr);
        listViewThucDon = (RecyclerView) view.findViewById(R.id.list_thuc_don);
        tvTenLoai = (TextView) view.findViewById(R.id.tv_ten_loai);
        edtTimKiemMon = (android.widget.SearchView) view.findViewById(R.id.edt_tim_kiem_mon);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_tinh_tien:
                phucVuPresenter.onClickTinhTien();
                break;
            case R.id.tv_ten_ban:
                if (!tvTrangThai.getText().equals(Utils.getStringByRes(R.string.trong)))
                    popupMenu.show();
                break;
            case R.id.btn_sale:
                phucVuPresenter.onClickSale();
                break;
            case R.id.btn_thuc_don:
                drawerLayout.openDrawer(GravityCompat.END);
                break;
            case R.id.btn_dat_ban:
                phucVuPresenter.onClickDatBan();
                break;
            default:
                break;
        }
    }

    @Override
    public void showTongTien(int tongTien) {
        tvTongTien.setText(Utils.formatMoney(tongTien));
    }

    @Override
    public void showGiamGia(int giamGia) {
        if (giamGia > 0) btnSale.setText(giamGia + "%");
        else btnSale.setText(Utils.getStringByRes(R.string.sale));
    }

    @Override
    public void showDatas(BanAdapter banAdapter, ThucDonOrderAdapter thucDonOrderAdapter, ThucDonAdapter thucDonAdapter, NhomMonAdapter nhomMonAdapter) {
        listViewNhomMon.setAdapter(nhomMonAdapter);
        listViewNhomMon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (edtTimKiemMon.hasFocus()) edtTimKiemMon.onActionViewCollapsed();

                phucVuPresenter.onClickNhomMon(position);
            }
        });

        listViewThucDon.setLayoutManager(new LinearLayoutManager(getContext()));
        listViewThucDon.setAdapter(thucDonAdapter);

        listViewBan.setLayoutManager(new GridLayoutManager(getContext(), 4));
        listViewBan.setAdapter(banAdapter);

        listViewThucDonOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        listViewThucDonOrder.setAdapter(thucDonOrderAdapter);
    }

    @Override
    public void showNhomMon(NhomMon nhomMon) {
        tvTenLoai.setText(nhomMon.getTenLoai());
        tableRow.setBackgroundColor(nhomMon.getMauSac());
    }

    @Override
    public void showBanDatTruoc(Ban ban) {
        tvTenBan.setText(ban.getTenBan());
        tvTrangThai.setText(ban.getStringTrangThai());
        layoutThongTinBan.setVisibility(GONE);
        btnDatBan.setVisibility(GONE);
    }

    @Override
    public void showBanTrong(Ban ban) {
        tvTenBan.setText(ban.getTenBan());
        tvTrangThai.setText(ban.getStringTrangThai());
        layoutThongTinBan.setVisibility(GONE);
        btnDatBan.setVisibility(VISIBLE);
        btnDatBan.setText(Utils.getStringByRes(R.string.dat_ban));
        btnDatBan.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dat_truoc, 0, 0, 0);
    }

    @Override
    public void showBanPhucVu(HoaDon hoaDon) {
        tvTenBan.setText(hoaDon.getBan().getTenBan());
        tvTrangThai.setText(hoaDon.getBan().getStringTrangThai());
        layoutThongTinBan.setVisibility(VISIBLE);
        btnDatBan.setVisibility(GONE);

        tvGioDen.setText(Utils.formatDate(hoaDon.getGioDen()));
        tvTongTien.setText(Utils.formatMoney(hoaDon.getTongTien()));
        btnSale.setText(hoaDon.getStringGiamGia());
    }
}
