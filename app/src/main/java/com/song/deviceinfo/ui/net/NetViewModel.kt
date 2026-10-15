package com.song.deviceinfo.ui.net

import android.content.Context
import androidx.core.util.Pair
import com.song.deviceinfo.info.NetWorkInfo
import com.song.deviceinfo.ui.base.NormalViewModel

class NetViewModel : NormalViewModel() {
    fun getNetWorkInfo(context: Context?): List<Pair<String, String>> {
        return NetWorkInfo.getNetWorkInfo(context!!)
    }
}