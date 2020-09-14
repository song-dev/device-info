package com.song.deviceinfo.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.text.TextUtils;

import java.util.List;

import androidx.core.util.Pair;

/**
 * Created by chensongsong on 2020/6/1.
 */
public class SocUtils {

    /**
     * 读取 SOC 型号信息
     *
     * @return
     */
    public static String getSocInfo() {
        String socStr = "";
        socStr = CommandUtils.execute("getprop ro.board.platform");
        if (TextUtils.isEmpty(socStr)) {
            socStr = CommandUtils.execute("getprop ro.hardware");
            if (TextUtils.isEmpty(socStr)) {
                socStr = CommandUtils.execute("getprop ro.boot.hardware");
            }
        }
        return socStr;
    }

    public static String getCoreInfo() {
        String core = FileUtils.readFile("/sys/devices/system/cpu/present");
        if (TextUtils.isEmpty(core)) {
            core = CommandUtils.execute("cat /sys/devices/system/cpu/present");
        }
        if (TextUtils.isEmpty(core)) {
            core = Constants.UNKNOWN;
        }
        return core;
    }

    public static void getGPUInfo(Context context, List<Pair<String, String>> list) {
        ActivityManager am = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        list.add(new Pair<>("GlEsVersion", info.getGlEsVersion()));
        list.add(new Pair<>("GlEsVersion", info.reqGlEsVersion + ""));
        list.add(new Pair<>("GlEsVersion", info.reqInputFeatures + ""));
        list.add(new Pair<>("GlEsVersion", info.reqKeyboardType + ""));
        list.add(new Pair<>("GlEsVersion", info.reqNavigation + ""));
        list.add(new Pair<>("GlEsVersion", info.reqTouchScreen + ""));
        list.add(new Pair<>("GlEsVersion", info.describeContents() + ""));
    }

    public static void getCpuInfo(){

    }

}
