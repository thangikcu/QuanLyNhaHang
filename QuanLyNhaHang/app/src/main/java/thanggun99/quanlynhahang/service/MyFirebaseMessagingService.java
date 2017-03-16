package thanggun99.quanlynhahang.service;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import thanggun99.quanlynhahang.R;
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

    public static final String DAT_BAN = "DAT_BAN";
    public static final String MA_DAT_BAN = "MA_DAT_BAN";
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

                        sendBroadcast(datBanIntent);
                        showNotify(Utils.getStringByRes(R.string.khach_hang_dat_ban),
                                object.getString("tenKhachHang"));
                        break;
                    case HUY_DAT_BAN_CHUA_SET_BAN_ACTION:
                        Intent maDatBanIntent = new Intent(HUY_DAT_BAN_CHUA_SET_BAN_ACTION);
                        maDatBanIntent.putExtra(MA_DAT_BAN, object.getInt("maDatBan"));

                        sendBroadcast(maDatBanIntent);
                        showNotify(Utils.getStringByRes(R.string.khach_hang_huy_dat_ban),
                                object.getString("tenKhachHang"));
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
                            KhachHang khachHangUpdate = new KhachHang();

                            khachHangUpdate.setTenKhachHang(object.getString("tenKhachHang"));
                            khachHangUpdate.setSoDienThoai(object.getString("soDienThoai"));

                            datBanUpdate.setKhachHang(khachHangUpdate);
                        }


                        datBanUpdateIntent.putExtra(DAT_BAN, datBanUpdate);

                        sendBroadcast(datBanUpdateIntent);

                        showNotify(Utils.getStringByRes(R.string.update_dat_ban),
                                object.getString("tenKhachHang"));
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

