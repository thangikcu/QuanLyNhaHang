package com.thanggun99.khachhang.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.thanggun99.khachhang.R;
import com.thanggun99.khachhang.util.API;
import com.thanggun99.khachhang.util.Utils;

import java.util.HashMap;
import java.util.Map;

public class FeedbackActivity extends Activity implements View.OnClickListener {
    EditText edtTitle, edtContent;
    ProgressDialog progressDialog;
    TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(Utils.getStringByRes(R.string.loading));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        edtContent = (EditText) findViewById(R.id.edt_content);
        edtTitle = (EditText) findViewById(R.id.edt_title);

        tvError = (TextView) findViewById(R.id.tv_error);

        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.btn_gop_y).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_gop_y:
                if (checkForm()) {
                    new FeedbackTask().execute(edtTitle.getText().toString(), edtContent.getText().toString());
                }
                break;
            default:
                break;
        }
    }

    private class FeedbackTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            if (Utils.isConnectingToInternet()) {
                progressDialog.show();
            } else {
                Utils.notifi(Utils.getStringByRes(R.string.kiem_tra_ket_noi_mang));
                cancel(true);
            }
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                Utils.notifi(Utils.getStringByRes(R.string.cam_on_ban_da_gop_y));
                edtTitle.setText("");
                edtContent.setText("");
                tvError.setVisibility(View.GONE);
            } else {
                tvError.setVisibility(View.VISIBLE);
            }

            progressDialog.dismiss();
            super.onPostExecute(aBoolean);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            Map<String, String> valuesPost = new HashMap<>();
            valuesPost.put("title", params[0]);
            valuesPost.put("content", params[1]);
            String s = API.callService(API.FEEDBACK_URL, null, valuesPost);

            if (!TextUtils.isEmpty(s) && s.contains("success")) {
                return true;
            }
            return false;
        }
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
