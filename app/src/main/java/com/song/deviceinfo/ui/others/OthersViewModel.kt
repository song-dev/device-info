package com.song.deviceinfo.ui.others

import android.content.Context
import androidx.core.util.Pair
import com.song.deviceinfo.info.OthersInfo
import com.song.deviceinfo.ui.base.NormalViewModel

/**
 * Created by chensongsong on 2020/9/22.
 */
class OthersViewModel : NormalViewModel() {
    fun getOthersInfo(context: Context?): List<Pair<String, String>> {
        return OthersInfo.getOthersInfo(context!!)
    }
}
