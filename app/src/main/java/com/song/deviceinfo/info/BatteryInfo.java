package com.song.deviceinfo.info;

import android.content.Context;

import com.song.deviceinfo.utils.BatteryUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.core.util.Pair;

/**
 * @author 谷闹年
 * @date 2018/1/5
 */
public class BatteryInfo {

    /**
     * 获取电池信息
     *
     * @return 电池JSON
     */
    public static List<Pair<String, String>> getBatteryInfo(Context context) {
        ArrayList<Pair<String, String>> list = new ArrayList<>();
        BatteryUtils.getBatteryInfo(context, list);
        return list;
    }

}
