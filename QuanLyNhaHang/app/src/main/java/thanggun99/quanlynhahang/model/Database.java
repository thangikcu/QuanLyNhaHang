package thanggun99.quanlynhahang.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import thanggun99.quanlynhahang.model.entity.Ban;
import thanggun99.quanlynhahang.model.entity.DatBan;
import thanggun99.quanlynhahang.model.entity.HoaDon;
import thanggun99.quanlynhahang.model.entity.KhachHang;
import thanggun99.quanlynhahang.model.entity.NhomMon;
import thanggun99.quanlynhahang.model.entity.ThucDon;
import thanggun99.quanlynhahang.model.entity.ThucDonOrder;
import thanggun99.quanlynhahang.util.API;
import thanggun99.quanlynhahang.util.Utils;

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
    private ArrayList<DatBan> datBanChuaTinhTienList;
    private ArrayList<DatBan> datBanTinhTienList;
    private ArrayList<DatBan> datBanChuaSetBanList;
    private ArrayList<KhachHang> khachHangList;

    public Database() {
        hoaDonTinhTienList = new ArrayList<>();
        datBanTinhTienList = new ArrayList<>();
    }

    public boolean getDatas() {
        boolean taskOk;
        taskOk = loadListNhomMon();
        if (taskOk) taskOk = loadListThucDon();
        if (taskOk) taskOk = loadListBan();
        if (taskOk) taskOk = loadListKhachHang();
        if (taskOk) taskOk = loadListDatBan();
        if (taskOk) taskOk = loadListHoaDon();
        return taskOk;
    }

    private boolean loadListKhachHang() {
        String s = API.callService(API.GET_KHACH_HANG, null);

        if (!TextUtils.isEmpty(s)) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("khachHang");

                khachHangList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = (JSONObject) jsonArray.get(i);

                    KhachHang khachHang = new KhachHang();
                    khachHang.setMaKhachHang(object.getInt("maKhachHang"));
                    khachHang.setTenKhachHang(object.getString("tenKhachHang"));
                    khachHang.setSoDienThoai(object.getString("sdt"));
                    khachHang.setDiaChi(object.getString("diaChi"));
                    khachHang.setTenDangNhap(object.getString("tenDangNhap"));
                    khachHang.setMatKhau(object.getString("matKhau"));
                    khachHang.setMaToken(object.getInt("maToken"));

                    khachHangList.add(khachHang);
                }
                return true;

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return false;
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
                            hoaDon.setDatBan(getDatBanChuaTinhTienByMaDatBan(object.getInt("maDatBan")));

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
                    datBanChuaTinhTienList = new ArrayList<>();
                    datBanChuaSetBanList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = (JSONObject) jsonArray.get(i);
                        DatBan datBan = new DatBan();

                        datBan.setMaDatBan(object.getInt("maDatBan"));
                        datBan.setGioDen(object.getString("gioDen"));
                        datBan.setYeuCau(object.getString("yeuCau"));
                        datBan.setTrangThai(object.getInt("trangThai"));

                        if (!object.isNull("maKhachHang")) {
                            datBan.setKhachHang(getKhachHangByMa(object.getInt("maKhachHang")));
                        } else {
                            datBan.setTenKhachHang(object.getString("tenKhachHang"));
                            datBan.setSoDienThoai(object.getString("soDienThoai"));
                        }

                        if (!object.isNull("maBan")) {
                            datBan.setBan(getBanByMaBan(object.getInt("maBan")));
                            datBanChuaTinhTienList.add(datBan);
                        } else {

                            datBanChuaSetBanList.add(datBan);
                        }

                    }
                    return true;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Nullable
    public KhachHang getKhachHangByMa(int maKhachHang) {
        for (KhachHang khachHang : khachHangList) {
            if (khachHang.getMaKhachHang() == maKhachHang) {
                return khachHang;
            }
        }
        return null;
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

    public ArrayList<ThucDon> getListThucDonByTenMon(String newText) {
        ArrayList<ThucDon> thucDonTimKiem = new ArrayList<>();
        for (ThucDon thucDon : thucDonList) {
            if (Utils.removeAccent(thucDon.getTenMon().trim().toLowerCase()).contains(Utils.removeAccent(newText.trim().toLowerCase()))) {
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

    public HoaDon getHoaDonChuaTinhTienByMaBan(int maBan) {
        for (HoaDon hoaDon : hoaDonChuaTinhTienList) {
            if (hoaDon.getBan().getMaBan() == maBan) {
                return hoaDon;
            }
        }
        return null;
    }

    public DatBan getDatBanChuaTinhTienByMaBan(int maBan) {
        for (DatBan datBan : datBanChuaTinhTienList) {
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

    public DatBan getDatBanChuaTinhTienByMaDatBan(int maDatBan) {
        for (DatBan datBan : datBanChuaTinhTienList) {
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

    public ArrayList<DatBan> getDatBanChuaTinhTienList() {
        return datBanChuaTinhTienList;
    }

    public void setDatBanChuaTinhTienList(ArrayList<DatBan> datBanChuaTinhTienList) {
        this.datBanChuaTinhTienList = datBanChuaTinhTienList;
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
        hoaDonTinhTienList.add(0, hoaDon);
    }

    public void addDatBanChuaTinhTien(DatBan datBan) {
        datBanChuaTinhTienList.add(0, datBan);
    }

    public void addhoaDonChuaTinhTien(HoaDon hoaDon) {
        hoaDonChuaTinhTienList.add(0, hoaDon);
    }

    public void removeHoaDonChuaTinhTien(HoaDon hoaDon) {
        hoaDonChuaTinhTienList.remove(hoaDon);
    }

    public void removeDatBanChuaTinhTien(DatBan datBan) {
        datBanChuaTinhTienList.remove(datBan);
    }

    public ArrayList<DatBan> getDatBanTinhTienList() {
        return datBanTinhTienList;
    }

    public void setDatBanTinhTienList(ArrayList<DatBan> datBanTinhTienList) {
        this.datBanTinhTienList = datBanTinhTienList;
    }

    public void onTinhTienHoaDon(HoaDon currentHoaDon) {
        if (currentHoaDon.getDatBan() != null) {
            addDatBanTinhTien(currentHoaDon.getDatBan());
            removeDatBanChuaTinhTien(currentHoaDon.getDatBan());
        }
        addHoaDonTinhTien(currentHoaDon);
        removeHoaDonChuaTinhTien(currentHoaDon);
    }

    public void addDatBanTinhTien(DatBan datBan) {
        datBanTinhTienList.add(0, datBan);
    }

    public void onHuyBan(HoaDon currentHoaDon) {
        if (currentHoaDon.getDatBan() != null) {

            removeDatBanChuaTinhTien(currentHoaDon.getDatBan());
        }
        removeHoaDonChuaTinhTien(currentHoaDon);
    }

    public ArrayList<DatBan> getDatBanChuaSetBanList() {
        return this.datBanChuaSetBanList;
    }

    public ArrayList<DatBan> getListDatBanChuaSetBanByTenKhachHang(String keyWord) {
        ArrayList<DatBan> datBans = new ArrayList<>();
        for (DatBan datBan : datBanChuaSetBanList) {
            if (Utils.removeAccent(datBan.getTenKhachHang().trim().toLowerCase())
                    .contains(Utils.removeAccent(keyWord.trim().toLowerCase()))) {
                datBans.add(datBan);
            }
        }
        return datBans;
    }

    public void addDatBanChuaSetBan(DatBan datBan) {
        datBanChuaSetBanList.add(0, datBan);
    }

    public void removeDatBanChuaSetBan(DatBan datBan) {
        datBanChuaSetBanList.remove(datBan);
    }

    public void onKhachDatBanVaoBan(DatBan datBan) {
        addDatBanChuaTinhTien(datBan);
        removeDatBanChuaSetBan(datBan);
    }

    public DatBan getDatBanChuaSetBanByMa(int maDatBan) {
        for (DatBan datBan : datBanChuaSetBanList) {
            if (datBan.getMaDatBan() == maDatBan) {
                return datBan;
            }
        }
        return null;
    }
}
