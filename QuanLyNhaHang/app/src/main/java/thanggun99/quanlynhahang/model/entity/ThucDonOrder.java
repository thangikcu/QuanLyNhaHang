package thanggun99.quanlynhahang.model.entity;

import android.graphics.Bitmap;

/**
 * Created by Thanggun99 on 24/11/2016.
 */

public class ThucDonOrder extends ThucDon{
    private int soLuong;
    private int maChitietHD;

    public ThucDonOrder(int maChitietHD, int maMon, String tenMon, int maLoai, int donGia, String donViTinh, Bitmap hinhAnh, int soLuong) {
        super(maMon, tenMon, maLoai, donGia, donViTinh, hinhAnh);
        this.maChitietHD = maChitietHD;
        this.soLuong = soLuong;
    }

    public ThucDonOrder() {
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
