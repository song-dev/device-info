package com.song.deviceinfo.ui.virtual;

import android.content.Context;

import com.song.deviceinfo.info.VirtualAppInfo;
import com.song.deviceinfo.ui.base.NormalViewModel;

import java.util.List;

import androidx.core.util.Pair;

/**
 * Created by chensongsong on 2020/7/10.
 */
public class VirtualAppViewModel extends NormalViewModel {

    public List<Pair<String, String>> getVirtualAppInfo(Context context) {
        return VirtualAppInfo.getVirtualAppInfo(context);
    }

}
