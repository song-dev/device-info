package com.song.deviceinfo.ui.build

import android.content.Context
import androidx.core.util.Pair
import com.song.deviceinfo.info.BuildInfo
import com.song.deviceinfo.ui.base.NormalViewModel

/**
 * Created by chensongsong on 2021/9/9.
 */
class BuildInfoViewModel : NormalViewModel() {
    fun getBuildInfo(context: Context?): List<Pair<String, String>> {
        return BuildInfo.getBuildInfo(context)
    }
}
