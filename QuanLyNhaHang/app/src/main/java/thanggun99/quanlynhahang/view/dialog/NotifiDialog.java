package thanggun99.quanlynhahang.view.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import thanggun99.quanlynhahang.R;


public class NotifiDialog extends BaseDialog {
    private TextView tvNotifi;

    public NotifiDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_notifi);
        getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        setCancelable(true);

        tvNotifi = (TextView) findViewById(R.id.tv_notifi);
        btnCancle = (Button) findViewById(R.id.btn_cancel);
        btnCancle.setOnClickListener(this);
    }

    public void notifi(String message) {
        tvNotifi.setText(message);
        show();
    }
}
