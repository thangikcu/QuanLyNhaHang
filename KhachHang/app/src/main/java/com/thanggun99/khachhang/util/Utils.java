package com.thanggun99.khachhang.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.thanggun99.khachhang.App;
import com.thanggun99.khachhang.dialog.NotifiDialog;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * Created by Thanggun99 on 05/12/2016.
 */

public class Utils {

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
        return new java.text.SimpleDateFormat("hh:mm a - dd/MM/yyyy").format(date);
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
        Log.d("Thanggggggggggggg", message);
    }
}
