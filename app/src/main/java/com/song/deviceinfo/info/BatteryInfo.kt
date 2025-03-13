package com.song.deviceinfo.info

import android.content.Context
import androidx.core.util.Pair
import com.song.deviceinfo.utils.BatteryUtils.getBatteryInfo

/**
 * Created by chensongsong on 2020/6/1.
 */
object BatteryInfo {
    /**
     * 获取电池信息
     *
     * @return 电池JSON
     */
    fun getBatteryInfo(context: Context): List<Pair<String, String>> {
        val list = ArrayList<Pair<String, String>>()
        getBatteryInfo(context, list)
        return list
    }
}
