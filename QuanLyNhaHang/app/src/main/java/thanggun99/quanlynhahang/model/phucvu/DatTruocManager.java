package thanggun99.quanlynhahang.model.phucvu;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import thanggun99.quanlynhahang.model.entity.DatTruoc;
import thanggun99.quanlynhahang.util.API;

import static android.content.ContentValues.TAG;
import static thanggun99.quanlynhahang.util.API.callService;

/**
 * Created by Thanggun99 on 18/12/2016.
 */

public class DatTruocManager {
    private static ArrayList<DatTruoc> datTruocs = new ArrayList<>();

    public boolean loadListDatTruoc() {
        String s = callService(API.GET_DAT_TRUOC_URL, null);
        if (!TextUtils.isEmpty(s)) {
            try {
                JSONObject jsonObj = new JSONObject(s);
                if (jsonObj != null) {
                    JSONArray jsonArray = jsonObj.getJSONArray("datTruoc");
                    datTruocs.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = (JSONObject) jsonArray.get(i);

                        DatTruoc datTruoc = new DatTruoc(object.getInt("maDatTruoc"), BanManager.getBanByMaBan(object.getInt("maBan")), object.getString("tenKhachHang"), object.getString("soDienThoai"), object.getString("gioDen"), object.getString("ghiChu"), object.getInt("trangThai"));
                        datTruocs.add(datTruoc);
                    }
                    return true;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public DatTruoc getDatTruocByMaBan(int maBan) {
        for (int i = 0; i < datTruocs.size(); i++) {
            if (datTruocs.get(i).getBan().getMaBan() == maBan) {
                return datTruocs.get(i);
            }
        }
        return null;
    }

    public boolean datBan(DatTruoc datTruoc) {
        Map<String, String> postParams = new HashMap<>();
        postParams.put("tenKhachHang", datTruoc.getTenKhachHang());
        postParams.put("soDienThoai", datTruoc.getSoDienThoai());
        postParams.put("gioDen", datTruoc.getGioDen());
        if (!TextUtils.isEmpty(datTruoc.getGhiChu()))
            postParams.put("ghiChu", datTruoc.getGhiChu());
        postParams.put("maBan", String.valueOf(datTruoc.getBan().getMaBan()));

        String s = API.callService(API.DAT_TRUOC_URL, null, postParams);

        if (!TextUtils.isEmpty(s)) {
            datTruoc.setMaDatTruoc(Integer.parseInt(s.trim()));
            datTruocs.add(datTruoc);
            return true;
        }
        return false;
    }

    public Boolean huyDatBan(DatTruoc datTruoc) {
        Map<String, String> getParams = new HashMap<>();
        getParams.put("maDatTruoc", String.valueOf(datTruoc.getMaDatTruoc()));
        getParams.put("maBan", String.valueOf(datTruoc.getBan().getMaBan()));

        String s = API.callService(API.DELETE_DAT_TRUOC_URL, getParams);

        if (!TextUtils.isEmpty(s) && s.trim().contains("success")) {
            datTruocs.remove(datTruoc);
            return true;
        }
        return false;
    }

    public static ArrayList<DatTruoc> getDatTruocs() {
        return datTruocs;
    }

    public void deleteDatTruoc(int maDatTruoc) {
        for (int i = 0; i < datTruocs.size(); i++) {
            if (datTruocs.get(i).getMaDatTruoc() == maDatTruoc) {
                datTruocs.remove(i);
                return;
            }
        }
    }

    public Boolean updateDatBan(DatTruoc datTruocUpdate) {
        Map<String, String> postParams, getParams;
        postParams = new HashMap<>();
        getParams = new HashMap<>();
        getParams.put("maDatTruoc", String.valueOf(datTruocUpdate.getMaDatTruoc()));
        postParams.put("tenKhachHang", datTruocUpdate.getTenKhachHang());
        postParams.put("soDienThoai", datTruocUpdate.getSoDienThoai());
        postParams.put("gioDen", datTruocUpdate.getGioDen());
        if (!TextUtils.isEmpty(datTruocUpdate.getGhiChu()))
            postParams.put("ghiChu", datTruocUpdate.getGhiChu());

        String s = API.callService(API.UPDATE_DAT_TRUOC_URL, getParams, postParams);

        if (!TextUtils.isEmpty(s) && s.trim().contains("success")) {
            return true;
        }
        return false;
    }
}
