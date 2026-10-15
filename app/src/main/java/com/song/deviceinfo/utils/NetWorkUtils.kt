package com.song.deviceinfo.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.telephony.TelephonyManager
import android.text.TextUtils
import androidx.core.util.Pair

/**
 * Created by chensongsong on 2020/5/25.
 */
object NetWorkUtils {
    /**
     * 判断是否有网络连接，并不代表可以数据访问
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun isNetworkConnected(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.activeNetworkInfo
        return networkInfo != null && networkInfo.isAvailable
    }
    
    /**
     * 数据流量是否打开
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun isMobileEnabled(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        try {
            val cmClass = Class.forName(cm.javaClass.name)
            val method = cmClass.getDeclaredMethod("getMobileDataEnabled")
            method.isAccessible = true
            return method.invoke(cm) as Boolean
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
    
    /**
     * 判断当前网络是否可以数据访问(测试不可靠)
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun isNetSystemUsable(context: Context): Boolean {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val networkCapabilities = manager.getNetworkCapabilities(manager.activeNetwork)
                return networkCapabilities!!.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            } else {
                val runtime = Runtime.getRuntime()
                val process = runtime.exec("ping -c 3 www.baidu.com")
                return 0 == process.waitFor()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
    
    /**
     * 判断是否是 WIFI 网络
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun isWifi(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (manager != null) {
            val networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            return networkInfo != null && networkInfo.isConnectedOrConnecting
        }
        return false
    }
    
    /**
     * 判断当前网络详细类型
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun getNetWorkType(context: Context): String {
        if (!isNetworkConnected(context)) {
            return "NONE"
        }
        if (isWifi(context)) {
            return "WIFI"
        }
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (telephonyManager != null) {
            val networkType = telephonyManager.networkType
            when (networkType) {
                TelephonyManager.NETWORK_TYPE_GPRS -> return "GPRS"
                TelephonyManager.NETWORK_TYPE_EDGE -> return "EDGE"
                TelephonyManager.NETWORK_TYPE_CDMA -> return "CDMA"
                TelephonyManager.NETWORK_TYPE_1xRTT -> return "1xRTT"
                TelephonyManager.NETWORK_TYPE_IDEN -> return "IDEN"
                TelephonyManager.NETWORK_TYPE_UMTS -> return "UMTS"
                TelephonyManager.NETWORK_TYPE_EVDO_0 -> return "EVDO_0"
                TelephonyManager.NETWORK_TYPE_EVDO_A -> return "EVDO_A"
                TelephonyManager.NETWORK_TYPE_HSDPA -> return "HSDPA"
                TelephonyManager.NETWORK_TYPE_HSUPA -> return "HSUPA"
                TelephonyManager.NETWORK_TYPE_HSPA -> return "HSPA"
                TelephonyManager.NETWORK_TYPE_EVDO_B -> return "EVDO_B"
                TelephonyManager.NETWORK_TYPE_EHRPD -> return "EHRPD"
                TelephonyManager.NETWORK_TYPE_HSPAP -> return "HSPAP"
                TelephonyManager.NETWORK_TYPE_LTE -> return "LTE"
                TelephonyManager.NETWORK_TYPE_NR -> return "5G"
                else -> {}
            }
        }
        return "NONE"
    }
    
    /**
     * 网络信息
     *
     * @param context
     * @return
     */
    fun getNetWorkInfo(context: Context, list: MutableList<Pair<String, String>>) {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.activeNetworkInfo
        if (networkInfo != null) {
            list.add(Pair("NET Type Name", networkInfo.typeName))
            val subName = networkInfo.subtypeName
            if (!TextUtils.isEmpty(subName)) {
                list.add(Pair("NET SUB NAME", subName))
            }
            list.add(Pair("NET NAME", networkInfo.extraInfo))
        }
    }
}
