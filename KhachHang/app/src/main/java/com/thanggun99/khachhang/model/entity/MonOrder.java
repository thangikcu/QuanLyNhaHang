package com.thanggun99.khachhang.model.entity;

public class MonOrder extends Mon {
    private int soLuong;
    private int maChitietHD;

    public MonOrder(Mon mon, int soLuong, int maChitietHD) {
        super(mon.getMaMon(), mon.getTenMon(), mon.getMaLoai(), mon.getDonGia(), mon.getDonViTinh(),mon.getHinhAnh(), mon.getRating(), mon.getPersonRating());
        this.soLuong = soLuong;
        this.maChitietHD = maChitietHD;
    }

    public MonOrder() {
    }

    public void setMon(Mon mon) {
        setMaMon(mon.getMaMon());
        setTenMon(mon.getTenMon());
        setMaLoai(mon.getMaLoai());
        setDonGia(mon.getDonGia());
        setDonViTinh(mon.getDonViTinh());
        setHinhAnh(mon.getHinhAnh());
        setRating(mon.getRating());
        setPersonRating(mon.getPersonRating());
    }

    public int getMaChitietHD() {
        return maChitietHD;
    }

    public void setMaChitietHD(int maChitietHD) {
        this.maChitietHD = maChitietHD;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getTongTien(){
        return getDonGia() * soLuong;
    }

}
