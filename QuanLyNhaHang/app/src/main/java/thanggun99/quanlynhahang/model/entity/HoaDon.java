package thanggun99.quanlynhahang.model.entity;

import java.util.ArrayList;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.util.Utils;

/**
 * Created by Thanggun99 on 24/11/2016.
 */

public class HoaDon {
    private int maHoaDon;
    private int giamGia;
    private Ban ban;
    private String gioDen;
    private ArrayList<ThucDonOrder> thucDonOrders = new ArrayList<>();

    public HoaDon() {
    }

    public HoaDon(int maHoaDon, int giamGia, String gioDen, ArrayList<ThucDonOrder> thucDonOrders) {
        this.maHoaDon = maHoaDon;
        this.giamGia = giamGia;
        this.gioDen = gioDen;
        this.thucDonOrders = thucDonOrders;
    }

    public Ban getBan() {
        return ban;
    }

    public void setBan(Ban ban) {
        this.ban = ban;
    }

    public ArrayList<ThucDonOrder> getThucDonOrders() {
        return thucDonOrders;
    }

    public void setThucDonOrders(ArrayList<ThucDonOrder> thucDonOrders) {
        this.thucDonOrders = thucDonOrders;
    }

    public void addThucDonOrder(ThucDonOrder thucDonOrder) {
        this.thucDonOrders.add(0, thucDonOrder);
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public int getGiamGia() {
        return giamGia;
    }

    public String getStringGiamGia() {
        if (giamGia > 0) {
            return giamGia + "%";
        } else {
            return Utils.getStringByRes(R.string.sale);
        }
    }

    public void setGiamGia(int giamGia) {
        this.giamGia = giamGia;
    }

    public String getGioDen() {
        return gioDen;
    }

    public void setGioDen(String gioDen) {
        this.gioDen = gioDen;
    }


    public int getTongTien() {
        int tongTien = 0;
        for (int i = 0; i < thucDonOrders.size(); i++) {
            tongTien += thucDonOrders.get(i).getTongTien();
        }
        if (giamGia > 0) {
            return tongTien - (tongTien / 100 * giamGia);
        }
        return tongTien;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        return false;

    }

    @Override
    public int hashCode() {
        return maHoaDon;
    }
}
