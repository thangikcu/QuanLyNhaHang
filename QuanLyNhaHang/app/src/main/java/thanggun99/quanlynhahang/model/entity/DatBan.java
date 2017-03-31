package thanggun99.quanlynhahang.model.entity;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import thanggun99.quanlynhahang.util.API;

/**
 * Created by Thanggun99 on 18/12/2016.
 */

public class DatBan implements Serializable {
    public static final int CHUA_TINH_TIEN = 0;
    public static final int DA_TINH_TIEN = 1;

    private int maDatBan;
    private Ban ban;
    private String tenKhachHang;
    private String soDienThoai;
    private String gioDen;
    private String yeuCau;
    private int trangThai;

    private KhachHang khachHang;

    public DatBan(int maDatBan, String tenKhachHang, String soDienThoai, String gioDen, String yeuCau, int trangThai) {
        this.maDatBan = maDatBan;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.gioDen = gioDen;
        this.yeuCau = yeuCau;
        this.trangThai = trangThai;
    }

    public DatBan(int maDatBan, KhachHang khachHang, Ban maBan, String gioDen, String yeuCau, int trangThai) {
        this.maDatBan = maDatBan;
        this.khachHang = khachHang;
        ban = maBan;
        this.gioDen = gioDen;
        this.yeuCau = yeuCau;
        this.trangThai = trangThai;
    }

    public DatBan() {

    }

    public Boolean updateDatBan(DatBan datBanUpdate) {
        Map<String, String> postParams, getParams;
        postParams = new HashMap<>();
        getParams = new HashMap<>();
        getParams.put("maDatBan", String.valueOf(getMaDatBan()));
        postParams.put("tenKhachHang", datBanUpdate.getTenKhachHang());
        postParams.put("soDienThoai", datBanUpdate.getSoDienThoai());
        postParams.put("maTokenAdmin", String.valueOf(Admin.getMaToken()));
        postParams.put("gioDen", datBanUpdate.getGioDen());
        if (getKhachHang() != null) {
            postParams.put("maKhachHang", String.valueOf(getKhachHang().getMaKhachHang()));
        }
        if (!TextUtils.isEmpty(datBanUpdate.getYeuCau())) {

            postParams.put("yeuCau", datBanUpdate.getYeuCau());
        }

        String s = API.callService(API.UPDATE_DAT_BAN_URL, getParams, postParams);

        if (!TextUtils.isEmpty(s) && s.trim().contains("success")) {
            setTenKhachHang(datBanUpdate.getTenKhachHang());
            setSoDienThoai(datBanUpdate.getSoDienThoai());
            setYeuCau(datBanUpdate.getYeuCau());
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
        postParams.put("maTokenAdmin", String.valueOf(Admin.getMaToken()));
        if (!TextUtils.isEmpty(getYeuCau()))
            postParams.put("yeuCau", getYeuCau());
        if (getBan() != null) {

            postParams.put("maBan", String.valueOf(getBan().getMaBan()));
        }

        String s = API.callService(API.DAT_BAN_URL, null, postParams);

        if (!TextUtils.isEmpty(s)) {
            try {
                setMaDatBan(Integer.parseInt(s.trim()));

            } catch (NumberFormatException e) {
                e.printStackTrace();
                return false;
            }

            if (getBan() != null) {

                getBan().setTrangThai(Ban.DA_DAT_TRUOC);
            }
            return true;
        }
        return false;
    }

    public Boolean KhachVaoBan() {
        Map<String, String> postParams = new HashMap<>();
        postParams.put("maDatBan", String.valueOf(getMaDatBan()));
        postParams.put("maTokenAdmin", String.valueOf(Admin.getMaToken()));
        postParams.put("tenBan", getBan().getTenBan());
        postParams.put("maBan", String.valueOf(getBan().getMaBan()));

        String s = API.callService(API.KHACH_VAO_BAN_URL, null, postParams);

        if (!TextUtils.isEmpty(s) && s.contains("success")) {

            getBan().setTrangThai(Ban.DA_DAT_TRUOC);
            return true;
        }
        return false;

    }

    public Boolean huyDatBan() {
        Map<String, String> getParams = new HashMap<>();
        getParams.put("maDatBan", String.valueOf(getMaDatBan()));
        getParams.put("tenKhachHang", getTenKhachHang());
        getParams.put("maTokenAdmin", String.valueOf(Admin.getMaToken()));
        if (getKhachHang() != null) {

            getParams.put("maKhachHang", String.valueOf(getKhachHang().getMaKhachHang()));
        }
        if (getBan() != null) {

            getParams.put("maBan", String.valueOf(getBan().getMaBan()));
        }

        String s = API.callService(API.DELETE_DAT_BAN_URL, getParams);

        if (!TextUtils.isEmpty(s) && s.trim().contains("success")) {
            if (getBan() != null) {

                getBan().setTrangThai(Ban.TRONG);
                if (getKhachHang() != null) {

                    getKhachHang().setDatBan(null);
                }
            }
            return true;
        }
        return false;
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
        if (khachHang != null) {
            return khachHang.getHoTen();
        }

        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        if (khachHang != null) {
            khachHang.setHoTen(tenKhachHang);
        } else {

            this.tenKhachHang = tenKhachHang;
        }
    }

    public String getSoDienThoai() {
        if (khachHang != null) {
            return khachHang.getSoDienThoai();
        }
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        if (khachHang != null) {
            khachHang.setSoDienThoai(soDienThoai);
        } else {

            this.soDienThoai = soDienThoai;
        }
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


    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        khachHang.setDatBan(this);
        this.khachHang = khachHang;
    }
}
