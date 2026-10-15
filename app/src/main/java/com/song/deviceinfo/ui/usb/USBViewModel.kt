package com.song.deviceinfo.ui.usb

import android.content.Context
import androidx.core.util.Pair
import com.song.deviceinfo.info.USBInfo
import com.song.deviceinfo.ui.base.NormalViewModel

/**
 * Created by chensongsong on 2020/6/8.
 */
class USBViewModel : NormalViewModel() {
    fun getUsbInfo(context: Context?): List<Pair<String, String>> {
        return USBInfo.getUSBInfo(context!!)
    }
}
