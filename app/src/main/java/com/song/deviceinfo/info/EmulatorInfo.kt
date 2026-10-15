package com.song.deviceinfo.info

import android.content.Context
import android.os.Build
import androidx.core.util.Pair
import com.google.firebase.perf.metrics.AddTrace
import com.song.deviceinfo.utils.CommandUtils.execute
import com.song.deviceinfo.utils.CommandUtils.getProperty
import com.song.deviceinfo.utils.EmulatorUtils.arch
import com.song.deviceinfo.utils.EmulatorUtils.bluetoothCheck
import com.song.deviceinfo.utils.EmulatorUtils.brandInfo
import com.song.deviceinfo.utils.EmulatorUtils.cameraCheck
import com.song.deviceinfo.utils.EmulatorUtils.checkPipes
import com.song.deviceinfo.utils.EmulatorUtils.getBatteryInfo
import com.song.deviceinfo.utils.EmulatorUtils.getLauncherPackageName
import com.song.deviceinfo.utils.EmulatorUtils.getMapsInfo
import com.song.deviceinfo.utils.EmulatorUtils.mapsArch
import com.song.deviceinfo.utils.EmulatorUtils.modelName
import com.song.deviceinfo.utils.EmulatorUtils.qemuCheck
import com.song.deviceinfo.utils.EmulatorUtils.setLauncherInfo
import com.song.deviceinfo.utils.EmulatorUtils.sound
import com.song.deviceinfo.utils.EmulatorUtils.specialFilesEmulatorCheck
import com.song.deviceinfo.utils.EmulatorUtils.suVersion
import com.song.deviceinfo.utils.EmulatorUtils.thermalCheck

/**
 * Created by chensongsong on 2020/7/9.
 */
object EmulatorInfo {
    /**
     * 获取应用列表
     *
     * @param context
     * @return
     */
    @AddTrace(name = "EmulatorInfo.getEmulatorInfo")
    fun getEmulatorInfo(context: Context): List<Pair<String, String>> {
        val list: MutableList<Pair<String, String>> = ArrayList()
        list.add(Pair("Su Version", suVersion))
        list.add(Pair("Sound", sound))
        list.add(Pair("Battery", getBatteryInfo(context)))
        list.add(Pair("BrandInfo", brandInfo))
        val launcher = getLauncherPackageName(context)
        list.add(Pair("Launcher", launcher))
        setLauncherInfo(context, list, launcher)
        list.add(Pair("ThermalCount", thermalCheck().toString() + ""))
        list.add(Pair("CameraCheck", cameraCheck(context).toString() + ""))
        list.add(Pair("RSSI", NetWorkInfo.getRssi(context)))
        list.add(Pair("Abi", getProperty("ro.product.cpu.abi")))
        list.add(Pair("AbiList", getProperty("ro.product.cpu.abilist")))
        list.add(Pair("Hardware", getProperty("ro.hardware")))
        list.add(Pair("Arch", execute("uname -m")))
        list.add(Pair("BluetoothFile", bluetoothCheck().toString() + ""))
        list.add(Pair("SpecialFiles", specialFilesEmulatorCheck().toString() + ""))
        list.add(Pair("x86", arch.toString() + ""))
        list.add(Pair("MapsArch", mapsArch))
        // 新增 ARM 模拟器检测特性
        getMapsInfo(list)
        list.add(Pair("Qemu", qemuCheck().toString() + ""))
        list.add(Pair("Pipes", checkPipes().toString() + ""))
        list.add(Pair("Model Name", modelName))
        list.add(Pair("Product", getProperty("ro.product.name")))
        list.add(Pair("Manufacturer", getProperty("ro.product.manufacturer")))
        list.add(Pair("Brand", getProperty("ro.product.brand")))
        list.add(Pair("Device", getProperty("ro.product.device")))
        list.add(Pair("Model", getProperty("ro.product.model")))
        list.add(Pair("Fingerprint", Build.FINGERPRINT))
        return list
    }
}
