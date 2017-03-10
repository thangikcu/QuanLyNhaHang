package thanggun99.quanlynhahang.util;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import thanggun99.quanlynhahang.App;
import thanggun99.quanlynhahang.view.dialog.NotifiDialog;

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

    public void showToast(String message) {
        Toast.makeText(App.getContext(), message, Toast.LENGTH_SHORT).show();
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

    public static String convertColorToHex(Drawable drawable) {

        return String.format("#%06x",  (((ColorDrawable)drawable).getColor() & 0x00FFFFFF));
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
