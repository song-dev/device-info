package com.song.deviceinfo.utils

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

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
        var fileReader: FileReader? = null
        var reader: BufferedReader? = null
        try {
            var line: String?
            val sb = StringBuffer()
            fileReader = FileReader(file)
            reader = BufferedReader(fileReader)
            while ((reader.readLine().also { line = it }) != null) {
                sb.append(line)
                sb.append('\n')
            }
            if (sb.length > 0) {
                sb.deleteCharAt(sb.length - 1)
            }
            return sb.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            if (fileReader != null) {
                try {
                    fileReader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return null
    }
}
