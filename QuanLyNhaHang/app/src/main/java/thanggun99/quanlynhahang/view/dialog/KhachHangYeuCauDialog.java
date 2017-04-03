package thanggun99.quanlynhahang.view.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.adapter.MonKhachHangYeuCauAdapter;
import thanggun99.quanlynhahang.adapter.YeuCauAdapter;
import thanggun99.quanlynhahang.model.entity.Ban;
import thanggun99.quanlynhahang.model.entity.YeuCau;
import thanggun99.quanlynhahang.presenter.PhucVuPresenter;


public class KhachHangYeuCauDialog extends BaseDialog implements PhucVuPresenter.YeuCauView {

    private Context context;
    private PhucVuPresenter phucVuPresenter;
    private RecyclerView yeuCauRecyclerView;
    private YeuCauAdapter yeuCauAdapter;
    private LinearLayout chiTietYeuCauLayout, yeuCauLayout;
    private TextView tvTenKhachHang, tvSoDienThoai, tvTenBan;
    private Button btnClose;

    private RecyclerView monOrderRecyclerView;
    private MonKhachHangYeuCauAdapter monKhachHangYeuCauAdapter;
    private Spinner banSpinner;
    private ArrayList<Ban> banList;
    private ArrayAdapter<String> banAdapter;
    private Ban ban;

    @SuppressLint("NewApi")
    public KhachHangYeuCauDialog(Context context, PhucVuPresenter phucVuPresenter) {
        super(context);
        this.context = context;
        this.phucVuPresenter = phucVuPresenter;
        setContentView(R.layout.dialog_khach_hang_yeu_cau);
        getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        setCancelable(true);

        chiTietYeuCauLayout = (LinearLayout) findViewById(R.id.layout_chi_tiet_yeu_cau);
        chiTietYeuCauLayout.setVisibility(View.GONE);

        monOrderRecyclerView = (RecyclerView) chiTietYeuCauLayout.findViewById(R.id.list_mon_order);
        monOrderRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        monKhachHangYeuCauAdapter = new MonKhachHangYeuCauAdapter(context);
        monOrderRecyclerView.setAdapter(monKhachHangYeuCauAdapter);

        banSpinner = (Spinner) chiTietYeuCauLayout.findViewById(R.id.spn_ban);
        tvTenBan = (TextView) chiTietYeuCauLayout.findViewById(R.id.tv_ten_ban);

        banList = new ArrayList<>();
        banAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, banList);

        banAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        banSpinner.setAdapter(banAdapter);
        banSpinner.setDropDownWidth(250);

        tvTenKhachHang = (TextView) chiTietYeuCauLayout.findViewById(R.id.tv_ten_khach_hang);
        tvSoDienThoai = (TextView) chiTietYeuCauLayout.findViewById(R.id.tv_so_dien_thoai);
        btnOk = (Button) chiTietYeuCauLayout.findViewById(R.id.btn_ok);
        btnCancle = (Button) chiTietYeuCauLayout.findViewById(R.id.btn_cancel);

        btnOk.setOnClickListener(this);
        btnCancle.setOnClickListener(this);


        yeuCauLayout = (LinearLayout) findViewById(R.id.layout_yeu_cau);
        yeuCauLayout.setVisibility(View.VISIBLE);

        btnClose = (Button) yeuCauLayout.findViewById(R.id.btn_close);
        btnClose.setOnClickListener(this);

        yeuCauRecyclerView = (RecyclerView) yeuCauLayout.findViewById(R.id.list_yeu_cau);
        yeuCauRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        phucVuPresenter.setYeuCauView(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                if (!tvTenBan.isShown()) {
                    ban = banList.get(banSpinner.getSelectedItemPosition());
                }
                phucVuPresenter.khachHangOrderMon(ban);
                dismiss();
                break;
            case R.id.btn_cancel:
                chiTietYeuCauLayout.setVisibility(View.GONE);
                yeuCauLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_close:
                dismiss();
                break;
            default:
                break;
        }
    }

    public void setYeuCauList() {
        yeuCauAdapter = new YeuCauAdapter(context, phucVuPresenter);
        yeuCauRecyclerView.setAdapter(yeuCauAdapter);
    }

    @Override
    public void showChiTietYeuCau(YeuCau yeuCau) {

        monKhachHangYeuCauAdapter.changeData(yeuCau.getMonYeuCauList());

        tvTenKhachHang.setText(yeuCau.getKhachHang().getHoTen());
        tvSoDienThoai.setText(yeuCau.getKhachHang().getSoDienThoai());

        yeuCauLayout.setVisibility(View.GONE);
        chiTietYeuCauLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void setBanList(ArrayList<Ban> banList) {
        this.banList.clear();
        for (Ban ban : banList) {
            if (ban.getTrangThai() == 0) {
                this.banList.add(ban);
            }
        }
        banAdapter.notifyDataSetChanged();

        tvTenBan.setVisibility(View.GONE);
        banSpinner.setVisibility(View.VISIBLE);

    }

    @Override
    public void showBan(Ban ban) {
        this.ban = ban;
        tvTenBan.setText(ban.getTenBan());

        tvTenBan.setVisibility(View.VISIBLE);
        banSpinner.setVisibility(View.INVISIBLE);
    }

    @Override
    public void notifyDatachange() {
        if (yeuCauAdapter != null) {

            yeuCauAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void notifyYeuCauChange(YeuCau yeuCau) {
        if (yeuCauAdapter != null) {

            yeuCauAdapter.notifyItemChanged(yeuCau);
            chiTietYeuCauLayout.setVisibility(View.GONE);
            yeuCauLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void notifyRemoveYeuCau(YeuCau yeuCau) {
        if (yeuCauAdapter != null) {

            yeuCauAdapter.notifyItemRemoved(yeuCau);
            chiTietYeuCauLayout.setVisibility(View.GONE);
            yeuCauLayout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void notifyAddYeuCau() {
        if (yeuCauAdapter == null) {
            setYeuCauList();
        } else {
            yeuCauAdapter.notifyItemInserted(0);
        }
        chiTietYeuCauLayout.setVisibility(View.GONE);
        yeuCauLayout.setVisibility(View.VISIBLE);
    }
}
