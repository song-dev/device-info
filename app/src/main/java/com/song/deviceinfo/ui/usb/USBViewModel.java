package com.song.deviceinfo.ui.usb;

import android.content.Context;

import com.song.deviceinfo.info.USBInfo;
import com.song.deviceinfo.ui.base.NormalViewModel;

import java.util.List;

import androidx.core.util.Pair;

/**
 * Created by chensongsong on 2020/6/8.
 */
public class USBViewModel extends NormalViewModel {

    public List<Pair<String, String>> getUsbInfo(Context context) {
        return USBInfo.getUSBInfo(context);
    }
}
