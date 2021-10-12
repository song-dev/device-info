package com.song.deviceinfo.ui.build;

import android.content.Context;

import com.song.deviceinfo.info.BuildInfo;
import com.song.deviceinfo.ui.base.NormalViewModel;

import java.util.List;

import androidx.core.util.Pair;

/**
 * Created by chensongsong on 2021/9/9.
 */
public class BuildInfoViewModel extends NormalViewModel {

    public List<Pair<String, String>> getBuildInfo(Context context) {
        return BuildInfo.getBuildInfo(context);
    }
}
