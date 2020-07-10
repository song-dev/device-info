package com.song.deviceinfo.info;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import androidx.core.util.Pair;

/**
 * Created by chensongsong on 2020/7/9.
 */
public class VirtualAppInfo {

    /**
     * 获取应用列表
     *
     * @param context
     * @return
     */
    public static List<Pair<String, String>> getVirtualAppInfo(Context context) {
        List<Pair<String, String>> list = new ArrayList<>();
        list.add(new Pair<>("FilesDir", context.getFilesDir().getAbsolutePath()));
        list.add(new Pair<>("Permission", moreOpenCheck() + ""));
        return list;
    }

    /**
     * 0. 多开检测 false
     * 1. 多开检测 true
     * 2. 检测失败（$unknown）
     * 检测多开, 若可访问规定目录则为正常，否则为多开环境
     *
     * @return
     */
    public static native int moreOpenCheck();

}
