package com.song.deviceinfo.ui.emulator;

import android.content.Context;

import com.song.deviceinfo.info.EmulatorInfo;
import com.song.deviceinfo.ui.base.NormalViewModel;

import java.util.List;

import androidx.core.util.Pair;

/**
 * Created by chensongsong on 2020/7/9.
 */
public class EmulatorViewModel extends NormalViewModel {

    public List<Pair<String, String>> getEmulatorInfo(Context context) {
        return EmulatorInfo.getEmulatorInfo(context);
    }

}
