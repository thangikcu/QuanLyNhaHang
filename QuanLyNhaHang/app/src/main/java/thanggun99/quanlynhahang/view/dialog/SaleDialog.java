package thanggun99.quanlynhahang.view.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.model.entity.HoaDon;
import thanggun99.quanlynhahang.model.phucvu.MainPhucVuManager;

/**
 * Created by Thanggun99 on 10/12/2016.
 */

public class SaleDialog extends BaseDialog {
    private EditText edtSale;
    private int giamGia;
    private MainPhucVuManager mainPhucVuManager;


    public SaleDialog(Context context, MainPhucVuManager mainPhucVuManager) {
        super(context);
        setContentView(R.layout.dialog_sale);

        this.mainPhucVuManager = mainPhucVuManager;
        tvTitle = (TextView) findViewById(R.id.tv_ten_ban);
        edtSale = (EditText) findViewById(R.id.edt_sale);
        btnCancle = (Button) findViewById(R.id.btn_cancle);
        btnOk = (Button) findViewById(R.id.btn_ok);

        btnCancle.setOnClickListener(this);
        btnOk.setOnClickListener(this);
    }

    public void setContent(HoaDon hoaDon) {
        if (hoaDon.getThucDonOrders().size() > 0){
            tvTitle.setText(hoaDon.getBan().getTenBan());
            if (hoaDon.getGiamGia() > 0) {
                edtSale.setText(hoaDon.getGiamGia() + "");
                giamGia = hoaDon.getGiamGia();
            }else {
                giamGia = 0;
                edtSale.setText(null);
            }
            show();
        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.btn_ok) {
            if (!TextUtils.isEmpty(edtSale.getText()) && Integer.parseInt(edtSale.getText().toString()) <= 100 && Integer.parseInt(edtSale.getText().toString()) != giamGia) {
                mainPhucVuManager.saleHoaDon(Integer.parseInt(edtSale.getText().toString()));
            }
        }
    }
}
