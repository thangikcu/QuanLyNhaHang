package thanggun99.quanlynhahang.view.dialog;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import thanggun99.quanlynhahang.R;


public class ConfirmDialog extends BaseDialog {
    private TextView tvTitle, tvMessage;
    private OnClickOkListener onClickOkListener;

    public ConfirmDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_confirm);

        tvTitle = (TextView) findViewById(R.id.tv_title);

        tvMessage = (TextView) findViewById(R.id.tv_message);
        tvMessage.setMovementMethod(new ScrollingMovementMethod());

        btnOk = (Button) findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(this);

        btnCancle = (Button) findViewById(R.id.btn_cancel);
        btnCancle.setOnClickListener(this);
    }

    public void setContent(String title, String message) {
        tvTitle.setText(title);
        tvMessage.setText(message);
        show();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.btn_ok) {
            onClickOkListener.onClickOk();
        }
    }

    public void setOnClickOkListener(OnClickOkListener onClickOkListener) {
        this.onClickOkListener = onClickOkListener;
    }

    public interface OnClickOkListener{
        void onClickOk();
    }
}
