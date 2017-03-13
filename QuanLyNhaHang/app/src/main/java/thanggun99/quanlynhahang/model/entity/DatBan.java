package thanggun99.quanlynhahang.model.entity;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import thanggun99.quanlynhahang.util.API;

/**
 * Created by Thanggun99 on 18/12/2016.
 */

public class DatBan {
    private int maKhachHang;
    private int maDatBan;
    private Ban ban;
    private String tenKhachHang;
    private String soDienThoai;
    private String gioDen;
    private String yeuCau;
    private int trangThai;

    public DatBan(int maDatBan, int maKhachHang, Ban ban, String tenKhachHang, String soDienThoai, String gioDen, String yeuCau, int trangThai) {
        this.maDatBan = maDatBan;
        this.maKhachHang = maKhachHang;
        this.ban = ban;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.gioDen = gioDen;
        this.yeuCau = yeuCau;
        this.trangThai = trangThai;
    }

    public DatBan() {
    }

    public DatBan(int maDatBan, int maKhachHang, String tenKhachHang, String soDienThoai, String gioDen, String yeuCau, int trangThai) {

        this.maDatBan = maDatBan;
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.gioDen = gioDen;
        this.yeuCau = yeuCau;
        this.trangThai = trangThai;
    }

    public Boolean updateDatBan(DatBan datBanUpdate) {
        Map<String, String> postParams, getParams;
        postParams = new HashMap<>();
        getParams = new HashMap<>();
        getParams.put("maDatBan", String.valueOf(getMaDatBan()));
        postParams.put("tenKhachHang", datBanUpdate.getTenKhachHang());
        postParams.put("soDienThoai", datBanUpdate.getSoDienThoai());
        postParams.put("gioDen", datBanUpdate.getGioDen());
        if (!TextUtils.isEmpty(datBanUpdate.getYeuCau()))
            postParams.put("yeuCau", datBanUpdate.getYeuCau());

        String s = API.callService(API.UPDATE_DAT_BAN_URL, getParams, postParams);

        if (!TextUtils.isEmpty(s) && s.trim().contains("success")) {
            setTenKhachHang(datBanUpdate.getTenKhachHang());
            setYeuCau(datBanUpdate.getYeuCau());
            setSoDienThoai(datBanUpdate.getSoDienThoai());
            setGioDen(datBanUpdate.getGioDen());
            return true;
        }
        return false;
    }

    public boolean datBan() {
        Map<String, String> postParams = new HashMap<>();
        postParams.put("tenKhachHang", getTenKhachHang());
        postParams.put("soDienThoai", getSoDienThoai());
        postParams.put("gioDen", getGioDen());
        if (!TextUtils.isEmpty(getYeuCau()))
            postParams.put("yeuCau", getYeuCau());
        if (getBan() != null) {

            postParams.put("maBan", String.valueOf(getBan().getMaBan()));
        }

        String s = API.callService(API.DAT_BAN_URL, null, postParams);

        if (!TextUtils.isEmpty(s)) {
            setMaDatBan(Integer.parseInt(s.trim()));
            if (getBan() != null) {

                getBan().setTrangThai(1);
            }
            return true;
        }
        return false;
    }

    public Boolean huyDatBan() {
        Map<String, String> getParams = new HashMap<>();
        getParams.put("maDatBan", String.valueOf(getMaDatBan()));
        if (getBan() != null) {

            getParams.put("maBan", String.valueOf(getBan().getMaBan()));
        }

        String s = API.callService(API.DELETE_DAT_BAN_URL, getParams);

        if (!TextUtils.isEmpty(s) && s.trim().contains("success")) {
            if (getBan() != null) {

                getBan().setTrangThai(0);
            }
            return true;
        }
        return false;
    }

    public int getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(int maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public int getMaDatBan() {
        return maDatBan;
    }

    public void setMaDatBan(int maDatBan) {
        this.maDatBan = maDatBan;
    }

    public Ban getBan() {
        return ban;
    }

    public void setBan(Ban ban) {
        this.ban = ban;
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

    public String getGioDen() {
        return gioDen;
    }

    public void setGioDen(String gioDen) {
        this.gioDen = gioDen;
    }

    public String getYeuCau() {
        return yeuCau;
    }

    public void setYeuCau(String yeuCau) {
        this.yeuCau = yeuCau;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

}
