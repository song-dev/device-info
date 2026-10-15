package com.song.deviceinfo.info

import android.content.Context
import android.os.Process
import com.song.deviceinfo.utils.LogUtils.d
import java.io.File

/**
 * Created by chensongsong on 2021/9/10.
 */
object MapsInfo {
    fun getMapsInfo(context: Context?): List<String> {
        val list = mutableListOf<String>()
        val mapsFile = File("/proc/${Process.myPid()}/maps")
        try {
            mapsFile.forEachLine { line ->
                d("maps line: $line")
                list.add(line)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }
}
