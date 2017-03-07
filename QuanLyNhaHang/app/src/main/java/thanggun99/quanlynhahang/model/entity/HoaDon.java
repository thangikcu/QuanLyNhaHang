package thanggun99.quanlynhahang.model.entity;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.util.API;
import thanggun99.quanlynhahang.util.Utils;

/**
 * Created by Thanggun99 on 24/11/2016.
 */

public class HoaDon {
    private int maHoaDon;
    private DatBan datBan;
    private int giamGia;
    private Ban ban;
    private String gioDen;
    private int trangThai;
    private ArrayList<ThucDonOrder> thucDonOrders = new ArrayList<>();

    public HoaDon() {
    }

    public HoaDon(int maHoaDon, int giamGia, String gioDen, ArrayList<ThucDonOrder> thucDonOrders) {
        this.maHoaDon = maHoaDon;
        this.giamGia = giamGia;
        this.gioDen = gioDen;
        this.thucDonOrders = thucDonOrders;

    }

    public boolean taoMoiHoaDon(ThucDonOrder thucDonOrderNew) {
        Map<String, String> postParams = new HashMap<>();
        if (getDatBan() != null){
            postParams.put("maDatBan", String.valueOf(getDatBan().getMaDatBan()));
        }
        postParams.put("maBan", String.valueOf(getBan().getMaBan()));
        postParams.put("gioDen", getGioDen());
        postParams.put("maMon", String.valueOf(thucDonOrderNew.getMaMon()));
        postParams.put("soLuong", String.valueOf(thucDonOrderNew.getSoLuong()));

        String s = API.callService(API.TAO_MOI_HOA_DON_URL, null, postParams);
        if (!TextUtils.isEmpty(s)) {
            try {
                JSONArray jsonArray = new JSONObject(s).getJSONArray("ma");
                JSONObject object = (JSONObject) jsonArray.get(0);

                int maHoaDon = object.getInt("maHoaDon");
                int maChiTietHD = object.getInt("maChiTietHD");

                thucDonOrderNew.setMaChitietHD(maChiTietHD);
                setMaHoaDon(maHoaDon);

                addThucDonOrder(thucDonOrderNew);
                getBan().setTrangThai(2);
                return true;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public Boolean deleteHoaDon() {
        Map<String, String> getParams = new HashMap<>();
        if (getDatBan() != null){
            getParams.put("maDatBan", String.valueOf(getDatBan().getMaDatBan()));
        }
        getParams.put("maHoaDon", String.valueOf(getMaHoaDon()));
        getParams.put("maBan", String.valueOf(getBan().getMaBan()));

        String s = API.callService(API.DELETE_HOA_DON_URL, getParams);

        if (!TextUtils.isEmpty(s) && s.trim().contains("success")) {
            getBan().setTrangThai(0);
            return true;
        }
        return false;
    }

    public boolean tinhTien() {
        Map<String, String> getParams, postParams;
        getParams = new HashMap<>();
        postParams = new HashMap<>();
        getParams.put("maHoaDon", String.valueOf(getMaHoaDon()));
        postParams.put("tongTien", String.valueOf(getTongTien()));
        postParams.put("maBan", String.valueOf(getBan().getMaBan()));

        String s = API.callService(API.TINH_TIEN_HOA_DON_URL, getParams, postParams);

        if (!TextUtils.isEmpty(s) && s.trim().contains("success")) {
            getBan().setTrangThai(0);
            setTrangThai(1);
            return true;
        }
        return false;
    }

    public Boolean saleHoaDon(int presentSale) {
        Map<String, String> getParams, postParams;
        getParams = new HashMap<>();
        postParams = new HashMap<>();
        getParams.put("maHoaDon", String.valueOf(getMaHoaDon()));
        postParams.put("giamGia", String.valueOf(presentSale));

        String s = API.callService(API.UPDATE_GIAM_GIA_URL, getParams, postParams);

        if (!TextUtils.isEmpty(s) && s.trim().contains("success")) {
            setGiamGia(presentSale);
            return true;
        }
        return false;
    }

    public boolean deleteThucDonOrder(int maChiTietHD) {
        Map<String, String> getParams = new HashMap<>();
        getParams.put("maChiTietHD", String.valueOf(maChiTietHD));
        String s = API.callService(API.DELETE_MON_ORDER_URL, getParams);
        if (!TextUtils.isEmpty(s) && s.trim().contains("success")) {
            getThucDonOrders().remove(getThucDonOrderByMa(maChiTietHD));
            return true;
        }
        return false;
    }

    private ThucDonOrder getThucDonOrderByMa(int maChiTietHD) {
        for (ThucDonOrder thucDonOrder : getThucDonOrders()) {
            if (thucDonOrder.getMaChitietHD() == maChiTietHD) {
                return thucDonOrder;
            }
        }
        return null;
    }

    public boolean themThucDonOrder(ThucDonOrder thucDonOrderNew) {
        Map<String, String> postParams = new HashMap<>();
        postParams.put("maHoaDon", String.valueOf(getMaHoaDon()));
        postParams.put("maMon", String.valueOf(thucDonOrderNew.getMaMon()));
        postParams.put("soLuong", String.valueOf(thucDonOrderNew.getSoLuong()));

        String s = API.callService(API.THEM_THUC_DON_ORDER_URL, null, postParams);
        if (!TextUtils.isEmpty(s)) {

            thucDonOrderNew.setMaChitietHD(Integer.parseInt(s.trim()));

            addThucDonOrder(thucDonOrderNew);
            return true;
        }
        return false;
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

    public DatBan getDatBan() {
        return datBan;
    }

    public void setDatBan(DatBan datBan) {
        this.datBan = datBan;
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
        for (ThucDonOrder thucDonOrder : getThucDonOrders()) {
            tienMon += thucDonOrder.getTongTien();
        }
        return tienMon;
    }

    public int getSoMon() {
        int soMon = 0;
        for (ThucDonOrder thucDonOrder : getThucDonOrders()) {
            soMon += thucDonOrder.getSoLuong();
        }
        return soMon;
    }

    public int getTienGiamGia() {
        if (giamGia > 0) {
            return Math.round((float) (getTienMon() / 100 * giamGia) / 1000) * 1000;
        }
        return 0;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}
