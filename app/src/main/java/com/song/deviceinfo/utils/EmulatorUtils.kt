package com.song.deviceinfo.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.BatteryManager
import android.os.Build
import android.os.Process
import android.text.TextUtils
import androidx.core.util.Pair
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileReader
import java.io.IOException
import java.io.InputStream
import java.util.Locale

/**
 * 模拟器检测
 * Created by chensongsong on 2020/7/9.
 */
object EmulatorUtils {
    val suVersion: String?
        /**
         * 获取 su 版本
         *
         * @return
         */
        get() = CommandUtils.execute("su -v")
    
    val sound: String?
        /**
         * 读取声卡型号
         *
         * @return
         */
        get() {
            return if (FileUtils.exists("/proc/asound/card0/id")) FileUtils.readFile("cat /proc/asound/card0/id") else null
        }
    
    /**
     * 桌面应用
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun getLauncherPackageName(context: Context): String {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        val res = context.packageManager.resolveActivity(intent, 0) ?: return "\$unknown"
        if (res.activityInfo == null) {
            return "\$unknown"
        }
        return res.activityInfo.packageName
    }
    
    @SuppressLint("PackageManagerGetSignatures")
    fun setLauncherInfo(context: Context, list: MutableList<Pair<String, String>>, launcher: String) {
        val packageManager = context.packageManager
        try {
            val applicationInfo = packageManager.getApplicationInfo(launcher, 0)
            list.add(Pair("LauncherName", applicationInfo.loadLabel(packageManager).toString()))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                list.add(Pair("LauncherMinSdkVersion", applicationInfo.minSdkVersion.toString() + ""))
            }
            list.add(Pair("LauncherTargetSdkVersion", applicationInfo.targetSdkVersion.toString() + ""))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            val packageInfo = packageManager.getPackageInfo(launcher, 0)
            list.add(Pair("LauncherVersionName", packageInfo.versionName))
            list.add(Pair("LauncherVersionCode", packageInfo.versionCode.toString() + ""))
            list.add(Pair<String, String>("LauncherFirstInstallTime", TimeUtils.formatDate(packageInfo.firstInstallTime)))
            list.add(Pair<String, String>("LauncherLastUpdateTime", TimeUtils.formatDate(packageInfo.lastUpdateTime)))
            val signature = packageManager.getPackageInfo(launcher, PackageManager.GET_SIGNATURES).signatures[0]
            list.add(Pair("LauncherSign", HashUtils.md5Encode(signature.toByteArray())))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    private var brandJson: JSONObject? = null
    
    @JvmStatic val brandInfo: String?
        /**
         * @return 品牌信息列表
         */
        get() {
            if (brandJson != null && brandJson!!.length() > 0) {
                return brandJson.toString()
            }
            
            if (FileUtils.exists("/system/app")) {
                val file = File("/system/app")
                val list = file.list()
                if (list != null && list.size > 0) {
                    val hw = JSONArray()
                    val oppo = JSONArray()
                    val vivo = JSONArray()
                    val xm = JSONArray()
                    val op = JSONArray()
                    val smartisan = JSONArray()
                    val samsung = JSONArray()
                    val lenovo = JSONArray()
                    val zte = JSONArray()
                    val mz = JSONArray()
                    
                    // 读取值
                    for (s in list) {
                        var s = s
                        s = s.lowercase(Locale.getDefault())
                        if (((s.startsWith("hw") || s.contains("huawei")) && s != "hw")) {
                            hw.put(s)
                        } else if (s.contains("miui") || s.contains("xiaomi")) {
                            xm.put(s)
                        } else if (s.contains("oppo")) {
                            oppo.put(s)
                        } else if (s.contains("vivo")) {
                            vivo.put(s)
                        } else if (s.contains("samsung")) {
                            samsung.put(s)
                        } else if (s.startsWith("op")) {
                            op.put(s)
                        } else if (s.contains("smartisan")) {
                            smartisan.put(s)
                        } else if (s.contains("lenovo")) {
                            lenovo.put(s)
                        } else if (s.startsWith("zte")) {
                            zte.put(s)
                        } else if (s.startsWith("mz")) {
                            mz.put(s)
                        }
                    }
                    
                    if (FileUtils.exists("/system/emui")) {
                        hw.put("/system/emui")
                    }
                    
                    val jsonObject = JSONObject()
                    if (hw.length() > 0) {
                        try {
                            jsonObject.put("huawei", hw)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                    if (xm.length() > 0) {
                        try {
                            jsonObject.put("xiaomi", xm)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                    if (oppo.length() > 0) {
                        try {
                            jsonObject.put("oppo", oppo)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                    if (vivo.length() > 0) {
                        try {
                            jsonObject.put("vivo", vivo)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                    if (samsung.length() > 0) {
                        try {
                            jsonObject.put("samsung", samsung)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                    if (op.length() > 0) {
                        try {
                            jsonObject.put("oneplus", op)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                    if (smartisan.length() > 0) {
                        try {
                            jsonObject.put("smartisan", smartisan)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                    if (lenovo.length() > 0) {
                        try {
                            jsonObject.put("lenovo", lenovo)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                    if (zte.length() > 0) {
                        try {
                            jsonObject.put("zte", zte)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                    if (mz.length() > 0) {
                        try {
                            jsonObject.put("meizu", mz)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                    brandJson = jsonObject
                    return brandJson.toString()
                }
            }
            return null
        }
    
    @JvmStatic
    fun getBatteryInfo(context: Context): String? {
        val batteryStatus = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        if (batteryStatus != null) {
            val sb = StringBuffer()
            // 电量
            val level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            sb.append("Level: ")
            sb.append(level)
            // unknown=1, charging=2, discharging=3, not charging=4, full=5
            val status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
            sb.append("    Status: ")
            sb.append(status)
            return sb.toString()
        }
        return null
    }
    
    /**
     * qemu 检测
     *
     * @return
     */
    @JvmStatic
    fun qemuCheck(): Boolean {
        if (checkQEmuDriverFile("/proc/tty/drivers") || checkQEmuDriverFile("/proc/cpuinfo")) {
            return true
        }
        return "1" == CommandUtils.getProperty("ro.kernel.qemu")
    }
    
    /**
     * qemu特有的驱动列表
     */
    private val KNOWN_QEMU_DRIVERS = arrayOf("goldfish"
    )
    
    /**
     * 驱动程序的列表
     *
     * @return true为模拟器
     */
    private fun checkQEmuDriverFile(name: String): Boolean {
        val driver = File(name)
        if (driver.exists() && driver.canRead()) {
            val data = ByteArray(1024)
            try {
                val inStream: InputStream = FileInputStream(driver)
                inStream.read(data)
                inStream.close()
            } catch (e: Exception) {
            }
            val driverData = String(data)
            for (known_qemu_driver in KNOWN_QEMU_DRIVERS) {
                if (driverData.contains(known_qemu_driver)) {
                    return true
                }
            }
        }
        return false
    }
    
    /**
     * 设备通道文件，只兼容了qemu模拟器
     */
    private val KNOWN_PIPES = arrayOf("/dev/socket/qemud",
            "/dev/qemu_pipe"
    )
    
    /**
     * 检测“/dev/socket/qemud”，“/dev/qemu_pipe”这两个通道设备文件特征
     *
     * @return true为模拟器
     */
    @JvmStatic
    fun checkPipes(): Boolean {
        for (pipes in KNOWN_PIPES) {
            val qemu = File(pipes)
            if (qemu.exists()) {
                return true
            }
        }
        return false
    }
    
    @JvmStatic val modelName: String?
        get() {
            var fileReader: FileReader? = null
            var reader: BufferedReader? = null
            try {
                var line: String
                fileReader = FileReader("/proc/cpuinfo")
                reader = BufferedReader(fileReader)
                while ((reader.readLine().also { line = it }) != null) {
                    if (line.startsWith("model name")) {
                        return line.substring(line.indexOf(":") + 1).trim { it <= ' ' }
                    }
                }
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
    
    /**
     * 是否支持相机
     */
    @JvmStatic
    fun cameraCheck(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return !context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)
        }
        return false
    }
    
    fun getMapsInfo(list: MutableList<Pair<String, String>>) {
        val map: MutableMap<String, Int> = HashMap()
        val array = arrayOf("libRSDriver.so", "libRSCpuRef.so")
        
        val mapsFilename = "/proc/" + Process.myPid() + "/maps"
        var bufferedReader: BufferedReader? = null
        var fileReader: FileReader? = null
        try {
            fileReader = FileReader(mapsFilename)
            bufferedReader = BufferedReader(fileReader)
            var line: String
            while ((bufferedReader.readLine().also { line = it }) != null) {
                for (item in array) {
                    if (line.contains(item)) {
                        if (map.containsKey(item)) {
                            val temp = map[item]!! + 1
                            map[item] = temp
                        } else {
                            map[item] = 1
                        }
                    }
                }
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
        for ((key, value) in map) {
            list.add(Pair(key, value.toString()))
        }
    }
    
    @JvmStatic
    external fun specialFilesEmulatorCheck(): Int
    
    @JvmStatic
    external fun bluetoothCheck(): Int
    
    @JvmStatic val arch: Int
        external get
    
    @JvmStatic val mapsArch: String?
        external get
    
    @JvmStatic
    external fun thermalCheck(): Int
}
