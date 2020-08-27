package com.song.deviceinfo.ui.debug;

import android.content.Context;

import com.song.deviceinfo.info.DebugInfo;
import com.song.deviceinfo.ui.base.NormalViewModel;

import java.util.List;

import androidx.core.util.Pair;

/**
 * Created by chensongsong on 2020/7/14.
 */
public class DebugViewModel extends NormalViewModel {

    public List<Pair<String, String>> getDebugInfo(Context context) {
        return DebugInfo.getDebugInfo(context);
    }

}
