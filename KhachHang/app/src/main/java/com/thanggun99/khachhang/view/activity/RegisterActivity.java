package com.thanggun99.khachhang.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.thanggun99.khachhang.R;
import com.thanggun99.khachhang.model.entity.KhachHang;
import com.thanggun99.khachhang.util.API;
import com.thanggun99.khachhang.util.Utils;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends Activity implements View.OnClickListener {
    public static String USERNAME = "USERNAME";
    private EditText edtHoTen, edtDiaChi, edtSdt,
            edtUsername, edtPassword, edtRePassword;
    private Button btnRegister;
    private ImageButton btnBack;
    private TextView tvRegisterError;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(Utils.getStringByRes(R.string.loading));
        progressDialog.setCancelable(false);

        findViews();
        setEvents();
    }

    private void setEvents() {
        btnBack.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    private void findViews() {
        btnBack = (ImageButton) findViewById(R.id.btn_back);
        btnRegister = (Button) findViewById(R.id.btn_register);
        edtHoTen = (EditText) findViewById(R.id.edt_ho_ten);
        edtDiaChi = (EditText) findViewById(R.id.edt_dia_chi);
        edtSdt = (EditText) findViewById(R.id.edt_sdt);
        edtUsername = (EditText) findViewById(R.id.edt_username);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        edtRePassword = (EditText) findViewById(R.id.edt_re_password);
        tvRegisterError = (TextView) findViewById(R.id.tv_error_register);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_register) {
            if (checkForm()) {
                KhachHang khachHang = new KhachHang();
                khachHang.setTenKhachHang(edtHoTen.getText().toString().trim());
                khachHang.setDiaChi(edtDiaChi.getText().toString().trim());
                khachHang.setSoDienThoai(edtSdt.getText().toString().trim());
                khachHang.setTenDangNhap(edtUsername.getText().toString().trim());
                khachHang.setMatKhau(edtRePassword.getText().toString().trim());

                new RegisterTask().execute(khachHang);
            }
        }
        if (v.getId() == R.id.btn_back){
            onBackPressed();
        }
    }

    private class RegisterTask extends AsyncTask<KhachHang, Void, String> {
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
        protected void onPostExecute(String s) {
            if (s.equals("exist")) {
                tvRegisterError.setVisibility(View.GONE);
                edtUsername.setError(Utils.getStringByRes(R.string.ten_tai_khoan_da_ton_tai));
                edtUsername.requestFocus();
            } else if (s.equals("success")) {
                tvRegisterError.setVisibility(View.GONE);
                Intent intent = new Intent();
                intent.putExtra(USERNAME, edtUsername.getText().toString());
                setResult(1, intent);
                finish();
            } else {
                tvRegisterError.setVisibility(View.VISIBLE);
            }
            progressDialog.dismiss();
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(KhachHang...khachHangs) {
            Map<String, String> registerInfo = new HashMap<>();
            registerInfo.put("hoTen", khachHangs[0].getTenKhachHang());
            registerInfo.put("diaChi", khachHangs[0].getDiaChi());
            registerInfo.put("soDienThoai", khachHangs[0].getSoDienThoai());
            registerInfo.put("tenDangNhap", khachHangs[0].getTenDangNhap());
            registerInfo.put("matKhau", khachHangs[0].getMatKhau());
            registerInfo.put("token", Utils.getToken());

            String s = API.callService(API.REGISTER_URL, null, registerInfo);
            if (!TextUtils.isEmpty(s)) {
                return s.trim();
            }
            return "";
        }
    }

    private boolean checkForm() {
        View focusView = null;
        boolean cancel = false;

        if (TextUtils.isEmpty(edtRePassword.getText())) {
            edtRePassword.setError(Utils.getStringByRes(R.string.nhap_lai_mat_khau));
            focusView = edtRePassword;
            cancel = true;
        }
        if (TextUtils.isEmpty(edtPassword.getText()) || edtPassword.getText().length() < 6) {
            edtPassword.setError(Utils.getStringByRes(R.string.nhap_mat_khau));
            focusView = edtPassword;
            cancel = true;
        }
        if (TextUtils.isEmpty(edtUsername.getText())) {
            edtUsername.setError(Utils.getStringByRes(R.string.nhap_ten_dang_nhap));
            focusView = edtUsername;
            cancel = true;
        }
        if (TextUtils.isEmpty(edtSdt.getText()) || edtSdt.getText().length() < 9) {
            edtSdt.setError(Utils.getStringByRes(R.string.nhap_so_dien_thoai));
            focusView = edtSdt;
            cancel = true;
        }
        if (TextUtils.isEmpty(edtDiaChi.getText())) {
            edtDiaChi.setError(Utils.getStringByRes(R.string.nhap_dia_chi));
            focusView = edtDiaChi;
            cancel = true;
        }
        if (TextUtils.isEmpty(edtHoTen.getText())) {
            edtHoTen.setError(Utils.getStringByRes(R.string.nhap_ho_ten));
            focusView = edtHoTen;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
            return false;
        }
        if (!edtRePassword.getText().toString().trim().equals(edtPassword.getText().toString().trim())) {
            edtRePassword.setError(Utils.getStringByRes(R.string.mat_khau_khong_khop));
            edtRePassword.requestFocus();
            return false;
        }
        return true;
    }
}
