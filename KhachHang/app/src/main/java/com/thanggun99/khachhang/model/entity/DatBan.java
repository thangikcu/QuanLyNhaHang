package com.thanggun99.khachhang.model.entity;

import java.io.Serializable;

/**
 * Created by Thanggun99 on 05/03/2017.
 */

public class DatBan implements Serializable{
    private int maDatBan;
    private String gioDen;
    private String yeuCau;
    private Ban ban;
    private int trangThai;

    public DatBan(int maDatBan, String gioDen, String yeuCau, Ban ban, int trangThai) {
        this.maDatBan = maDatBan;
        this.gioDen = gioDen;
        this.yeuCau = yeuCau;
        this.ban = ban;
        this.trangThai = trangThai;
    }

    public DatBan() {
    }

    public int getMaDatBan() {
        return maDatBan;
    }

    public void setMaDatBan(int maDatBan) {
        this.maDatBan = maDatBan;
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

    public Ban getBan() {
        return ban;
    }

    public void setBan(Ban ban) {
        this.ban = ban;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}
