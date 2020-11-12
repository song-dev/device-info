package com.song.deviceinfo.info;

import android.content.Context;

import com.google.firebase.perf.metrics.AddTrace;
import com.song.deviceinfo.utils.DeviceUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.core.util.Pair;

/**
 * Created by chensongsong on 2020/8/3.
 */
public class DeviceInfo {

    @AddTrace(name = "DeviceInfo.getDeviceInfo")
    public static List<Pair<String, String>> getDeviceInfo(Context context) {
        List<Pair<String, String>> list = new ArrayList<>();
        list.add(new Pair<>("AndroidId", DeviceUtils.getAndroidId(context)));
        list.add(new Pair<>("IMEI", DeviceUtils.getIMEI(context)));
        DeviceUtils.getDeviceInfo(context,list);
        list.add(new Pair<>("ICCID", DeviceUtils.getIccId(context)));
        DeviceUtils.getSimInfo(context,list);
        DeviceUtils.getOtherInfo(context,list);
        return list;
    }

}
