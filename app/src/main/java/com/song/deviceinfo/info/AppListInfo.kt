package com.song.deviceinfo.info

import android.content.Context
import android.content.pm.ApplicationInfo
import com.song.deviceinfo.model.beans.ApplicationBean

/**
 * Created by chensongsong on 2020/6/3.
 */
object AppListInfo {
    /**
     * 获取应用列表
     *
     * @param context
     * @return
     */
    fun getAppListInfo(context: Context): List<ApplicationBean> {
        val list = mutableListOf<ApplicationBean>()
        val packageManager = context.applicationContext.packageManager
        val installedPackages = packageManager.getInstalledPackages(0)
        for (info in installedPackages) {
            val bean = ApplicationBean()
            bean.name = info.applicationInfo.loadLabel(packageManager).toString()
            bean.packageName = info.packageName
            bean.version = info.versionName
            bean.icon = info.applicationInfo.loadIcon(packageManager)
            bean.buildVersion = info.applicationInfo.targetSdkVersion
            bean.isSystemApp = (ApplicationInfo.FLAG_SYSTEM and info.applicationInfo.flags) != 0
            list.add(bean)
        }
        return list
    }
}
