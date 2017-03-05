package com.thanggun99.khachhang.model.entity;

/**
 * Created by Thanggun99 on 17/11/2016.
 */

public class Ban {
    private int maBan;
    private String tenBan;
    private int trangThai;

    public Ban(int maBan, String tenBan, int trangThai) {
        this.maBan = maBan;
        this.tenBan = tenBan;
        this.trangThai = trangThai;
    }

    public Ban() {

    }


    public int getMaBan() {
        return maBan;
    }

    public void setMaBan(int maBan) {
        this.maBan = maBan;
    }

    public String getTenBan() {
        return tenBan;
    }

    public void setTenBan(String tenBan) {
        this.tenBan = tenBan;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}
