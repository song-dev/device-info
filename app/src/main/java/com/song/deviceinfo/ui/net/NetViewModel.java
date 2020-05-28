package com.song.deviceinfo.ui.net;

import android.content.Context;

import com.song.deviceinfo.info.NetWorkInfo;
import com.song.deviceinfo.ui.base.NormalViewModel;

import java.util.List;

import androidx.core.util.Pair;

public class NetViewModel extends NormalViewModel {

    public List<Pair<String, String>> getNetWorkInfo(Context context) {
        return NetWorkInfo.getNetWorkInfo(context);
    }
}