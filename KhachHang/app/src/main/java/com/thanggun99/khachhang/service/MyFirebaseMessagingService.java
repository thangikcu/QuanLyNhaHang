package com.thanggun99.khachhang.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.thanggun99.khachhang.MainActivity;
import com.thanggun99.khachhang.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Thanggun99 on 16/02/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static String NOTIFI_ACTION = "NOTIFI_ACTION";
    public static String NOTIFI = "NOTIFI";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage == null) return;

        if (remoteMessage.getNotification() != null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setContentIntent(PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT))
                    .setAutoCancel(true)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).notify(0, builder.build());

            Intent intentNotifi = new Intent(NOTIFI_ACTION);
            intentNotifi.putExtra(NOTIFI, remoteMessage.getNotification().getBody().toString());
            LocalBroadcastManager.getInstance(this).sendBroadcast(intentNotifi);
        }
        if (remoteMessage.getData().size() > 0) {
            JSONObject jsonObject = new JSONObject(remoteMessage.getData());
            try {
                jsonObject.getString("thang");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

}
