package com.song.deviceinfo.info

import android.content.Context
import androidx.core.util.Pair
import com.song.deviceinfo.utils.DebugUtils.isAllowMockLocation
import com.song.deviceinfo.utils.DebugUtils.isDebugConnected
import com.song.deviceinfo.utils.DebugUtils.isDebugVersion
import com.song.deviceinfo.utils.DebugUtils.isOpenDebug
import com.song.deviceinfo.utils.DebugUtils.tracerPid
import com.song.deviceinfo.utils.DebugUtils.usbDebugStatus

/**
 * Created by chensongsong on 2020/7/14.
 */
object DebugInfo {
    fun getDebugInfo(context: Context): List<Pair<String, String>> {
        val list: MutableList<Pair<String, String>> = ArrayList()
        list.add(Pair("DebugOpen", isOpenDebug(context).toString() + ""))
        list.add(Pair("UsbDebugStatus", usbDebugStatus))
        list.add(Pair("TracerPid", tracerPid.toString() + ""))
        list.add(Pair("DebugVersion", isDebugVersion(context).toString() + ""))
        list.add(Pair("DebugConnected", isDebugConnected.toString() + ""))
        list.add(Pair("AllowMockLocation", isAllowMockLocation(context).toString() + ""))
        return list
    }
}
