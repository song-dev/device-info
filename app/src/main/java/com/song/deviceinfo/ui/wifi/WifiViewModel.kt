package com.song.deviceinfo.ui.wifi

import android.content.Context
import androidx.core.util.Pair
import com.song.deviceinfo.info.WifiInfo
import com.song.deviceinfo.ui.base.NormalViewModel

/**
 * Created by chensongsong on 2020/9/22.
 */
class WifiViewModel : NormalViewModel() {
    fun getWifiInfo(context: Context): List<Pair<String, String>> {
        return WifiInfo.getWifiInfo(context)
    }
}
