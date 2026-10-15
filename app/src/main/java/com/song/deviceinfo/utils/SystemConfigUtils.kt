package com.song.deviceinfo.utils

import android.content.Context
import android.os.SystemClock
import java.util.TimeZone

/**
 * Created by chensongsong on 2020/6/1.
 */
object SystemConfigUtils {
    @JvmStatic val currentTimeZone: String
        /**
         * 获取当前时区
         *
         * @return
         */
        get() {
            val tz = TimeZone.getDefault()
            return tz.getDisplayName(false, TimeZone.SHORT)
        }
    
    /**
     * 获取当前系统语言格式
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun getCurrentLanguage(context: Context): String {
        val locale = context.resources.configuration.locale
        val language = locale.language
        val country = locale.country
        val lc = language + "_" + country
        return locale.displayLanguage
    }
    
    @JvmStatic val systemUpdate: String
        /**
         * 获取当前系统运行时间
         */
        get() {
            val nanoTime = SystemClock.elapsedRealtime()
            val DAY = (1000 * 60 * 60 * 24).toLong()
            val HOUR = (1000 * 60 * 60).toLong()
            val MINUTES = (1000 * 60).toLong()
            val day = nanoTime / DAY
            return if (day > 0) {
                (nanoTime / DAY).toString() + " days " + nanoTime % DAY / HOUR + ":" + nanoTime % DAY % HOUR / MINUTES
            } else {
                (nanoTime % DAY / HOUR).toString() + ":" + nanoTime % DAY % HOUR / MINUTES
            }
        }
}
