package com.song.deviceinfo

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.bun.miitmdid.core.JLibrary
import com.didichuxing.doraemonkit.DoraemonKit
import com.song.deviceinfo.utils.LogUtils
import com.tencent.bugly.crashreport.CrashReport

/**
 * Application class for Device Info app
 * Created by chensongsong on 2020/6/9.
 */
class DeviceInfoApplication : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
    
    override fun onCreate() {
        super.onCreate()
        LogUtils.initLogger(this)
        // Initialize third-party libraries
        // JLibrary.InitEntry(applicationContext)
        CrashReport.initCrashReport(applicationContext, "0334b35dea", false)
        DoraemonKit.install(this)
    }
}
