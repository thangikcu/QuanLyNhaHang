package com.thanggun99.khachhang.service;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.thanggun99.khachhang.model.entity.DatBan;
import com.thanggun99.khachhang.model.entity.KhachHang;
import com.thanggun99.khachhang.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Thanggun99 on 16/02/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static final String UPDATE_DAT_BAN_ACTION = "UPDATE_DAT_BAN_ACTION";
    public static final String HUY_DAT_BAN_ACTION = "HUY_DAT_BAN_ACTION";

    public static final String KHACH_HANG = "KHACH_HANG";

    public static final String NOTIFI_ACTION = "NOTIFI_ACTION";
    public static final String LOGOUT_ACTION = "LOGOUT_ACTION";

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
                    case LOGOUT_ACTION:
                        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(LOGOUT_ACTION));
                        break;
                    case UPDATE_DAT_BAN_ACTION:
                        if (KhachHang.getMaKhachHang() == object.getInt("maKhachHang")) {

                            Utils.showLog("Update Khach hang");
                            Intent datBanUpdateIntent = new Intent(UPDATE_DAT_BAN_ACTION);

                            DatBan datBanUpdate = new DatBan();
                            datBanUpdate.setMaDatBan(object.getInt("maDatBan"));
                            datBanUpdate.setGioDen(object.getString("gioDen"));
                            if (!object.isNull("yeuCau")) {

                                datBanUpdate.setYeuCau(object.getString("yeuCau"));
                            }


                            KhachHang khachHangUpdate = new KhachHang();
                            khachHangUpdate.setTenKhachHang(object.getString("tenKhachHang"));
                            khachHangUpdate.setSoDienThoai(object.getString("soDienThoai"));
                            khachHangUpdate.setCurrentDatBan(datBanUpdate);


                            datBanUpdateIntent.putExtra(KHACH_HANG, khachHangUpdate);

                            LocalBroadcastManager.getInstance(this).sendBroadcast(datBanUpdateIntent);
                        }
                        break;
                    case HUY_DAT_BAN_ACTION:
                        if (KhachHang.getMaKhachHang() == object.getInt("maKhachHang")) {

                            Utils.showLog("delete dat ban");
                            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(HUY_DAT_BAN_ACTION));
                        }
                        break;
                    case NOTIFI_ACTION:
                        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(NOTIFI_ACTION));
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
