package thanggun99.quanlynhahang.model.entity;

/**
 * Created by Thanggun99 on 18/12/2016.
 */

public class DatBan {
    private int maKhachHang;
    private int maDatBan;
    private Ban ban;
    private String tenKhachHang;
    private String soDienThoai;
    private String gioDen;
    private String yeuCau;
    private int trangThai;

    public DatBan(int maDatBan, Ban ban, String tenKhachHang, String soDienThoai, String gioDen, String yeuCau, int trangThai) {
        this.maDatBan = maDatBan;
        this.ban = ban;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.gioDen = gioDen;
        this.yeuCau = yeuCau;
        this.trangThai = trangThai;
    }

    public DatBan() {
    }

    public int getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(int maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public int getMaDatBan() {
        return maDatBan;
    }

    public void setMaDatBan(int maDatBan) {
        this.maDatBan = maDatBan;
    }

    public Ban getBan() {
        return ban;
    }

    public void setBan(Ban ban) {
        this.ban = ban;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getGioDen() {
        return gioDen;
    }

    public void setGioDen(String gioDen) {
        this.gioDen = gioDen;
    }

    public String getYeuCau() {
        return yeuCau;
    }

    public void setYeuCau(String yeuCau) {
        this.yeuCau = yeuCau;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}
