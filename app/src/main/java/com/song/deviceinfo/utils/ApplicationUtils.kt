package com.song.deviceinfo.utils

import android.content.Context

/**
 * Created by chensongsong on 2021/9/28.
 */
object ApplicationUtils {
    @JvmStatic
    fun isPkgInstalled(context: Context, pkgName: String): Boolean {
        var packageInfo = try {
            context.packageManager.getPackageInfo(pkgName, 0)
        } catch (e: Exception) {
            null
        }
        return packageInfo != null
    }
}
