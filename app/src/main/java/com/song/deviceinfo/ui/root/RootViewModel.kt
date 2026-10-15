package com.song.deviceinfo.ui.root

import android.content.Context
import androidx.core.util.Pair
import com.song.deviceinfo.info.RootInfo
import com.song.deviceinfo.ui.base.NormalViewModel

/**
 * Created by chensongsong on 2020/7/15.
 */
class RootViewModel : NormalViewModel() {
    fun getRootInfo(context: Context?): List<Pair<String, String>> {
        return RootInfo.getRootInfo(context!!)
    }
}
