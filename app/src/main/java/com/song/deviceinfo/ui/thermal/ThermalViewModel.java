package com.song.deviceinfo.ui.thermal;

import com.song.deviceinfo.info.ThermalInfo;
import com.song.deviceinfo.ui.base.NormalViewModel;

import java.util.List;

import androidx.core.util.Pair;

public class ThermalViewModel extends NormalViewModel<Pair<String, String>> {

    @Override
    public List<Pair<String, String>> getNormalInfo() {
        return ThermalInfo.getThermalInfo();
    }
}
