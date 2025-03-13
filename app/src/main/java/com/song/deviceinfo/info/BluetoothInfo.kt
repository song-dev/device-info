package com.song.deviceinfo.info

import android.bluetooth.BluetoothManager
import android.content.Context
import android.os.Build
import androidx.core.util.Pair
import com.google.firebase.perf.metrics.AddTrace
import com.song.deviceinfo.R

/**
 * Created by chensongsong on 2020/9/22.
 */
object BluetoothInfo {
    @AddTrace(name = "BluetoothInfo.getBluetoothInfo")
    fun getBluetoothInfo(context: Context): List<Pair<String, String>> {
        var enable = false
        val list: MutableList<Pair<String, String>> = ArrayList()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            val manager = (context.applicationContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager)
            if (manager != null) {
                // 仅依赖 BLUETOOTH、ACCESS_WIFI_STATE 权限，TODO 蓝牙需要打开
                val adapter = manager.adapter
                enable = adapter.isEnabled
                val devices = adapter.bondedDevices
                for (device in devices) {
                    list.add(Pair("Bluetooth Name", device.name))
                    list.add(Pair("Bluetooth Mac", device.address))
                    list.add(Pair("Type", device.type.toString() + ""))
                    list.add(Pair("State", device.bondState.toString() + ""))
                    list.add(Pair("", ""))
                }
            }
        }
        if (list.isEmpty()) {
            if (enable) {
                list.add(Pair("Bluetooth Scan", context.getString(R.string.usb_not_found)))
            } else {
                list.add(Pair("Bluetooth Scan", "Please turn on the Bluetooth switch."))
            }
        }
        return list
    }
}
