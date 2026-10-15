package com.song.deviceinfo.info

import android.content.Context
import android.os.Process
import com.song.deviceinfo.utils.LogUtils.d
import java.io.BufferedReader
import java.io.FileReader

/**
 * Created by chensongsong on 2021/9/10.
 */
object MapsInfo {
    fun getMapsInfo(context: Context?): List<String> {
        val list: MutableList<String> = ArrayList()
        val mapsFilename = "/proc/" + Process.myPid() + "/maps"
        var bufferedReader: BufferedReader? = null
        var fileReader: FileReader? = null
        try {
            fileReader = FileReader(mapsFilename)
            bufferedReader = BufferedReader(fileReader)
            var line: String
            while ((bufferedReader.readLine().also { line = it }) != null) {
                d("maps line: $line")
                list.add(line)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                bufferedReader?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            try {
                fileReader?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return list
    }
}
