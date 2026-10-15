package com.song.deviceinfo.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

/**
 * Created by chensongsong on 2020/9/22.
 */
class TimeUtils {
    companion object {
        @JvmStatic
        @SuppressLint("SimpleDateFormat")
        fun formatDate(time: Long): String {
            return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(time))
        }
    }
}
