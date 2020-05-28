package com.song.deviceinfo.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by chensongsong on 2020/5/28.
 */
public class DensityUtils {

    /**
     * 获取 dpi
     *
     * @param context
     */
    public static int getDensityDpi(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.densityDpi;
    }

    /**
     * 获取屏幕标准密度倍数
     *
     * @param context
     * @return
     */
    public static float getDensity(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.density;
    }

    /**
     * 获取像素密度等级
     *
     * @param context
     * @return
     */
    public static String getDensityId(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float density = displayMetrics.density;
        if (density < 1.0) {
            return "ldpi";
        } else if (density <= 1.0) {
            return "mdpi";
        } else if (density <= 1.5) {
            return "hdpi";
        } else if (density <= 2.0) {
            return "xhdpi";
        } else if (density <= 3.0) {
            return "xxhdpi";
        } else {
            return "xxxhdpi";
        }
    }

    /**
     * 获取屏幕宽度，单位 dp
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics display = context.getResources().getDisplayMetrics();
        return display.widthPixels;
    }

    /**
     * 获取屏幕高度，单位 dp
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics display = context.getResources().getDisplayMetrics();
        return display.heightPixels;
    }

    /**
     * 获取屏幕宽度，单位 px
     *
     * @param context
     * @return
     */
    public static int getScreenWidthWithPx(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float density = displayMetrics.density;
        return (int) (displayMetrics.widthPixels * density + 0.5f);
    }

    /**
     * 获取屏幕高度，单位 px
     *
     * @param context
     * @return
     */
    public static int getScreenHeightWithPx(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float density = displayMetrics.density;
        return (int) (displayMetrics.heightPixels * density + 0.5f);
    }

    /**
     * 获取屏幕刷新率
     *
     * @param activity
     * @return
     */
    public static int getRefreshRate(Activity activity) {
        return (int) activity.getWindowManager().getDefaultDisplay().getRefreshRate();
    }

    /**
     * dp转px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


}
