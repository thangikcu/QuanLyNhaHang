package com.thanggun99.khachhang.util;

import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.thanggun99.khachhang.App;
import com.thanggun99.khachhang.R;
import com.thanggun99.khachhang.view.dialog.NotifiDialog;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * Created by Thanggun99 on 05/12/2016.
 */

public class Utils {
    private static int id = 0;


    public static String formatMoney(int s) {
        return s / 1000 + "k";
    }

    public static String formatDate(String d) {
        Date date = null;
        try {
            date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new java.text.SimpleDateFormat("HH:mm a - dd/MM/yyyy").format(date);
    }

    public static String getStringByRes(int id) {
        return App.getContext().getResources().getString(id);
    }

    public static Uri.Builder builderParams(Uri.Builder builder, Map<String, String> params) {
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.appendQueryParameter(entry.getKey(), entry.getValue());
        }
        return builder;
    }



    public static void showNotify(String title, String message) {
/*        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        .setContentIntent(PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT))*/

        NotificationCompat.Builder builder = new NotificationCompat.Builder(App.getContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        ((NotificationManager) App.getContext().getSystemService(Context.NOTIFICATION_SERVICE)).notify(id, builder.build());
        id++;
    }

    public static void showToast(String message) {
        Toast.makeText(App.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public static String getToken() {
        return FirebaseInstanceId.getInstance().getToken();
    }

    public static boolean isConnectingToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) App.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            return true;
        }
        return false;
    }

    public static void notifi(String message) {
        new NotifiDialog(App.getContext()).notifi(message);
    }

    public static void showLog(String message) {
        if (!TextUtils.isEmpty(message)) {

            Log.d("Thanggggggggggggg", message);
        }
    }
}
