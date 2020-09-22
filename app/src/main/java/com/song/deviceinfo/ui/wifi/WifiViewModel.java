package com.song.deviceinfo.ui.wifi;

import android.content.Context;

import com.song.deviceinfo.info.WifiInfo;
import com.song.deviceinfo.ui.base.NormalViewModel;

import java.util.List;

import androidx.core.util.Pair;

/**
 * Created by chensongsong on 2020/9/22.
 */
public class WifiViewModel extends NormalViewModel {

    public List<Pair<String, String>> getWifiInfo(Context context) {
        return WifiInfo.getWifiInfo(context);
    }

}
