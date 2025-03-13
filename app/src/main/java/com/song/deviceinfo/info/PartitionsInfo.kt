package com.song.deviceinfo.info

import android.text.TextUtils
import com.song.deviceinfo.model.beans.PartitionsBean
import com.song.deviceinfo.utils.CommandUtils.exec
import com.song.deviceinfo.utils.Constants

/**
 * Created by chensongsong on 2020/5/29.
 */
object PartitionsInfo {
    val partitionsInfo: List<PartitionsBean>
        get() {
            val lines = exec("mount")
            val df = parseDf()
            val list: MutableList<PartitionsBean> = ArrayList()
            for (line in lines) {
                val args = line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                if (args.size < 4) {
                    continue
                }
                val mount = args[0]
                val path = args[2]
                val type = args[4]
                val rws = args[5]
                var rw = ""
                if (!TextUtils.isEmpty(rws)) {
                    rw = if (rws.startsWith("(rw")) {
                        "read-write"
                    } else if (rws.startsWith("(ro")) {
                        "read-only"
                    } else {
                        Constants.UNKNOWN
                    }
                }
                val bean = PartitionsBean()
                bean.path = path
                bean.mount = mount
                bean.fs = type
                bean.mod = rw
                if (df.containsKey(mount)) {
                    val partitionsBean = df[mount]
                    bean.ratio = partitionsBean!!.ratio
                    bean.used = partitionsBean.used
                    bean.size = partitionsBean.size
                }
                list.add(bean)
            }
            return list
        }
    
    /**
     * 解析 df 数据
     *
     * @return
     */
    private fun parseDf(): Map<String, PartitionsBean> {
        val dfs = exec("df -h")
        val map: MutableMap<String, PartitionsBean> = HashMap()
        for (line in dfs) {
            if (line.startsWith("Filesystem")) {
                continue
            }
            val args = line.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (args.size < 5) {
                continue
            }
            val path = args[0]
            val size = args[1]
            val used = args[2]
            val ratio = args[4]
            val mount = args[5]
            val bean = PartitionsBean()
            bean.path = path
            bean.mount = mount
            bean.size = size
            bean.used = used
            var i = 0
            try {
                i = ratio.replace("%", "").toInt()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            bean.ratio = i
            map[path] = bean
        }
        return map
    }
}
