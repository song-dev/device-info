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
        val list: MutableList<Pair<String, String>> = ArrayList()
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
            val list: MutableList<USBBean> = ArrayList()
            val files = file.listFiles() ?: return list
            for (i in files.indices) {
                val name = files[i].name
                if (name.startsWith("usb") || name.contains("-")) {
                    val sb = StringBuilder()
                    sb.append("/sys/bus/usb/drivers/usb/")
                    sb.append(name)
                    sb.append("/")
                    val bean = USBBean()
                    parseUSB(sb.toString(), bean)
                    list.add(bean)
                }
            }
            return list
        }
    
    private fun parseUSB(path: String, bean: USBBean) {
        var sb = StringBuilder()
        sb.append(path)
        sb.append("manufacturer")
        bean.manufacturerName = readFile(sb.toString())
        sb = StringBuilder()
        sb.append(path)
        sb.append("product")
        bean.productName = readFile(sb.toString())
        sb = StringBuilder()
        sb.append(path)
        sb.append("serial")
        bean.serialNumber = readFile(sb.toString())
        sb = StringBuilder()
        sb.append(path)
        sb.append("idProduct")
        bean.productId = readFile(sb.toString())
        sb = StringBuilder()
        sb.append(path)
        sb.append("idVendor")
        bean.vendorId = readFile(sb.toString())
        sb = StringBuilder()
        sb.append(path)
        sb.append("version")
        bean.version = readFile(sb.toString())
        sb = StringBuilder()
        sb.append(path)
        sb.append("busnum")
        var busNum = readFile(sb.toString())
        sb = StringBuilder()
        sb.append(path)
        sb.append("devnum")
        var devNum = readFile(sb.toString())
        if (busNum != null) {
            if (devNum != null) {
                devNum = parsePath(devNum, "0", 3)
                val builder = StringBuilder()
                builder.append(busNum)
                builder.append(devNum)
                bean.num = builder.toString()
                busNum = parsePath(busNum, "0", 3)
            }
        }
        val builder = StringBuilder()
        builder.append("/dev/bus/usb/")
        builder.append(busNum)
        builder.append("/")
        builder.append(devNum)
        bean.path = builder.toString()
    }
    
    private fun parsePath(numStr: String, str: String, size: Int): String {
        var size = size
        val sb = StringBuilder()
        size -= numStr.length
        while (size > 0) {
            sb.append(str)
            size--
        }
        sb.append(numStr)
        return sb.toString()
    }
}
