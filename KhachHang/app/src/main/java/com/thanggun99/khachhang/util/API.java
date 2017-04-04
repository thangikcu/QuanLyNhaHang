package com.thanggun99.khachhang.util;

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
    public static final String SCHEME = "http";
    public static final String HOST = "192.168.56.1";
//    public static final String HOST = "thanggun99.000webhostapp.com";
    public static final String PATH = "WebService";

    public static final String PATH_KHACH_HANG = "KhachHangService/";
    public static final String PATH_TEST = "TestService/";
    public static final String PATH_TOKEN = "TokenService/";

    public static final String LOGIN_URL = PATH_KHACH_HANG + "Login.php";
    public static final String REGISTER_URL = PATH_KHACH_HANG + "Register.php";
    public static final String FEEDBACK_URL = PATH_KHACH_HANG + "Feedback.php";
    public static final String REGISTER_TOKEN_URL = PATH_TOKEN + "RegisterToken.php";
    public static final String TEST_URL = PATH_TEST + "Test.php";
    public static final String CHANGE_PASSWORD_URL = PATH_KHACH_HANG + "ChangePassword.php";
    public static final String DAT_BAN_URL = PATH_KHACH_HANG + "DatBan.php";
    public static final String GET_THONG_TIN_PHUC_VU_URL = PATH_KHACH_HANG + "GetThongTinPhucVu.php";
    public static final String HUY_DAT_BAN_URL = PATH_KHACH_HANG + "HuyDatBan.php";
    public static final String UPDATE_DAT_BAN_URL = PATH_KHACH_HANG + "UpdateDatBan.php";
    public static final String GET_TIN_TUC_URL = PATH_KHACH_HANG + "GetTinTuc.php";
    public static final String GET_NHOM_MON_URL = PATH_KHACH_HANG + "GetNhomMon.php";
    public static final String GET_MON_URL = PATH_KHACH_HANG + "GetMon.php";
    public static final String SEND_YEU_CAU_URL = PATH_KHACH_HANG + "SendYeuCau.php";
    public static final String GET_YEU_CAU_URL = PATH_KHACH_HANG + "GetYeuCau.php";

    public static String callService(String url, Map<String, String> getParams) {
        return callService(url, getParams, null);
    }

    public static String callService(String url, Map<String, String> getParams, Map<String, String> postParams) {
        String response = "";
        HttpURLConnection connect;
        InputStream is;

        Uri.Builder builder = new Uri.Builder()
                .scheme(SCHEME)
                .authority(HOST)
                .appendPath(PATH)
                .appendEncodedPath(url);

        if (getParams != null) {
            builder = Utils.builderParams(builder, getParams);
        }
        try {
            connect = (HttpURLConnection) new URL(builder.build().toString()).openConnection();
            connect.setRequestProperty("accept", "application/json");
            connect.setRequestProperty("Connection", "close");
            connect.setDoInput(true);
            connect.setConnectTimeout(5000);

            if (postParams != null) {
                Uri.Builder builderPostParams = new Uri.Builder();
                builderPostParams = Utils.builderParams(builderPostParams, postParams);

                connect.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded;charset=UTF-8");
                connect.setRequestMethod("POST");
                connect.setDoOutput(true);
                connect.setReadTimeout(5000);

                OutputStream outputStream = connect.getOutputStream();
                outputStream.write(builderPostParams.build().getEncodedQuery().getBytes());
                outputStream.close();
            } else connect.setRequestMethod("GET");

            is = connect.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            response = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Utils.showLog(response);
        return response;
    }
}
