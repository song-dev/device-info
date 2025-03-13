package com.song.deviceinfo.info

import android.app.Activity
import android.os.Build
import androidx.core.util.Pair
import com.song.deviceinfo.R
import com.song.deviceinfo.utils.CommandUtils.getProperty
import com.song.deviceinfo.utils.DensityUtils.getDensityDpi
import com.song.deviceinfo.utils.DensityUtils.getDensityId
import com.song.deviceinfo.utils.DensityUtils.getRefreshRate
import com.song.deviceinfo.utils.SocUtils.socInfo
import com.song.deviceinfo.utils.SystemConfigUtils.currentTimeZone
import com.song.deviceinfo.utils.SystemConfigUtils.getCurrentLanguage
import com.song.deviceinfo.utils.SystemConfigUtils.systemUpdate

/**
 * Created by chensongsong on 2020/5/27.
 */
object SystemInfo {
    fun getSystemInfo(activity: Activity): List<Pair<String, String>> {
        val list = ArrayList<Pair<String, String>>()
        val context = activity.applicationContext
        list.add(Pair(context.getString(R.string.system_manufacture), Build.MANUFACTURER))
        list.add(Pair(context.getString(R.string.system_model), Build.MODEL))
        list.add(Pair(context.getString(R.string.system_brand), Build.BRAND))
        list.add(Pair(context.getString(R.string.system_release), Build.VERSION.RELEASE))
        list.add(Pair(context.getString(R.string.system_api), Build.VERSION.SDK_INT.toString() + ""))
        list.add(Pair(context.getString(R.string.system_code_name), Build.VERSION.CODENAME))
        list.add(Pair(context.getString(R.string.system_density),
                getDensityDpi(context).toString() + " (" + getDensityId(context) + ")"))
        list.add(Pair(context.getString(R.string.system_refresh_rate), getRefreshRate(activity).toString() + " Hz"))
        list.add(Pair(context.getString(R.string.system_device), Build.DEVICE))
        list.add(Pair(context.getString(R.string.system_product), Build.PRODUCT))
        list.add(Pair(context.getString(R.string.system_board), Build.BOARD))
        list.add(Pair(context.getString(R.string.system_platform), socInfo))
        list.add(Pair(context.getString(R.string.system_build), Build.ID))
        list.add(Pair(context.getString(R.string.system_vm), System.getProperty("java.vm.version")))
        list.add(Pair(context.getString(R.string.system_security), getProperty("ro.build.version.security_patch")))
        list.add(Pair(context.getString(R.string.system_baseband), getProperty("gsm.version.baseband")))
        list.add(Pair(context.getString(R.string.system_build_type), Build.TYPE))
        list.add(Pair(context.getString(R.string.system_tags), Build.TAGS))
        list.add(Pair(context.getString(R.string.system_incremental), getProperty("ro.build.version.incremental")))
        list.add(Pair(context.getString(R.string.system_description), getProperty("ro.build.description")))
        list.add(Pair(context.getString(R.string.system_fingerprint), Build.FINGERPRINT))
        list.add(Pair(context.getString(R.string.system_device_features), "68"))
        list.add(Pair(context.getString(R.string.system_builder), "builder@" + getProperty("ro.build.host")))
        list.add(Pair(context.getString(R.string.system_language), getCurrentLanguage(context)))
        list.add(Pair(context.getString(R.string.system_timezone), currentTimeZone))
        list.add(Pair(context.getString(R.string.system_uptime), systemUpdate))
        
        return list
    }
}
