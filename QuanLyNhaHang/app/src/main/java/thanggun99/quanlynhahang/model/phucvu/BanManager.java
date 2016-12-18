package thanggun99.quanlynhahang.model.phucvu;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import thanggun99.quanlynhahang.model.entity.Ban;
import thanggun99.quanlynhahang.util.API;

import static thanggun99.quanlynhahang.util.API.callService;


/**
 * Created by Thanggun99 on 04/12/2016.
 */

public class BanManager {
    private static ArrayList<Ban> listBan = new ArrayList<>();

    public BanManager() {
    }

    public boolean loadListBan() {
        String s = callService(API.GET_BAN_URL, null);
        if (!TextUtils.isEmpty(s)) {
            try {
                JSONObject jsonObj = new JSONObject(s);
                if (jsonObj != null) {
                    JSONArray jsonArray = jsonObj.getJSONArray("ban");
                    listBan.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = (JSONObject) jsonArray.get(i);

                        Ban ban = new Ban(object.getInt("maBan"), object.getString("tenBan"), object.getInt("trangThai"), object.getInt("hienThi"));
                        listBan.add(ban);
                    }
                    return true;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static Ban getBanByMaBan(int maban) {
        for (int i = 0; i < listBan.size(); i++) {
            if (listBan.get(i).getMaBan() == maban) {
                return listBan.get(i);
            }
        }
        return null;
    }

    public ArrayList<Ban> getListBan() {
        return listBan;
    }

    public Ban getBanAt(int position) {
        return listBan.get(position);
    }

    public boolean updateBan(Ban ban) {
        Map<String, String> getParams, postParams;
        getParams = new HashMap<>();
        postParams = new HashMap();
        getParams.put("maBan", String.valueOf(ban.getMaBan()));

        if (ban.getTrangThai() != Ban.NOT_SET) {
            postParams.put("trangThai", String.valueOf(ban.getTrangThai()));
        }
        if (!TextUtils.isEmpty(ban.getTenBan())) {
            postParams.put("tenBan", ban.getTenBan());
        }
        if (ban.getHienThi() != Ban.NOT_SET) {
            postParams.put("hienThi", String.valueOf(ban.getHienThi()));
        }

        String s = API.callService(API.UPDATE_BAN_URL, getParams, postParams);

        if (!TextUtils.isEmpty(s) && s.trim().contains("success")) return true;
        return false;
    }

}
