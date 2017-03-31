package com.thanggun99.khachhang.model.entity;

import com.thanggun99.khachhang.R;
import com.thanggun99.khachhang.util.Utils;

import java.util.ArrayList;

public class HoaDon {
    private int maHoaDon;
    private DatBan datBan;
    private int giamGia;
    private String tenBan;
    private String gioDen;
    private int trangThai;
    private ArrayList<MonOrder> monOrderList = new ArrayList<>();

    public HoaDon() {
    }

    public HoaDon(int maHoaDon, int giamGia, String gioDen, ArrayList<MonOrder> monOrderList) {
        this.maHoaDon = maHoaDon;
        this.giamGia = giamGia;
        this.gioDen = gioDen;
        this.monOrderList = monOrderList;

    }

    private MonOrder getMonOrderByMa(int maChiTietHD) {
        for (MonOrder monOrder : getMonOrderList()) {
            if (monOrder.getMaChitietHD() == maChiTietHD) {
                return monOrder;
            }
        }
        return null;
    }

    public String getTenBan() {
        return tenBan;
    }

    public void setTenBan(String tenBan) {
        this.tenBan = tenBan;
    }

    public ArrayList<MonOrder> getMonOrderList() {
        return monOrderList;
    }

    public void setMonOrderList(ArrayList<MonOrder> monOrderList) {
        this.monOrderList = monOrderList;
    }

    public void addMonOrder(MonOrder monOrder) {
        this.monOrderList.add(0, monOrder);
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public int getGiamGia() {
        return giamGia;
    }

    public String getStringGiamGia() {
        if (giamGia > 0) {
            return giamGia + "%";
        } else {
            return Utils.getStringByRes(R.string.sale);
        }
    }

    public void setGiamGia(int giamGia) {
        this.giamGia = giamGia;
    }

    public String getGioDen() {
        return gioDen;
    }

    public void setGioDen(String gioDen) {
        this.gioDen = gioDen;
    }

    public DatBan getDatBan() {
        return datBan;
    }

    public void setDatBan(DatBan datBan) {
        this.datBan = datBan;
    }

    public int getTongTien() {
        return Math.round((float) (getTienMon() - getTienGiamGia()) / 1000) * 1000;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        return false;

    }

    @Override
    public int hashCode() {
        return maHoaDon;
    }

    public int getTienMon() {
        int tienMon = 0;
        for (MonOrder monOrder : getMonOrderList()) {
            tienMon += monOrder.getTongTien();
        }
        return tienMon;
    }

    public int getSoMon() {
        int soMon = 0;
        for (MonOrder monOrder : getMonOrderList()) {
            soMon += monOrder.getSoLuong();
        }
        return soMon;
    }

    public int getTienGiamGia() {
        if (giamGia > 0) {
            return Math.round((float) (getTienMon() / 100 * giamGia) / 1000) * 1000;
        }
        return 0;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

}
