package com.song.deviceinfo.ui.app;

import android.content.Context;

import com.song.deviceinfo.info.AppInfo;
import com.song.deviceinfo.ui.base.NormalViewModel;

import java.util.List;

import androidx.core.util.Pair;

/**
 * Created by chensongsong on 2020/9/22.
 */
public class AppViewModel extends NormalViewModel {

    public List<Pair<String, String>> getAppInfo(Context context) {
        return AppInfo.getAppInfo(context);
    }

}
