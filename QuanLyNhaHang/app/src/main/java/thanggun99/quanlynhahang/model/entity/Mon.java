package thanggun99.quanlynhahang.model.entity;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import thanggun99.quanlynhahang.util.API;

/**
 * Created by Thanggun99 on 20/11/2016.
 */

public class Mon {
    private int maMon, hienThi;
    private String tenMon;
    private int maLoai;
    private int donGia;
    private String donViTinh;
    private byte[] hinhAnh;
    private float rating;
    private int personRating;
    private String hinhAnhString;

    public Mon(int maMon, String tenMon, int maLoai, int donGia, String donViTinh, byte[] hinhAnh, float rating, int personRating) {
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.maLoai = maLoai;
        this.donGia = donGia;
        this.donViTinh = donViTinh;
        this.hinhAnh = hinhAnh;
        this.rating = rating;
        this.personRating = personRating;
    }

    public Mon(int maMon, String tenMon, int maLoai, int donGia, String donViTinh, int hienThi, float rating, int personRating) {
        this.maMon = maMon;
        this.hienThi = hienThi;
        this.tenMon = tenMon;
        this.maLoai = maLoai;
        this.donGia = donGia;
        this.donViTinh = donViTinh;
        this.rating = rating;
        this.personRating = personRating;
    }

    public Mon() {
    }

    public int getHienThi() {
        return hienThi;
    }

    public void setHienThi(int hienThi) {
        this.hienThi = hienThi;
    }

    public int getMaMon() {
        return maMon;
    }

    public void setMaMon(int maMon) {
        this.maMon = maMon;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof Mon)) return false;

        return tenMon.equals(((Mon) obj).getTenMon());

    }

    @Override
    public int hashCode() {
        return tenMon.hashCode();
    }

    public byte[] getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getPersonRating() {
        return personRating;
    }

    public void setPersonRating(int personRating) {
        this.personRating = personRating;
    }

    public String getRatingPoint() {
        return "(" + rating + "/" + personRating + ")";
    }

    public Boolean updateMon(Mon mon) {
        Map<String, String> postParams = new HashMap<>();

        postParams.put("maMon", String.valueOf(maMon));
        postParams.put("tenMon", mon.getTenMon());
        postParams.put("donViTinh", mon.getDonViTinh());
        postParams.put("donGia", String.valueOf(mon.getDonGia()));
        postParams.put("maLoai", String.valueOf(mon.getMaLoai()));
        postParams.put("hienThi", String.valueOf(mon.getHienThi()));
        postParams.put("hinhAnh", mon.getHinhAnhString());

        String s = API.callService(API.UPDATE_MON_URL, null, postParams);

        if (!TextUtils.isEmpty(s) && s.trim().contains("success")) {

            setDonViTinh(mon.getDonViTinh());
            setTenMon(mon.getTenMon());
            setDonGia(mon.getDonGia());
            setMaLoai(mon.getMaLoai());
            setHienThi(mon.getHienThi());
            setHinhAnh(mon.getHinhAnh());
            return true;
        }
        return false;
    }

    public Boolean delete() {
        Map<String, String> getParams = new HashMap<>();
        getParams.put("maMon", String.valueOf(maMon));

        String s = API.callService(API.DELETE_MON_URL, getParams);

        return !TextUtils.isEmpty(s) && s.trim().contains("success");
    }

    public Boolean addNew() {
        Map<String, String> postParams = new HashMap<>();
        postParams.put("tenMon", tenMon);
        postParams.put("donViTinh", donViTinh);
        postParams.put("donGia", String.valueOf(donGia));
        postParams.put("maLoai", String.valueOf(maLoai));
        postParams.put("hienThi", String.valueOf(hienThi));
        postParams.put("hinhAnh", hinhAnhString);

        String s = API.callService(API.THEM_MON_URL, null, postParams);

        if (!TextUtils.isEmpty(s)) {
            try {

                setMaMon(Integer.parseInt(s.trim()));
                return true;


            } catch (NumberFormatException e) {
                e.printStackTrace();
                return false;
            }

        } else {
            return false;
        }
    }

    public void setHinhAnhString(String hinhAnhString) {
        this.hinhAnhString = hinhAnhString;
    }

    public String getHinhAnhString() {
        return hinhAnhString;
    }
}
