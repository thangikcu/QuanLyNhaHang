package com.thanggun99.khachhang.model.entity;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.thanggun99.khachhang.App;
import com.thanggun99.khachhang.util.API;
import com.thanggun99.khachhang.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Thanggun99 on 28/02/2017.
 */

public class KhachHang implements Serializable{

    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";

    private static int maKhachHang;
    private String tenKhachHang;
    private String soDienThoai;
    private String diaChi;
    private String tenDangNhap;
    private String matKhau;
    private boolean ghiNho;
    private int maToken;
    private String kieuDangNhap;
    private ArrayList<DatBan> listHSDatBan;
    private DatBan currentDatBan;


    public KhachHang(int maKhachHang, String tenKhachHang, String soDienThoai,
                     String diaChi, String tenDangNhap, String matKhau, int maToken, ArrayList<DatBan> listHSDatBan, DatBan currentDatBan) {
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.maToken = maToken;
        this.listHSDatBan = listHSDatBan;
        this.currentDatBan = currentDatBan;
    }

    public KhachHang() {

    }

    public boolean isGhiNhoDangNhap() {
        if (TextUtils.isEmpty(App.getPreferences().getString(USERNAME, null))) {
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
        if (!TextUtils.isEmpty(s)) {
            if (s.contains("fail")) {
                return "fail";

            } else if (s.contains("other")) {
                huyGhiNhoDangNhap();
                return "other";
            } else {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    maKhachHang = jsonObject.getInt("maKhachHang");
                    tenKhachHang = jsonObject.getString("tenKhachHang");
                    soDienThoai = jsonObject.getString("soDienThoai");
                    diaChi = jsonObject.getString("diaChi");
                    maToken = jsonObject.getInt("maToken");
                    if (ghiNho) {
                        ghiNhoDangNhap();
                    }
                    return "success";

                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
    }
    return "fail";
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

    public static int getMaKhachHang() {
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

    public boolean changePassword(String newPassword) {
        Map<String, String> postParams = new HashMap<>();
        postParams.put("maKhachHang", String.valueOf(maKhachHang));
        postParams.put("matKhauMoi", newPassword);
        String s = API.callService(API.CHANGE_PASSWORD_URL, null, postParams);

        if (!TextUtils.isEmpty(s) && s.contains("success")) {
            setMatKhau(newPassword);
            return true;
        }
        return false;
    }

    public void addDatBan(DatBan datBan) {
        if (listHSDatBan == null) {
            listHSDatBan = new ArrayList<>();
        }
        listHSDatBan.add(datBan);
    }

    public Boolean huyDatBan() {
        Map<String, String> getParams = new HashMap<>();
        getParams.put("maDatBan", String.valueOf(currentDatBan.getMaDatBan()));
        getParams.put("tenKhachHang", getTenKhachHang());

        String s = API.callService(API.HUY_DAT_BAN_URL, getParams);

        if (!TextUtils.isEmpty(s) && s.trim().contains("success")) {
            currentDatBan = null;
            return true;
        }
        return false;
    }

    public ArrayList<DatBan> getListHSDatBan() {
        return listHSDatBan;
    }

    public void setListHSDatBan(ArrayList<DatBan> listHSDatBan) {
        this.listHSDatBan = listHSDatBan;
    }

    public DatBan getCurrentDatBan() {
        return currentDatBan;
    }

    public void setCurrentDatBan(DatBan currentDatBan) {
        this.currentDatBan = currentDatBan;
    }

    public Boolean datBan(DatBan datBan) {
        Map<String, String> valuesPost = new HashMap<>();
        valuesPost.put("maKhachHang", String.valueOf(maKhachHang));
        valuesPost.put("gioDen", datBan.getGioDen());
        valuesPost.put("tenKhachHang", getTenKhachHang());
        if (!TextUtils.isEmpty(datBan.getYeuCau())) {

            valuesPost.put("yeuCau", datBan.getYeuCau());
        }
        String s = API.callService(API.DAT_BAN_URL, null, valuesPost);

        if (!TextUtils.isEmpty(s)) {
            datBan.setMaDatBan(Integer.parseInt(s.trim()));
            datBan.setTrangThai(0);

            currentDatBan = datBan;
            return true;
        }
        return false;
    }

    public Boolean getInfoDatBan() {
        Map<String, String> getParams = new HashMap<>();
        getParams.put("maKhachHang", String.valueOf(maKhachHang));

        String s = API.callService(API.GET_INFO_DAT_BAN_URL, getParams);

        if (!TextUtils.isEmpty(s)) {
            if (s.contains("fail")) {
                return false;
            }
            try {
                JSONObject object = new JSONObject(s);
                DatBan datBan = new DatBan();
                datBan.setMaDatBan(object.getInt("maDatBan"));
                datBan.setGioDen(object.getString("gioDen"));
                datBan.setYeuCau(object.getString("yeuCau"));
                datBan.setTrangThai(0);

                currentDatBan = datBan;

                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public Boolean updateDatBan(DatBan datBan) {
        Map<String, String> valuesPost = new HashMap<>();
        valuesPost.put("maDatBan", String.valueOf(currentDatBan.getMaDatBan()));
        valuesPost.put("gioDen", datBan.getGioDen());
        valuesPost.put("tenKhachHang", getTenKhachHang());
        if (!TextUtils.isEmpty(datBan.getYeuCau())) {

            valuesPost.put("yeuCau", datBan.getYeuCau());
        }
        String s = API.callService(API.UPDATE_DAT_BAN_URL, null, valuesPost);

        if (!TextUtils.isEmpty(s) && s.contains("success")) {
            currentDatBan.setYeuCau(datBan.getYeuCau());
            currentDatBan.setGioDen(datBan.getGioDen());
            return true;
        }
        return false;
    }
}
