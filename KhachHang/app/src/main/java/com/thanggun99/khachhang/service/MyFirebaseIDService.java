package com.thanggun99.khachhang.service;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.thanggun99.khachhang.util.API;
import com.thanggun99.khachhang.util.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Thanggun99 on 16/02/2017.
 */

public class MyFirebaseIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        Map<String, String> map = new HashMap<>();
        map.put("token", Utils.getToken());

        API.callService(API.REGISTER_TOKEN_URL, null, map);
    }
}
