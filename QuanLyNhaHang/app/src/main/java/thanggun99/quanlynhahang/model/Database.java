package thanggun99.quanlynhahang.model;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import thanggun99.quanlynhahang.model.entity.Admin;
import thanggun99.quanlynhahang.model.entity.Ban;
import thanggun99.quanlynhahang.model.entity.DatBan;
import thanggun99.quanlynhahang.model.entity.HoaDon;
import thanggun99.quanlynhahang.model.entity.KhachHang;
import thanggun99.quanlynhahang.model.entity.Mon;
import thanggun99.quanlynhahang.model.entity.MonOrder;
import thanggun99.quanlynhahang.model.entity.NhomMon;
import thanggun99.quanlynhahang.model.entity.TinTuc;
import thanggun99.quanlynhahang.model.entity.YeuCau;
import thanggun99.quanlynhahang.util.API;
import thanggun99.quanlynhahang.util.Utils;

/**
 * Created by Thanggun99 on 07/03/2017.
 */

public class Database {
    private ArrayList<Ban> banList;
    private ArrayList<HoaDon> hoaDonChuaTinhTienList;
    private ArrayList<HoaDon> hoaDonTinhTienList;
    private ArrayList<Mon> monList;
    private ArrayList<NhomMon> nhomMonList;
    private ArrayList<DatBan> datBanChuaTinhTienList;
    private ArrayList<DatBan> datBanTinhTienList;
    private ArrayList<DatBan> datBanChuaSetBanList;
    private ArrayList<KhachHang> khachHangList;
    private ArrayList<YeuCau> yeuCauList;
    private Admin admin;
    private ArrayList<TinTuc> tinTucList;

    public Database() {
        datBanTinhTienList = new ArrayList<>();
    }

    public boolean getDatas() {
        boolean taskOk;
        taskOk = loadListNhomMon();
        if (taskOk) taskOk = loadListMon();
        if (taskOk) taskOk = loadListBan();
        if (taskOk) taskOk = loadListKhachHang();
        if (taskOk) taskOk = loadListYeuCau();
        if (taskOk) taskOk = loadListDatBan();
        if (taskOk) taskOk = loadListHoaDon();
        return taskOk;
    }

    private boolean loadListYeuCau() {
        String s = API.callService(API.GET_YEU_CAU_URL, null);

        if (!TextUtils.isEmpty(s)) {
            try {
                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject != null) {
                    JSONArray jsonArray = jsonObject.getJSONArray("yeuCau");

                    yeuCauList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = (JSONObject) jsonArray.get(i);

                        YeuCau yeuCau = new YeuCau();
                        yeuCau.setMaYeuCau(object.getInt("maYeuCau"));
                        yeuCau.setKhachHang(getKhachHangByMa(object.getInt("maKhachHang")));
                        yeuCau.setThoiGian(object.getString("thoiGian"));
                        yeuCau.setYeuCauJson(object.getString("yeuCau"));

                        Utils.showLog("hoho" + yeuCau.getYeuCauJson());
                        setDataForYeuCau(yeuCau);

                        yeuCauList.add(yeuCau);
                    }
                    return true;
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return false;
    }


    public boolean loadListTinTuc() {
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
                    tinTuc.setHienThi(object.getInt("hienThi"));

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


    private boolean loadListKhachHang() {
        String s = API.callService(API.GET_KHACH_HANG_URL, null);

        if (!TextUtils.isEmpty(s)) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("khachHang");

                khachHangList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = (JSONObject) jsonArray.get(i);

                    KhachHang khachHang = new KhachHang();
                    khachHang.setMaKhachHang(object.getInt("maKhachHang"));
                    khachHang.setHoTen(object.getString("tenKhachHang"));
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
                    hoaDonTinhTienList = new ArrayList<>();

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
                            hoaDon.setDatBan(getDatBanChuaTinhTienByMa(object.getInt("maDatBan")));

                        }
                        if (!object.isNull("maKhachHang")) {
                            hoaDon.setKhachHang(getKhachHangByMa(object.getInt("maKhachHang")));
                        }
                        hoaDon.setGioDen(object.getString("gioDen"));

                        JSONArray array = object.getJSONArray("thucDonOrder");
                        ArrayList<MonOrder> monOrders = new ArrayList<>();

                        for (int j = 0; j < array.length(); j++) {
                            JSONObject object1 = (JSONObject) array.get(j);

                            monOrders.add(new MonOrder(getMonByMaMon(object1.getInt("maMon")),
                                    object1.getInt("soLuong"), object1.getInt("maChiTietHD")));
                        }
                        hoaDon.setMonOrderList(monOrders);
                        if (hoaDon.getTrangThai() == HoaDon.DA_TINH_TIEN) {

                            hoaDonTinhTienList.add(hoaDon);
                        } else {

                            hoaDonChuaTinhTienList.add(hoaDon);
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

    private boolean loadListDatBan() {
        String s = API.callService(API.GET_DAT_BAN_URL, null);
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
                        if (!object.isNull("yeuCau")) {

                            datBan.setYeuCau(object.getString("yeuCau"));
                        }
                        datBan.setTrangThai(DatBan.CHUA_TINH_TIEN);

                        if (!object.isNull("maKhachHang")) {
                            KhachHang khachHang = getKhachHangByMa(object.getInt("maKhachHang"));

                            if (khachHang != null) {
                                datBan.setKhachHang(khachHang);
                            }

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
        String s = API.callService(API.GET_BAN_URL, null);
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
                                object.getString("donViTinh"), object.getInt("hienThi"), (float) object.getDouble("rating"), object.getInt("personRating"));

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

    public ArrayList<Mon> getListMonByTenMon(String newText) {
        ArrayList<Mon> monTimKiem = new ArrayList<>();
        for (Mon mon : monList) {
            if (Utils.removeAccent(mon.getTenMon().trim().toLowerCase())
                    .contains(Utils.removeAccent(newText.trim().toLowerCase()))) {
                monTimKiem.add(mon);
            }
        }
        return monTimKiem;
    }

    public ArrayList<YeuCau> getYeuCauList() {
        return yeuCauList;
    }

    public void setYeuCauList(ArrayList<YeuCau> yeuCauList) {
        this.yeuCauList = yeuCauList;
    }

    public ArrayList<TinTuc> getTinTucList() {
        return tinTucList;
    }

    public void setTinTucList(ArrayList<TinTuc> tinTucList) {
        this.tinTucList = tinTucList;
    }

    public ArrayList<Mon> getListMonByMaLoai(int maLoai) {
        ArrayList<Mon> monTheoLoai = new ArrayList<>();
        for (Mon mon : monList) {
            if (mon.getMaLoai() == maLoai) {
                monTheoLoai.add(mon);
            }
        }
        return monTheoLoai;
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
            if (datBan.getBan() != null && datBan.getBan().getMaBan() == maBan) {
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

    public DatBan getDatBanChuaTinhTienByMa(int maDatBan) {
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

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void addKhachHang(KhachHang khachHang) {
        khachHangList.add(khachHang);
    }

    public ArrayList<TinTuc> getListTinTucByTieuDe(String keyWord) {
        ArrayList<TinTuc> tinTucs = new ArrayList<>();
        for (TinTuc tinTuc : tinTucList) {
            if (Utils.removeAccent(tinTuc.getTieuDe().trim().toLowerCase())
                    .contains(Utils.removeAccent(keyWord.trim().toLowerCase()))) {
                tinTucs.add(tinTuc);
            }
        }
        return tinTucs;
    }

    public void addTinTuc(TinTuc tinTuc) {
        tinTucList.add(0, tinTuc);
    }

    public void deleteTinTuc(TinTuc tinTuc) {
        tinTucList.remove(tinTuc);
    }

    public void setDataForYeuCau(YeuCau yeuCau) {

        if (TextUtils.isEmpty(yeuCau.getYeuCauJson())) {
            return;
        }

        try {

            JSONObject yeuCauObject = new JSONObject(yeuCau.getYeuCauJson());

            yeuCau.setType(yeuCauObject.getInt("type"));
            yeuCau.setMaHoaDon(yeuCauObject.getInt("maHoaDon"));

            JSONArray monOderHuyJsonArray = yeuCauObject.getJSONArray("monYeuCauHuyList");
            ArrayList<MonOrder> monYeuCauHuyList = new ArrayList<>();

            for (int j = 0; j < monOderHuyJsonArray.length(); j++) {
                JSONObject monOrderObject = (JSONObject) monOderHuyJsonArray.get(j);

                MonOrder monOrder = new MonOrder();
                monOrder.setMon(getMonByMaMon(monOrderObject.getInt("maMon")));
                monOrder.setSoLuong(monOrderObject.getInt("soLuong"));

                monYeuCauHuyList.add(monOrder);
            }
            yeuCau.setMonYeuCauHuyList(monYeuCauHuyList);

            JSONArray monOderJsonArray = yeuCauObject.getJSONArray("monYeuCauList");
            ArrayList<MonOrder> monYeuCauList = new ArrayList<>();

            for (int j = 0; j < monOderJsonArray.length(); j++) {
                JSONObject monOrderObject = (JSONObject) monOderJsonArray.get(j);

                MonOrder monOrder = new MonOrder();
                monOrder.setMon(getMonByMaMon(monOrderObject.getInt("maMon")));
                monOrder.setSoLuong(monOrderObject.getInt("soLuong"));

                monYeuCauList.add(monOrder);
            }
            yeuCau.setMonYeuCauList(monYeuCauList);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public YeuCau getYeuCauByMaKhachHang(int maKhachHang) {
        for (YeuCau yeuCau : yeuCauList) {
            if (yeuCau.getKhachHang().getMaKhachHang() == maKhachHang) {
                return yeuCau;
            }
        }
        return null;
    }

    public void addYeuCau(YeuCau yeuCauNew) {
        yeuCauList.add(0, yeuCauNew);
    }

    public void onTaoMoiHoaDonKhachHang(YeuCau currentYeuCau) {
        DatBan datBan = currentYeuCau.getKhachHang().getDatBan();
        if (datBan != null) {
            if (datBan.getBan() == null) {

                onKhachDatBanVaoBan(datBan);
            }
        }

        yeuCauList.remove(currentYeuCau);

    }

    public void setDatBanChuaSetBanList(ArrayList<DatBan> datBanChuaSetBanList) {
        this.datBanChuaSetBanList = datBanChuaSetBanList;
    }

    public ArrayList<KhachHang> getKhachHangList() {
        return khachHangList;
    }

    public void setKhachHangList(ArrayList<KhachHang> khachHangList) {
        this.khachHangList = khachHangList;
    }

    public HoaDon getHoaDonChuaTinhTienByMa(int maHoaDon) {
        for (HoaDon hoaDon : hoaDonChuaTinhTienList) {
            if (hoaDon.getMaHoaDon() == maHoaDon) {
                return hoaDon;
            }
        }
        return null;
    }

    public void removeYeuCau(YeuCau yeuCau) {
        yeuCauList.remove(yeuCau);
    }

    public void deleteMon(Mon mon) {
        monList.remove(mon);
    }

    public void addMon(Mon mon) {
        monList.add(0, mon);
    }

    public void refresh() {
        tinTucList = null;
        banList = null;
        hoaDonChuaTinhTienList = null;
        hoaDonTinhTienList = null;
        monList = null;
        nhomMonList = null;
        datBanChuaTinhTienList = null;
        datBanChuaSetBanList = null;
        khachHangList = null;
        yeuCauList = null;

    }
}
