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
    public static final String SCHEME = "http";
    public static final String HOST = "192.168.56.1";
//    public static final String HOST = "thanggun99.000webhostapp.com";
    public static final String PATH = "WebService";
    public static final String PATH_BAN = "BanService/";
    public static final String PATH_MON = "MonService/";
    public static final String PATH_HOA_DON = "HoaDonService/";
    public static final String PATH_DAT_BAN = "DatBanService/";
    public static final String PATH_NHOM_MON = "NhomMonService/";
    public static final String PATH_TOKEN = "TokenService/";
    private static final String PATH_KHACH_HANG = "KhachHangService/";
    private static final String PATH_ADMIN = "AdminService/";
    private static final String PATH_TIN_TUC = "TinTucService/";
    private static final String PATH_YEU_CAU = "YeuCauService/";


    public static final String GET_BAN_URL = PATH_BAN + "GetBan.php";
    public static final String UPDATE_BAN_URL = "UpdateBan.php";

    public static final String GET_MON_URL = PATH_MON + "GetMon.php";

    public static final String GET_HOA_DON_URL = PATH_HOA_DON + "GetHoaDon.php";
    public static final String TAO_MOI_HOA_DON_URL = PATH_HOA_DON + "TaoMoiHoaDon.php";
    public static final String TINH_TIEN_HOA_DON_URL = PATH_HOA_DON + "TinhTienHoaDon.php";
    public static final String DELETE_HOA_DON_URL = PATH_HOA_DON + "DeleteHoaDon.php";

    public static final String GET_DAT_BAN_URL = PATH_DAT_BAN + "GetDatBan.php";
    public static final String DAT_BAN_URL = PATH_DAT_BAN + "DatBan.php";
    public static final String KHACH_VAO_BAN_URL = PATH_DAT_BAN + "KhachVaoBan.php";
    public static final String UPDATE_DAT_BAN_URL = PATH_DAT_BAN + "UpdateDatBan.php";
    public static final String DELETE_DAT_BAN_URL = PATH_DAT_BAN + "DeleteDatBan.php";

    public static final String GET_NHOM_MON_URL = PATH_NHOM_MON + "GetNhomMon.php";

    public static final String UPDATE_MON_ORDER_URL = PATH_HOA_DON + "UpdateMonOrder.php";
    public static final String UPDATE_GIAM_GIA_URL = PATH_HOA_DON + "UpdateGiamGia.php";
    public static final String THEM_MON_ORDER_URL = PATH_HOA_DON + "ThemMonOrder.php";
    public static final String DELETE_MON_ORDER_URL = PATH_HOA_DON + "DeleteMonOrder.php";
    public static final String REGISTER_TOKEN_URL = PATH_TOKEN + "RegisterToken.php";
    public static final String GET_KHACH_HANG_URL = PATH_KHACH_HANG + "GetKhachHang.php";
    public static final String LOGIN_URL = PATH_ADMIN + "Login.php";
    public static final String CHANGE_PASSWORD_URL = PATH_ADMIN + "ChangePassword.php";
    public static final String GET_TIN_TUC_URL = PATH_TIN_TUC + "GetTinTuc.php";
    public static final String THEM_TIN_TUC_URL = PATH_TIN_TUC + "ThemTinTuc.php";
    public static final String DELETE_TIN_TUC_URL = PATH_TIN_TUC + "DeleteTinTuc.php";
    public static final String UPDATE_TIN_TUC_URL = PATH_TIN_TUC + "UpdateTinTuc.php";
    public static final String GET_YEU_CAU_URL = PATH_YEU_CAU + "GetYeuCau.php";
    public static final String ORDER_MON_KHACH_HANG_YEU_CAU = PATH_HOA_DON + "OrderMonKhachHangYeuCau.php";
    public static final String THEM_MON_URL = PATH_MON + "ThemMon.php";
    public static final String UPDATE_MON_URL = PATH_MON + "UpdateMon.php";
    public static final String DELETE_MON_URL = PATH_MON + "DeleteMon.php";

    public static String callService(String path, Map<String, String> getParams) {
        return callService(path, getParams, null);
    }

    public static String callService(String path, Map<String, String> getParams, Map<String, String> postParams) {
        String response = null;

        HttpURLConnection connect;
        InputStream is;

        Uri.Builder builder = new Uri.Builder()
                .scheme(SCHEME)
                .authority(HOST)
                .appendPath(PATH)
                .appendEncodedPath(path);

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
            String line = null;
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
