package com.song.deviceinfo.info;

import android.content.Context;
import android.os.Build;

import com.song.deviceinfo.R;

import java.util.ArrayList;
import java.util.List;

import androidx.core.util.Pair;

/**
 * Created by chensongsong on 2020/5/27.
 */
public class SystemInfo {

    public static List<Pair<String, String>> getSystemInfo(Context context) {
        ArrayList<Pair<String, String>> list = new ArrayList<>();

        list.add(new Pair<>(context.getString(R.string.system_manufacture), Build.MANUFACTURER));
        list.add(new Pair<>(context.getString(R.string.system_model), Build.MODEL));
        list.add(new Pair<>(context.getString(R.string.system_brand), Build.BRAND));
        list.add(new Pair<>(context.getString(R.string.system_release), Build.VERSION.RELEASE));
        list.add(new Pair<>(context.getString(R.string.system_api), Build.VERSION.SDK_INT + ""));
        list.add(new Pair<>(context.getString(R.string.system_code_name), Build.VERSION.CODENAME));

        return list;
    }

}
