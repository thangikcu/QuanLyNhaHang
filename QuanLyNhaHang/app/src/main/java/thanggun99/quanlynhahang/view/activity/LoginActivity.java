package thanggun99.quanlynhahang.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.model.LoginTask;
import thanggun99.quanlynhahang.model.entity.Admin;
import thanggun99.quanlynhahang.util.Utils;
import thanggun99.quanlynhahang.view.dialog.NotifiDialog;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginTask.OnFinishLoginListener {
    private EditText edtUsername, edtPassword;
    private CheckBox ckbGhiNho;
    private TextView tvError;
    private Button btnLogin;
    private LoginTask loginTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginTask = new LoginTask(this);
        loginTask.setOnFinishLoginListener(this);

        edtUsername = (EditText) findViewById(R.id.edt_username);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        ckbGhiNho = (CheckBox) findViewById(R.id.ckb_ghi_nho);
        tvError = (TextView) findViewById(R.id.tv_error_login);
        btnLogin = (Button) findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(this);

        loginTask.loginAuto();
    }

    private boolean checkForm() {
        View focusView = null;
        boolean cancel = false;

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

        if (cancel) {
            focusView.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            if (checkForm()) {
                Admin admin = new Admin();
                admin.setTenDangNhap(edtUsername.getText().toString().trim());
                admin.setMatKhau(edtPassword.getText().toString().trim());
                admin.setKieuDangNhap(LoginTask.NOT_AUTO);
                admin.setGhiNho(ckbGhiNho.isChecked());

                loginTask.login(admin);
            }
        }
    }


    @Override
    public void onLoginSuccess() {
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Admin.ADMIN, loginTask.getAdmin());
        startActivity(intent);
    }

    @Override
    public void onLoginFail() {
        tvError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onOtherLogin() {
        NotifiDialog notifiDialog = new NotifiDialog(this);
        notifiDialog.notifi(Utils.getStringByRes(R.string.other_people_login));

    }
}

