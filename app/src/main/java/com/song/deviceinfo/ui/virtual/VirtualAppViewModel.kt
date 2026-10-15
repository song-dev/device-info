package com.song.deviceinfo.ui.virtual

import android.content.Context
import androidx.core.util.Pair
import com.song.deviceinfo.info.VirtualAppInfo
import com.song.deviceinfo.ui.base.NormalViewModel

/**
 * Created by chensongsong on 2020/7/10.
 */
class VirtualAppViewModel : NormalViewModel() {
    fun getVirtualAppInfo(context: Context): List<Pair<String, String>> {
        return VirtualAppInfo.getVirtualAppInfo(context)
    }
}
