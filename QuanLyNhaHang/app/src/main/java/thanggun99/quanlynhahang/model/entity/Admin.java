package thanggun99.quanlynhahang.model.entity;

import android.content.SharedPreferences;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import thanggun99.quanlynhahang.App;
import thanggun99.quanlynhahang.model.LoginTask;
import thanggun99.quanlynhahang.util.API;
import thanggun99.quanlynhahang.util.Utils;

/**
 * Created by Thanggun99 on 19/03/2017.
 */

public class Admin extends Person implements Serializable{

    public static final String ADMIN = "ADMIN";
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";

    private static int maAdmin;
    private static int maToken;
    private int type;
    private boolean ghiNho;
    private String kieuDangNhap;
    private String tenDangNhap;
    private String matKhau;

    public Admin(int maAdmin, String hoTen, String soDienThoai, String diaChi,
                 int maToken, int type, boolean ghiNho, String kieuDangNhap, String tenDangNhap, String matKhau) {
        super(hoTen, soDienThoai, diaChi);
        Admin.maAdmin = maAdmin;
        Admin.maToken = maToken;
        this.type = type;
        this.ghiNho = ghiNho;
        this.kieuDangNhap = kieuDangNhap;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
    }

    public Admin() {
    }

    public void ghiNhoDangNhap() {
        SharedPreferences.Editor editor = App.getPreferences().edit();
        editor.putString(USERNAME, tenDangNhap);
        editor.putString(PASSWORD, matKhau);
        editor.apply();
    }

    public void huyGhiNhoDangNhap() {
        App.getPreferences().edit().clear().apply();
    }

    public String login() {
        Map<String, String> postParams = new HashMap<>();
        postParams.put("tenDangNhap", tenDangNhap);
        postParams.put("matKhau", matKhau);
        postParams.put("token", Utils.getToken());
        postParams.put("mode", kieuDangNhap);

        String s = API.callService(API.LOGIN_URL, null, postParams);
        if (!TextUtils.isEmpty(s)) {
            if (s.contains("fail")) {
                return LoginTask.FAIL;

            } else if (s.contains("other")) {
                huyGhiNhoDangNhap();
                return LoginTask.OTHER;
            } else {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    maAdmin = jsonObject.getInt("maAdmin");
                    hoTen = jsonObject.getString("hoTen");
                    soDienThoai = jsonObject.getString("soDienThoai");
                    diaChi = jsonObject.getString("diaChi");
                    maToken = jsonObject.getInt("maToken");
                    type = jsonObject.getInt("type");

                    if (ghiNho) {
                        ghiNhoDangNhap();
                    }
                    return LoginTask.SUCCESS;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return LoginTask.FAIL;
    }

    public static int getMaAdmin() {
        return maAdmin;
    }

    public void setMaAdmin(int maAdmin) {
        Admin.maAdmin = maAdmin;
    }

    public static int getMaToken() {
        return maToken;
    }

    public void setMaToken(int maToken) {
        Admin.maToken = maToken;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isGhiNho() {
        return ghiNho;
    }

    public void setGhiNho(boolean ghiNho) {
        this.ghiNho = ghiNho;
    }

    public String getKieuDangNhap() {
        return kieuDangNhap;
    }

    public void setKieuDangNhap(String kieuDangNhap) {
        this.kieuDangNhap = kieuDangNhap;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public Boolean changePassword(String passwordNew) {
        Map<String, String> postParams = new HashMap<>();
        postParams.put("maAdmin", String.valueOf(maAdmin));
        postParams.put("matKhauMoi", passwordNew);
        String s = API.callService(API.CHANGE_PASSWORD_URL, null, postParams);

        if (!TextUtils.isEmpty(s) && s.contains("success")) {
            setMatKhau(passwordNew);
            return true;
        }
        return false;
    }
}
