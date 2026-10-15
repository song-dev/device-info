package com.song.deviceinfo.model.beans

import org.json.JSONObject

/**
 * Created by chensongsong on 2020/6/4.
 */
class InputBean {

    var name: String? = null
    var attribute: String? = null
    var sys: String? = null
    var handlers: String? = null

    override fun toString(): String {
        return "InputBean{" +
                "name='" + name + '\'' +
                ", attribute='" + attribute + '\'' +
                ", sys='" + sys + '\'' +
                ", handlers='" + handlers + '\'' +
                '}'
    }

    fun toJSON(): JSONObject {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("name", name)
            jsonObject.put("attribute", attribute)
            jsonObject.put("sys", sys)
            jsonObject.put("handlers", handlers)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return jsonObject
    }
}
