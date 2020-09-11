package com.song.deviceinfo;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import com.song.deviceinfo.utils.LanguageUtils;
import com.tencent.bugly.crashreport.CrashReport;

import androidx.multidex.MultiDex;

/**
 * Created by chensongsong on 2020/6/9.
 */
public class APP extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), "0334b35dea", false);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            LanguageUtils.changeAppLanguage(this, LanguageUtils.getDefaultLanguage(this));
        }
    }
}
