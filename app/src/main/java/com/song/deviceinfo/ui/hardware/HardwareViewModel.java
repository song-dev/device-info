package com.song.deviceinfo.ui.hardware;

import android.content.Context;

import com.song.deviceinfo.info.HardwareInfo;
import com.song.deviceinfo.ui.base.NormalViewModel;

import java.util.List;

import androidx.core.util.Pair;

/**
 * Created by chensongsong on 2021/9/29.
 */
public class HardwareViewModel extends NormalViewModel {

    public List<Pair<String, String>> getHardwareInfo(Context context) {
        return HardwareInfo.getHardwareInfo(context);
    }

}
