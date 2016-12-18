package thanggun99.quanlynhahang.model.phucvu;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import thanggun99.quanlynhahang.model.entity.NhomMon;
import thanggun99.quanlynhahang.util.API;


/**
 * Created by Thanggun99 on 04/12/2016.
 */

public class NhomMonManager {
    private static ArrayList<NhomMon> nhomMons = new ArrayList<>();

    public NhomMonManager() {
    }

    public boolean loadListNhomMon() {
        String s = API.callService(API.GET_NHOM_MON_URL, null);

        if (!TextUtils.isEmpty(s)) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject != null) {
                    JSONArray jsonArray = jsonObject.getJSONArray("nhomMon");
                    nhomMons.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = (JSONObject) jsonArray.get(i);
                        nhomMons.add(new NhomMon(object.getInt("maLoai"), object.getString("tenLoai"), object.getString("mauSac")));
                    }
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public ArrayList<NhomMon> getListNhomMon() {
        return nhomMons;
    }

    public NhomMon getNhomMonAt(int position) {
        return nhomMons.get(position);
    }

}
