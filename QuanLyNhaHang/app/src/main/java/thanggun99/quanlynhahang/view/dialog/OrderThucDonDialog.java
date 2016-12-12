package thanggun99.quanlynhahang.view.dialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.model.entity.Ban;
import thanggun99.quanlynhahang.model.entity.HoaDon;
import thanggun99.quanlynhahang.model.entity.ThucDon;
import thanggun99.quanlynhahang.model.entity.ThucDonOrder;
import thanggun99.quanlynhahang.model.phucvu.MainPhucVuManager;

/**
 * Created by Thanggun99 on 22/11/2016.
 */

public class OrderThucDonDialog extends BaseDialog{
    private TextView tvTenMon;
    private TextView tvDVT;
    private EditText edtSoLuong;
    private ImageButton btnCong, btnTru;
    private ImageView ivThucDon;
    private MainPhucVuManager mainPhucVuManager;

    public OrderThucDonDialog(Context context, MainPhucVuManager mainPhucVuManager) {
        super(context);
        setContentView(R.layout.dialog_order_thuc_don);

        this.mainPhucVuManager = mainPhucVuManager;
        tvTitle = (TextView) findViewById(R.id.tv_ten_ban);
        tvDVT = (TextView) findViewById(R.id.tv_don_vi_tinh);
        edtSoLuong = (EditText) findViewById(R.id.edt_so_luong);
        btnCong = (ImageButton) findViewById(R.id.btn_cong);
        btnTru = (ImageButton) findViewById(R.id.btn_tru);
        btnOk = (Button) findViewById(R.id.btn_ok);
        btnCancle = (Button) findViewById(R.id.btn_cancle);
        ivThucDon = (ImageView) findViewById(R.id.iv_thuc_don);
        tvTenMon = (TextView) findViewById(R.id.tv_ten_mon);

        btnCong.setOnClickListener(this);
        btnTru.setOnClickListener(this);
        btnCancle.setOnClickListener(this);
    }

    public void setContent(String tenBan,String tenMon, Bitmap bitmap, String dvt) {
        tvTitle.setText(tenBan);
        tvTenMon.setText(tenMon);
        tvDVT.setText(dvt);
        if (bitmap != null){
            ivThucDon.setImageBitmap(bitmap);
        }else
            ivThucDon.setImageResource(R.drawable.ic_food);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_cong:
                int sl = 1;
                if (!edtSoLuong.getText().toString().isEmpty()) {
                    sl = Integer.parseInt(edtSoLuong.getText().toString());
                    sl++;
                }
                edtSoLuong.setText(sl + "");
                break;
            case R.id.btn_tru:
                int sl2 = 1;
                if (!edtSoLuong.getText().toString().isEmpty()) {
                    sl2 = Integer.parseInt(edtSoLuong.getText().toString());
                    if(sl2 > 1) sl2--;
                }
                edtSoLuong.setText(sl2 + "");
                break;
            default:
                break;
        }
    }

    public void setContentUpdate(String tenBan, final ThucDonOrder thucDonOrder) {
        setContent(tenBan, thucDonOrder.getTenMon(), thucDonOrder.getHinhAnh(), thucDonOrder.getDonViTinh());
        edtSoLuong.setText(thucDonOrder.getSoLuong()+"");
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtSoLuong.getText() != null && Integer.parseInt(edtSoLuong.getText().toString()) != thucDonOrder.getSoLuong()){
                    ThucDonOrder orderUpdate = new ThucDonOrder();
                    orderUpdate.setMaChitietHD(thucDonOrder.getMaChitietHD());
                    orderUpdate.setSoLuong(Integer.parseInt(edtSoLuong.getText().toString()));
                    orderUpdate.setTenMon(thucDonOrder.getTenMon());

                    mainPhucVuManager.updateThucDonOrder(orderUpdate);
                }
            }
        });
        show();
    }

    public void setContentThem(final HoaDon hoaDon, final ThucDon thucDon) {
        setContent(hoaDon.getBan().getTenBan(), thucDon.getTenMon(), thucDon.getHinhAnh(), thucDon.getDonViTinh());
        edtSoLuong.setText("1");
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtSoLuong.getText() != null){
                    ThucDonOrder thucDonOrderNew = new ThucDonOrder();
                    thucDonOrderNew.setSoLuong(Integer.parseInt(edtSoLuong.getText().toString()));
                    thucDonOrderNew.setMaMon(thucDon.getMaMon());
                    thucDonOrderNew.setTenMon(thucDon.getTenMon());

                    mainPhucVuManager.themThucDonOrder(thucDonOrderNew);
                }
            }
        });
        show();
    }

    public void setContentTaoMoi(final Ban ban , final ThucDon thucDon) {
        setContent(ban.getTenBan(), thucDon.getTenMon(), thucDon.getHinhAnh(), thucDon.getDonViTinh());
        edtSoLuong.setText("1");
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtSoLuong.getText() != null) {
                    String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                    ThucDonOrder thucDonOrderNew = new ThucDonOrder();
                    thucDonOrderNew.setSoLuong(Integer.parseInt(edtSoLuong.getText().toString()));
                    thucDonOrderNew.setMaMon(thucDon.getMaMon());
                    thucDonOrderNew.setTenMon(thucDon.getTenMon());

                    HoaDon hoaDonNew = new HoaDon();
                    hoaDonNew.setGioDen(date);
                    hoaDonNew.setBan(ban);

                    mainPhucVuManager.taoMoiHoaDon(hoaDonNew, thucDonOrderNew);
                }
            }
        });
        show();
    }
}
