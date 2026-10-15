package com.song.deviceinfo.model.beans

import android.graphics.drawable.Drawable
import org.json.JSONObject

/**
 * Created by chensongsong on 2020/6/3.
 */
class ApplicationBean {

    var name: String? = null
    var packageName: String? = null
    var version: String? = null
    var buildVersion: Int = 0
    var icon: Drawable? = null
    var isSystemApp: Boolean = false

    override fun toString(): String {
        return "ApplicationBean{" +
                "name='" + name + '\'' +
                ", packageName='" + packageName + '\'' +
                ", version='" + version + '\'' +
                ", buildVersion=" + buildVersion +
                ", icon=" + icon +
                ", systemApp=" + isSystemApp +
                '}'
    }

    fun toJSON(): JSONObject {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("name", name)
            jsonObject.put("packageName", packageName)
            jsonObject.put("version", version)
            jsonObject.put("buildVersion", buildVersion)
            jsonObject.put("icon", icon)
            jsonObject.put("systemApp", isSystemApp)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return jsonObject
    }
}
