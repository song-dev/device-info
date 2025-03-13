package com.song.deviceinfo.ui.soc

import android.content.Context
import androidx.core.util.Pair
import com.song.deviceinfo.info.SOCInfo
import com.song.deviceinfo.ui.base.NormalViewModel

/**
 * Created by chensongsong on 2020/6/8.
 */
class SOCViewModel : NormalViewModel() {
    fun getSOCInfo(context: Context?): List<Pair<String, String>> {
        return SOCInfo.getSOCInfo(context!!)
    }
}
