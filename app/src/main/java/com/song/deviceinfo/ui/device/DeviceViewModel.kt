package com.song.deviceinfo.ui.device

import android.content.Context
import androidx.core.util.Pair
import com.song.deviceinfo.info.DeviceInfo
import com.song.deviceinfo.ui.base.NormalViewModel

/**
 * Created by chensongsong on 2020/8/3.
 */
class DeviceViewModel : NormalViewModel() {
    fun getDeviceInfo(context: Context?): List<Pair<String, String>> {
        return DeviceInfo.getDeviceInfo(context!!)
    }
}
