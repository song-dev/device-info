package com.song.deviceinfo.utils;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by chensongsong on 2020/6/1.
 */
public class OkHttpUtils {

    private static final String TAG = "OkHttpUtils";

    private static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    private static OkHttpClient getInstance() {
        return Client.okHttpClient;
    }

    private static class Client {

        private static OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .hostnameVerifier(new SongHostnameVerifier())
                .build();

    }

    private static class SongHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    public static String post(String url, String json) {
        Log.e(TAG, "REQUEST URL: " + url);
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = null;
        try {
            response = getInstance().newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
