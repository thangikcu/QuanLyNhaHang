package com.thanggun99.khachhang.model.entity;

import java.io.Serializable;


/**
 * Created by Thanggun99 on 23/03/2017.
 */

public class TinTuc implements Serializable {
    public static final String TIN_TUC = "TIN_TUC";

    private int maTinTuc;
    private String tieuDe;
    private String noiDung;
    private String ngayDang;
    private byte[] hinhAnh;

    public TinTuc(int maTinTuc, String tieuDe, String noiDung, String ngayDang, byte[] hinhAnh) {
        this.maTinTuc = maTinTuc;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.ngayDang = ngayDang;
        this.hinhAnh = hinhAnh;
    }

    public TinTuc() {
    }

    public int getMaTinTuc() {
        return maTinTuc;
    }

    public void setMaTinTuc(int maTinTuc) {
        this.maTinTuc = maTinTuc;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getNgayDang() {
        return ngayDang;
    }

    public void setNgayDang(String ngayDang) {
        this.ngayDang = ngayDang;
    }

    public byte[] getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

}
