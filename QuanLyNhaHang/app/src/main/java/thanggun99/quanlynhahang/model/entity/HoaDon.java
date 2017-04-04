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
    public static int DA_TINH_TIEN = 1;
    public static int CHUA_TINH_TIEN = 0;

    private int maHoaDon;
    private DatBan datBan;
    private KhachHang khachHang;
    private int giamGia;
    private Ban ban;
    private String gioDen;
    private int trangThai;
    private ArrayList<MonOrder> monOrderList = new ArrayList<>();
    private String yeuCauJson;

    public HoaDon() {
    }

    public HoaDon(int maHoaDon, int giamGia, String gioDen, ArrayList<MonOrder> monOrderList) {
        this.maHoaDon = maHoaDon;
        this.giamGia = giamGia;
        this.gioDen = gioDen;
        this.monOrderList = monOrderList;

    }

    public boolean taoMoiHoaDon(MonOrder monOrderNew) {
        Map<String, String> postParams = new HashMap<>();
        if (getDatBan() != null) {
            postParams.put("maDatBan", String.valueOf(getDatBan().getMaDatBan()));
        }
        postParams.put("maBan", String.valueOf(getBan().getMaBan()));
        postParams.put("gioDen", getGioDen());

        if (monOrderNew != null) {
            postParams.put("maMon", String.valueOf(monOrderNew.getMaMon()));
            postParams.put("soLuong", String.valueOf(monOrderNew.getSoLuong()));
        } else {
            postParams.put("listMon", getYeuCauJson());
            postParams.put("maKhachHang", String.valueOf(khachHang.getMaKhachHang()));
        }


        String s = API.callService(API.TAO_MOI_HOA_DON_URL, null, postParams);
        if (!TextUtils.isEmpty(s) && !s.contains("false")) {
            try {
                JSONArray jsonArray = new JSONObject(s).getJSONArray("ma");
                JSONObject object = (JSONObject) jsonArray.get(0);

                getBan().setTrangThai(Ban.DANG_PHUC_VU);

                int maHoaDon = object.getInt("maHoaDon");
                setMaHoaDon(maHoaDon);

                if (monOrderNew != null) {
                    int maChiTietHD = object.getInt("maChiTietHD");

                    monOrderNew.setMaChitietHD(maChiTietHD);

                    addMonOrder(monOrderNew);
                } else {
                    setYeuCauJson(null);
                    JSONArray arrayMaChiTiet = object.getJSONArray("maChiTietHD");

                    for (int i = 0; i < arrayMaChiTiet.length(); i++) {
                        JSONObject object1 = (JSONObject) arrayMaChiTiet.get(i);

                        monOrderList.get(i).setMaChitietHD(object1.getInt("maChiTietHD"));
                    }
                }

                return true;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    public Boolean orderMonKhachHangYeuCau(YeuCau yeuCau) {
        Map<String, String> postParams = new HashMap<>();

        postParams.put("maHoaDon", String.valueOf(maHoaDon));
        postParams.put("maKhachHang", String.valueOf(getKhachHang().getMaKhachHang()));

        JSONObject object = new JSONObject();

        JSONArray listMonUpdate = new JSONArray();
        JSONArray listMonNew = new JSONArray();

        ArrayList<MonOrder> monYeuCauList = new ArrayList<>();
        monYeuCauList.addAll(yeuCau.getMonYeuCauList());

        ArrayList<MonOrder> monUpdateList = new ArrayList<>();

        try {

            for (MonOrder monYeuCau : yeuCau.getMonYeuCauList()) {
                for (MonOrder monDaOrder : getMonOrderList()) {
                    if (monDaOrder.getMaMon() == monYeuCau.getMaMon()) {
                        JSONObject monUpdate = new JSONObject();

                        int soLuong = monDaOrder.getSoLuong() + monYeuCau.getSoLuong();

                        monUpdate.put("maChiTietHD", monDaOrder.getMaChitietHD());
                        monUpdate.put("soLuong", soLuong);

                        listMonUpdate.put(monUpdate);

                        MonOrder monOrder = monDaOrder;
                        monOrder.setSoLuong(soLuong);

                        monUpdateList.add(monOrder);
                        monYeuCauList.remove(monYeuCau);
                    }
                }
            }
            for (MonOrder monYeuCau : monYeuCauList) {
                JSONObject monNew = new JSONObject();

                monNew.put("maMon", monYeuCau.getMaMon());
                monNew.put("soLuong", monYeuCau.getSoLuong());

                listMonNew.put(monNew);
            }


            object.put("listMonUpdate", listMonUpdate);
            object.put("listMonNew", listMonNew);

            postParams.put("listMon", object.toString());

        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        String s = API.callService(API.ORDER_MON_KHACH_HANG_YEU_CAU, null, postParams);

        if (!TextUtils.isEmpty(s)) {

            try {
                JSONArray arrayMaChiTiet = new JSONObject(s).getJSONArray("maChiTietHD");


                for (int i = 0; i < arrayMaChiTiet.length(); i++) {
                    JSONObject object1 = (JSONObject) arrayMaChiTiet.get(i);

                    monYeuCauList.get(i).setMaChitietHD(object1.getInt("maChiTietHD"));
                    Utils.showLog(object1.getInt("maChiTietHD") + "");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }

            for (MonOrder monOrder : monOrderList) {
                for (MonOrder monUpdate : monUpdateList) {
                    if (monOrder.getMaChitietHD() == monUpdate.getMaChitietHD()) {
                        monOrder.setSoLuong(monUpdate.getSoLuong());
                    }
                }
            }
            monOrderList.addAll(0, monYeuCauList);

            return true;

        }


        return false;
    }

    public Boolean deleteHoaDon() {
        Map<String, String> getParams = new HashMap<>();
        if (getDatBan() != null) {
            getParams.put("maDatBan", String.valueOf(getDatBan().getMaDatBan()));
        }
        getParams.put("maHoaDon", String.valueOf(getMaHoaDon()));
        getParams.put("maBan", String.valueOf(getBan().getMaBan()));

        String s = API.callService(API.DELETE_HOA_DON_URL, getParams);

        if (!TextUtils.isEmpty(s) && s.trim().contains("success")) {
            getBan().setTrangThai(Ban.TRONG);
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
        if (khachHang != null) {
            postParams.put("maKhachHang", String.valueOf(khachHang.getMaKhachHang()));
        }
        int maDatBan = 0;
        if (getDatBan() != null) {
            maDatBan = getDatBan().getMaDatBan();
        }
        postParams.put("maDatBan", String.valueOf(maDatBan));

        String s = API.callService(API.TINH_TIEN_HOA_DON_URL, getParams, postParams);

        if (!TextUtils.isEmpty(s) && s.trim().contains("success")) {
            getBan().setTrangThai(Ban.TRONG);
            if (getDatBan() != null) {

                getDatBan().setTrangThai(DatBan.DA_TINH_TIEN);
            }
            setTrangThai(HoaDon.DA_TINH_TIEN);
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
        if (getKhachHang() != null) {
            postParams.put("maKhachHang", String.valueOf(getKhachHang().getMaKhachHang()));
        }

        String s = API.callService(API.UPDATE_GIAM_GIA_URL, getParams, postParams);

        if (!TextUtils.isEmpty(s) && s.trim().contains("success")) {
            setGiamGia(presentSale);
            return true;
        }
        return false;
    }

    public boolean deleteMonOrder(int maChiTietHD) {
        Map<String, String> getParams = new HashMap<>();
        getParams.put("maChiTietHD", String.valueOf(maChiTietHD));
        String s = API.callService(API.DELETE_MON_ORDER_URL, getParams);
        if (!TextUtils.isEmpty(s) && s.trim().contains("success")) {
            getMonOrderList().remove(getMonOrderByMa(maChiTietHD));
            return true;
        }
        return false;
    }

    private MonOrder getMonOrderByMa(int maChiTietHD) {
        for (MonOrder monOrder : getMonOrderList()) {
            if (monOrder.getMaChitietHD() == maChiTietHD) {
                return monOrder;
            }
        }
        return null;
    }

    public boolean themMonOrder(MonOrder monOrderNew) {
        Map<String, String> postParams = new HashMap<>();
        postParams.put("maHoaDon", String.valueOf(getMaHoaDon()));
        postParams.put("maMon", String.valueOf(monOrderNew.getMaMon()));
        postParams.put("soLuong", String.valueOf(monOrderNew.getSoLuong()));

        String s = API.callService(API.THEM_MON_ORDER_URL, null, postParams);
        if (!TextUtils.isEmpty(s)) {

            monOrderNew.setMaChitietHD(Integer.parseInt(s.trim()));

            addMonOrder(monOrderNew);
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

    public ArrayList<MonOrder> getMonOrderList() {
        return monOrderList;
    }

    public void setMonOrderList(ArrayList<MonOrder> monOrderList) {
        this.monOrderList = monOrderList;
    }

    public void addMonOrder(MonOrder monOrder) {
        this.monOrderList.add(0, monOrder);
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
        return this == obj;

    }

    @Override
    public int hashCode() {
        return maHoaDon;
    }

    public int getTienMon() {
        int tienMon = 0;
        for (MonOrder monOrder : getMonOrderList()) {
            tienMon += monOrder.getTongTien();
        }
        return tienMon;
    }

    public int getSoMon() {
        int soMon = 0;
        for (MonOrder monOrder : getMonOrderList()) {
            soMon += monOrder.getSoLuong();
        }
        return soMon;
    }

    public int getTienGiamGia() {
        if (giamGia > 0) {
            return Math.round((float) (getTienMon() / 100 * giamGia) / 1000) * 1000;
        }
        return 0;
    }

    public String getYeuCauJson() {
        return yeuCauJson;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
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

}
