package thanggun99.quanlynhahang.model.entity;

import android.graphics.Bitmap;

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
