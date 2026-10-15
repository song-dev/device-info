package com.song.deviceinfo.info

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import androidx.core.util.Pair
import com.song.deviceinfo.utils.ApplicationUtils.isPkgInstalled
import com.song.deviceinfo.utils.CommandUtils.exec
import com.song.deviceinfo.utils.CommandUtils.execute
import com.song.deviceinfo.utils.CommandUtils.uidStrFormat
import java.io.File
import java.util.Locale

/**
 * Created by chensongsong on 2020/7/9.
 */
object VirtualAppInfo {
    /**
     * 获取应用列表
     *
     * @param context
     * @return
     */
    fun getVirtualAppInfo(context: Context): List<Pair<String, String>> {
        val list: MutableList<Pair<String, String>> = ArrayList()
        list.add(Pair("FilesDir", context.filesDir.absolutePath))
        list.add(Pair("Permission", moreOpenCheck(context).toString() + ""))
        list.add(Pair("Binder", systemServer.toString() + ""))
        list.add(Pair("Process", processCheck().toString() + ""))
        list.add(Pair("PackageNum", packageCheck(context).toString() + ""))
        list.add(Pair("360 分身大师", is360(context).toString() + ""))
        list.add(Pair("分身大师 X 版", is360Xposed()))
        return list
    }
    
    /**
     * 进程检测，若出现同一个 uid 下出现的进程名对应 /data/data/pkg 私有目录，超出 1 个则为多开
     * 需要排除当前进程名存在多个情况
     *
     * @return
     */
    @Deprecated("当前方案在 6.0 以上机型不可用，因为只能获取当前 uid 进程列表")
    private fun processCheck(): Boolean {
        val filter = uidStrFormat
        if (TextUtils.isEmpty(filter)) {
            return false
        }
        val result = execute("ps")
        if (result == null || result.isEmpty()) {
            return false
        }
        val lines = result.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (lines.size <= 0) {
            return false
        }
        var exitDirCount = 0
        try {
            for (i in lines.indices) {
                if (lines[i].contains(filter!!)) {
                    val pkgStartIndex = lines[i].lastIndexOf(" ")
                    val processName = lines[i].substring(if (pkgStartIndex <= 0)
                        0
                    else
                        pkgStartIndex + 1, lines[i].length)
                    val dataFile = File(String.format("/data/data/%s",
                            processName, Locale.CHINA))
                    if (dataFile.exists()) {
                        exitDirCount++
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return exitDirCount > 1
    }
    
    /**
     * 若 applist 存在两个当前包名则为多开
     *
     * @param context
     * @return
     */
    @Deprecated("大部分多开软件已经绕过")
    private fun packageCheck(context: Context?): Boolean {
        try {
            if (context == null) {
                return false
            }
            var count = 0
            val packageName = context.packageName
            val pm = context.packageManager
            val pkgs = pm.getInstalledPackages(0)
            for (info in pkgs) {
                if (packageName == info.packageName) {
                    count++
                }
            }
            return count > 1
        } catch (ignore: Exception) {
        }
        return false
    }
    
    private val systemServer: Boolean
        /**
         * 获取系统 binder 服务数量，若保持在 140 左右则为多开
         *
         * @return
         */
        get() = false
    
    /**
     * 针对 360 包名检测
     *
     * @param context
     * @return
     */
    @SuppressLint("SdCardPath")
    private fun is360(context: Context): Boolean {
        return isPkgInstalled(context, "com.qihoo.magic")
    }
    
    /**
     * 针对 360 包名权限检测
     *
     * @return
     */
    private fun is360Xposed(): String {
        return exec("ls /data/data/com.qihoo.magic.xposed/").contentToString()
    }
    
    /**
     * 0. 多开检测 false
     * 1. 多开检测 true
     * -1. 检测失败（$unknown）
     * 检测多开, 若可访问规定目录则为正常，否则为多开环境
     *
     * @return
     */
    external fun moreOpenCheck(context: Context?): Int
}
