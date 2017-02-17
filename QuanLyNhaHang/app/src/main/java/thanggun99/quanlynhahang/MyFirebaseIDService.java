package thanggun99.quanlynhahang;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Thanggun99 on 14/02/2017.
 */

public class MyFirebaseIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {

        String refreshToken = FirebaseInstanceId.getInstance().getToken();
    }
}
