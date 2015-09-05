package app.museu.macs.util;

import android.app.Application;
import android.content.Context;

/**
 * Created by jeremias on 17/08/15.
 */
public class MyApplication extends Application {
    private static Context context;

    public void onCreate(){
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}
