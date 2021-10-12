package com.song.deviceinfo.info;

import android.content.Context;
import android.text.TextUtils;

import com.song.deviceinfo.utils.CommandUtils;
import com.song.deviceinfo.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import androidx.core.util.Pair;

/**
 * Created by chensongsong on 2021/9/9.
 */
public class BuildInfo {

    public static List<Pair<String, String>> getBuildInfo(Context context) {
        List<Pair<String, String>> list = new ArrayList<>();
        String[] array = CommandUtils.exec("getprop");
        for (String item : array) {
            if (!TextUtils.isEmpty(item)) {
                try {
                    String[] split = item.split(":");
                    if (split.length == 1) {
                        list.add(new Pair<>(split[0].trim(), Constants.UNKNOWN));
                    } else if (split.length == 2) {
                        list.add(new Pair<>(split[0].trim(), split[1].trim()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

}
