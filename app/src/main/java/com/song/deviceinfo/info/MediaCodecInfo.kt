package com.song.deviceinfo.info

import android.media.MediaCodecList
import androidx.core.util.Pair

/**
 * Created by chensongsong on 2020/6/3.
 */
object MediaCodecInfo {
    val codeCInfo: List<Pair<String, String>>
        get() {
            val list = ArrayList<Pair<String, String>>()
            val codecCount = MediaCodecList.getCodecCount()
            for (i in 0 until codecCount) {
                val codecInfo = MediaCodecList.getCodecInfoAt(i)
                if (codecInfo != null) {
                    val types = codecInfo.supportedTypes
                    if (types != null) {
                        for (type in types) {
                            list.add(Pair(codecInfo.name, type))
                        }
                    }
                }
            }
            return list
        }
}
