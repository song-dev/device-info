package com.song.deviceinfo.utils;

import android.content.Context;
import android.content.pm.PackageInfo;

/**
 * Created by chensongsong on 2021/9/28.
 */
public class ApplicationUtils {

    public static boolean isPkgInstalled(Context context, String pkgName) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(pkgName, 0);
        } catch (Exception e) {
            packageInfo = null;
        }
        return packageInfo != null;
    }

}
