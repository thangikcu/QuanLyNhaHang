package thanggun99.quanlynhahang.model.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Thanggun99 on 29/03/2017.
 */

public class YeuCau implements Serializable{
    public static final int HOA_DON_MOI = 0;
    public static final int THEM_MON = 1;
    public static final int HUY_HOA_DON = 2;
    public static final int TINH_TIEN_HOA_DON = 3;

    private int maYeuCau;
    private KhachHang khachHang;
    private int type;
    private int maHoaDon;
    private String thoiGian;

    private ArrayList<MonOrder> monYeuCauHuyList;
    private ArrayList<MonOrder> monYeuCauList;
    private String yeuCauJson;

    public YeuCau() {
        monYeuCauList = new ArrayList<>();
        monYeuCauHuyList = new ArrayList<>();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public int getMaYeuCau() {
        return maYeuCau;
    }

    public void setMaYeuCau(int maYeuCau) {
        this.maYeuCau = maYeuCau;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public ArrayList<MonOrder> getMonYeuCauHuyList() {
        return monYeuCauHuyList;
    }

    public void setMonYeuCauHuyList(ArrayList<MonOrder> monYeuCauHuyList) {
        this.monYeuCauHuyList = monYeuCauHuyList;
    }

    public ArrayList<MonOrder> getMonYeuCauList() {
        return monYeuCauList;
    }

    public void setMonYeuCauList(ArrayList<MonOrder> monYeuCauList) {
        this.monYeuCauList = monYeuCauList;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public void setYeuCauJson(String yeuCauJson) {
        this.yeuCauJson = yeuCauJson;
    }

    public String getYeuCauJson() {
        return yeuCauJson;
    }

    public void update(YeuCau yeuCauNew) {
        setType(yeuCauNew.getType());
        setYeuCauJson(yeuCauNew.getYeuCauJson());
        setThoiGian(yeuCauNew.getThoiGian());
        setMonYeuCauHuyList(yeuCauNew.getMonYeuCauHuyList());
        setMonYeuCauList(yeuCauNew.getMonYeuCauList());
    }
}
