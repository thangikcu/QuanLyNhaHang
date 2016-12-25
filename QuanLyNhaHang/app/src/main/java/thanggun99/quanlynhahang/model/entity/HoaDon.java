package thanggun99.quanlynhahang.model.entity;

import java.util.ArrayList;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.util.Utils;

/**
 * Created by Thanggun99 on 24/11/2016.
 */

public class HoaDon {
    private int maHoaDon;
    private int maDatTruoc;
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

    public int getMaDatTruoc() {
        return maDatTruoc;
    }

    public void setMaDatTruoc(int maDatTruoc) {
        this.maDatTruoc = maDatTruoc;
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
        return Math.round((float) (getTienMon() - getTienGiamGia()) / 1000) * 1000;
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

    public int getTienMon() {
        int tienMon = 0;
        for (int i = 0; i < thucDonOrders.size(); i++) {
            tienMon += thucDonOrders.get(i).getTongTien();
        }
        return tienMon;
    }

    public int getSoMon() {
        int soMon = 0;
        for (int i = 0; i < thucDonOrders.size(); i++) {
            soMon += thucDonOrders.get(i).getSoLuong();
        }
        return soMon;
    }

    public int getTienGiamGia() {
        if (giamGia > 0) {
            return Math.round((float) (getTienMon() / 100 * giamGia) / 1000) * 1000;
        }
        return 0;
    }
}
