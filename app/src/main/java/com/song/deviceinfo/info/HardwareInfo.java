package com.song.deviceinfo.info;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import java.util.ArrayList;
import java.util.List;

import androidx.core.util.Pair;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by chensongsong on 2020/9/22.
 */
public class HardwareInfo {

    public static List<Pair<String, String>> getHardwareInfo(Context context) {
        List<Pair<String, String>> list = new ArrayList<>();
        SensorManager sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        Sensor gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        list.add(new Pair<>("GYROSCOPE", gyroscope.getName()));
        Sensor magnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        list.add(new Pair<>("MAGNETIC", magnetic.getName()));
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        list.add(new Pair<>("ACCELEROMETER", accelerometer.getName()));
        return list;
    }


}
