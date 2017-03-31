package com.thanggun99.khachhang.model.entity;

import java.util.ArrayList;

/**
 * Created by Thanggun99 on 29/03/2017.
 */

public class YeuCau {
    public static final int HOA_DON_MOI = 0;
    public static final int THEM_MON = 1;
    public static final int HUY_HOA_DON = 2;
    public static final int TINH_TIEN_HOA_DON = 3;

    private int maYeuCau;
    private int type;

    private int maHoaDon;
    private ArrayList<MonYeuCau> monYeuCauHuyList;
    private ArrayList<MonYeuCau> monYeuCauList;

    public YeuCau() {
        monYeuCauList = new ArrayList<>();
        monYeuCauHuyList = new ArrayList<>();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public void addMon(MonYeuCau monYeuCau) {

        for (MonYeuCau yeuCau : monYeuCauList) {
            if (yeuCau.getMaMon() == monYeuCau.getMaMon()) {
                yeuCau.setSoLuong(yeuCau.getSoLuong() + monYeuCau.getSoLuong());
                return;
            }
        }
        monYeuCauList.add(monYeuCau);
    }

    public void setMaYeuCau(int maYeuCau) {
        this.maYeuCau = maYeuCau;
    }

    public int getMaYeuCau() {
        return maYeuCau;
    }

    public ArrayList<MonYeuCau> getMonYeuCauHuyList() {
        return monYeuCauHuyList;
    }

    public ArrayList<MonYeuCau> getMonYeuCauList() {
        return monYeuCauList;
    }

    public void setMonYeuCauList(ArrayList<MonYeuCau> monYeuCauList) {
        this.monYeuCauList = monYeuCauList;
    }

    public void setMonYeuCauHuyList(ArrayList<MonYeuCau> monYeuCauHuyList) {
        this.monYeuCauHuyList = monYeuCauHuyList;
    }
}
