package com.song.deviceinfo.info

import android.content.Context
import androidx.core.util.Pair
import com.google.firebase.perf.metrics.AddTrace
import com.song.deviceinfo.utils.DeviceUtils.getAndroidId
import com.song.deviceinfo.utils.DeviceUtils.getDeviceInfo
import com.song.deviceinfo.utils.DeviceUtils.getIMEI
import com.song.deviceinfo.utils.DeviceUtils.getIccId
import com.song.deviceinfo.utils.DeviceUtils.getOtherInfo
import com.song.deviceinfo.utils.DeviceUtils.getSimInfo

/**
 * Created by chensongsong on 2020/8/3.
 */
object DeviceInfo {
    @AddTrace(name = "DeviceInfo.getDeviceInfo")
    fun getDeviceInfo(context: Context): List<Pair<String, String>> {
        val list: MutableList<Pair<String, String>> = ArrayList()
        list.add(Pair("AndroidId", getAndroidId(context)))
        list.add(Pair("IMEI", getIMEI(context)))
        getDeviceInfo(context, list)
        list.add(Pair("ICCID", getIccId(context)))
        getSimInfo(context, list)
        getOtherInfo(context, list)
        return list
    }
}
