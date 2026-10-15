package com.song.deviceinfo.info

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.text.TextUtils
import androidx.core.util.Pair
import com.song.deviceinfo.utils.CommandUtils.execute
import com.song.deviceinfo.utils.CommandUtils.getProperty
import com.song.deviceinfo.utils.DecimalUtils.round
import com.song.deviceinfo.utils.FileUtils.readFile
import com.song.deviceinfo.utils.LogUtils.e
import com.song.deviceinfo.utils.SocUtils.getGPUInfo
import com.song.deviceinfo.utils.SocUtils.setCpuInfo
import java.io.BufferedReader
import java.io.File
import java.io.FileFilter
import java.io.FileReader
import java.io.IOException
import java.util.Collections
import java.util.Objects
import java.util.regex.Pattern

/**
 * Created by chensongsong on 2020/6/8.
 */
object SOCInfo {
    fun getSOCInfo(context: Context): List<Pair<String, String>> {
        val list: MutableList<Pair<String, String>> = ArrayList()
        setCpuInfo(list)
        setFrequency(list)
        list.add(Pair("Machine", execute("uname -m")))
        list.add(Pair("ABI", Build.CPU_ABI))
        list.add(Pair("Thermal", cpuTemp + "℃"))
        list.add(Pair("CPU", getProperty("ro.board.platform")))
        getGPUInfo(context, list)
        return list
    }
    
    private val CPU_FILTER = FileFilter { pathname -> Pattern.matches("cpu[0-9]", pathname.name) }
    
    private val cpuTemp: String?
        get() {
            var temp: String? = null
            try {
                val fr = FileReader("/sys/class/thermal/thermal_zone9/subsystem/thermal_zone9/temp")
                val br = BufferedReader(fr)
                temp = br.readLine()
                br.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return if (TextUtils.isEmpty(temp)) null else if (temp!!.length >= 5) (temp.toInt() / 1000).toString() + "" else temp
        }
    
    @SuppressLint("DefaultLocale")
    private fun setFrequency(list: MutableList<Pair<String, String>>) {
        try {
            val cores = Objects.requireNonNull(File("/sys/devices/system/cpu/").listFiles(CPU_FILTER)).size
            list.add(Pair("Cores", cores.toString() + ""))
            if (cores > 0) {
                val min = ArrayList<Int>()
                val max = ArrayList<Int>()
                for (i in 0 until cores) {
                    min.add(readFile(String.format("/sys/devices/system/cpu/cpu%d/cpufreq/cpuinfo_min_freq", i))!!.toInt())
                    max.add(readFile(String.format("/sys/devices/system/cpu/cpu%d/cpufreq/cpuinfo_max_freq", i))!!.toInt())
                }
                Collections.sort(min)
                Collections.sort(max)
                e(max.toString())
                if (max.size > 0) {
                    list.add(Pair("Clock speed", min[0].toString() + " - " + max[max.size - 1] + " MHz"))
                    val map: MutableMap<Int, Int> = HashMap()
                    for (temp in max) {
                        val count = map[temp]
                        map[temp] = if ((count == null)) 1 else count + 1
                    }
                    e(map.toString())
                    val sb = StringBuffer()
                    for ((key, value) in map) {
                        sb.append(value)
                                .append(" x ")
                                .append(round(key / 1000.0 / 1000.0, 2))
                                .append(" GHz")
                                .append('\n')
                    }
                    list.add(Pair("Clusters", sb.deleteCharAt(sb.length - 1).toString()))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
