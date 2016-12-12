package thanggun99.quanlynhahang.util;

import android.net.Uri;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import thanggun99.quanlynhahang.App;

/**
 * Created by Thanggun99 on 05/12/2016.
 */

public class Utils {

    public static String formatMoney(int s) {
        return (s / 1000) + "k";
    }

    public static String formatDate(String d) {
        Date date = null;
        try {
            date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm a").format(date);
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
}
