package thanggun99.quanlynhahang.model.entity;

import java.io.Serializable;

/**
 * Created by Thanggun99 on 19/03/2017.
 */

public abstract class Person implements Serializable{
    protected String hoTen;
    protected String soDienThoai;
    protected String diaChi;


    public Person(String hoTen, String soDienThoai, String diaChi) {
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
    }


    public Person() {
    }


    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
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
}
