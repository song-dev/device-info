package com.song.deviceinfo.ui.camera

import android.content.Context
import androidx.core.util.Pair
import com.song.deviceinfo.info.CameraInfo
import com.song.deviceinfo.ui.base.NormalViewModel

/**
 * Created by chensongsong on 2020/9/30.
 */
class CameraViewModel : NormalViewModel() {
    fun getCameraInfo(context: Context?): List<Pair<String, String>> {
        return CameraInfo.getCameraInfo(context!!)
    }
}
