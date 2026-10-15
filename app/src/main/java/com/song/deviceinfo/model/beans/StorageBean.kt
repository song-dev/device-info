package com.song.deviceinfo.model.beans

import org.json.JSONObject

/**
 * Created by chensongsong on 2020/6/1.
 */
class StorageBean {

    /**
     * 剩余存储空间
     */
    var freeStore: String? = null

    /**
     * 已用存储空间
     */
    var usedStore: String? = null

    /**
     * 总共存储空间
     */
    var totalStore: String? = null

    /**
     * 使用率
     */
    var ratioStore: Int = 0

    /**
     * 存储路径
     */
    var storePath: String? = null

    /**
     * 剩余存储空间
     */
    var freeMemory: String? = null

    /**
     * 已用存储空间
     */
    var usedMemory: String? = null

    /**
     * 总共存储空间
     */
    var totalMemory: String? = null

    /**
     * 使用率
     */
    var ratioMemory: Int = 0

    /**
     * 内存信息
     */
    var memInfo: String? = null

    /**
     * 真实 ROM 空间
     */
    var romSize: String? = null

    override fun toString(): String {
        return "StorageBean{" +
                "freeStore='" + freeStore + '\'' +
                ", usedStore='" + usedStore + '\'' +
                ", totalStore='" + totalStore + '\'' +
                ", ratioStore=" + ratioStore +
                ", storePath='" + storePath + '\'' +
                ", freeMemory='" + freeMemory + '\'' +
                ", usedMemory='" + usedMemory + '\'' +
                ", totalMemory='" + totalMemory + '\'' +
                ", ratioMemory=" + ratioMemory +
                ", memInfo='" + memInfo + '\'' +
                ", romSize='" + romSize + '\'' +
                '}'
    }

    fun toJSON(): JSONObject {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("freeStore=", freeStore)
            jsonObject.put("usedStore", usedStore)
            jsonObject.put("totalStore", totalStore)
            jsonObject.put("ratioStore", ratioStore)
            jsonObject.put("storePath", storePath)
            jsonObject.put("freeMemory", freeMemory)
            jsonObject.put("usedMemory", usedMemory)
            jsonObject.put("totalMemory", totalMemory)
            jsonObject.put("ratioMemory", ratioMemory)
            jsonObject.put("memInfo", memInfo)
            jsonObject.put("romSize", romSize)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return jsonObject
    }
}
