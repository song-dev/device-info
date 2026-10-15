package com.song.deviceinfo.ui.debug

import android.content.Context
import androidx.core.util.Pair
import com.song.deviceinfo.info.DebugInfo
import com.song.deviceinfo.ui.base.NormalViewModel

/**
 * Created by chensongsong on 2020/7/14.
 */
class DebugViewModel : NormalViewModel() {
    fun getDebugInfo(context: Context?): List<Pair<String, String>> {
        return DebugInfo.getDebugInfo(context!!)
    }
}
