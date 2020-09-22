package com.song.deviceinfo.info;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;

import com.google.firebase.perf.metrics.AddTrace;
import com.song.deviceinfo.utils.HashUtils;
import com.song.deviceinfo.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.core.util.Pair;

/**
 * Created by chensongsong on 2020/9/22.
 */
public class AppInfo {

    @SuppressLint("PackageManagerGetSignatures")
    @AddTrace(name = "AppInfo.getAppInfo")
    public static List<Pair<String, String>> getAppInfo(Context context) {
        List<Pair<String, String>> list = new ArrayList<>();
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        String packageName = context.getPackageName();
        list.add(new Pair<>("AppName", applicationInfo.loadLabel(packageManager).toString()));
        list.add(new Pair<>("PackageName", packageName));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            list.add(new Pair<>("MinSdkVersion", applicationInfo.minSdkVersion + ""));
        }
        list.add(new Pair<>("TargetSdkVersion", applicationInfo.targetSdkVersion + ""));
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            list.add(new Pair<>("VersionName", packageInfo.versionName));
            list.add(new Pair<>("VersionCode", packageInfo.versionCode + ""));
            list.add(new Pair<>("FirstInstallTime", TimeUtils.formatDate(packageInfo.firstInstallTime)));
            list.add(new Pair<>("LastUpdateTime", TimeUtils.formatDate(packageInfo.lastUpdateTime)));
            Signature signature = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES).signatures[0];
            list.add(new Pair<>("Sign", HashUtils.md5Encode(signature.toByteArray())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


}
