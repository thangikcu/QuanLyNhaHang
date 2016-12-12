package thanggun99.quanlynhahang.model.entity;

import android.graphics.Color;

/**
 * Created by Thanggun99 on 17/11/2016.
 */

public class NhomMon {
    private int maLoai;
    private String tenLoai, mauSac;

    public NhomMon(int maLoai, String tenLoai, String mauSac) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
        this.mauSac = mauSac;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public int getMauSac() {
        return Color.parseColor(mauSac);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof ThucDon)) return false;

        if (!tenLoai.equals(((NhomMon) obj).tenLoai)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return tenLoai.hashCode();
    }
}
