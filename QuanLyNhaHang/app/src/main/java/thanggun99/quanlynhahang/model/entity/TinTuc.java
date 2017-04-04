package thanggun99.quanlynhahang.model.entity;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import thanggun99.quanlynhahang.util.API;

/**
 * Created by Thanggun99 on 23/03/2017.
 */

public class TinTuc {
    private int maTinTuc;
    private String tieuDe;
    private String noiDung;
    private String ngayDang;
    private byte[] hinhAnh;
    private int hienThi;
    private String hinhAnhString;

    public TinTuc(int maTinTuc, String tieuDe, String noiDung, String ngayDang, byte[] hinhAnh, int hienThi) {
        this.maTinTuc = maTinTuc;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.ngayDang = ngayDang;
        this.hinhAnh = hinhAnh;
        this.hienThi = hienThi;
    }

    public TinTuc() {
    }

    public int getMaTinTuc() {
        return maTinTuc;
    }

    public void setMaTinTuc(int maTinTuc) {
        this.maTinTuc = maTinTuc;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getNgayDang() {
        return ngayDang;
    }

    public void setNgayDang(String ngayDang) {
        this.ngayDang = ngayDang;
    }

    public byte[] getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public int getHienThi() {
        return hienThi;
    }

    public void setHienThi(int hienThi) {
        this.hienThi = hienThi;
    }

    public void setHinhAnhString(String hinhAnhString) {
        this.hinhAnhString = hinhAnhString;
    }

    public String getHinhAnhString() {
        return hinhAnhString;
    }

    public Boolean addNew() {
        Map<String, String> postParams = new HashMap<>();
        postParams.put("tieuDe", tieuDe);
        postParams.put("noiDung", noiDung);
        postParams.put("ngayDang", ngayDang);
        postParams.put("hienThi", String.valueOf(hienThi));
        postParams.put("hinhAnh", hinhAnhString);

        String s = API.callService(API.THEM_TIN_TUC_URL, null, postParams);

        if (!TextUtils.isEmpty(s)) {
            try {

                setMaTinTuc(Integer.parseInt(s.trim()));
                return true;


            } catch (NumberFormatException e) {
                e.printStackTrace();
                return false;
            }

        } else {
            return false;
        }

    }

    public Boolean delete() {
        Map<String, String> getParams = new HashMap<>();
        getParams.put("maTinTuc", String.valueOf(getMaTinTuc()));

        String s = API.callService(API.DELETE_TIN_TUC_URL, getParams);

        return !TextUtils.isEmpty(s) && s.trim().contains("success");
    }

    public Boolean updateTinTuc(TinTuc tinTucUpdate) {

        Map<String, String> postParams;
        postParams = new HashMap<>();

        postParams.put("maTinTuc", String.valueOf(getMaTinTuc()));
        postParams.put("tieuDe", tinTucUpdate.getTieuDe());
        postParams.put("noiDung", tinTucUpdate.getNoiDung());
        postParams.put("ngayDang", tinTucUpdate.getNgayDang());
        postParams.put("hienThi", String.valueOf(tinTucUpdate.getHienThi()));
        postParams.put("hinhAnh", tinTucUpdate.getHinhAnhString());

        String s = API.callService(API.UPDATE_TIN_TUC_URL, null, postParams);

        if (!TextUtils.isEmpty(s) && s.trim().contains("success")) {

            setTieuDe(tinTucUpdate.getTieuDe());
            setNoiDung(tinTucUpdate.getNoiDung());
            setNgayDang(tinTucUpdate.getNgayDang());
            setHienThi(tinTucUpdate.getHienThi());
            setHinhAnh(tinTucUpdate.getHinhAnh());
            return true;
        }
        return false;
    }
}
