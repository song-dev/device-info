package com.song.deviceinfo.ui.codecs

import androidx.core.util.Pair
import com.song.deviceinfo.info.MediaCodecInfo
import com.song.deviceinfo.ui.base.BaseViewModel

/**
 * Created by chensongsong on 2020/6/3.
 */
class CodecViewModel : BaseViewModel<Pair<String, String>>() {
    val codeCInfo: List<Pair<String, String>>
        get() = MediaCodecInfo.codeCInfo
}
