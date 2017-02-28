package com.thanggun99.khachhang.model.entity;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.thanggun99.khachhang.App;
import com.thanggun99.khachhang.util.API;
import com.thanggun99.khachhang.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Thanggun99 on 28/02/2017.
 */

public class KhachHang {
    private int maKhachHang;
    private String tenKhachHang;
    private String soDienThoai;
    private String diaChi;
    private String tenDangNhap;
    private String matKhau;
    private boolean ghiNho;
    private int maToken;
    private String kieuDangNhap;

    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";

    public KhachHang(int maKhachHang, String tenKhachHang, String soDienThoai, String diaChi, String tenDangNhap, String matKhau, int maToken) {
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.maToken = maToken;
    }

    public KhachHang() {

    }

    public boolean isGhiNhoDangNhap() {
        if (TextUtils.isEmpty(App.getPreferences().getString(USERNAME, null))){
            return false;
        }
        return true;
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
        Map<String, String> khachHangMap = new HashMap<>();
        khachHangMap.put("tenDangNhap", tenDangNhap);
        khachHangMap.put("matKhau", matKhau);
        khachHangMap.put("token", Utils.getToken());
        khachHangMap.put("mode", kieuDangNhap);

        String s = API.callService(API.LOGIN_URL, null, khachHangMap);
        if (!TextUtils.isEmpty(s) && s.contains("fail")) {
            return "fail";

        } else if (!TextUtils.isEmpty(s) && s.contains("other")) {
            return "other";
        } else {
            try {
                JSONObject jsonObject = (JSONObject) new JSONObject(s).getJSONArray("khachHang").get(0);
                maKhachHang = jsonObject.getInt("maKhachHang");
                tenKhachHang = jsonObject.getString("tenKhachHang");
                soDienThoai = jsonObject.getString("sdt");
                diaChi = jsonObject.getString("diaChi");
                maToken = jsonObject.getInt("maToken");
                if (ghiNho) {
                    ghiNhoDangNhap();
                }
                return "success";

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return "fail";
        }
    }

    public String getKieuDangNhap() {
        return kieuDangNhap;
    }

    public void setKieuDangNhap(String kieuDangNhap) {
        this.kieuDangNhap = kieuDangNhap;
    }


  /*  private Map<String, String> toMap() {
        Map<String, String> khachHangMap = new HashMap<>();
        if (maKhachHang != 0) {
            khachHangMap.put("maKhachHang", String.valueOf(maKhachHang));
        }
        if (!TextUtils.isEmpty(tenKhachHang)) {
            khachHangMap.put("tenKhachHang", tenKhachHang);
        }
        if (!TextUtils.isEmpty(soDienThoai)) {
            khachHangMap.put("sdt", soDienThoai);
        }
        if (!TextUtils.isEmpty(diaChi)) {
            khachHangMap.put("diaChi", diaChi);
        }
        if (!TextUtils.isEmpty(tenDangNhap)) {
            khachHangMap.put("tenDangNhap", tenDangNhap);
        }
        if (!TextUtils.isEmpty(matKhau)) {
            khachHangMap.put("matKhau", matKhau);
        }
        if (maToken != 0) {
            khachHangMap.put("maToken", String.valueOf(maToken));
        }
        if (!TextUtils.isEmpty(Utils.getToken())) {
            khachHangMap.put("token", Utils.getToken());
        }
        return khachHangMap;
    }*/

    public int getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(int maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
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

    public void setGhiNho(boolean ghiNho) {
        this.ghiNho = ghiNho;
    }

    public int getMaToken() {
        return maToken;
    }

    public void setMaToken(int maToken) {
        this.maToken = maToken;
    }

}
