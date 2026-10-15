package com.song.deviceinfo.utils

import android.content.Context
import android.content.pm.ApplicationInfo
import android.location.Criteria
import android.location.LocationManager
import android.location.LocationProvider
import android.os.Build
import android.os.Debug
import android.provider.Settings

/**
 * Created by chensongsong on 2020/7/14.
 */
object DebugUtils {
    /**
     * 是否开启debug模式
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun isOpenDebug(context: Context): Boolean {
        try {
            return (Settings.Secure.getInt(context.contentResolver, Settings.Secure.ADB_ENABLED, 0) > 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
    
    /**
     * APP 是否是 debug 版本
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun isDebugVersion(context: Context): Boolean {
        try {
            return (context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
    
    @JvmStatic val isDebugConnected: Boolean
        /**
         * 是否正在调试
         *
         * @return
         */
        get() {
            try {
                return Debug.isDebuggerConnected()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return false
        }
    
    @JvmStatic val usbDebugStatus: String?
        /**
         * 读取当前 USB 调试状态
         *
         * @return
         */
        get() = CommandUtils.execute("getprop init.svc.adbd")
    
    /**
     * 判断是否打开了允许虚拟位置
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun isAllowMockLocation(context: Context): Boolean {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            try {
                val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val providerStr = LocationManager.GPS_PROVIDER
                val provider = locationManager.getProvider(providerStr)
                if (provider != null) {
                    locationManager.addTestProvider(
                            provider.name,
                            provider.requiresNetwork(),
                            provider.requiresSatellite(),
                            provider.requiresCell(),
                            provider.hasMonetaryCost(),
                            provider.supportsAltitude(),
                            provider.supportsSpeed(),
                            provider.supportsBearing(),
                            provider.powerRequirement,
                            provider.accuracy)
                } else {
                    locationManager.addTestProvider(
                            providerStr,
                            true, true, false, false, true, true, true,
                            Criteria.POWER_HIGH, Criteria.ACCURACY_FINE)
                }
                locationManager.setTestProviderEnabled(providerStr, true)
                locationManager.setTestProviderStatus(providerStr, LocationProvider.AVAILABLE, null, System.currentTimeMillis())
                // 模拟位置可用
                return true
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
        } else {
            try {
                return Settings.Secure.getInt(context.contentResolver, Settings.Secure.ALLOW_MOCK_LOCATION, 0) != 0
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return false
    }
    
    @JvmStatic val tracerPid: Int
        external get
}