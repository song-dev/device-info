package com.song.deviceinfo.ui.thermal;

import com.song.deviceinfo.info.ThermalInfo;
import com.song.deviceinfo.ui.base.NormalViewModel;

import java.util.List;

import androidx.core.util.Pair;

/**
 * Created by chensongsong on 2020/5/27.
 */
public class ThermalViewModel extends NormalViewModel {

    public List<Pair<String, String>> getThermalInfo() {
        return ThermalInfo.getThermalInfo();
    }
}
