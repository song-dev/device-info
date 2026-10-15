package com.song.deviceinfo.ui.thermal

import androidx.core.util.Pair
import com.song.deviceinfo.info.ThermalInfo
import com.song.deviceinfo.ui.base.NormalViewModel

/**
 * Created by chensongsong on 2020/5/27.
 */
class ThermalViewModel : NormalViewModel() {
    val thermalInfo: List<Pair<String, String>>
        get() = ThermalInfo.thermalInfo
}
