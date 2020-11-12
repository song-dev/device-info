package com.song.deviceinfo.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.Objects;

/**
 * Created by chensongsong on 2020/5/28.
 */
public class DensityUtils {

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
     * 获取屏幕宽度，单位 px
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics display = context.getResources().getDisplayMetrics();
        return display.widthPixels;
    }

    /**
     * 获取屏幕高度，单位 px
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics display = context.getResources().getDisplayMetrics();
        return display.heightPixels;
    }

    /**
     * 获取屏幕宽度，单位 dp
     *
     * @param context
     * @return
     */
    public static int getScreenWidthWithDp(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float density = displayMetrics.density;
        return (int) (displayMetrics.widthPixels / density + 0.5f);
    }

    /**
     * 获取屏幕高度，单位 dp
     *
     * @param context
     * @return
     */
    public static int getScreenHeightWithDp(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float density = displayMetrics.density;
        return (int) (displayMetrics.heightPixels / density + 0.5f);
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

    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    private static int getStatusBarHeightWithReflect(Context context) {
        int statusBarHeight = -1;
        try {
            @SuppressLint("PrivateApi") Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(Objects.requireNonNull(clazz.getField("status_bar_height").get(object)).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    public static boolean hideStatusBar(Context context) {
        return checkFullScreenByTheme(context)
                || checkFullScreenByCode(context)
                || checkFullScreenByCode2(context);
    }

    private static boolean checkFullScreenByTheme(Context context) {
        Resources.Theme theme = context.getTheme();
        if (theme != null) {
            TypedValue typedValue = new TypedValue();
            boolean result = theme.resolveAttribute(android.R.attr.windowFullscreen, typedValue, false);
            if (result) {
                typedValue.coerceToString();
                if (typedValue.type == TypedValue.TYPE_INT_BOOLEAN) {
                    return typedValue.data != 0;
                }
            }
        }
        return false;
    }

    private static boolean checkFullScreenByCode(Context context) {
        if (context instanceof Activity) {
            Window window = ((Activity) context).getWindow();
            if (window != null) {
                View decorView = window.getDecorView();
                return (decorView.getSystemUiVisibility() & View.SYSTEM_UI_FLAG_FULLSCREEN) == View.SYSTEM_UI_FLAG_FULLSCREEN;
            }
        }
        return false;
    }

    private static boolean checkFullScreenByCode2(Context context) {
        if (context instanceof Activity) {
            return (((Activity) context).getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN)
                    == WindowManager.LayoutParams.FLAG_FULLSCREEN;
        }
        return false;
    }

    public static boolean hasNavigationBar(Context context) {
        if (context instanceof Activity) {
            WindowManager windowManager = ((Activity) context).getWindowManager();
            Display d = windowManager.getDefaultDisplay();
            DisplayMetrics realDisplayMetrics = new DisplayMetrics();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                d.getRealMetrics(realDisplayMetrics);
            }
            int realHeight = realDisplayMetrics.heightPixels;
            int realWidth = realDisplayMetrics.widthPixels;
            DisplayMetrics displayMetrics = new DisplayMetrics();
            d.getMetrics(displayMetrics);
            int displayHeight = displayMetrics.heightPixels;
            int displayWidth = displayMetrics.widthPixels;
            return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
        }
        return false;
    }

}
