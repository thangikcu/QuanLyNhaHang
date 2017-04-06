package com.thanggun99.khachhang.view.fragment;


import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.thanggun99.khachhang.R;
import com.thanggun99.khachhang.presenter.KhachHangPresenter;
import com.thanggun99.khachhang.util.Utils;

import butterknife.BindView;

@SuppressLint("ValidFragment")
public class FeedbackFragment extends BaseFragment implements View.OnClickListener, KhachHangPresenter.FeedbackView {
    @BindView(R.id.edt_title)
    EditText edtTitle;
    @BindView(R.id.edt_content)
    EditText edtContent;
    @BindView(R.id.tv_error)
    TextView tvError;
    @BindView(R.id.btn_gop_y)
    Button btnGopY;

    private KhachHangPresenter khachHangPresenter;

    public FeedbackFragment(KhachHangPresenter khachHangPresenter) {
        super(R.layout.fragment_feedback);
        this.khachHangPresenter = khachHangPresenter;
    }


    @Override
    public void initComponents() {

    }

    @Override
    public void setEvents() {
        btnGopY.setOnClickListener(this);
        khachHangPresenter.setFeedbackView(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_gop_y:
                if (checkForm()) {
                    khachHangPresenter.sentFeedback(edtTitle.getText().toString().trim(),
                            edtContent.getText().toString().trim());
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void showOnsuccess() {
        Utils.notifi(Utils.getStringByRes(R.string.cam_on_ban_da_gop_y));
        edtTitle.setText("");
        edtContent.setText("");
        tvError.setVisibility(View.GONE);
    }

    @Override
    public void showOnFail() {
        tvError.setVisibility(View.VISIBLE);
    }

    private boolean checkForm() {
        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(edtContent.getText())) {
            edtContent.setError(Utils.getStringByRes(R.string.nhap_noi_dung));
            cancel = true;
            focusView = edtContent;
        }
        if (TextUtils.isEmpty(edtTitle.getText())) {
            edtTitle.setError(Utils.getStringByRes(R.string.nhap_tieu_de));
            cancel = true;
            focusView = edtTitle;
        }
        if (cancel) {
            focusView.requestFocus();
            return false;
        }
        return true;
    }

}
