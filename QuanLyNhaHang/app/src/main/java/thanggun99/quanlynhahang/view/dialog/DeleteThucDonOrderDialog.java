package thanggun99.quanlynhahang.view.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.presenter.PhucVuPresenter;
import thanggun99.quanlynhahang.util.Utils;

/**
 * Created by Thanggun99 on 28/11/2016.
 */

public class DeleteThucDonOrderDialog extends BaseDialog {
    private TextView tvTenMon;
    private PhucVuPresenter phucVuPresenter;

    public DeleteThucDonOrderDialog(Context context, PhucVuPresenter phucVuPresenter) {
        super(context);
        setContentView(R.layout.dialog_delete_thuc_don_order);

        this.phucVuPresenter = phucVuPresenter;
        tvTenMon = (TextView) findViewById(R.id.tv_ten_mon);

        tvTitle = (TextView) findViewById(R.id.tv_ten_ban);

        btnOk = (Button) findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(this);

        btnCancle = (Button) findViewById(R.id.btn_cancle);
        btnCancle.setOnClickListener(this);
    }

    public void setContent(String tenBan, String tenMon) {
        tvTitle.setText(tenBan);
        tvTenMon.setText(String.format(Utils.getStringByRes(R.string.huy_order), tenMon));
        show();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.btn_ok) {
            phucVuPresenter.deleteThucDonOrder();
            dismiss();
        }
    }
}
