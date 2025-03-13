package com.song.deviceinfo.ui.emulator

import android.content.Context
import androidx.core.util.Pair
import com.song.deviceinfo.info.EmulatorInfo
import com.song.deviceinfo.ui.base.NormalViewModel

/**
 * Created by chensongsong on 2020/7/9.
 */
class EmulatorViewModel : NormalViewModel() {
    fun getEmulatorInfo(context: Context?): List<Pair<String, String>> {
        return EmulatorInfo.getEmulatorInfo(context!!)
    }
}
