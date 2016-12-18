package thanggun99.quanlynhahang.model.entity;

/**
 * Created by Thanggun99 on 18/12/2016.
 */

public class DatTruoc {
    private int maKhachHang;
    private int maDatTruoc;
    private Ban ban;
    private String tenKhachHang;
    private String soDienThoai;
    private String gioDen;
    private String ghiChu;
    private int trangThai;

    public DatTruoc(int maDatTruoc, Ban ban, String tenKhachHang, String soDienThoai, String gioDen, String ghiChu, int trangThai) {
        this.maDatTruoc = maDatTruoc;
        this.ban = ban;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.gioDen = gioDen;
        this.ghiChu = ghiChu;
        this.trangThai = trangThai;
    }

    public DatTruoc() {
    }

    public int getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(int maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public int getMaDatTruoc() {
        return maDatTruoc;
    }

    public void setMaDatTruoc(int maDatTruoc) {
        this.maDatTruoc = maDatTruoc;
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

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}
