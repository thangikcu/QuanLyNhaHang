package thanggun99.quanlynhahang.model.entity;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.util.API;
import thanggun99.quanlynhahang.util.Utils;

/**
 * Created by Thanggun99 on 17/11/2016.
 */

public class Ban implements Serializable{
    private int maBan, hienThi;
    private String tenBan;
    private int trangThai;
    public static final int NOT_SET = -1;
    private int selected;

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

    public boolean updateBan(Ban ban) {
        Map<String, String> getParams, postParams;
        getParams = new HashMap<>();
        postParams = new HashMap();
        getParams.put("maBan", String.valueOf(ban.getMaBan()));

        if (ban.getTrangThai() != Ban.NOT_SET) {
            postParams.put("trangThai", String.valueOf(ban.getTrangThai()));
        }
        if (!TextUtils.isEmpty(ban.getTenBan())) {
            postParams.put("tenBan", ban.getTenBan());
        }
        if (ban.getHienThi() != Ban.NOT_SET) {
            postParams.put("hienThi", String.valueOf(ban.getHienThi()));
        }

        String s = API.callService(API.UPDATE_BAN_URL, getParams, postParams);

        if (!TextUtils.isEmpty(s) && s.trim().contains("success")) return true;
        return false;
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
        else if (trangThai == 1)return Utils.getStringByRes(R.string.da_dat_truoc);
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

    @Override
    public String toString() {
        return tenBan;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }
}
