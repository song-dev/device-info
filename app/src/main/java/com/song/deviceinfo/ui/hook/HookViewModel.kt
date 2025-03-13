package com.song.deviceinfo.ui.hook

import android.content.Context
import androidx.core.util.Pair
import com.song.deviceinfo.info.HookInfo
import com.song.deviceinfo.ui.base.NormalViewModel

/**
 * Created by chensongsong on 2020/7/16.
 */
class HookViewModel : NormalViewModel() {
    fun getHookInfo(context: Context?): List<Pair<String, String>> {
        return HookInfo.getHookInfo(context!!)
    }
}
