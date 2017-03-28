package com.thanggun99.khachhang.model.entity;

/**
 * Created by Thanggun99 on 20/11/2016.
 */

public class Mon {
    private int maMon;
    private String tenMon;
    private int maLoai;
    private int donGia;
    private String donViTinh;
    private byte[] hinhAnh;
    private float rating;
    private int personRating;

    public Mon(int maMon, String tenMon, int maLoai, int donGia, String donViTinh, byte[] hinhAnh, float rating, int personRating) {
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.maLoai = maLoai;
        this.donGia = donGia;
        this.donViTinh = donViTinh;
        this.hinhAnh = hinhAnh;
        this.rating = rating;
        this.personRating = personRating;
    }

    public Mon(int maMon, String tenMon, int maLoai, int donGia, String donViTinh, float rating, int personRating) {
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.maLoai = maLoai;
        this.donGia = donGia;
        this.donViTinh = donViTinh;
        this.rating = rating;
        this.personRating = personRating;
    }

    public Mon() {
    }

    public int getMaMon() {
        return maMon;
    }

    public void setMaMon(int maMon) {
        this.maMon = maMon;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public byte[] getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof Mon)) return false;

        if (!tenMon.equals(((Mon) obj).getTenMon())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return tenMon.hashCode();
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getPersonRating() {
        return personRating;
    }

    public void setPersonRating(int personRating) {
        this.personRating = personRating;
    }

    public String getRatingPoint() {
        return "(" + rating + "/" + personRating + ")";
    }
}
