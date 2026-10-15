package com.song.deviceinfo.model.beans

import org.json.JSONException
import org.json.JSONObject

/**
 * Created by chensongsong on 2020/5/29.
 */
class PartitionsBean {

    /**
     * 文件夹路径
     */
    var path: String? = null

    /**
     * 挂载路径
     */
    var mount: String? = null

    /**
     * 类型
     */
    var fs: String? = null

    /**
     * 权限
     */
    var mod: String? = null

    /**
     * 可用大小
     */
    var used: String? = null

    /**
     * 总共大小
     */
    var size: String? = null

    /**
     * 已用占比
     */
    var ratio: Int = 0

    override fun toString(): String {
        return "PartitionsBean{" +
                "path='" + path + '\'' +
                ", mount='" + mount + '\'' +
                ", fs='" + fs + '\'' +
                ", mod='" + mod + '\'' +
                ", used='" + used + '\'' +
                ", size='" + size + '\'' +
                ", ratio=" + ratio +
                '}'
    }

    fun toJSON(): JSONObject {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("path", path)
            jsonObject.put("mount", mount)
            jsonObject.put("fs", fs)
            jsonObject.put("mod", mod)
            jsonObject.put("used", used)
            jsonObject.put("size", size)
            jsonObject.put("ratio", ratio)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return jsonObject
    }
}
