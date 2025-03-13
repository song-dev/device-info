package com.song.deviceinfo.utils

import android.app.ActivityManager
import android.content.Context
import android.text.format.Formatter
import com.song.deviceinfo.model.beans.StorageBean
import java.io.BufferedReader
import java.io.FileReader

/**
 * Created by chensongsong on 2020/6/2.
 */
object MemoryUtils {
    /**
     * 读取内存信息
     *
     * @return
     */
    @JvmStatic
    fun getMemoryInfo(context: Context, bean: StorageBean) {
        try {
            val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val info = ActivityManager.MemoryInfo()
            manager.getMemoryInfo(info)
            val totalMem = info.totalMem
            val availMem = info.availMem
            val usedMem = totalMem - availMem
            val total = Formatter.formatFileSize(context, totalMem)
            val usable = Formatter.formatFileSize(context, usedMem)
            val free = Formatter.formatFileSize(context, availMem)
            bean.totalMemory = total
            bean.freeMemory = free
            bean.usedMemory = usable
            val ratio = ((availMem / totalMem.toDouble()) * 100).toInt()
            bean.ratioMemory = ratio
            val v = totalMem / 1024 / 1024 / 1024.0
            val ram = if (v <= 1) {
                "1 GB"
            } else if (v <= 2) {
                "2 GB"
            } else if (v <= 4) {
                "4 GB"
            } else if (v <= 6) {
                "6 GB"
            } else if (v <= 8) {
                "8 GB"
            } else if (v <= 12) {
                "12 GB"
            } else {
                "16 GB"
            }
            bean.memInfo = ram
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    /**
     * 通过读取文件获取内存大小
     *
     * @param context
     * @return
     */
    private fun getMemoryTotal(context: Context): String {
        try {
            val fileReader = FileReader("/proc/meminfo")
            val bufferedReader = BufferedReader(fileReader, Constants.BUF_1024)
            val line = bufferedReader.readLine()
            val split = line.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val l = split[1].toLong() * Constants.BUF_1024
            bufferedReader.close()
            return Formatter.formatFileSize(context, l)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Constants.UNKNOWN
    }
}
