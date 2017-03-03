package thanggun99.quanlynhahang.view.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.presenter.phucvu.PhucVuPresenter;

/**
 * Created by Thanggun99 on 10/12/2016.
 */

public class ErrorDialog extends BaseDialog{
    private PhucVuPresenter phucVuPresenter;

    public ErrorDialog(Context context, PhucVuPresenter phucVuPresenter) {
        super(context);
        setContentView(R.layout.dialog_error);
        setCancelable(false);

        this.phucVuPresenter = phucVuPresenter;
        btnOk = (Button) findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        phucVuPresenter.loadDatas();
    }
}
