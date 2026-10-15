package com.song.deviceinfo.info

import android.content.Context
import android.text.TextUtils
import androidx.core.util.Pair
import com.song.deviceinfo.utils.CommandUtils.exec
import com.song.deviceinfo.utils.Constants

/**
 * Created by chensongsong on 2021/9/9.
 */
object BuildInfo {
    fun getBuildInfo(context: Context?): List<Pair<String, String>> {
        val list: MutableList<Pair<String, String>> = ArrayList()
        val array = exec("getprop")
        for (item in array) {
            if (!TextUtils.isEmpty(item)) {
                try {
                    val split = item.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    if (split.size == 1) {
                        list.add(Pair(split[0].trim { it <= ' ' }, Constants.UNKNOWN))
                    } else if (split.size == 2) {
                        list.add(Pair(split[0].trim { it <= ' ' }, split[1].trim { it <= ' ' }))
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return list
    }
}
