package com.thanggun99.khachhang.model;

import android.text.TextUtils;
import android.util.Base64;

import com.thanggun99.khachhang.model.entity.Mon;
import com.thanggun99.khachhang.model.entity.NhomMon;
import com.thanggun99.khachhang.model.entity.TinTuc;
import com.thanggun99.khachhang.util.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Thanggun99 on 25/03/2017.
 */

public class Database {
    private ArrayList<NhomMon> nhomMonList;
    private ArrayList<TinTuc> tinTucList;
    private ArrayList<Mon> monList;

    public Database() {
    }

    public Boolean loadTinTucList() {
        String s = API.callService(API.GET_TIN_TUC_URL, null);

        if (!TextUtils.isEmpty(s)) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("tinTuc");

                tinTucList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = (JSONObject) jsonArray.get(i);

                    TinTuc tinTuc = new TinTuc();
                    tinTuc.setMaTinTuc(object.getInt("maTinTuc"));
                    tinTuc.setTieuDe(object.getString("tieuDe"));
                    tinTuc.setNoiDung(object.getString("noiDung"));
                    tinTuc.setNgayDang(object.getString("ngayDang"));

                    if (!object.isNull("hinhAnh")) {
                        byte[] bytes = Base64.decode(object.getString("hinhAnh"), Base64.DEFAULT);

                        tinTuc.setHinhAnh(bytes);
                    }

                    tinTucList.add(tinTuc);
                }
                return true;

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    public Boolean loadThucDonList() {
        boolean taskOk;

        taskOk = loadListNhomMon();
        if (taskOk) loadListMon();

        return taskOk;
    }

    private boolean loadListNhomMon() {
        String s = API.callService(API.GET_NHOM_MON_URL, null);

        if (!TextUtils.isEmpty(s)) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject != null) {
                    JSONArray jsonArray = jsonObject.getJSONArray("nhomMon");
                    nhomMonList = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = (JSONObject) jsonArray.get(i);
                        nhomMonList.add(new NhomMon(object.getInt("maLoai"), object.getString("tenLoai"),
                                object.getString("mauSac")));
                    }
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean loadListMon() {
        String s = API.callService(API.GET_MON_URL, null);

        if (!TextUtils.isEmpty(s)) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject != null) {
                    JSONArray jsonArray = jsonObject.getJSONArray("thucDon");
                    monList = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = (JSONObject) jsonArray.get(i);

                        Mon mon = new Mon(object.getInt("maMon"), object.getString("tenMon"),
                                object.getInt("maLoai"), object.getInt("donGia"),
                                object.getString("donViTinh"), (float) object.getDouble("rating"), object.getInt("personRating"));

                        if (!object.isNull("hinhAnh")) {
                            byte[] hinhAnh = Base64.decode(object.getString("hinhAnh"), Base64.DEFAULT);

                            mon.setHinhAnh(hinhAnh);
                        }


                        monList.add(mon);
                    }
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public Mon getMonByMaMon(int maMon) {
        for (Mon mon : monList) {
            if (mon.getMaMon() == maMon) {
                return mon;
            }
        }
        return null;
    }

    public ArrayList<Mon> getListThucDonByMaLoai(int maLoai) {
        ArrayList<Mon> monTheoLoai = new ArrayList<>();
        for (Mon mon : monList) {
            if (mon.getMaLoai() == maLoai) {
                monTheoLoai.add(mon);
            }
        }
        return monTheoLoai;
    }

    public ArrayList<TinTuc> getTinTucList() {
        return tinTucList;
    }

    public void setTinTucList(ArrayList<TinTuc> tinTucList) {
        this.tinTucList = tinTucList;
    }

    public ArrayList<Mon> getMonList() {
        return monList;
    }

    public void setMonList(ArrayList<Mon> monList) {
        this.monList = monList;
    }

    public ArrayList<NhomMon> getNhomMonList() {
        return nhomMonList;
    }

    public void setNhomMonList(ArrayList<NhomMon> nhomMonList) {
        this.nhomMonList = nhomMonList;
    }
}
