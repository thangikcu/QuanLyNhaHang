package thanggun99.quanlynhahang.model.entity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import thanggun99.quanlynhahang.App;
import thanggun99.quanlynhahang.R;

/**
 * Created by Thanggun99 on 20/11/2016.
 */

public class ThucDon {
    private int maMon, hienThi;
    private String tenMon;
    private int maLoai;
    private int donGia;
    private String donViTinh;
    private Bitmap hinhAnh;

    public ThucDon(int maMon, String tenMon, int maLoai, int donGia, String donViTinh, Bitmap hinhAnh) {
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.maLoai = maLoai;
        this.donGia = donGia;
        this.donViTinh = donViTinh;
        this.hinhAnh = hinhAnh;
    }

    public ThucDon(int maMon, String tenMon, int maLoai, int donGia, String donViTinh, int hienThi) {
        this.maMon = maMon;
        this.hienThi = hienThi;
        this.tenMon = tenMon;
        this.maLoai = maLoai;
        this.donGia = donGia;
        this.donViTinh = donViTinh;
    }

    public ThucDon() {
    }

    public int getHienThi() {
        return hienThi;
    }

    public void setHienThi(int hienThi) {
        this.hienThi = hienThi;
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

    public Bitmap getHinhAnh() {
        if (hinhAnh != null) return hinhAnh;
        return BitmapFactory.decodeResource(App.getContext().getResources(), R.drawable.ic_food);
    }

    public void setHinhAnh(Bitmap hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof ThucDon)) return false;

        if (!tenMon.equals(((ThucDon) obj).getTenMon())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return tenMon.hashCode();
    }
}
