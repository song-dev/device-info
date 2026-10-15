package com.song.deviceinfo.utils

import java.io.File

/**
 * Created by chensongsong on 2020/6/1.
 */
object FileUtils {
    /**
     * 是否存在
     *
     * @param name
     * @return
     */
    fun exists(name: String): Boolean {
        return File(name).exists()
    }

    @JvmStatic
    fun readFile(name: String): String? {
        return readFile(File(name))
    }

    @JvmStatic
    fun readFile(file: File?): String? {
        if (file == null) {
            return null
        }
        return try {
            val sb = StringBuilder()
            file.forEachLine { line ->
                sb.append(line)
                sb.append('\n')
            }
            if (sb.isNotEmpty()) {
                sb.deleteCharAt(sb.length - 1)
            }
            sb.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
