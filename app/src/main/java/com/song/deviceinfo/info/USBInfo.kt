package com.song.deviceinfo.info

import android.content.Context
import android.text.TextUtils
import androidx.core.util.Pair
import com.song.deviceinfo.R
import com.song.deviceinfo.model.beans.USBBean
import com.song.deviceinfo.utils.FileUtils.readFile
import java.io.File

/**
 * Created by chensongsong on 2020/6/8.
 */
object USBInfo {
    /**
     * 获取 USB 设备信息
     *
     * @param context
     * @return
     */
    fun getUSBInfo(context: Context): List<Pair<String, String>> {
        val list = mutableListOf<Pair<String, String>>()
        val info = info
        if (!info.isEmpty()) {
            for (i in info.indices) {
                val bean = info[i]
                list.add(Pair(context.getString(R.string.usb_vendor), bean.manufacturerName))
                list.add(Pair(context.getString(R.string.usb_model), bean.productName))
                list.add(Pair(context.getString(R.string.usb_device_id), bean.vendorId + ":" + bean.productId))
                if (!TextUtils.isEmpty(bean.serialNumber)) {
                    list.add(Pair(context.getString(R.string.usb_serial), bean.serialNumber))
                }
                list.add(Pair(context.getString(R.string.usb_number), bean.num))
                list.add(Pair(context.getString(R.string.usb_path), bean.path))
                list.add(Pair(context.getString(R.string.usb_version), bean.version))
                if (i != (info.size - 1)) {
                    list.add(Pair("", ""))
                }
            }
        } else {
            list.add(Pair(context.getString(R.string.usb_devices), context.getString(R.string.usb_not_found)))
        }
        return list
    }
    
    val isUSBDrivers: Boolean
        get() {
            val files = (File("/sys/bus/usb/drivers/usb/")).listFiles() ?: return false
            for (file in files) {
                val name = file.name
                if (name.startsWith("usb") || name.contains("-")) {
                    return true
                }
            }
            return false
        }
    
    private val info: List<USBBean>
        get() {
            val file = File("/sys/bus/usb/drivers/usb/")
            val list: MutableList<USBBean> = mutableListOf()
            val files = file.listFiles() ?: return list
            for (i in files.indices) {
                val name = files[i].name
                if (name.startsWith("usb") || name.contains("-")) {
                    val bean = USBBean()
                    parseUSB("/sys/bus/usb/drivers/usb/$name/", bean)
                    list.add(bean)
                }
            }
            return list
        }
    
    private fun parseUSB(path: String, bean: USBBean) {
        bean.manufacturerName = readFile("${path}manufacturer")
        bean.productName = readFile("${path}product")
        bean.serialNumber = readFile("${path}serial")
        bean.productId = readFile("${path}idProduct")
        bean.vendorId = readFile("${path}idVendor")
        bean.version = readFile("${path}version")
        var busNum = readFile("${path}busnum")
        var devNum = readFile("${path}devnum")
        if (busNum != null) {
            if (devNum != null) {
                devNum = parsePath(devNum, "0", 3)
                bean.num = busNum + devNum
                busNum = parsePath(busNum, "0", 3)
            }
        }
        bean.path = "/dev/bus/usb/$busNum/$devNum"
    }
    
    private fun parsePath(numStr: String, str: String, size: Int): String {
        val sb = StringBuilder()
        var remaining = size - numStr.length
        while (remaining > 0) {
            sb.append(str)
            remaining--
        }
        sb.append(numStr)
        return sb.toString()
    }
}
