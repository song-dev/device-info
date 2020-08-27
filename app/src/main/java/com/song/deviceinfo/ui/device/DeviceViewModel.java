package com.song.deviceinfo.ui.device;

import android.content.Context;

import com.song.deviceinfo.info.DeviceInfo;
import com.song.deviceinfo.ui.base.NormalViewModel;

import java.util.List;

import androidx.core.util.Pair;

/**
 * Created by chensongsong on 2020/8/3.
 */
public class DeviceViewModel extends NormalViewModel {

    public List<Pair<String, String>> getDeviceInfo(Context context) {
        return DeviceInfo.getDeviceInfo(context);
    }

}
