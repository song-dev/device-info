package com.song.deviceinfo.utils;

import android.content.Context;
import android.os.SystemClock;

import java.util.Locale;
import java.util.TimeZone;

public class SystemConfigUtils {

    /**
     * 获取当前时区
     *
     * @return
     */
    public static String getCurrentTimeZone() {
        TimeZone tz = TimeZone.getDefault();
        return tz.getDisplayName(false, TimeZone.SHORT);
    }


    /**
     * 获取当前系统语言格式
     *
     * @param context
     * @return
     */
    public static String getCurrentLanguage(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        String country = locale.getCountry();
        String lc = language + "_" + country;
        return locale.getDisplayLanguage();
    }

    /**
     * 获取当前系统运行时间
     */
    public static String getSystemUpdate() {
        long nanoTime = SystemClock.elapsedRealtime();
        long DAY = 1000 * 60 * 60 * 24;
        long HOUR = 1000 * 60 * 60;
        long MINUTES = 1000 * 60;
        long day = nanoTime / DAY;
        if (day > 0) {
            return nanoTime / DAY + " days " + nanoTime % DAY / HOUR + ":" + nanoTime % DAY % HOUR / MINUTES;
        } else {
            return nanoTime % DAY / HOUR + ":" + nanoTime % DAY % HOUR / MINUTES;
        }
    }

}
