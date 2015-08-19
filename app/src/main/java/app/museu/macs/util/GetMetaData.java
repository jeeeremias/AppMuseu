package app.museu.macs.util;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by jeremias on 17/08/15.
 */
public class GetMetaData {
    public static String getGoogleKeyAPI() {
        String value = null;
        try {
            ApplicationInfo app = MyApplication.getAppContext().getPackageManager().getApplicationInfo(MyApplication.getAppContext().getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = app.metaData;
            value = bundle.getString("google.credencial.API_KEY");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return value;
    }
}
