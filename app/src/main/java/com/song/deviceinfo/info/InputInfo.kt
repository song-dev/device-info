package com.song.deviceinfo.info

import android.os.Build
import android.text.TextUtils
import android.view.InputDevice
import com.song.deviceinfo.model.beans.InputBean
import com.song.deviceinfo.utils.FileUtils.readFile

/**
 * Created by chensongsong on 2020/6/4.
 */
object InputInfo {
    private val inputList: MutableList<InputBean> = ArrayList()
    
    val inputInfo: List<InputBean>
        get() {
            if (inputList.isEmpty()) {
                // maybe exec getevent -pl
                val devices = readFile("/proc/bus/input/devices")
                if (!TextUtils.isEmpty(devices)) {
                    readDevices(devices!!)
                } else {
                    devicesByInterface
                }
            }
            return inputList
        }
    
    private val devicesByInterface: Unit
        get() {
            val ids = InputDevice.getDeviceIds()
            for (i in ids.indices) {
                val device = InputDevice.getDevice(ids[i])
                if (device != null) {
                    val name = device.name
                    if (name != null) {
                        val inputBean = InputBean()
                        inputBean.name = device.name
                        inputBean.handlers = device.descriptor
                        val sb = StringBuilder()
                        sb.append("id: ")
                        sb.append(device.id)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            sb.append(" vendor: ")
                            sb.append(device.vendorId)
                            sb.append(" product: ")
                            sb.append(device.productId)
                        }
                        inputBean.attribute = sb.toString()
                        inputList.add(inputBean)
                    }
                }
            }
        }
    
    val isMtk: Boolean
        get() {
            val ids = InputDevice.getDeviceIds()
            for (id in ids) {
                val inputDevice = InputDevice.getDevice(id)
                if (inputDevice != null) {
                    val name = inputDevice.name
                    if (name != null && name == "mtk-tpd") {
                        return true
                    }
                }
            }
            return false
        }
    
    /**
     * 从文件读取配置
     *
     * @param devices
     */
    private fun readDevices(devices: String) {
        for (device in devices.split("\n\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
            if (device != null && !device.isEmpty()) {
                val bean = Bean(device)
                val name = bean.name
                if (name != null && !name.isEmpty()) {
                    val inputBean = InputBean()
                    inputBean.name = bean.name
                    inputBean.sys = "sysfs: " + bean.sys
                    inputBean.handlers = "handlers: " + bean.handlers
                    val buffer = StringBuffer()
                    buffer.append("bus: ")
                    buffer.append(bean.bus)
                    buffer.append(" vendor: ")
                    buffer.append(bean.vendor)
                    buffer.append(" product: ")
                    buffer.append(bean.product)
                    buffer.append(" ver: ")
                    buffer.append(bean.version)
                    inputBean.attribute = buffer.toString()
                    inputList.add(inputBean)
                }
            }
        }
    }
    
    class Bean(device: String) {
        var bus: String? = null
        
        var vendor: String? = null
        
        var product: String? = null
        
        var version: String? = null
        
        var name: String? = null
        
        private var phys: String? = null
        
        var sys: String? = null
        
        var handlers: String? = null
        
        init {
            for (line in device.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
                if (!line.isEmpty()) if (line.startsWith("I:")) {
                    this.bus = parseAttribute(line, "Bus")
                    this.vendor = parseAttribute(line, "Vendor")
                    this.product = parseAttribute(line, "Product")
                    this.version = parseAttribute(line, "Version")
                } else if (line.startsWith("N:")) {
                    this.name = parseValue(line, "Name")
                    if (this.name != null) {
                        this.name = name!!.replace("\"", "")
                    }
                } else if (line.startsWith("P:")) {
                    this.phys = parseValue(line, "Phys")
                } else if (line.startsWith("S:")) {
                    this.sys = parseValue(line, "Sysfs")
                } else if (line.startsWith("H:")) {
                    this.handlers = parseValue(line, "Handlers")
                }
            }
        }
        
        companion object {
            private fun parseValue(data: String, key: String): String? {
                val sb = StringBuffer()
                sb.append(key)
                sb.append("=")
                val i = data.indexOf(sb.toString())
                return if ((i >= 0)) data.substring(i + key.length + 1) else null
            }
            
            private fun parseAttribute(data: String, key: String): String? {
                val sb = StringBuffer()
                sb.append(key)
                sb.append("=")
                var i = data.indexOf(sb.toString())
                if (i >= 0) {
                    val j = key.length + i + 1
                    i = data.indexOf(" ", i)
                    return if ((i >= 0)) data.substring(j, i) else data.substring(j)
                }
                return null
            }
        }
    }
}
