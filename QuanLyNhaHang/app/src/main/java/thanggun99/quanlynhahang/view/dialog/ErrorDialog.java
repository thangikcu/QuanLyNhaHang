package thanggun99.quanlynhahang.view.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.presenter.MainPresenter;

/**
 * Created by Thanggun99 on 10/12/2016.
 */

public class ErrorDialog extends BaseDialog{
    private MainPresenter mainPresenter;

    public ErrorDialog(Context context, MainPresenter mainPresenter) {
        super(context);
        setContentView(R.layout.dialog_error);
        setCancelable(false);

        this.mainPresenter = mainPresenter;
        btnOk = (Button) findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mainPresenter.getDatas();
        dismiss();
    }
}
