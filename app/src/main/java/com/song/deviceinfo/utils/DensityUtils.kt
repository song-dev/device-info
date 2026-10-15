package com.song.deviceinfo.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import java.util.Objects

/**
 * Created by chensongsong on 2020/5/28.
 */
object DensityUtils {
    /**
     * dp转px
     */
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
    
    /**
     * px转dp
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }
    
    /**
     * 获取 dpi
     *
     * @param context
     */
    @JvmStatic
    fun getDensityDpi(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        return displayMetrics.densityDpi
    }
    
    /**
     * 获取屏幕标准密度倍数
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun getDensity(context: Context): Float {
        val displayMetrics = context.resources.displayMetrics
        return displayMetrics.density
    }
    
    /**
     * 获取像素密度等级
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun getDensityId(context: Context): String {
        val displayMetrics = context.resources.displayMetrics
        val density = displayMetrics.density
        return if (density < 1.0) {
            "ldpi"
        } else if (density <= 1.0) {
            "mdpi"
        } else if (density <= 1.5) {
            "hdpi"
        } else if (density <= 2.0) {
            "xhdpi"
        } else if (density <= 3.0) {
            "xxhdpi"
        } else {
            "xxxhdpi"
        }
    }
    
    /**
     * 获取屏幕宽度，单位 px
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun getScreenWidth(context: Context): Int {
        val display = context.resources.displayMetrics
        return display.widthPixels
    }
    
    /**
     * 获取屏幕高度，单位 px
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun getScreenHeight(context: Context): Int {
        val display = context.resources.displayMetrics
        return display.heightPixels
    }
    
    /**
     * 获取屏幕宽度，单位 dp
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun getScreenWidthWithDp(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        val density = displayMetrics.density
        return (displayMetrics.widthPixels / density + 0.5f).toInt()
    }
    
    /**
     * 获取屏幕高度，单位 dp
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun getScreenHeightWithDp(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        val density = displayMetrics.density
        return (displayMetrics.heightPixels / density + 0.5f).toInt()
    }
    
    /**
     * 获取屏幕刷新率
     *
     * @param activity
     * @return
     */
    @JvmStatic
    fun getRefreshRate(activity: Activity): Int {
        return activity.windowManager.defaultDisplay.refreshRate.toInt()
    }
    
    @JvmStatic
    fun getStatusBarHeight(context: Context): Int {
        val resources = context.resources
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }
    
    private fun getStatusBarHeightWithReflect(context: Context): Int {
        var statusBarHeight = -1
        try {
            @SuppressLint("PrivateApi") val clazz = Class.forName("com.android.internal.R\$dimen")
            val `object` = clazz.newInstance()
            val height = Objects.requireNonNull(clazz.getField("status_bar_height")[`object`]).toString().toInt()
            statusBarHeight = context.resources.getDimensionPixelSize(height)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return statusBarHeight
    }
    
    @JvmStatic
    fun getNavigationBarHeight(context: Context): Int {
        val resources = context.resources
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }
    
    @JvmStatic
    fun hideStatusBar(context: Context): Boolean {
        return checkFullScreenByTheme(context)
                || checkFullScreenByCode(context)
                || checkFullScreenByCode2(context)
    }
    
    private fun checkFullScreenByTheme(context: Context): Boolean {
        val theme = context.theme
        if (theme != null) {
            val typedValue = TypedValue()
            val result = theme.resolveAttribute(android.R.attr.windowFullscreen, typedValue, false)
            if (result) {
                typedValue.coerceToString()
                if (typedValue.type == TypedValue.TYPE_INT_BOOLEAN) {
                    return typedValue.data != 0
                }
            }
        }
        return false
    }
    
    private fun checkFullScreenByCode(context: Context): Boolean {
        if (context is Activity) {
            val window = context.window
            if (window != null) {
                val decorView = window.decorView
                return (decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_FULLSCREEN) == View.SYSTEM_UI_FLAG_FULLSCREEN
            }
        }
        return false
    }
    
    private fun checkFullScreenByCode2(context: Context): Boolean {
        if (context is Activity) {
            return ((context.window.attributes.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN)
                    == WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        return false
    }
    
    @JvmStatic
    fun hasNavigationBar(context: Context?): Boolean {
        if (context is Activity) {
            val windowManager = context.windowManager
            val d = windowManager.defaultDisplay
            val realDisplayMetrics = DisplayMetrics()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                d.getRealMetrics(realDisplayMetrics)
            }
            val realHeight = realDisplayMetrics.heightPixels
            val realWidth = realDisplayMetrics.widthPixels
            val displayMetrics = DisplayMetrics()
            d.getMetrics(displayMetrics)
            val displayHeight = displayMetrics.heightPixels
            val displayWidth = displayMetrics.widthPixels
            return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0
        }
        return false
    }
}
