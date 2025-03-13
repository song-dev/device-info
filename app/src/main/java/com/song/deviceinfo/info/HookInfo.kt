package com.song.deviceinfo.info

import android.content.Context
import androidx.core.util.Pair
import com.song.deviceinfo.utils.HookUtils.chargeXposedHookMethod
import com.song.deviceinfo.utils.HookUtils.chargeXposedJars
import com.song.deviceinfo.utils.HookUtils.chargeXposedPackage
import com.song.deviceinfo.utils.HookUtils.checkRunningProcesses
import com.song.deviceinfo.utils.HookUtils.classCheck
import com.song.deviceinfo.utils.XposedHookUtils.checkXposedInjet

/**
 * Created by chensongsong on 2020/7/16.
 */
object HookInfo {
    fun getHookInfo(context: Context): List<Pair<String, String>> {
        val list: MutableList<Pair<String, String>> = ArrayList()
        list.add(Pair("Process", checkRunningProcesses(context).toString() + ""))
        list.add(Pair("XposedHookMethod", chargeXposedHookMethod()))
        list.add(Pair("XposedJars", chargeXposedJars()))
        list.add(Pair("XposedPackage", chargeXposedPackage(context)))
        list.add(Pair("XposedInject", checkXposedInjet(context).toString()))
        list.add(Pair("XposedClass", classCheck().toString() + ""))
        return list
    }
}
