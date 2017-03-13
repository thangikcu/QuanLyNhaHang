package thanggun99.quanlynhahang.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import thanggun99.quanlynhahang.R;

/**
 * Created by Thanggun99 on 12/12/2016.
 */

public abstract class BaseDialog extends Dialog implements View.OnClickListener{
    protected Button btnOk, btnCancle;
    protected TextView tvTitle;

    public BaseDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_cancel) dismiss();
    }
}
