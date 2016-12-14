package thanggun99.quanlynhahang.model.phucvu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import thanggun99.quanlynhahang.model.entity.ThucDon;
import thanggun99.quanlynhahang.util.API;


/**
 * Created by Thanggun99 on 05/12/2016.
 */

public class ThucDonManager {
    private static ArrayList<ThucDon> thucDons = new ArrayList<>();

    public ThucDonManager() {
    }

    public boolean loadListThucDon() {
        String s = API.callService(API.THUC_DON_URL, null);

        if (!TextUtils.isEmpty(s)) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject != null) {
                    JSONArray jsonArray = jsonObject.getJSONArray("thucDon");
                    thucDons.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = (JSONObject) jsonArray.get(i);
                        Bitmap bitmap = null;

                        if (!object.getString("hinhAnh").equals("")) {
                            byte[] hinhAnh = Base64.decode(object.getString("hinhAnh"), Base64.DEFAULT);
                            bitmap = BitmapFactory.decodeByteArray(hinhAnh, 0, hinhAnh.length);
                        }

                        ThucDon thucDon = new ThucDon(object.getInt("maMon"), object.getString("tenMon"), object.getInt("maLoai"), object.getInt("donGia"), object.getString("donViTinh"), bitmap, object.getInt("hienThi"));
                        thucDons.add(thucDon);
                    }
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public ArrayList<ThucDon> getListThucDonByMaLoai(int maLoai) {
        ArrayList<ThucDon> thucDonTheoLoai = new ArrayList<>();
        for (int i = 0; i < thucDons.size(); i++) {
            if (thucDons.get(i).getMaLoai() == maLoai) {
                thucDonTheoLoai.add(thucDons.get(i));
            }
        }
        return thucDonTheoLoai;
    }

    public ArrayList<ThucDon> getListThucDonByTenMon(String newText) {
        ArrayList<ThucDon> thucDonTimKiem = new ArrayList<>();
        for (int i = 0; i < thucDons.size(); i++) {
            if (thucDons.get(i).getTenMon().toLowerCase().trim().contains(newText.toLowerCase().trim())) {
                thucDonTimKiem.add(thucDons.get(i));
            }
        }
        return thucDonTimKiem;
    }

    public static ThucDon getThucDonByMaMon(int maMon) {
        for (int i = 0; i < thucDons.size(); i++) {
            if (thucDons.get(i).getMaMon() == maMon) {
                return thucDons.get(i);
            }
        }
        return null;
    }

    public ArrayList<ThucDon> getListThucDon() {
        return thucDons;
    }

}
