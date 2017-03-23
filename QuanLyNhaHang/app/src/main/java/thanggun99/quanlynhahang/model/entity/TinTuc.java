package thanggun99.quanlynhahang.model.entity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import thanggun99.quanlynhahang.App;
import thanggun99.quanlynhahang.R;

/**
 * Created by Thanggun99 on 23/03/2017.
 */

public class TinTuc {
    private int maTinTuc;
    private String tieuDe;
    private String noiDung;
    private String ngayDang;
    private Bitmap hinhAnh;
    private int hienThi;

    public TinTuc(int maTinTuc, String tieuDe, String noiDung, String ngayDang, Bitmap hinhAnh, int hienThi) {
        this.maTinTuc = maTinTuc;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.ngayDang = ngayDang;
        this.hinhAnh = hinhAnh;
        this.hienThi = hienThi;
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

    public Bitmap getHinhAnh() {
        if (hinhAnh != null)
            return hinhAnh;
        return BitmapFactory.decodeResource(App.getContext().getResources(), R.drawable.ic_news);
    }

    public void setHinhAnh(Bitmap hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public int getHienThi() {
        return hienThi;
    }

    public void setHienThi(int hienThi) {
        this.hienThi = hienThi;
    }
}
