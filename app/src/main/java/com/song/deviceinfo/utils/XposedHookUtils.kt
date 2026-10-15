package com.song.deviceinfo.utils

import android.content.Context
import android.content.pm.PackageInfo
import com.song.deviceinfo.model.beans.XposedHookBean
import org.json.JSONObject
import java.util.Locale

/**
 * Created by chensongsong on 2020/8/3.
 */
object XposedHookUtils {
    /**
     * 检测是否有需要的关键字
     * 如果有 则表示APP被劫持
     * 当发现有Xposed工具时，下面数据显示为：
     * "xposedInfo":{//Xposed的详细信息
     * "xposedApp"："flase",//是否hook了本APP
     * "xposedImei"："flase",//是否hook了IMEI号
     * "xposedSerial"："flase",//是否hook了序列号
     * "xposedSsid"："flase",//是否hook了SSID
     * "xposedMac"："flase",//是否hook了本MAC地址
     * "xposedAddress"："flase",//是否hook了蓝牙地址
     * "xposedAndroidId"："flase",//是否hook了AndroidId
     * "xposedImsi"："flase",//是否hook了IMSI
     * "xposedLatitude"："flase",//是否hook了纬度
     * "xposedLongitude"："flase"//是否hook了经度
     * }
     *
     * @return
     */
    @JvmStatic
    fun checkXposedInjet(context: Context): JSONObject {
        val jsonObject = JSONObject()
        val xposedHookBean = XposedHookBean()
        val packageName = getPackageName(context)
        try {
            val classLoader = ClassLoader.getSystemClassLoader()
            val clsXposedHtlpers = classLoader.loadClass("de.robv.android.xposed.XposedHelpers").newInstance()
            checkKeyWordInFiled(clsXposedHtlpers, "fieldCache", xposedHookBean, packageName!!)
            checkKeyWordInFiled(clsXposedHtlpers, "methodCache", xposedHookBean, packageName)
            checkKeyWordInFiled(clsXposedHtlpers, "constructorCache", xposedHookBean, packageName)
            jsonObject.put("xposedApp", xposedHookBean.isXposedApp.toString() + "")
            jsonObject.put("xposedImei", xposedHookBean.isXposedImei.toString() + "")
            jsonObject.put("xposedSerial", xposedHookBean.isXposedSerial.toString() + "")
            jsonObject.put("xposedSsid", xposedHookBean.isXposedSsid.toString() + "")
            jsonObject.put("xposedMac", xposedHookBean.isXposedMac.toString() + "")
            jsonObject.put("xposedAddress", xposedHookBean.isXposedAddress.toString() + "")
            jsonObject.put("xposedAndroidId", xposedHookBean.isXposedAndroidId.toString() + "")
            jsonObject.put("xposedImsi", xposedHookBean.isXposedImsi.toString() + "")
            jsonObject.put("xposedLatitude", xposedHookBean.isXposedLatitude.toString() + "")
            jsonObject.put("xposedLongitude", xposedHookBean.isXposedLongitude.toString() + "")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return jsonObject
    }
    
    /**
     * 关键字 只是表明hook了这些函数 并不能代表使用
     * getDeviceId 修改IMEI号
     * SERIAL  修改序列号
     * getSSID 修改WIFI名称
     * getMacAddress 修改 WIFI MAC地址
     * BluetoothAdapter#getAddress 修改蓝牙MAC地址
     * Secure#getString 修改androidId
     * getSubscriberId 修改IMSI
     * getLatitude 纬度
     * getLongitude 经度
     *
     * @param cls
     * @param fieldName
     * @return
     */
    private fun checkKeyWordInFiled(cls: Any, fieldName: String, xposedHookBean: XposedHookBean, packageName: String): Boolean {
        val map: Map<*, *>
        try {
            val field = cls.javaClass.getDeclaredField(fieldName)
            field.isAccessible = true
            map = field[cls] as Map<*, *>
            if (!map.isEmpty()) {
                for (aKeySet in map.keys) {
                    if (aKeySet.toString().contains(packageName)) {
                        xposedHookBean.isXposedApp = true
                    }
                    if (aKeySet.toString().lowercase(Locale.getDefault()).contains("getDeviceId".lowercase(Locale.getDefault()))) {
                        xposedHookBean.isXposedImei = true
                    }
                    if (aKeySet.toString().lowercase(Locale.getDefault()).contains("SERIAL".lowercase(Locale.getDefault()))) {
                        xposedHookBean.isXposedSerial = true
                    }
                    if (aKeySet.toString().lowercase(Locale.getDefault()).contains("getSSID".lowercase(Locale.getDefault()))) {
                        xposedHookBean.isXposedSsid = true
                    }
                    if (aKeySet.toString().lowercase(Locale.getDefault()).contains("getMacAddress".lowercase(Locale.getDefault()))) {
                        xposedHookBean.isXposedMac = true
                    }
                    if (aKeySet.toString().lowercase(Locale.getDefault()).contains("BluetoothAdapter#getAddress".lowercase(Locale.getDefault()))) {
                        xposedHookBean.isXposedAddress = true
                    }
                    if (aKeySet.toString().lowercase(Locale.getDefault()).contains("Secure#getString".lowercase(Locale.getDefault()))) {
                        xposedHookBean.isXposedAndroidId = true
                    }
                    if (aKeySet.toString().lowercase(Locale.getDefault()).contains("getSubscriberId".lowercase(Locale.getDefault()))) {
                        xposedHookBean.isXposedImsi = true
                    }
                    if (aKeySet.toString().lowercase(Locale.getDefault()).contains("getLatitude".lowercase(Locale.getDefault()))) {
                        xposedHookBean.isXposedLatitude = true
                    }
                    if (aKeySet.toString().lowercase(Locale.getDefault()).contains("getLongitude".lowercase(Locale.getDefault()))) {
                        xposedHookBean.isXposedLongitude = true
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
    
    private fun getPackageName(context: Context): String? {
        val packageInfo: PackageInfo
        val packageManager = context.packageManager
        try {
            packageInfo = packageManager.getPackageInfo(context.packageName, 0)
            if (packageInfo != null) {
                return packageInfo.packageName
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}
