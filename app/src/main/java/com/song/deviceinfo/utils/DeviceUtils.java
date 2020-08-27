package com.song.deviceinfo.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

/**
 * Created by chensongsong on 2020/8/3.
 */
public class DeviceUtils {

    public static String getAndroidId(Context context) {
        try {
            return Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Constants.UNKNOWN;
    }

    @SuppressLint("MissingPermission")
    public static String getImei(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Constants.UNKNOWN;
    }

    @SuppressLint("MissingPermission")
    public static String getImsi(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getSubscriberId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Constants.UNKNOWN;
    }

    @SuppressLint("MissingPermission")
    public static String getSimCountryIso(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getSimCountryIso();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Constants.UNKNOWN;
    }

}
