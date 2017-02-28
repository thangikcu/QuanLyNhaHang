package com.thanggun99.khachhang;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Thanggun99 on 17/02/2017.
 */

public class App extends Application {
    private static Context context;
    private static SharedPreferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public static Context getContext() {
        return context;
    }

    public static SharedPreferences getPreferences() {
        return preferences;
    }
}
