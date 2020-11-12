package com.song.deviceinfo.ui.others;

import android.content.Context;

import com.song.deviceinfo.info.OthersInfo;
import com.song.deviceinfo.ui.base.NormalViewModel;

import java.util.List;

import androidx.core.util.Pair;

/**
 * Created by chensongsong on 2020/9/22.
 */
public class OthersViewModel extends NormalViewModel {

    public List<Pair<String, String>> getOthersInfo(Context context) {
        return OthersInfo.getOthersInfo(context);
    }

}
