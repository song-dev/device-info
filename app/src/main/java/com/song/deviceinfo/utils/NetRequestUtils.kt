package com.song.deviceinfo.utils

import com.google.firebase.perf.metrics.AddTrace
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Logger
import java.io.IOException
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import java.util.concurrent.TimeUnit

/**
 * Created by chensongsong on 2018/7/12.
 */
object NetRequestUtils {
    private val JSON = "application/json; charset=utf-8".toMediaType()
    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .cookieJar(PersistenceCookieJar())
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    fun requestPost(url: String, json: String): String? {
        return try {
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

    fun requestPostByForm(url: String, params: Map<String, String>): String? {
        return try {
            val formBuilder = FormBody.Builder()
            params.forEach { (key, value) ->
                formBuilder.add(key, value)
            }
            val request = Request.Builder()
                .url(url)
                .post(formBuilder.build())
                .build()
            val response = client.newCall(request).execute()
            response.body?.string()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    @AddTrace(name = "NetRequestUtils.requestGet")
    fun requestGet(url: String): String? {
        return try {
            val request = Request.Builder()
                .url(url)
                .get()
                .build()
            val response = client.newCall(request).execute()
            response.body?.string()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private class PersistenceCookieJar : CookieJar {
        private val cookieStore = mutableMapOf<String, List<Cookie>>()

        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            cookieStore[url.host] = cookies
        }

        override fun loadForRequest(url: HttpUrl): List<Cookie> {
            val cookies = cookieStore[url.host] ?: return emptyList()
            val validCookies = cookies.filter { !it.expiresAt.hasExpired() }
            cookieStore[url.host] = validCookies
            return validCookies
        }

        private fun Long.hasExpired() = this < System.currentTimeMillis()
    }
    
    private class HttpLogger : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            LogUtils.d("Logger: $message")
        }
    }
}
