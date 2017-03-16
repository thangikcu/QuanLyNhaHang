package thanggun99.quanlynhahang.model.entity;

import java.io.Serializable;


public class KhachHang implements Serializable{
    private int maKhachHang;
    private String tenKhachHang;
    private String soDienThoai;
    private String diaChi;
    private String tenDangNhap;
    private String matKhau;
    private int maToken;

    public KhachHang(int maKhachHang, String tenKhachHang, String soDienThoai,
                     String diaChi, String tenDangNhap, String matKhau, int maToken) {
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.maToken = maToken;
    }

    public KhachHang() {

    }

    public int getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(int maKhachHang) {
        this.maKhachHang = maKhachHang;
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

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
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

}
