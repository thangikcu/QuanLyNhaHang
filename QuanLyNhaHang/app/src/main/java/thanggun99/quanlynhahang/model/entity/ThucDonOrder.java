package thanggun99.quanlynhahang.model.entity;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import thanggun99.quanlynhahang.util.API;

/**
 * Created by Thanggun99 on 24/11/2016.
 */

public class ThucDonOrder extends ThucDon{
    private int soLuong;
    private int maChitietHD;

    public ThucDonOrder(ThucDon thucDon, int soLuong, int maChitietHD) {
        super(thucDon.getMaMon(), thucDon.getTenMon(), thucDon.getMaLoai(), thucDon.getDonGia(), thucDon.getDonViTinh(), thucDon.getHinhAnh());
        this.soLuong = soLuong;
        this.maChitietHD = maChitietHD;
    }

    public ThucDonOrder() {
    }

    public void setThucDon(ThucDon thucDon) {
        setTenMon(thucDon.getTenMon());
        setMaLoai(thucDon.getMaLoai());
        setDonGia(thucDon.getDonGia());
        setDonViTinh(thucDon.getDonViTinh());
        setHinhAnh(thucDon.getHinhAnh());
    }

    public boolean updateThucDonOrder(int soLuong) {
        Map<String, String> getParams, postParams;
        getParams = new HashMap<>();
        postParams = new HashMap<>();
        int newSoLuong = getSoLuong() + soLuong;
        postParams.put("soLuong", String.valueOf(newSoLuong));
        getParams.put("maChiTietHD", String.valueOf(getMaChitietHD()));

        String s = API.callService(API.UPDATE_THUC_DON_ORDER_URL, getParams, postParams);

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
