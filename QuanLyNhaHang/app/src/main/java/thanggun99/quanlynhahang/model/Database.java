package thanggun99.quanlynhahang.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import thanggun99.quanlynhahang.model.entity.Ban;
import thanggun99.quanlynhahang.model.entity.DatBan;
import thanggun99.quanlynhahang.model.entity.HoaDon;
import thanggun99.quanlynhahang.model.entity.NhomMon;
import thanggun99.quanlynhahang.model.entity.ThucDon;
import thanggun99.quanlynhahang.model.entity.ThucDonOrder;
import thanggun99.quanlynhahang.util.API;

import static thanggun99.quanlynhahang.util.API.callService;

/**
 * Created by Thanggun99 on 07/03/2017.
 */

public class Database {
    private ArrayList<Ban> banList;
    private ArrayList<HoaDon> hoaDonChuaTinhTienList;
    private ArrayList<HoaDon> hoaDonTinhTienList;
    private ArrayList<ThucDon> thucDonList;
    private ArrayList<NhomMon> nhomMonList;
    private ArrayList<DatBan> datBanList;

    public Database() {
        hoaDonTinhTienList = new ArrayList<>();
    }

    public boolean getDatas() {
        boolean taskOk;
        taskOk = loadListNhomMon();
        if (taskOk) taskOk = loadListThucDon();
        if (taskOk) taskOk = loadListBan();
        if (taskOk) taskOk = loadListDatBan();
        if (taskOk) taskOk = loadListHoaDon();
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

    private boolean loadListHoaDon() {
        String s = API.callService(API.GET_HOA_DON_URL, null);
        if (!TextUtils.isEmpty(s)) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject != null) {
                    JSONArray jsonArray = jsonObject.getJSONArray("hoaDon");
                    hoaDonChuaTinhTienList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = (JSONObject) jsonArray.get(i);
                        HoaDon hoaDon = new HoaDon();
                        hoaDon.setTrangThai(object.getInt("trangThai"));
                        hoaDon.setMaHoaDon(object.getInt("maHoaDon"));
                        hoaDon.setBan(getBanByMaBan(object.getInt("maBan")));
                        if (object.toString().contains("giamGia")) {
                            hoaDon.setGiamGia(object.getInt("giamGia"));
                        }
                        if (object.toString().contains("maDatBan")) {
                            hoaDon.setDatBan(getDatBanByMaDatBan(object.getInt("maDatBan")));

                        }
                        hoaDon.setGioDen(object.getString("gioDen"));

                        JSONArray array = object.getJSONArray("thucDonOrder");
                        ArrayList<ThucDonOrder> thucDonOrders = new ArrayList<>();

                        for (int j = 0; j < array.length(); j++) {
                            JSONObject object1 = (JSONObject) array.get(j);

                            thucDonOrders.add(new ThucDonOrder(getThucDonByMaMon(object1.getInt("maMon")),
                                    object1.getInt("soLuong"), object1.getInt("maChiTietHD")));
                        }
                        hoaDon.setThucDonOrders(thucDonOrders);
                        hoaDonChuaTinhTienList.add(hoaDon);
                    }
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean loadListDatBan() {
        String s = callService(API.GET_DAT_BAN_URL, null);
        if (!TextUtils.isEmpty(s)) {
            try {
                JSONObject jsonObj = new JSONObject(s);
                if (jsonObj != null) {
                    JSONArray jsonArray = jsonObj.getJSONArray("datBan");
                    datBanList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = (JSONObject) jsonArray.get(i);
                        Ban ban = null;
                        int maKhachHang = 0;

                        if (!object.isNull("maBan")) {

                            ban = getBanByMaBan(object.getInt("maBan"));
                        }
                        if (!object.isNull("maKhachHang")) {
                            maKhachHang = object.getInt("maKhachHang");
                        }

                        DatBan datBan = new DatBan(object.getInt("maDatBan"), maKhachHang, ban,
                                object.getString("tenKhachHang"), object.getString("soDienThoai"), object.getString("gioDen"),
                                object.getString("yeuCau"), object.getInt("trangThai"));
                        datBanList.add(datBan);
                    }
                    return true;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean loadListBan() {
        String s = callService(API.GET_BAN_URL, null);
        if (!TextUtils.isEmpty(s)) {
            try {
                JSONObject jsonObj = new JSONObject(s);
                if (jsonObj != null) {
                    JSONArray jsonArray = jsonObj.getJSONArray("ban");
                    banList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = (JSONObject) jsonArray.get(i);

                        Ban ban = new Ban(object.getInt("maBan"), object.getString("tenBan"),
                                object.getInt("trangThai"), object.getInt("hienThi"));
                        banList.add(ban);
                    }
                    return true;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean loadListThucDon() {
        String s = callService(API.GET_THUC_DON_URL, null);

        if (!TextUtils.isEmpty(s)) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject != null) {
                    JSONArray jsonArray = jsonObject.getJSONArray("thucDon");
                    thucDonList = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = (JSONObject) jsonArray.get(i);
                        Bitmap bitmap = null;

                        if (!object.getString("hinhAnh").equals("")) {
                            byte[] hinhAnh = Base64.decode(object.getString("hinhAnh"), Base64.DEFAULT);
                            bitmap = BitmapFactory.decodeByteArray(hinhAnh, 0, hinhAnh.length);
                        }

                        ThucDon thucDon = new ThucDon(object.getInt("maMon"), object.getString("tenMon"),
                                object.getInt("maLoai"), object.getInt("donGia"), object.getString("donViTinh"),
                                bitmap, object.getInt("hienThi"));
                        thucDonList.add(thucDon);
                    }
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public ThucDon getThucDonByMaMon(int maMon) {
        for (ThucDon thucDon : thucDonList) {
            if (thucDon.getMaMon() == maMon) {
                return thucDon;
            }
        }
        return null;
    }

    public NhomMon getNhomMonAt(int position) {
        return nhomMonList.get(position);
    }

    public ArrayList<ThucDon> getListThucDonByTenMon(String newText) {
        ArrayList<ThucDon> thucDonTimKiem = new ArrayList<>();
        for (ThucDon thucDon : thucDonList) {
            if (thucDon.getTenMon().toLowerCase().trim().contains(newText.toLowerCase().trim())) {
                thucDonTimKiem.add(thucDon);
            }
        }
        return thucDonTimKiem;
    }

    public ArrayList<ThucDon> getListThucDonByMaLoai(int maLoai) {
        ArrayList<ThucDon> thucDonTheoLoai = new ArrayList<>();
        for (ThucDon thucDon : thucDonList) {
            if (thucDon.getMaLoai() == maLoai) {
                thucDonTheoLoai.add(thucDon);
            }
        }
        return thucDonTheoLoai;
    }

    public HoaDon getHoaDonByMaBan(int maBan) {
        for (HoaDon hoaDon : hoaDonChuaTinhTienList) {
            if (hoaDon.getBan().getMaBan() == maBan) {
                return hoaDon;
            }
        }
        return null;
    }

    public DatBan getDatBanByMaBan(int maBan) {
        for (DatBan datBan : datBanList) {
            if (datBan.getBan().getMaBan() == maBan) {
                return datBan;
            }
        }
        return null;
    }

    public Ban getBanByMaBan(int maban) {
        for (Ban ban : banList) {
            if (ban.getMaBan() == maban) {
                return ban;
            }
        }
        return null;
    }

    public DatBan getDatBanByMaDatBan(int maDatBan) {
        for (DatBan datBan : datBanList) {
            if (datBan.getMaDatBan() == maDatBan) {
                return datBan;
            }
        }
        return null;
    }

    public ArrayList<Ban> getBanList() {
        return banList;
    }

    public void setBanList(ArrayList<Ban> banList) {
        this.banList = banList;
    }

    public ArrayList<HoaDon> getHoaDonChuaTinhTienList() {
        return hoaDonChuaTinhTienList;
    }

    public void setHoaDonChuaTinhTienList(ArrayList<HoaDon> hoaDonChuaTinhTienList) {
        this.hoaDonChuaTinhTienList = hoaDonChuaTinhTienList;
    }

    public ArrayList<ThucDon> getThucDonList() {
        return thucDonList;
    }

    public void setThucDonList(ArrayList<ThucDon> thucDonList) {
        this.thucDonList = thucDonList;
    }

    public ArrayList<NhomMon> getNhomMonList() {
        return nhomMonList;
    }

    public void setNhomMonList(ArrayList<NhomMon> nhomMonList) {
        this.nhomMonList = nhomMonList;
    }

    public ArrayList<DatBan> getDatBanList() {
        return datBanList;
    }

    public void setDatBanList(ArrayList<DatBan> datBanList) {
        this.datBanList = datBanList;
    }

    public Ban getBanAt(int position) {
        return banList.get(position);
    }


    public ArrayList<HoaDon> getHoaDonTinhTienList() {
        return hoaDonTinhTienList;
    }

    public void setHoaDonTinhTienList(ArrayList<HoaDon> hoaDonTinhTienList) {
        this.hoaDonTinhTienList = hoaDonTinhTienList;
    }

    public void addHoaDonTinhTien(HoaDon hoaDon) {
        hoaDonTinhTienList.add(hoaDon);
    }

    public void addDatBan(DatBan datBan) {
        datBanList.add(datBan);
    }

    public void addhoaDonChuaTinhTien(HoaDon hoaDon) {
        hoaDonChuaTinhTienList.add(hoaDon);
    }

    public void removeHoaDonChuaTinhTien(HoaDon hoaDon) {
        hoaDonChuaTinhTienList.remove(hoaDon);
    }

    public void removeDatBan(DatBan datBan) {
        datBanList.remove(datBan);
    }
}
