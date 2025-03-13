package com.song.deviceinfo.info

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.util.Pair
import com.google.firebase.perf.metrics.AddTrace
import com.song.deviceinfo.utils.HashUtils.md5Encode
import com.song.deviceinfo.utils.TimeUtils.Companion.formatDate

/**
 * Created by chensongsong on 2020/9/22.
 */
object AppInfo {
    @SuppressLint("PackageManagerGetSignatures")
    @AddTrace(name = "AppInfo.getAppInfo")
    fun getAppInfo(context: Context): List<Pair<String, String>> {
        val list: MutableList<Pair<String, String>> = ArrayList()
        val packageManager = context.packageManager
        val applicationInfo = context.applicationInfo
        val packageName = context.packageName
        list.add(Pair("AppName", applicationInfo.loadLabel(packageManager).toString()))
        list.add(Pair("PackageName", packageName))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            list.add(Pair("MinSdkVersion", applicationInfo.minSdkVersion.toString() + ""))
        }
        list.add(Pair("TargetSdkVersion", applicationInfo.targetSdkVersion.toString() + ""))
        try {
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            list.add(Pair("VersionName", packageInfo.versionName))
            list.add(Pair("VersionCode", packageInfo.versionCode.toString() + ""))
            list.add(Pair("FirstInstallTime", formatDate(packageInfo.firstInstallTime)))
            list.add(Pair("LastUpdateTime", formatDate(packageInfo.lastUpdateTime)))
            val signature = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES).signatures[0]
            list.add(Pair("Sign", md5Encode(signature.toByteArray())))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }
}
