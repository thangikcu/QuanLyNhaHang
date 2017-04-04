package thanggun99.quanlynhahang.model.entity;

import java.io.Serializable;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.util.Utils;

/**
 * Created by Thanggun99 on 17/11/2016.
 */

public class Ban implements Serializable {
    public static final int TRONG = 0;
    public static final int DA_DAT_TRUOC = 1;
    public static final int DANG_PHUC_VU = 2;

    private int maBan, hienThi;
    private String tenBan;
    private int trangThai;

    private boolean selected;

    public Ban(int maBan, String tenBan, int trangThai, int hienThi) {
        this.maBan = maBan;
        this.tenBan = tenBan;
        this.trangThai = trangThai;
        this.hienThi = hienThi;
    }

    public Ban() {
    }

    public Ban(int maBan) {
        this.maBan = maBan;
    }

    public Ban(String tenBan) {

        this.tenBan = tenBan;
    }

    public int getHienThi() {
        return hienThi;
    }

    public void setHienThi(int hienThi) {
        this.hienThi = hienThi;
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

    public String getStringTrangThai() {
        if (trangThai == TRONG) return Utils.getStringByRes(R.string.trong);
        else if (trangThai == DA_DAT_TRUOC) return Utils.getStringByRes(R.string.da_dat_truoc);
        else return Utils.getStringByRes(R.string.dang_phuc_vu);
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }


    public int getIdResBgBan() {
        if (trangThai == DA_DAT_TRUOC) {
            return R.drawable.table_button_background_reserved;
        } else if (trangThai == DANG_PHUC_VU) {
            return R.drawable.table_button_background_serving;
        }
        return R.drawable.table_button_background_free;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof Mon)) return false;

        return tenBan.equals(((Ban) obj).tenBan);

    }

    @Override
    public int hashCode() {
        return tenBan.hashCode();
    }

    @Override
    public String toString() {
        return tenBan;
    }

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
