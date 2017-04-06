package thanggun99.quanlynhahang.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import thanggun99.quanlynhahang.util.Utils;

/**
 * Created by Thanggun99 on 06/04/2017.
 */

public class ConnectChangeBroadcastReceiver extends BroadcastReceiver {
    public static final String CONNECT_FAIL = "CONNECT_FAIL";
    public static final String CONNECT_AVAILABLE = "CONNECT_AVAILABLE";

    @Override
    public void onReceive(final Context context, Intent intent) {

        if (Utils.isConnectingToInternet()) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (Utils.isConnectAvalilabe()) {
                        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(CONNECT_AVAILABLE));
                    } else {
                        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(CONNECT_FAIL));
                    }
                }
            }).start();

        } else {
            LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(CONNECT_FAIL));
        }
    }
}
