package com.song.deviceinfo.ui.system

import android.app.Activity
import androidx.core.util.Pair
import com.song.deviceinfo.info.SystemInfo
import com.song.deviceinfo.ui.base.NormalViewModel

/**
 * Created by chensongsong on 2020/5/27.
 */
class SystemViewModel : NormalViewModel() {
    fun getSystemInfo(activity: Activity): List<Pair<String, String>> {
        return SystemInfo.getSystemInfo(activity)
    }
}