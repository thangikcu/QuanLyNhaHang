package thanggun99.quanlynhahang.service;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import thanggun99.quanlynhahang.model.entity.Admin;
import thanggun99.quanlynhahang.model.entity.Ban;
import thanggun99.quanlynhahang.model.entity.DatBan;
import thanggun99.quanlynhahang.model.entity.KhachHang;
import thanggun99.quanlynhahang.model.entity.YeuCau;
import thanggun99.quanlynhahang.util.Utils;

/**
 * Created by Thanggun99 on 14/02/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static final String DAT_BAN_ACTION = "DAT_BAN_ACTION";
    public static final String HUY_DAT_BAN_ACTION = "HUY_DAT_BAN_ACTION";
    public static final String UPDATE_DAT_BAN_ACTION = "UPDATE_DAT_BAN_ACTION";
    public static final String KHACH_VAO_BAN_ACTION = "KHACH_VAO_BAN_ACTION";
    public static final String KHACH_HANG_REGISTER_ACTION = "KHACH_HANG_REGISTER_ACTION";
    public static final String KHACH_HANG_YEU_CAU_ACTION = "KHACH_HANG_YEU_CAU_ACTION";
    public static final String LOGOUT_ACTION = "LOGOUT_ACTION";

    public static final String DAT_BAN = "DAT_BAN";
    public static final String KHACH_HANG = "KHACH_HANG";
    public static final String YEU_CAU = "YEU_CAU";
    public static final String MA_DAT_BAN = "MA_DAT_BAN";
    public static final String TEN_KHACH_HANG = "TEN_KHACH_HANG";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage == null) return;

        if (remoteMessage.getNotification() != null) {
            Utils.showNotify(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());

        }
        if (remoteMessage.getData().size() > 0) {
            JSONObject object = new JSONObject(remoteMessage.getData());
            Utils.showLog(object.toString());
            try {
                String action = object.getString("action");

                switch (action) {
                    case DAT_BAN_ACTION:
                        Intent datBanIntent = new Intent(DAT_BAN_ACTION);
                        DatBan datBan = new DatBan();

                        if (!object.isNull("maKhachHang")) {

                            datBan.setKhachHang(new KhachHang(object.getInt("maKhachHang")));

                        } else {

                            if (!object.isNull("maBan")) {

                                datBan.setBan(new Ban(object.getInt("maBan")));
                            }
                            datBan.setSoDienThoai(object.getString("soDienThoai"));
                            datBan.setTenKhachHang(object.getString("tenKhachHang"));
                        }


                        datBan.setTrangThai(DatBan.CHUA_TINH_TIEN);
                        datBan.setMaDatBan(object.getInt("maDatBan"));
                        datBan.setGioDen(object.getString("gioDen"));
                        if (!object.isNull("yeuCau")) {

                            datBan.setYeuCau(object.getString("yeuCau"));
                        }

                        datBanIntent.putExtra(DAT_BAN, datBan);
                        datBanIntent.putExtra(TEN_KHACH_HANG, object.getString("tenKhachHang"));

                        LocalBroadcastManager.getInstance(this).sendBroadcast(datBanIntent);

                        break;
                    case HUY_DAT_BAN_ACTION:
                        Intent maDatBanIntent = new Intent(HUY_DAT_BAN_ACTION);
                        maDatBanIntent.putExtra(MA_DAT_BAN, object.getInt("maDatBan"));
                        maDatBanIntent.putExtra(TEN_KHACH_HANG, object.getString("tenKhachHang"));

                        LocalBroadcastManager.getInstance(this).sendBroadcast(maDatBanIntent);

                        break;
                    case UPDATE_DAT_BAN_ACTION:
                        Intent datBanUpdateIntent = new Intent(UPDATE_DAT_BAN_ACTION);

                        DatBan datBanUpdate = new DatBan();
                        datBanUpdate.setMaDatBan(object.getInt("maDatBan"));
                        datBanUpdate.setGioDen(object.getString("gioDen"));
                        if (!object.isNull("yeuCau")) {

                            datBanUpdate.setYeuCau(object.getString("yeuCau"));
                        }

                        if (!object.isNull("soDienThoai")) {
                            datBanUpdate.setTenKhachHang(object.getString("tenKhachHang"));
                            datBanUpdate.setSoDienThoai(object.getString("soDienThoai"));
                        }


                        datBanUpdateIntent.putExtra(DAT_BAN, datBanUpdate);
                        datBanUpdateIntent.putExtra(TEN_KHACH_HANG, object.getString("tenKhachHang"));

                        LocalBroadcastManager.getInstance(this).sendBroadcast(datBanUpdateIntent);

                        break;
                    case KHACH_HANG_REGISTER_ACTION:
                        Intent khachHangDangKyIntent = new Intent(KHACH_HANG_REGISTER_ACTION);

                        KhachHang khachHangNew = new KhachHang();
                        khachHangNew.setMaKhachHang(object.getInt("maKhachHang"));
                        khachHangNew.setHoTen(object.getString("tenKhachHang"));
                        khachHangNew.setSoDienThoai(object.getString("soDienThoai"));
                        khachHangNew.setDiaChi(object.getString("diaChi"));
                        khachHangNew.setTenDangNhap(object.getString("tenDangNhap"));
                        khachHangNew.setMatKhau(object.getString("matKhau"));
                        khachHangNew.setMaToken(object.getInt("maToken"));

                        khachHangDangKyIntent.putExtra(KHACH_HANG, khachHangNew);

                        LocalBroadcastManager.getInstance(this).sendBroadcast(khachHangDangKyIntent);
                        break;
                    case KHACH_VAO_BAN_ACTION:
                        Intent khachVaoBanIntent = new Intent(KHACH_VAO_BAN_ACTION);

                        DatBan datBanVaoBan = new DatBan();

                        Ban ban = new Ban();
                        ban.setMaBan(object.getInt("maBan"));
                        ban.setTenBan(object.getString("tenBan"));

                        datBanVaoBan.setBan(ban);
                        datBanVaoBan.setMaDatBan(object.getInt("maDatBan"));

                        khachVaoBanIntent.putExtra(DAT_BAN, datBanVaoBan);

                        LocalBroadcastManager.getInstance(this).sendBroadcast(khachVaoBanIntent);
                        break;
                    case KHACH_HANG_YEU_CAU_ACTION:
                        Intent khachHangYeuCauIntent = new Intent(KHACH_HANG_YEU_CAU_ACTION);
                        YeuCau yeuCau = new YeuCau();
                        yeuCau.setThoiGian(object.getString("thoiGian"));
                        yeuCau.setKhachHang(new KhachHang(object.getInt("maKhachHang")));
                        yeuCau.setYeuCauJson(object.getString("yeuCau"));

                        khachHangYeuCauIntent.putExtra(TEN_KHACH_HANG, object.getString("tenKhachHang"));
                        khachHangYeuCauIntent.putExtra(YEU_CAU, yeuCau);

                        LocalBroadcastManager.getInstance(this).sendBroadcast(khachHangYeuCauIntent);
                        break;
                    case LOGOUT_ACTION:
                        if (Admin.getMaAdmin() == object.getInt("maAdmin")) {

                            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(LOGOUT_ACTION));
                        }
                        break;
                    default:
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }


}

