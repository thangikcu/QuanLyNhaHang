package thanggun99.quanlynhahang.service;

import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.HashMap;
import java.util.Map;

import thanggun99.quanlynhahang.util.API;
import thanggun99.quanlynhahang.util.Utils;

/**
 * Created by Thanggun99 on 14/02/2017.
 */

public class MyFirebaseIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {

        Map<String, String> map = new HashMap<>();
        map.put("type", "1");
        map.put("token", Utils.getToken());

        API.callService(API.REGISTER_TOKEN_URL, null, map);
    }
}
