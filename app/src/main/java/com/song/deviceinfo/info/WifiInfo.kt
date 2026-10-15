package com.song.deviceinfo.info

import android.content.Context
import android.net.wifi.WifiManager
import androidx.core.util.Pair
import com.google.firebase.perf.metrics.AddTrace
import com.song.deviceinfo.R

/**
 * Created by chensongsong on 2020/9/22.
 */
object WifiInfo {
    @AddTrace(name = "WifiInfo.getWifiInfo")
    fun getWifiInfo(context: Context): List<Pair<String, String>> {
        var enable = false
        val list: MutableList<Pair<String, String>> = ArrayList()
        val manager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        if (manager != null) {
            // 依赖 ACCESS_FINE_LOCATION、ACCESS_WIFI_STATE 权限，需要 WIFI 打开，TODO Android Q 之后 WiFi 扫描获取 BSSID 为随机生成
            val scanResults = manager.scanResults
            enable = manager.isWifiEnabled
            if (scanResults != null && !scanResults.isEmpty()) {
                for (scanResult in scanResults) {
                    list.add(Pair("SSID", scanResult.SSID))
                    list.add(Pair("BSSID", scanResult.BSSID))
                    list.add(Pair("Capabilities", scanResult.capabilities))
                    list.add(Pair("Frequency", scanResult.frequency.toString() + ""))
                    list.add(Pair("Level", scanResult.level.toString() + ""))
                    list.add(Pair("", ""))
                }
            }
            try {
                // 依赖 CHANGE_WIFI_STATE 权限
                manager.startScan()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        if (list.isEmpty()) {
            if (enable) {
                list.add(Pair("WIFI Scan", context.getString(R.string.usb_not_found)))
            } else {
                list.add(Pair("WIFI Scan", "Please turn on WiFi switch"))
            }
        }
        return list
    }
}
