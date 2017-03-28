package thanggun99.quanlynhahang.view.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import thanggun99.quanlynhahang.R;


public class KhachHangOrderDialog extends BaseDialog {

    public KhachHangOrderDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_khach_hang_order);
        getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        setCancelable(true);

        btnOk = (Button) findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        dismiss();
    }
}
