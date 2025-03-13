package com.song.deviceinfo.utils

import android.app.ActivityManager
import android.content.Context
import android.text.TextUtils
import androidx.core.util.Pair
import com.song.deviceinfo.model.beans.CpuBean
import java.io.BufferedReader
import java.io.FileReader
import java.util.Locale

/**
 * Created by chensongsong on 2020/6/1.
 */
object SocUtils {
    @JvmStatic val socInfo: String?
        /**
         * 读取 SOC 型号信息
         *
         * @return
         */
        get() {
            var socStr: String? = ""
            socStr = CommandUtils.execute("getprop ro.board.platform")
            if (TextUtils.isEmpty(socStr)) {
                socStr = CommandUtils.execute("getprop ro.hardware")
                if (TextUtils.isEmpty(socStr)) {
                    socStr = CommandUtils.execute("getprop ro.boot.hardware")
                }
            }
            return socStr
        }
    
    fun setCpuInfo(list: MutableList<Pair<String, String>>) {
        try {
            val bufferedReader = BufferedReader(FileReader("/proc/cpuinfo"))
            var line = ""
            val bean = CpuBean()
            val parts = HashSet<String>()
            val implementer = HashSet<String>()
            while ((bufferedReader.readLine().also { if (it != null) line = it }) != null) {
                val result = line.lowercase(Locale.getDefault())
                LogUtils.d("CPU: $result")
                val split = result.split(":\\s+".toRegex(), limit = 2).toTypedArray()
                if (split[0].startsWith("cpu part")) {
                    parts.add(split[1])
                } else if (split[0].startsWith("hardware")) {
                    bean.hardware = split[1]
                } else if (split[0].startsWith("features")) {
                    bean.features = split[1]
                } else if (split[0].startsWith("cpu implementer")) {
                    implementer.add(split[1])
                }
            }
            bean.parts = parts.toTypedArray<String>()
            bean.implementers = implementer.toTypedArray<String>()
            list.add(Pair("Parts", parts.toString()))
            list.add(Pair("Implementer", implementer.toString()))
            list.add(Pair("Hardware", bean.hardware))
            list.add(Pair("Features", bean.features))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    val coreInfo: String
        get() {
            var core = FileUtils.readFile("/sys/devices/system/cpu/present")
            if (TextUtils.isEmpty(core)) {
                core = CommandUtils.execute("cat /sys/devices/system/cpu/present")
            }
            if (TextUtils.isEmpty(core)) {
                core = Constants.UNKNOWN
            }
            return core!!
        }
    
    fun getGPUInfo(context: Context, list: MutableList<Pair<String, String>>) {
        val am = context.applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val info = am.deviceConfigurationInfo
        list.add(Pair("GlEsVersion", info.glEsVersion))
        list.add(Pair("GlEsVersion", info.reqGlEsVersion.toString() + ""))
        list.add(Pair("GlEsVersion", info.reqInputFeatures.toString() + ""))
        list.add(Pair("GlEsVersion", info.reqKeyboardType.toString() + ""))
        list.add(Pair("GlEsVersion", info.reqNavigation.toString() + ""))
        list.add(Pair("GlEsVersion", info.reqTouchScreen.toString() + ""))
        list.add(Pair("GlEsVersion", info.describeContents().toString() + ""))
    }
}
