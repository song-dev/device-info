package com.song.deviceinfo.info

import android.content.Context
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import android.text.TextUtils
import android.webkit.WebSettings
import androidx.core.util.Pair
import com.google.firebase.perf.metrics.AddTrace
import com.song.deviceinfo.utils.Constants
import com.song.deviceinfo.utils.DensityUtils.getDensity
import com.song.deviceinfo.utils.DensityUtils.getDensityDpi
import com.song.deviceinfo.utils.DensityUtils.getNavigationBarHeight
import com.song.deviceinfo.utils.DensityUtils.getScreenHeight
import com.song.deviceinfo.utils.DensityUtils.getScreenHeightWithDp
import com.song.deviceinfo.utils.DensityUtils.getScreenWidth
import com.song.deviceinfo.utils.DensityUtils.getScreenWidthWithDp
import com.song.deviceinfo.utils.DensityUtils.getStatusBarHeight
import com.song.deviceinfo.utils.DensityUtils.hasNavigationBar
import com.song.deviceinfo.utils.DensityUtils.hideStatusBar
import com.song.deviceinfo.utils.FileUtils.readFile
import com.song.deviceinfo.utils.OaidUtils
import com.song.deviceinfo.utils.SdUtils.isMounted
import java.util.Locale

/**
 * Created by chensongsong on 2020/9/22.
 */
object OthersInfo {
    @AddTrace(name = "OthersInfo.getOthersInfo")
    fun getOthersInfo(context: Context): List<Pair<String, String>> {
        val list: MutableList<Pair<String, String>> = ArrayList()
        list.add(Pair("UA", getDefaultUserAgent(context)))
        list.add(Pair("BootId", readFile("/proc/sys/kernel/random/boot_id")))
        list.add(Pair("UUID", readFile("/proc/sys/kernel/random/uuid")))
        list.add(Pair("PoolSize", readFile("/proc/sys/kernel/random/poolsize")))
        list.add(Pair("Entropy Avail", readFile("/proc/sys/kernel/random/entropy_avail")))
        list.add(Pair("Read Threshold", readFile("/proc/sys/kernel/random/read_wakeup_threshold")))
        list.add(Pair("Write Threshold", readFile("/proc/sys/kernel/random/write_wakeup_threshold")))
        list.add(Pair("Secs", readFile("/proc/sys/kernel/random/urandom_min_reseed_secs")))
        OaidUtils().getOaidsInfo(context, list)
        list.add(Pair("Country", Locale.getDefault().language + "-" + Locale.getDefault().country))
        // 显示
        list.add(Pair("DPI", getDensityDpi(context).toString() + ""))
        list.add(Pair("Density", getDensity(context).toString() + ""))
        list.add(Pair("Width * Height", getScreenWidth(context).toString() + " X " + getScreenHeight(context)))
        list.add(Pair("WidthDp * HeightDp", getScreenWidthWithDp(context).toString() + " X " + getScreenHeightWithDp(context)))
        list.add(Pair("StatusBarHeight", getStatusBarHeight(context).toString() + ""))
        list.add(Pair("NavigationBarHeight", getNavigationBarHeight(context).toString() + ""))
        try {
            list.add(Pair("SCREEN BRIGHTNESS", Settings.System.getInt(context.contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS).toString() + ""))
            list.add(Pair("SCREEN BRIGHTNESS AUTO", (Settings.System.getInt(context.contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE) == 1).toString() + ""))
            list.add(Pair("SCREEN ORIENTATION AUTO", (Settings.System.getInt(context.contentResolver,
                    Settings.System.ACCELEROMETER_ROTATION) == 1).toString() + ""))
        } catch (e: SettingNotFoundException) {
            e.printStackTrace()
        }
        list.add(Pair("HideStatusBar", hideStatusBar(context).toString() + ""))
        list.add(Pair("HasNavigationBar", hasNavigationBar(context).toString() + ""))
        // 存储
        list.add(Pair("SDCardEnable", isMounted.toString() + ""))
        // 相机，网络
        return list
    }
    
    private fun getDefaultUserAgent(context: Context): String {
        var ua: String? = null
        try {
            ua = System.getProperty("http.agent")
            if (TextUtils.isEmpty(ua)) {
                val localMethod = WebSettings::class.java.getDeclaredMethod("getDefaultUserAgent", *arrayOf<Class<*>>(Context::class.java))
                ua = localMethod.invoke(WebSettings::class.java, *arrayOf<Any>(context)) as String
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return if (TextUtils.isEmpty(ua)) Constants.UNKNOWN else ua!!
    }
}
