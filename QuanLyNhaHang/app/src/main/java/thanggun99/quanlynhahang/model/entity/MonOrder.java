package thanggun99.quanlynhahang.model.entity;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import thanggun99.quanlynhahang.util.API;

/**
 * Created by Thanggun99 on 24/11/2016.
 */

public class MonOrder extends Mon {
    private int soLuong;
    private int maChitietHD;

    public MonOrder(Mon mon, int soLuong, int maChitietHD) {
        super(mon.getMaMon(), mon.getTenMon(), mon.getMaLoai(), mon.getDonGia(), mon.getDonViTinh(), mon.getHinhAnh(), mon.getRating(), mon.getPersonRating());
        this.soLuong = soLuong;
        this.maChitietHD = maChitietHD;
    }

    public MonOrder() {
    }

    public void setMon(Mon mon) {
        setMaMon(mon.getMaMon());
        setTenMon(mon.getTenMon());
        setMaLoai(mon.getMaLoai());
        setDonGia(mon.getDonGia());
        setDonViTinh(mon.getDonViTinh());
        setHinhAnh(mon.getHinhAnh());
        setRating(mon.getRating());
        setPersonRating(mon.getPersonRating());
    }

    public boolean updateMonOrder(int soLuong) {
        Map<String, String> getParams, postParams;
        getParams = new HashMap<>();
        postParams = new HashMap<>();
        int newSoLuong = getSoLuong() + soLuong;
        postParams.put("soLuong", String.valueOf(newSoLuong));
        getParams.put("maChiTietHD", String.valueOf(getMaChitietHD()));

        String s = API.callService(API.UPDATE_MON_ORDER_URL, getParams, postParams);

        if (!TextUtils.isEmpty(s) && s.trim().contains("success")) {
            setSoLuong(newSoLuong);
            return true;
        }
        return false;
    }


    public int getMaChitietHD() {
        return maChitietHD;
    }

    public void setMaChitietHD(int maChitietHD) {
        this.maChitietHD = maChitietHD;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getTongTien(){
        return getDonGia() * soLuong;
    }

}
