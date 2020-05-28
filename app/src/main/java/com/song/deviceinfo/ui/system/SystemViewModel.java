package com.song.deviceinfo.ui.system;

import android.app.Activity;

import com.song.deviceinfo.info.SystemInfo;
import com.song.deviceinfo.ui.base.NormalViewModel;

import java.util.List;

import androidx.core.util.Pair;

/**
 * Created by chensongsong on 2020/5/27.
 */
public class SystemViewModel extends NormalViewModel {

    public List<Pair<String, String>> getSystemInfo(Activity activity) {
        return SystemInfo.getSystemInfo(activity);
    }
}