package com.song.deviceinfo.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.telephony.CellInfo
import android.telephony.CellInfoCdma
import android.telephony.CellInfoGsm
import android.telephony.CellInfoLte
import android.telephony.CellInfoWcdma
import android.telephony.TelephonyManager
import android.text.TextUtils
import androidx.core.util.Pair
import com.song.deviceinfo.R
import java.net.Inet6Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.Enumeration
import java.util.Locale

/**
 * Created by chensongsong on 2020/4/29.
 */
object GatewayUtils {
    /**
     * 获取内网 IPv6
     *
     * @param name
     * @return
     */
    @JvmStatic
    fun getHostIpv6(name: String?): String {
        var hostIp = ""
        try {
            val nis: Enumeration<*> = NetworkInterface.getNetworkInterfaces()
            var ia: InetAddress? = null
            while (nis.hasMoreElements()) {
                val ni = nis.nextElement() as NetworkInterface
                val ias = ni.inetAddresses
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement()
                    if ((ia is Inet6Address) && TextUtils.equals(name, ni.name) && !ia.isLoopbackAddress()) {
                        var address = ia.getHostAddress().lowercase(Locale.getDefault())
                        if (address.indexOf('%') > -1) {
                            address = address.substring(0, address.indexOf('%'))
                        }
                        hostIp = address
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return hostIp
    }
    
    /**
     * 获取 ipv4 地址
     *
     * @return
     */
    @JvmStatic
    fun getIp(context: Context): Map<String, String> {
        val map: MutableMap<String, String> = HashMap()
        try {
            val vpn = isVpn(context)
            val nis: Enumeration<*> = NetworkInterface.getNetworkInterfaces()
            var ia: InetAddress? = null
            while (nis.hasMoreElements()) {
                val ni = nis.nextElement() as NetworkInterface
                val ias = ni.inetAddresses
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement()
                    if (ia is Inet6Address) {
                        continue
                    }
                    if (vpn) {
                        // vpn ip
                        if (!ia.isLoopbackAddress && !ia.isSiteLocalAddress) {
                            map["vpn"] = if (TextUtils.isEmpty(ia.hostAddress)) Constants.UNKNOWN else ia.hostAddress
                        } else if (!ia.isLoopbackAddress && ia.isSiteLocalAddress) {
                            map["en0"] = if (TextUtils.isEmpty(ia.hostAddress)) Constants.UNKNOWN else ia.hostAddress
                            map["network_name"] = if (TextUtils.isEmpty(ni.name)) Constants.UNKNOWN else ni.name
                        }
                    } else {
                        // vpn 关闭，数据流量
                        if (!ia.isLoopbackAddress) {
                            map["en0"] = if (TextUtils.isEmpty(ia.hostAddress)) Constants.UNKNOWN else ia.hostAddress
                            map["network_name"] = if (TextUtils.isEmpty(ni.name)) Constants.UNKNOWN else ni.name
                            // 内网 ipv6
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return map
    }
    
    /**
     * 获取代理信息
     *
     * @param context
     * @param list
     * @return
     */
    fun getProxyInfo(context: Context, list: MutableList<Pair<String, String>>) {
        val proxyAddress = System.getProperty("http.proxyHost")
        val port = System.getProperty("http.proxyPort")
        val proxyPort = (port ?: "-1").toInt()
        val isProxy = (!TextUtils.isEmpty(proxyAddress) && (proxyPort != -1))
        try {
            if (isVpn(context)) {
                list.add(Pair(context.getString(R.string.net_proxy), "vpn"))
            } else if (isProxy) {
                list.add(Pair(context.getString(R.string.net_proxy), "proxy"))
                list.add(Pair(context.getString(R.string.net_proxy_host), proxyAddress))
                list.add(Pair(context.getString(R.string.net_proxy_port), port))
            } else {
                list.add(Pair(context.getString(R.string.net_proxy), "false"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    private fun isVpn(context: Context): Boolean {
        try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = cm.getNetworkInfo(17)
            return networkInfo!!.isConnected
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
    
    /**
     * 获取mac地址
     *
     * @param context
     * @return
     */
    @JvmStatic
    @SuppressLint("HardwareIds")
    fun getMacAddress(context: Context): String {
        var macAddress = Constants.UNKNOWN
        val wifiInfo = getWifiInfo(context)
        if (wifiInfo != null) {
            macAddress = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mac
            } else {
                wifiInfo.macAddress
            }
        }
        return macAddress
    }
    
    private val mac: String
        /**
         * 通过网卡获取 Mac
         *
         * @return
         */
        get() {
            val mac = Constants.UNKNOWN
            try {
                val nis: Enumeration<*> = NetworkInterface.getNetworkInterfaces()
                while (nis.hasMoreElements()) {
                    val ni = nis.nextElement() as NetworkInterface
                    if ("wlan0" == ni.name) {
                        val hardwareAddress = ni.hardwareAddress
                        if (hardwareAddress == null || hardwareAddress.size == 0) {
                            continue
                        }
                        val sb = StringBuilder()
                        for (b in hardwareAddress) {
                            sb.append(String.format("%02X:", b))
                        }
                        if (sb.length > 0) {
                            sb.deleteCharAt(sb.length - 1)
                        }
                        return sb.toString()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return mac
        }
    
    /**
     * 拿到bssid
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun getBssid(context: Context): String {
        var macAddress = Constants.UNKNOWN
        val wifiInfo = getWifiInfo(context)
        if (wifiInfo != null) {
            val bssid = wifiInfo.bssid
            macAddress = if (TextUtils.isEmpty(bssid)) {
                Constants.UNKNOWN
            } else {
                bssid
            }
        }
        return macAddress
    }
    
    /**
     * 获取广播 ID
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun getSsid(context: Context): String {
        var ssid = Constants.UNKNOWN
        val wifiInfo = getWifiInfo(context)
        if (wifiInfo != null) {
            ssid = wifiInfo.ssid.replace("\"", "")
        }
        return ssid
    }
    
    /**
     * 获取WifiInfo
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun getWifiInfo(context: Context): WifiInfo? {
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        if (wifiManager != null) {
            return wifiManager.connectionInfo
        }
        return null
    }
    
    @JvmStatic
    @SuppressLint("MissingPermission")
    fun getMobileSignal(context: Context): Pair<Int, Int> {
        var dbm = -1
        var level = 0
        try {
            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val cellInfoList: List<CellInfo>?
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (tm == null) {
                    return Pair(dbm, level)
                }
                cellInfoList = tm.allCellInfo
                if (null != cellInfoList) {
                    for (cellInfo in cellInfoList) {
                        if (cellInfo is CellInfoGsm) {
                            val cellSignalStrengthGsm = cellInfo.cellSignalStrength
                            dbm = cellSignalStrengthGsm.dbm
                            level = cellSignalStrengthGsm.level
                        } else if (cellInfo is CellInfoCdma) {
                            val cellSignalStrengthCdma =
                                cellInfo.cellSignalStrength
                            dbm = cellSignalStrengthCdma.dbm
                            level = cellSignalStrengthCdma.level
                        } else if (cellInfo is CellInfoLte) {
                            val cellSignalStrengthLte = cellInfo.cellSignalStrength
                            dbm = cellSignalStrengthLte.dbm
                            level = cellSignalStrengthLte.level
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                            if (cellInfo is CellInfoWcdma) {
                                val cellSignalStrengthWcdma =
                                    cellInfo.cellSignalStrength
                                dbm = cellSignalStrengthWcdma.dbm
                                level = cellSignalStrengthWcdma.level
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Pair(dbm, level)
    }
}
