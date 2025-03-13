package com.song.deviceinfo.utils

import android.util.Log
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

/**
 * Created by chensongsong on 2020/6/1.
 */
object OkHttpUtils {
    private const val TAG = "OkHttpUtils"
    
    private val JSON = "application/json; charset=utf-8".toMediaType()
    
    private val client by lazy {
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .hostnameVerifier(SongHostnameVerifier())
            .build()
    }
    
    fun post(url: String, json: String): String? {
        return try {
            LogUtils.d("OkHttpUtils", "url: $url")
            val requestBody = RequestBody.create(JSON, json)
            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()
            val response = client.newCall(request).execute()
            response.body?.string()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    
    private class SongHostnameVerifier : HostnameVerifier {
        override fun verify(hostname: String?, session: SSLSession?): Boolean = true
    }
}
