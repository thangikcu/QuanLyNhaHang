package com.thanggun99.khachhang.model.entity;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.thanggun99.khachhang.App;
import com.thanggun99.khachhang.model.Database;
import com.thanggun99.khachhang.util.API;
import com.thanggun99.khachhang.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Thanggun99 on 28/02/2017.
 */

public class KhachHang implements Serializable {

    public static final String KH_PHUC_VU = "KH_PHUC_VU";
    public static final String KH_DAT_BAN = "KH_DAT_BAN";
    public static final String KH_NONE = "KH_NONE";
    public static final String FAIL = "FAIL";
    public static final String OTHER_LOGIN = "OTHER_LOGIN";
    public static final String LOGIN_SUCCESS = "LOGIN_SUCCESS";

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
    private HoaDon currentHoaDon;
    private YeuCau yeuCau;
    private Database database;

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
                return FAIL;

            } else if (s.contains("other")) {
                huyGhiNhoDangNhap();
                return OTHER_LOGIN;
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
                    if (getThongTinPhucVu() != FAIL) {
                        if (loadYeuCau()) {

                            return LOGIN_SUCCESS;
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return FAIL;
    }

    public String getThongTinPhucVu() {
        Map<String, String> getParams = new HashMap<>();
        getParams.put("maKhachHang", String.valueOf(maKhachHang));

        String s = API.callService(API.GET_THONG_TIN_PHUC_VU_URL, getParams);

        if (!TextUtils.isEmpty(s)) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray1 = jsonObject.getJSONArray("hoaDon");
                JSONArray jsonArray2 = jsonObject.getJSONArray("datBan");

                if (jsonArray2.length() > 0) {
                    JSONObject object = (JSONObject) jsonArray2.get(0);

                    DatBan datBan = new DatBan();
                    if (object.toString().contains("tenBan")) {
                        datBan.setTenBan(object.getString("tenBan"));
                    }
                    datBan.setMaDatBan(object.getInt("maDatBan"));
                    datBan.setGioDen(object.getString("gioDen"));
                    datBan.setYeuCau(object.getString("yeuCau"));
                    datBan.setTrangThai(0);

                    currentDatBan = datBan;
                }

                if (jsonArray1.length() > 0) {
                    JSONObject object = (JSONObject) jsonArray1.get(0);

                    HoaDon hoaDon = new HoaDon();
                    hoaDon.setMaHoaDon(object.getInt("maHoaDon"));
                    hoaDon.setTenBan(object.getString("tenBan"));
                    if (object.toString().contains("giamGia")) {
                        hoaDon.setGiamGia(object.getInt("giamGia"));
                    }

                    hoaDon.setGioDen(object.getString("gioDen"));

                    JSONArray array = object.getJSONArray("thucDonOrder");
                    ArrayList<MonOrder> monOrders = new ArrayList<>();

                    for (int j = 0; j < array.length(); j++) {
                        JSONObject object1 = (JSONObject) array.get(j);

                        if (database.getMonList() == null) {
                            if (database.loadThucDonList()) {
                                monOrders.add(new MonOrder(database.getMonByMaMon(object1.getInt("maMon")),
                                        object1.getInt("soLuong"), object1.getInt("maChiTietHD")));
                            }
                        } else {
                            monOrders.add(new MonOrder(database.getMonByMaMon(object1.getInt("maMon")),
                                    object1.getInt("soLuong"), object1.getInt("maChiTietHD")));
                        }

                    }
                    hoaDon.setMonOrderList(monOrders);
                    hoaDon.setTrangThai(0);
                    hoaDon.setDatBan(currentDatBan);

                    currentHoaDon = hoaDon;

                }

                if (currentHoaDon != null) {

                    return KH_PHUC_VU;
                } else if (currentDatBan != null) {

                    return KH_DAT_BAN;
                } else {

                    return KH_NONE;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return FAIL;
        } else {

            return FAIL;
        }
    }

    private boolean loadYeuCau() {
        HashMap<String, String> getParams = new HashMap<>();
        getParams.put("maKhachHang", String.valueOf(maKhachHang));

        String s = API.callService(API.GET_YEU_CAU_URL, getParams);

        if (!TextUtils.isEmpty(s)) {
            try {
                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject != null) {
                    yeuCau = new YeuCau();

                    JSONArray jsonArray = jsonObject.getJSONArray("yeuCau");

                    if (jsonArray.length() > 0) {

                        JSONObject object = (JSONObject) jsonArray.get(0);

                        yeuCau.setMaYeuCau(object.getInt("maYeuCau"));

                        JSONArray monOderHuyJsonArray = new JSONObject(object.getString("yeuCau"))
                                .getJSONArray("monYeuCauHuyList");
                        ArrayList<MonYeuCau> monYeuCauHuyList = new ArrayList<>();

                        for (int j = 0; j < monOderHuyJsonArray.length(); j++) {
                            JSONObject monOrderObject = (JSONObject) monOderHuyJsonArray.get(j);

                            MonYeuCau monYeuCau = new MonYeuCau();
                            monYeuCau.setMaMon(monOrderObject.getInt("maMon"));
                            monYeuCau.setSoLuong(monOrderObject.getInt("soLuong"));

                            monYeuCauHuyList.add(monYeuCau);
                        }
                        yeuCau.setMonYeuCauHuyList(monYeuCauHuyList);

                        JSONArray monOderJsonArray = new JSONObject(object.getString("yeuCau")).getJSONArray("monYeuCauList");
                        ArrayList<MonYeuCau> monYeuCauList = new ArrayList<>();

                        for (int j = 0; j < monOderJsonArray.length(); j++) {
                            JSONObject monOrderObject = (JSONObject) monOderJsonArray.get(j);

                            MonYeuCau monYeuCau = new MonYeuCau();
                            monYeuCau.setMaMon(monOrderObject.getInt("maMon"));
                            monYeuCau.setSoLuong(monOrderObject.getInt("soLuong"));

                            monYeuCauList.add(monYeuCau);
                        }
                        yeuCau.setMonYeuCauList(monYeuCauList);
                    }

                    return true;
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return false;
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

    public HoaDon getCurrentHoaDon() {
        return currentHoaDon;
    }

    public void setCurrentHoaDon(HoaDon currentHoaDon) {
        this.currentHoaDon = currentHoaDon;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public YeuCau getYeuCau() {
        return yeuCau;
    }

    public void setYeuCau(YeuCau yeuCau) {
        this.yeuCau = yeuCau;
    }

    public boolean sendYeuCau(MonYeuCau monYeuCau) {

        if (monYeuCau != null) {
            yeuCau.addMon(monYeuCau);
        }
        if (currentHoaDon != null) {
            yeuCau.setMaHoaDon(currentHoaDon.getMaHoaDon());
        }

        Map<String, String> valuesPost = new HashMap<>();
        valuesPost.put("maKhachHang", String.valueOf(maKhachHang));
        valuesPost.put("tenKhachHang", tenKhachHang);

        String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        valuesPost.put("thoiGian", Utils.formatDate(date));
        Gson gson = new Gson();
        String yeuCau = gson.toJson(getYeuCau());
        valuesPost.put("yeuCau", yeuCau);

        String s = API.callService(API.SEND_YEU_CAU_URL, null, valuesPost);

        if (!TextUtils.isEmpty(s) && s.contains("success")) {
            return true;
        }
        return false;

    }

}
