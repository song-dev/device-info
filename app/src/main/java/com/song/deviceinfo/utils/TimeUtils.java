package com.song.deviceinfo.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by chensongsong on 2020/9/22.
 */
public class TimeUtils {

    @SuppressLint("SimpleDateFormat")
    public static String formatDate(long time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time));
    }

    private void test(){
        Calendar calendar = Calendar.getInstance();
    }

}
