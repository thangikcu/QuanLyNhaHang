package thanggun99.quanlynhahang.model.phucvu;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import thanggun99.quanlynhahang.model.entity.DatBan;
import thanggun99.quanlynhahang.util.API;

import static thanggun99.quanlynhahang.util.API.callService;

/**
 * Created by Thanggun99 on 18/12/2016.
 */

public class DatBanManager {
    private static ArrayList<DatBan> datBans;

    public DatBanManager() {
        datBans = new ArrayList<>();
    }

    public boolean loadListDatBan() {
        String s = callService(API.GET_DAT_BAN_URL, null);
        if (!TextUtils.isEmpty(s)) {
            try {
                JSONObject jsonObj = new JSONObject(s);
                if (jsonObj != null) {
                    JSONArray jsonArray = jsonObj.getJSONArray("datBan");
                    datBans.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = (JSONObject) jsonArray.get(i);

                        DatBan datBan = new DatBan(object.getInt("maDatBan"), BanManager.getBanByMaBan(object.getInt("maBan")),
                                object.getString("tenKhachHang"), object.getString("soDienThoai"), object.getString("gioDen"),
                                object.getString("yeuCau"), object.getInt("trangThai"));
                        datBans.add(datBan);
                    }
                    return true;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public DatBan getDatBanByMaBan(int maBan) {
        for (int i = 0; i < datBans.size(); i++) {
            if (datBans.get(i).getBan().getMaBan() == maBan) {
                return datBans.get(i);
            }
        }
        return null;
    }

    public boolean datBan(DatBan datBan) {
        Map<String, String> postParams = new HashMap<>();
        postParams.put("tenKhachHang", datBan.getTenKhachHang());
        postParams.put("soDienThoai", datBan.getSoDienThoai());
        postParams.put("gioDen", datBan.getGioDen());
        if (!TextUtils.isEmpty(datBan.getYeuCau()))
            postParams.put("yeuCau", datBan.getYeuCau());
        postParams.put("maBan", String.valueOf(datBan.getBan().getMaBan()));

        String s = API.callService(API.DAT_BAN_URL, null, postParams);

        if (!TextUtils.isEmpty(s)) {
            datBan.setMaDatBan(Integer.parseInt(s.trim()));
            datBans.add(datBan);
            return true;
        }
        return false;
    }

    public Boolean huyDatBan(DatBan datBan) {
        Map<String, String> getParams = new HashMap<>();
        getParams.put("maDatBan", String.valueOf(datBan.getMaDatBan()));
        getParams.put("maBan", String.valueOf(datBan.getBan().getMaBan()));

        String s = API.callService(API.DELETE_DAT_BAN_URL, getParams);

        if (!TextUtils.isEmpty(s) && s.trim().contains("success")) {
            datBans.remove(datBan);
            return true;
        }
        return false;
    }

    public static ArrayList<DatBan> getDatBans() {
        return datBans;
    }

    public void deleteDatBan(int maDatBan) {
        for (int i = 0; i < datBans.size(); i++) {
            if (datBans.get(i).getMaDatBan() == maDatBan) {
                datBans.remove(i);
                return;
            }
        }
    }

    public Boolean updateDatBan(DatBan datBanUpdate) {
        Map<String, String> postParams, getParams;
        postParams = new HashMap<>();
        getParams = new HashMap<>();
        getParams.put("maDatBan", String.valueOf(datBanUpdate.getMaDatBan()));
        postParams.put("tenKhachHang", datBanUpdate.getTenKhachHang());
        postParams.put("soDienThoai", datBanUpdate.getSoDienThoai());
        postParams.put("gioDen", datBanUpdate.getGioDen());
        if (!TextUtils.isEmpty(datBanUpdate.getYeuCau()))
            postParams.put("yeuCau", datBanUpdate.getYeuCau());

        String s = API.callService(API.UPDATE_DAT_BAN_URL, getParams, postParams);

        if (!TextUtils.isEmpty(s) && s.trim().contains("success")) {
            return true;
        }
        return false;
    }

    public static DatBan getDatBanByMaDatBan(int maDatBan) {
        for (int i = 0; i < datBans.size(); i++) {
            if (datBans.get(i).getMaDatBan() == maDatBan){
                return datBans.get(i);
            }
        }
        return  null;
    }
}
