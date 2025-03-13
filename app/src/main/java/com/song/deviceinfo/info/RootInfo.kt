package com.song.deviceinfo.info

import android.content.Context
import androidx.core.util.Pair
import com.song.deviceinfo.utils.CommandUtils.execute
import com.song.deviceinfo.utils.RootUtils.existingDangerousProperties
import com.song.deviceinfo.utils.RootUtils.existingRWPaths
import com.song.deviceinfo.utils.RootUtils.existingRootFiles
import com.song.deviceinfo.utils.RootUtils.existingRootPackages

/**
 * Created by chensongsong on 2020/7/15.
 */
object RootInfo {
    fun getRootInfo(context: Context): List<Pair<String, String>> {
        val list: MutableList<Pair<String, String>> = ArrayList()
        list.add(Pair("Su_v", execute("su -v")))
        list.add(Pair("RwPaths", existingRWPaths().toString()))
        list.add(Pair("DangerousProperties", existingDangerousProperties().toString()))
        list.add(Pair("RootFiles", existingRootFiles().toString()))
        list.add(Pair("RootPackages", existingRootPackages(context).toString()))
        return list
    }
}
