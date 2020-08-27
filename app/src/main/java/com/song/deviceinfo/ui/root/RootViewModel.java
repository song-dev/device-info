package com.song.deviceinfo.ui.root;

import android.content.Context;

import com.song.deviceinfo.info.RootInfo;
import com.song.deviceinfo.ui.base.NormalViewModel;

import java.util.List;

import androidx.core.util.Pair;

/**
 * Created by chensongsong on 2020/7/15.
 */
public class RootViewModel extends NormalViewModel {

    public List<Pair<String, String>> getRootInfo(Context context) {
        return RootInfo.getRootInfo(context);
    }

}
