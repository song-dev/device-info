package com.song.deviceinfo.ui.battery

import android.content.Context
import androidx.core.util.Pair
import com.song.deviceinfo.info.BatteryInfo
import com.song.deviceinfo.ui.base.NormalViewModel

/**
 * Created by chensongsong on 2020/5/27.
 */
class BatteryViewModel : NormalViewModel() {
    fun getBatteryInfo(context: Context): List<Pair<String, String>> {
        return BatteryInfo.getBatteryInfo(context)
    }
}