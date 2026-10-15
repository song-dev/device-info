package com.song.deviceinfo.ui.maps

import android.content.Context
import com.song.deviceinfo.info.MapsInfo
import com.song.deviceinfo.ui.base.BaseViewModel

/**
 * Created by chensongsong on 2021/9/10.
 */
class MapsViewModel : BaseViewModel<String>() {
    fun getMapsInfo(context: Context?): List<String> {
        return MapsInfo.getMapsInfo(context)
    }
}
