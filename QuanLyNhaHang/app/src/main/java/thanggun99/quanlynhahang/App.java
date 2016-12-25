package thanggun99.quanlynhahang;

import android.app.Application;
import android.content.Context;

/**
 * Created by Thanggun99 on 19/11/2016.
 */

public class App extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }

}
