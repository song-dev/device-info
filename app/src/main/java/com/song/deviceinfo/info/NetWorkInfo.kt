package com.song.deviceinfo.info

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.core.util.Pair
import com.song.deviceinfo.R
import com.song.deviceinfo.utils.GatewayUtils.getBssid
import com.song.deviceinfo.utils.GatewayUtils.getHostIpv6
import com.song.deviceinfo.utils.GatewayUtils.getIp
import com.song.deviceinfo.utils.GatewayUtils.getMacAddress
import com.song.deviceinfo.utils.GatewayUtils.getMobileSignal
import com.song.deviceinfo.utils.GatewayUtils.getProxyInfo
import com.song.deviceinfo.utils.GatewayUtils.getSsid
import com.song.deviceinfo.utils.GatewayUtils.getWifiInfo
import com.song.deviceinfo.utils.NetWorkUtils.getNetWorkInfo
import com.song.deviceinfo.utils.NetWorkUtils.getNetWorkType
import com.song.deviceinfo.utils.NetWorkUtils.isMobileEnabled
import com.song.deviceinfo.utils.NetWorkUtils.isNetSystemUsable
import com.song.deviceinfo.utils.NetWorkUtils.isNetworkConnected
import com.song.deviceinfo.utils.NetWorkUtils.isWifi

/**
 * Created by chensongsong on 2020/5/25.
 */
object NetWorkInfo {
    /**
     * @param context
     * @return
     */
    fun getNetWorkInfo(context: Context): List<Pair<String, String>> {
        val list: MutableList<Pair<String, String>> = ArrayList()
        getNetWorkStatus(context, list)
        if (isWifi(context)) {
            setWifiInfo(context, list)
        } else {
            getMobileInfo(context, list)
        }
        return list
    }
    
    private fun getNetWorkStatus(context: Context, list: MutableList<Pair<String, String>>) {
        list.add(Pair("Net Availability", isNetworkConnected(context).toString() + ""))
        list.add(Pair("Mobile Availability", isMobileEnabled(context).toString() + ""))
        list.add(Pair("WIFI Availability", isWifi(context).toString() + ""))
        list.add(Pair("NET TYPE", getNetWorkType(context)))
        list.add(Pair("Net System Usable", isNetSystemUsable(context).toString() + ""))
        getNetWorkInfo(context, list)
    }
    
    /**
     * wifi
     *
     * @param context
     * @return
     */
    private fun setWifiInfo(context: Context, list: MutableList<Pair<String, String>>) {
        try {
            val ips = getIp(context)
            if (ips.containsKey("en0")) {
                list.add(Pair(context.getString(R.string.net_ipv4), ips["en0"]))
                list.add(Pair(context.getString(R.string.net_ipv6), getHostIpv6(ips["network_name"])))
            } else if (ips.containsKey("vpn")) {
                list.add(Pair(context.getString(R.string.net_ipv4), ips["vpn"]))
            }
            list.add(Pair(context.getString(R.string.net_ssid), getSsid(context)))
            list.add(Pair(context.getString(R.string.net_bssid), getBssid(context)))
            list.add(Pair(context.getString(R.string.net_mac), getMacAddress(context)))
            getProxyInfo(context, list)
            val wifiInfo = getWifiInfo(context)
            if (wifiInfo != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    list.add(Pair(context.getString(R.string.net_frequency), wifiInfo.frequency.toString() + " MHz"))
                }
                list.add(Pair(context.getString(R.string.net_link_speed), wifiInfo.linkSpeed.toString() + " Mbps"))
                list.add(Pair(context.getString(R.string.net_id), wifiInfo.networkId.toString() + ""))
                val rssi = wifiInfo.rssi
                list.add(Pair(context.getString(R.string.net_rssi), "$rssi dBm"))
                list.add(Pair(context.getString(R.string.net_level), calculateSignalLevel(rssi).toString() + ""))
                list.add(Pair(context.getString(R.string.net_state), wifiInfo.supplicantState.name))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    /**
     * mobile
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    private fun getMobileInfo(context: Context, list: MutableList<Pair<String, String>>) {
        try {
            val ips = getIp(context)
            if (ips.containsKey("en0")) {
                list.add(Pair(context.getString(R.string.net_ipv4), ips["en0"]))
                list.add(Pair(context.getString(R.string.net_ipv6), getHostIpv6(ips["network_name"])))
            } else if (ips.containsKey("vpn")) {
                list.add(Pair(context.getString(R.string.net_ipv4), ips["vpn"]))
            }
            list.add(Pair(context.getString(R.string.net_mac), getMacAddress(context)))
            val signal = getMobileSignal(context)
            list.add(Pair(context.getString(R.string.net_rssi), signal.first.toString() + " dBm"))
            list.add(Pair(context.getString(R.string.net_level), signal.second.toString() + ""))
            getProxyInfo(context, list)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    private const val MIN_RSSI = -100
    private const val MAX_RSSI = -55
    
    /**
     * 根据 rssi 计算 level
     *
     * @param rssi
     * @return
     */
    private fun calculateSignalLevel(rssi: Int): Int {
        if (rssi <= MIN_RSSI) {
            return 0
        } else if (rssi >= MAX_RSSI) {
            return 4
        } else {
            val inputRange = (MAX_RSSI - MIN_RSSI).toFloat()
            val outputRange = 4f
            return ((rssi - MIN_RSSI).toFloat() * outputRange / inputRange).toInt()
        }
    }
    
    /**
     * Rssi
     *
     * @param context
     * @return
     */
    fun getRssi(context: Context): String {
        if (isWifi(context)) {
            val wifiInfo = getWifiInfo(context)
            return if (wifiInfo != null) {
                wifiInfo.rssi.toString() + " dBm"
            } else {
                "-1 dBm"
            }
        } else {
            return getMobileSignal(context).first.toString() + " dBm"
        }
    }
}
