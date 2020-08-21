package com.song.deviceinfo.utils;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by chensongsong on 2018/7/12.
 */

public class HttpUtils {

    private static final String TAG = "HttpUtils";

    private static String cookie = "";
    private static final String GEETEST_VALIDATE = "geetest_validate";
    private static final String GEETEST_SECCODE = "geetest_seccode";
    private static final String GEETEST_CHALLENGE = "geetest_challenge";

    public static String requsetUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(false);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String resultData = null;
                InputStream inputStream = urlConnection.getInputStream();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] data = new byte[1024];
                int len = -1;
                while ((len = inputStream.read(data)) != -1) {
                    byteArrayOutputStream.write(data, 0, len);
                }
                inputStream.close();
                resultData = new String(byteArrayOutputStream.toByteArray());
                byteArrayOutputStream.close();
                return resultData;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String requestPost(String urlString, String postParam){
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

        String params = null;
        try {
            JSONObject jsonObject = new JSONObject(postParam);
            String seccode = jsonObject.getString(GEETEST_SECCODE);
            String validate = jsonObject.getString(GEETEST_VALIDATE);
            String challenge = jsonObject.getString(GEETEST_CHALLENGE);
            params = GEETEST_VALIDATE + "=" + validate + "&" + GEETEST_SECCODE + "=" + seccode + "&" + GEETEST_CHALLENGE + "=" + challenge;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(mediaType, params);
        Request request = new Request
                .Builder()
                .post(requestBody)
                .header("Cookie", TextUtils.isEmpty(cookie)?"":cookie)
                .url(urlString)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String requestGet(String urlString){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(urlString).build();
        try {
            Response response = client.newCall(request).execute();
            cookie = response.header("Set-Cookie");
            Log.e(TAG, "requestGet: " + cookie);
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
