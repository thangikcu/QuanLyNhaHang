package thanggun99.quanlynhahang.service;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import thanggun99.quanlynhahang.model.entity.DatBan;
import thanggun99.quanlynhahang.model.entity.KhachHang;
import thanggun99.quanlynhahang.util.Utils;

/**
 * Created by Thanggun99 on 14/02/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static final String DAT_BAN_CHUA_SET_BAN_ACTION = "DAT_BAN_CHUA_SET_BAN_ACTION";
    public static final String HUY_DAT_BAN_CHUA_SET_BAN_ACTION = "HUY_DAT_BAN_CHUA_SET_BAN_ACTION";
    public static final String UPDATE_DAT_BAN_ACTION = "UPDATE_DAT_BAN_ACTION";
    public static final String LOGOUT_ACTION = "LOGOUT_ACTION";

    public static final String DAT_BAN = "DAT_BAN";
    public static final String KHACH_HANG = "KHACH_HANG";
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
                    case DAT_BAN_CHUA_SET_BAN_ACTION:
                        Intent datBanIntent = new Intent(DAT_BAN_CHUA_SET_BAN_ACTION);

                        KhachHang khachHang = new KhachHang();
                        khachHang.setMaKhachHang(object.getInt("maKhachHang"));

                        DatBan datBan = new DatBan();
                        datBan.setKhachHang(khachHang);
                        datBan.setMaDatBan(object.getInt("maDatBan"));
                        datBan.setGioDen(object.getString("gioDen"));
                        datBan.setTrangThai(0);
                        if (!object.isNull("yeuCau")) {

                            datBan.setYeuCau(object.getString("yeuCau"));
                        }

                        datBanIntent.putExtra(DAT_BAN, datBan);
                        datBanIntent.putExtra(TEN_KHACH_HANG, object.getString("tenKhachHang"));

                        LocalBroadcastManager.getInstance(this).sendBroadcast(datBanIntent);

                        break;
                    case HUY_DAT_BAN_CHUA_SET_BAN_ACTION:
                        Intent maDatBanIntent = new Intent(HUY_DAT_BAN_CHUA_SET_BAN_ACTION);
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
                    case LOGOUT_ACTION:
                        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(LOGOUT_ACTION));
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

