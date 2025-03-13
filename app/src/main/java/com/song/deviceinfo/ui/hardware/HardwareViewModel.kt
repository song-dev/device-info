package com.song.deviceinfo.ui.hardware

import android.content.Context
import androidx.core.util.Pair
import com.song.deviceinfo.info.HardwareInfo
import com.song.deviceinfo.ui.base.NormalViewModel

/**
 * Created by chensongsong on 2021/9/29.
 */
class HardwareViewModel : NormalViewModel() {
    fun getHardwareInfo(context: Context): List<Pair<String, String>> {
        return HardwareInfo.getHardwareInfo(context)
    }
}
