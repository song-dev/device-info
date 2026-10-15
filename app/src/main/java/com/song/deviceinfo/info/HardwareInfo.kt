package com.song.deviceinfo.info

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.core.util.Pair

/**
 * Created by chensongsong on 2020/9/22.
 */
object HardwareInfo {
    fun getHardwareInfo(context: Context): List<Pair<String, String>> {
        val list: MutableList<Pair<String, String>> = ArrayList()
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        list.add(Pair("GYROSCOPE", gyroscope!!.name))
        val magnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        list.add(Pair("MAGNETIC", magnetic!!.name))
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        list.add(Pair("ACCELEROMETER", accelerometer!!.name))
        return list
    }
}
