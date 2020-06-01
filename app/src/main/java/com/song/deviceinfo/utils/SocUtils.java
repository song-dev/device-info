package com.song.deviceinfo.utils;

import android.text.TextUtils;

/**
 * Created by chensongsong on 2020/6/1.
 */
public class SocUtils {

    /**
     * 读取 SOC 型号信息
     *
     * @return
     */
    public static String getSocInfo() {
        String socStr = "";
        socStr = CommandUtils.execute("getprop ro.board.platform");
        if (TextUtils.isEmpty(socStr)) {
            socStr = CommandUtils.execute("getprop ro.hardware");
            if (TextUtils.isEmpty(socStr)) {
                socStr = CommandUtils.execute("getprop ro.boot.hardware");
            }
        }
        return socStr;
    }

}
