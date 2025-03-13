package com.song.deviceinfo.ui.app

import android.content.Context
import androidx.core.util.Pair
import com.song.deviceinfo.info.AppInfo
import com.song.deviceinfo.ui.base.NormalViewModel

/**
 * Created by chensongsong on 2020/9/22.
 */
class AppViewModel : NormalViewModel() {
    fun getAppInfo(context: Context): List<Pair<String, String>> {
        return AppInfo.getAppInfo(context)
    }
}
