package com.song.deviceinfo.info;

import android.content.Context;

import com.song.deviceinfo.utils.HookUtils;
import com.song.deviceinfo.utils.XposedHookUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.core.util.Pair;

/**
 * Created by chensongsong on 2020/7/16.
 */
public class HookInfo {

    public static List<Pair<String, String>> getHookInfo(Context context) {
        List<Pair<String, String>> list = new ArrayList<>();
        list.add(new Pair<>("Process", HookUtils.checkRunningProcesses(context) + ""));
        list.add(new Pair<>("XposedHookMethod", HookUtils.chargeXposedHookMethod()));
        list.add(new Pair<>("XposedJars", HookUtils.chargeXposedJars()));
        list.add(new Pair<>("XposedPackage", HookUtils.chargeXposedPackage(context)));
        list.add(new Pair<>("XposedInject", XposedHookUtils.checkXposedInjet(context).toString()));
        list.add(new Pair<>("XposedClass", HookUtils.classCheck() + ""));
        return list;
    }

}
