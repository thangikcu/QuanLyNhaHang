package thanggun99.quanlynhahang.util;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by Thanggun99 on 17/11/2016.
 */

public class API {
    public final static int GET = 1;
    public final static int POST = 2;
    public static final String SCHEME = "http";
    public static final String HOST = "192.168.56.1";
    public static final String PATH = "WebService";
    public static final String BAN_URL = "GetBan.php";
    public static final String NHOM_MON_URL = "GetNhomMon.php";
    public static final String THUC_DON_URL = "GetThucDon.php";
    public static final String HOA_DON_URL = "GetHoaDon.php";
    public static final String TAO_MOI_HOA_DON_URL = "TaoMoiHoaDon.php";
    public static final String UPDATE_BAN_URL = "UpdateBan.php";
    public static final String UPDATE_THUC_DON_ORDER_URL = "UpdateThucDonOrder.php";
    public static final String UPDATE_GIAM_GIA_URL = "UpdateGiamGia.php";
    public static final String THEM_THUC_DON_ORDER_URL = "ThemThucDonOrder.php";
    public static final String DELETE_MON_ORDER_URL = "DeleteMonOrder.php";
    public static final String DELETE_HOA_DON_URL = "DeleteHoaDon.php";

    public static String callService(String path, int getMethod, Map<String, String> params) {
        return callService(path, getMethod, params, null);
    }

    public static String callService(String path, int method, Map<String, String> getParams, Map<String, String> postParams) {
        HttpURLConnection connect;
        InputStream is;
        String response = null;

        Uri.Builder builder = new Uri.Builder()
                .scheme(SCHEME)
                .authority(HOST)
                .appendPath(PATH)
                .appendPath(path);

        if (getParams != null) {
            builder = Utils.builderParams(builder, getParams);
        }
        try {
            connect = (HttpURLConnection) new URL(builder.build().toString()).openConnection();
            connect.setRequestProperty("accept", "application/json");
            connect.setRequestProperty("Connection", "close");
            connect.setDoInput(true);

            if (method == POST && postParams != null) {
                if (postParams != null) {
                    Uri.Builder builderPostParams = new Uri.Builder();
                    builderPostParams = Utils.builderParams(builderPostParams, postParams);

                    connect.setRequestProperty("Content-Type",
                            "application/x-www-form-urlencoded;charset=UTF-8");
                    connect.setRequestMethod("POST");
                    connect.setDoOutput(true);

                    OutputStream outputStream = connect.getOutputStream();
                    outputStream.write(builderPostParams.build().getEncodedQuery().getBytes());
                    outputStream.close();
                }
            } else connect.setRequestMethod("GET");

            is = connect.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            response = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

}
