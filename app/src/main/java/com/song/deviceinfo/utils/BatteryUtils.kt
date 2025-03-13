package com.song.deviceinfo.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import androidx.core.util.Pair
import com.song.deviceinfo.R

/**
 * Created by chensongsong on 2020/5/27.
 */
object BatteryUtils {
    /**
     * 获取电池信息
     *
     * @param context
     * @param list
     */
    fun getBatteryInfo(context: Context, list: MutableList<Pair<String, String>>) {
        try {
            val batteryStatus = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            if (batteryStatus != null) {
                val level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                val scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                var batteryLevel = -1.0
                if (level != -1 && scale != -1) {
                    batteryLevel = level.toDouble() / scale.toDouble()
                }
                // unknown=1, charging=2, discharging=3, not charging=4, full=5
                val status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
                // ac=1, usb=2, wireless=4
                val plugState = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)
                // unknown=1, good=2, overheat=3, dead=4, over voltage=5, unspecified failure=6, cold=7
                val health = batteryStatus.getIntExtra(BatteryManager.EXTRA_HEALTH, -1)
                val present = batteryStatus.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false)
                val technology = batteryStatus.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY) ?: "Unknown"
                val temperature = batteryStatus.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1)
                val voltage = batteryStatus.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1)
                list.add(Pair(context.getString(R.string.battery_level), "${DecimalUtils.mul(batteryLevel, 100.0)}%"))
                list.add(Pair(context.getString(R.string.battery_health), batteryHealth(health)))
                list.add(Pair(context.getString(R.string.battery_status), batteryStatus(status)))
                list.add(Pair(context.getString(R.string.battery_power_source), batteryPlugged(plugState)))
                list.add(Pair(context.getString(R.string.battery_technology), technology))
                list.add(Pair(context.getString(R.string.battery_present), present.toString()))
                list.add(Pair(context.getString(R.string.battery_temperature), "${temperature / 10}℃"))
                list.add(Pair(context.getString(R.string.battery_voltage), 
                    if (voltage > 1000) "${voltage / 1000f}V" else "${voltage}V"))
                list.add(Pair(context.getString(R.string.battery_average), getAverageCurrent(context).toString()))
                list.add(Pair(context.getString(R.string.battery_power_profile), getBatteryCapacity(context)))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    private fun getAverageCurrent(context: Context): Int {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
                batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE)
            } else {
                0
            }
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }
    
    /**
     * 获取电池容量
     *
     * @param context
     * @return
     */
    @SuppressLint("PrivateApi")
    fun getBatteryCapacity(context: Context?): String {
        var batteryCapacity = 0.0
        try {
            val powerProfileClass = "com.android.internal.os.PowerProfile"
            val mPowerProfile = Class.forName(powerProfileClass)
                .getConstructor(Context::class.java)
                .newInstance(context)
            batteryCapacity = Class.forName(powerProfileClass)
                .getMethod("getBatteryCapacity")
                .invoke(mPowerProfile) as Double
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "$batteryCapacity mAh"
    }
    
    /**
     * 健康情况
     *
     * @param status
     * @return
     */
    private fun batteryHealth(status: Int): String = when (status) {
        BatteryManager.BATTERY_HEALTH_COLD -> "Cold"
        BatteryManager.BATTERY_HEALTH_DEAD -> "Dead"
        BatteryManager.BATTERY_HEALTH_GOOD -> "Good"
        BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> "OverVoltage"
        BatteryManager.BATTERY_HEALTH_OVERHEAT -> "Overheat"
        BatteryManager.BATTERY_HEALTH_UNKNOWN -> "Unknown"
        BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE -> "Unspecified"
        else -> Constants.UNKNOWN
    }
    
    /**
     * 充电状态
     *
     * @param status
     * @return
     */
    private fun batteryStatus(status: Int): String = when (status) {
        BatteryManager.BATTERY_STATUS_CHARGING -> "Charging"
        BatteryManager.BATTERY_STATUS_DISCHARGING -> "DisCharging"
        BatteryManager.BATTERY_STATUS_FULL -> "Full"
        BatteryManager.BATTERY_STATUS_NOT_CHARGING -> "NotCharging"
        BatteryManager.BATTERY_STATUS_UNKNOWN -> "Unknown"
        else -> Constants.UNKNOWN
    }
    
    /**
     * 电源
     *
     * @param status
     * @return
     */
    private fun batteryPlugged(status: Int): String = when (status) {
        BatteryManager.BATTERY_PLUGGED_AC -> "AC"
        BatteryManager.BATTERY_PLUGGED_USB -> "USB"
        BatteryManager.BATTERY_PLUGGED_WIRELESS -> "Wireless"
        else -> Constants.UNKNOWN
    }
}
