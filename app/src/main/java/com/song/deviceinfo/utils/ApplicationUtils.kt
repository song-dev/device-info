package com.song.deviceinfo.utils

import android.content.Context

/**
 * Created by chensongsong on 2021/9/28.
 */
object ApplicationUtils {
    @JvmStatic
    fun isPkgInstalled(context: Context, pkgName: String): Boolean {
        val packageInfo = runCatching {
            context.packageManager.getPackageInfo(pkgName, 0)
        }.getOrNull()
        return packageInfo != null
    }
}
