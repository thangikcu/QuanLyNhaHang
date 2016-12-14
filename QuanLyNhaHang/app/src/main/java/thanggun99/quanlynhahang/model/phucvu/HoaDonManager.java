package thanggun99.quanlynhahang.model.phucvu;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import thanggun99.quanlynhahang.model.entity.HoaDon;
import thanggun99.quanlynhahang.model.entity.ThucDon;
import thanggun99.quanlynhahang.model.entity.ThucDonOrder;
import thanggun99.quanlynhahang.util.API;

import static android.content.ContentValues.TAG;


/**
 * Created by Thanggun99 on 05/12/2016.
 */

public class HoaDonManager {
    private static ArrayList<HoaDon> hoaDons = new ArrayList<>();

    public HoaDonManager() {
    }

    public boolean loadListHoaDon() {
        String s = API.callService(API.HOA_DON_URL, null);
        if (!TextUtils.isEmpty(s)) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject != null) {
                    JSONArray jsonArray = jsonObject.getJSONArray("hoaDon");
                    hoaDons.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = (JSONObject) jsonArray.get(i);
                        HoaDon hoaDon = new HoaDon();
                        hoaDon.setMaHoaDon(object.getInt("maHoaDon"));
                        hoaDon.setBan(BanManager.getBanByMaBan(object.getInt("maBan")));
                        if (object.toString().contains("giamGia")) {
                            hoaDon.setGiamGia(object.getInt("giamGia"));
                        }
                        hoaDon.setGioDen(object.getString("gioDen"));

                        JSONArray array = object.getJSONArray("thucDonOrder");
                        ArrayList<ThucDonOrder> thucDonOrders = new ArrayList<>();

                        for (int j = 0; j < array.length(); j++) {
                            JSONObject object1 = (JSONObject) array.get(j);

                            ThucDon thucDon = ThucDonManager.getThucDonByMaMon(object1.getInt("maMon"));
                            if (thucDon != null) {
                                thucDonOrders.add(new ThucDonOrder(object1.getInt("maChiTietHD"), thucDon.getMaMon(), thucDon.getTenMon(), thucDon.getMaLoai(), thucDon.getDonGia(), thucDon.getDonViTinh(), thucDon.getHinhAnh(), object1.getInt("soLuong")));
                            }
                        }
                        hoaDon.setThucDonOrders(thucDonOrders);
                        hoaDons.add(hoaDon);
                    }
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public HoaDon getHoaDonByMaBan(int maBan) {
        for (int i = 0; i < hoaDons.size(); i++) {
            if (hoaDons.get(i).getBan().getMaBan() == maBan) {
                return hoaDons.get(i);
            }
        }
        return null;
    }

    public boolean taoMoiHoaDon(HoaDon hoaDonNew, ThucDonOrder thucDonOrderNew) {
        Map<String, String> postParams = new HashMap<>();
        postParams.put("maBan", String.valueOf(hoaDonNew.getBan().getMaBan()));
        postParams.put("gioDen", hoaDonNew.getGioDen());
        postParams.put("maMon", String.valueOf(thucDonOrderNew.getMaMon()));
        postParams.put("soLuong", String.valueOf(thucDonOrderNew.getSoLuong()));

        String s = API.callService(API.TAO_MOI_HOA_DON_URL, null, postParams);
        if (!TextUtils.isEmpty(s)) {
            try {
                JSONArray jsonArray = new JSONObject(s).getJSONArray("ma");
                JSONObject object = (JSONObject) jsonArray.get(0);

                int maHoaDon = object.getInt("maHoaDon");
                int maChiTietHD = object.getInt("maChiTietHD");

                hoaDonNew.setMaHoaDon(maHoaDon);

                ThucDon thucDon = ThucDonManager.getThucDonByMaMon(thucDonOrderNew.getMaMon());
                if (thucDon != null) {
                    hoaDonNew.getBan().setTrangThai(2);
                    hoaDonNew.addThucDonOrder(new ThucDonOrder(maChiTietHD, thucDon.getMaMon(), thucDon.getTenMon(), thucDon.getMaLoai(), thucDon.getDonGia(), thucDon.getDonViTinh(), thucDon.getHinhAnh(), thucDonOrderNew.getSoLuong()));
                    hoaDons.add(hoaDonNew);
                    return true;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean themThucDonOrder(HoaDon hoaDon, ThucDonOrder thucDonOrderNew) {
        Map<String, String> postParams = new HashMap<>();
        postParams.put("maHoaDon", String.valueOf(hoaDon.getMaHoaDon()));
        postParams.put("maMon", String.valueOf(thucDonOrderNew.getMaMon()));
        postParams.put("soLuong", String.valueOf(thucDonOrderNew.getSoLuong()));

        String s = API.callService(API.THEM_THUC_DON_ORDER_URL, null, postParams);
        if (!TextUtils.isEmpty(s)) {
            ThucDon thucDon = ThucDonManager.getThucDonByMaMon(thucDonOrderNew.getMaMon());
            if (thucDon != null) {
                hoaDon.getThucDonOrders().add(0, new ThucDonOrder(Integer.parseInt(s.trim()), thucDon.getMaMon(), thucDon.getTenMon(), thucDon.getMaLoai(), thucDon.getDonGia(), thucDon.getDonViTinh(), thucDon.getHinhAnh(), thucDonOrderNew.getSoLuong()));
                return true;
            }
        }
        return false;
    }

    public boolean deleteThucDonOrder(int maChiTietHD) {
        Map<String, String> getParams = new HashMap<>();
        getParams.put("maChiTietHD", String.valueOf(maChiTietHD));
        String s = API.callService(API.DELETE_MON_ORDER_URL, getParams);
        Log.i(TAG, "deleteMonOrder: " + s);
        if (!TextUtils.isEmpty(s) && s.trim().contains("success")) return true;
        return false;
    }

    public boolean updateThucDonOrder(ThucDonOrder orderUpdate) {
        Map<String, String> getParams, postParams;
        getParams = new HashMap<>();
        postParams = new HashMap<>();
        postParams.put("soLuong", String.valueOf(orderUpdate.getSoLuong()));
        getParams.put("maChiTietHD", String.valueOf(orderUpdate.getMaChitietHD()));

        String s = API.callService(API.UPDATE_THUC_DON_ORDER_URL, getParams, postParams);

        if (!TextUtils.isEmpty(s) && s.trim().contains("success")) return true;
        return false;
    }

    public Boolean saleHoaDon(HoaDon hoaDon) {
        Map<String, String> getParams, postParams;
        getParams = new HashMap<>();
        postParams = new HashMap<>();
        getParams.put("maHoaDon", String.valueOf(hoaDon.getMaHoaDon()));
        postParams.put("giamGia", String.valueOf(hoaDon.getGiamGia()));

        String s = API.callService(API.UPDATE_GIAM_GIA_URL, getParams, postParams);

        if (!TextUtils.isEmpty(s) && s.trim().contains("success")) return true;
        return false;
    }

    public Boolean deleteHoaDon(HoaDon hoaDon) {
        Map<String, String> getParams = new HashMap<>();
        getParams.put("maHoaDon", String.valueOf(hoaDon.getMaHoaDon()));

        String s = API.callService(API.DELETE_HOA_DON_URL, getParams);

        if (!TextUtils.isEmpty(s) && s.trim().contains("success")){
            hoaDons.remove(hoaDon);
            return true;
        }
        return false;
    }

    public boolean tinhTien(HoaDon hoaDon) {
        Map<String, String> getParams, postParams;
        getParams = new HashMap<>();
        postParams = new HashMap<>();
        getParams.put("maHoaDon", String.valueOf(hoaDon.getMaHoaDon()));
        postParams.put("tongTien", String.valueOf(hoaDon.getTongTien()));

        String s = API.callService(API.TINH_TIEN_HOA_DON_URL, getParams, postParams);

        if (!TextUtils.isEmpty(s) && s.trim().contains("success")){
            hoaDons.remove(hoaDon);
            return true;
        }
        return false;
    }
}
