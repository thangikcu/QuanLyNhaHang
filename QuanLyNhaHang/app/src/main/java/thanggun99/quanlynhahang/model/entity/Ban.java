package thanggun99.quanlynhahang.model.entity;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.util.Utils;

/**
 * Created by Thanggun99 on 17/11/2016.
 */

public class Ban {
    private int maBan, hienThi;
    private String tenBan;
    private int trangThai;
    public static final int NOT_SET = -1;

    public Ban(int maBan, String tenBan, int trangThai, int hienThi) {
        this.maBan = maBan;
        this.tenBan = tenBan;
        this.trangThai = trangThai;
        this.hienThi = hienThi;
    }

    public Ban() {
        trangThai = NOT_SET;
        hienThi = NOT_SET;
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
        if (trangThai == 0) return Utils.getStringByRes(R.string.trong);
        else if (trangThai == 1)return Utils.getStringByRes(R.string.dat_truoc);
        else return Utils.getStringByRes(R.string.dang_phuc_vu);
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }


    public int getIdResBgBan() {
        if (trangThai == 1) {
            return R.drawable.table_button_background_reserved;
        } else if (trangThai == 2) {
            return R.drawable.table_button_background_serving;
        }
        return R.drawable.table_button_background_free;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof ThucDon)) return false;

        if (!tenBan.equals(((Ban) obj).tenBan)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return tenBan.hashCode();
    }
}