package thanggun99.quanlynhahang.model.entity;

import java.io.Serializable;


public class KhachHang extends Person implements Serializable{
    private int maKhachHang;
    private String tenDangNhap;
    private String matKhau;
    private int maToken;
    private DatBan datBan;

    public KhachHang(int maKhachHang, String tenKhachHang, String soDienThoai,
                     String diaChi, String tenDangNhap, String matKhau, int maToken) {
        super(tenKhachHang, soDienThoai, diaChi);
        this.maKhachHang = maKhachHang;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.maToken = maToken;
    }

    public KhachHang() {

    }

    public KhachHang(int maKhachHang) {

        this.maKhachHang = maKhachHang;
    }

    public int getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(int maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public int getMaToken() {
        return maToken;
    }

    public void setMaToken(int maToken) {
        this.maToken = maToken;
    }

    public void setDatBan(DatBan datBan) {
        this.datBan = datBan;
    }

    public DatBan getDatBan() {
        return datBan;
    }
}
