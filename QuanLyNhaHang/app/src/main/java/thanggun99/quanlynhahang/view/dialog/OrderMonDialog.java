package thanggun99.quanlynhahang.view.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.model.entity.Mon;
import thanggun99.quanlynhahang.presenter.PhucVuPresenter;

/**
 * Created by Thanggun99 on 22/11/2016.
 */

public class OrderMonDialog extends BaseDialog {
    private TextView tvTenMon;
    private TextView tvDVT;
    private EditText edtSoLuong;
    private ImageButton btnCong, btnTru;
    private ImageView ivMon;
    private PhucVuPresenter phucVuPresenter;

    public OrderMonDialog(Context context, PhucVuPresenter phucVuPresenter) {
        super(context);
        setContentView(R.layout.dialog_order_mon);

        this.phucVuPresenter = phucVuPresenter;
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvDVT = (TextView) findViewById(R.id.tv_don_vi_tinh);
        tvDVT.setMovementMethod(new ScrollingMovementMethod());
        edtSoLuong = (EditText) findViewById(R.id.edt_so_luong);
        btnCong = (ImageButton) findViewById(R.id.btn_cong);
        btnTru = (ImageButton) findViewById(R.id.btn_tru);
        btnOk = (Button) findViewById(R.id.btn_ok);
        btnCancle = (Button) findViewById(R.id.btn_cancel);
        ivMon = (ImageView) findViewById(R.id.iv_mon);
        tvTenMon = (TextView) findViewById(R.id.tv_message);
        tvTenMon.setMovementMethod(new ScrollingMovementMethod());

        btnOk.setOnClickListener(this);
        btnCong.setOnClickListener(this);
        btnTru.setOnClickListener(this);
        btnCancle.setOnClickListener(this);
    }

    public void setContent(String tenBan, Mon mon) {
        edtSoLuong.setText("1");
        tvTitle.setText(tenBan);
        tvTenMon.setText(mon.getTenMon());
        tvDVT.setText(mon.getDonViTinh());
        Glide.with(getContext())
                .load(mon.getHinhAnh())
                .placeholder(R.drawable.ic_food)
                .error(R.drawable.ic_food)
                .into(ivMon);
        show();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_ok:
                if (!TextUtils.isEmpty(edtSoLuong.getText())) {
                    try {
                        phucVuPresenter.orderMon(Integer.parseInt(edtSoLuong.getText().toString()));

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }

                    dismiss();
                }
                break;
            case R.id.btn_cong:
                int sl = 1;
                if (!edtSoLuong.getText().toString().isEmpty()) {
                    try {
                        sl = Integer.parseInt(edtSoLuong.getText().toString());
                        sl++;

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }

                }
                edtSoLuong.setText(sl + "");
                break;
            case R.id.btn_tru:
                int sl2 = 1;
                if (!edtSoLuong.getText().toString().isEmpty()) {
                    sl2 = Integer.parseInt(edtSoLuong.getText().toString());
                    if (sl2 > 1) sl2--;
                }
                edtSoLuong.setText(sl2 + "");
                break;
            default:
                break;
        }
    }

}
