package com.song.deviceinfo.info

import android.text.TextUtils
import androidx.core.util.Pair
import com.song.deviceinfo.utils.FileUtils.readFile
import java.io.File
import java.io.FilenameFilter

/**
 * Created by chensongsong on 2020/6/1.
 */
object ThermalInfo {
    val thermalInfo: List<Pair<String, String>>
        get() {
            val list: MutableList<Pair<String, String>> = ArrayList()
            val files = filter("/sys/class/thermal/", ThermalFilter("thermal_zone"))
            if (files != null && files.size > 0) {
                for (file in files) {
                    val type = readFile(File(file, "type"))
                    if (!TextUtils.isEmpty(type)) {
                        val temp = readFile(File(file, "temp"))
                        if (!TextUtils.isEmpty(temp)) {
                            list.add(Pair(type!!.trim { it <= ' ' }, temp!!.trim { it <= ' ' }))
                        }
                    }
                }
            }
            return list
        }
    
    private fun filter(path: String, filter: FilenameFilter): Array<File>? {
        val file = File(path)
        if (file.exists()) {
            return file.listFiles(filter)
        }
        return null
    }
    
    private class ThermalFilter(private val condition: String) : FilenameFilter {
        override fun accept(dir: File, name: String): Boolean {
            return name.startsWith(condition)
        }
    }
}
