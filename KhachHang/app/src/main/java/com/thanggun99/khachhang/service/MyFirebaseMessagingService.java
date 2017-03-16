package com.thanggun99.khachhang.service;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.thanggun99.khachhang.R;
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

    public static final String KHACH_HANG = "KHACH_HANG";

    public static final String NOTIFI_ACTION = "NOTIFI_ACTION";
    public static final String LOGOUT_ACTION = "LOGOUT_ACTION";
    public static final String NOTIFI = "NOTIFI";
    public static int id = 0;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage == null) return;

        if (remoteMessage.getNotification() != null) {
            showNotify(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }
        if (remoteMessage.getData().size() > 0) {
            JSONObject object = new JSONObject(remoteMessage.getData());
            try {
                String action = object.getString("action");
                switch (action) {
                    case LOGOUT_ACTION:
                        sendBroadcast(new Intent(LOGOUT_ACTION));
                        break;
                    case UPDATE_DAT_BAN_ACTION:
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

                        sendBroadcast(datBanUpdateIntent);

                        showNotify(Utils.getStringByRes(R.string.thong_bao),
                                Utils.getStringByRes(R.string.thong_tin_cua_ban_da_duoc_cap_nhat));
                        break;
                    default:
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    private void showNotify(String title, String message) {
/*        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        .setContentIntent(PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT))*/

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).notify(id, builder.build());
        id++;
    }

}
