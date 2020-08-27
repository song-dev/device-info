package com.song.deviceinfo.ui.hook;

import android.content.Context;

import com.song.deviceinfo.info.HookInfo;
import com.song.deviceinfo.ui.base.NormalViewModel;

import java.util.List;

import androidx.core.util.Pair;

/**
 * Created by chensongsong on 2020/7/16.
 */
public class HookViewModel extends NormalViewModel {

    public List<Pair<String, String>> getHookInfo(Context context) {
        return HookInfo.getHookInfo(context);
    }

}
