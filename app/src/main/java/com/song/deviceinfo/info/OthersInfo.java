package com.song.deviceinfo.info;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.WebSettings;

import com.google.firebase.perf.metrics.AddTrace;
import com.song.deviceinfo.utils.Constants;
import com.song.deviceinfo.utils.FileUtils;
import com.song.deviceinfo.utils.OaidUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.core.util.Pair;

/**
 * Created by chensongsong on 2020/9/22.
 */
public class OthersInfo {

    @AddTrace(name = "OthersInfo.getOthersInfo")
    public static List<Pair<String, String>> getOthersInfo(Context context) {
        List<Pair<String, String>> list = new ArrayList<>();
        list.add(new Pair<>("UA", getDefaultUserAgent(context)));
        list.add(new Pair<>("BootId", FileUtils.readFile("/proc/sys/kernel/random/boot_id")));
        list.add(new Pair<>("UUID", FileUtils.readFile("/proc/sys/kernel/random/uuid")));
        list.add(new Pair<>("PoolSize", FileUtils.readFile("/proc/sys/kernel/random/poolsize")));
        list.add(new Pair<>("Entropy Avail", FileUtils.readFile("/proc/sys/kernel/random/entropy_avail")));
        list.add(new Pair<>("Read Threshold", FileUtils.readFile("/proc/sys/kernel/random/read_wakeup_threshold")));
        list.add(new Pair<>("Write Threshold", FileUtils.readFile("/proc/sys/kernel/random/write_wakeup_threshold")));
        list.add(new Pair<>("Secs", FileUtils.readFile("/proc/sys/kernel/random/urandom_min_reseed_secs")));
        new OaidUtils().getOaidsInfo(context, list);
        list.add(new Pair<>("Country", Locale.getDefault().getLanguage() + "-" + Locale.getDefault().getCountry()));
        return list;
    }

    private static String getDefaultUserAgent(Context context) {
        String ua = null;
        try {
            ua = System.getProperty("http.agent");
            if (TextUtils.isEmpty(ua)) {
                Method localMethod = WebSettings.class.getDeclaredMethod("getDefaultUserAgent", new Class[]{Context.class});
                ua = (String) localMethod.invoke(WebSettings.class, new Object[]{context});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TextUtils.isEmpty(ua) ? Constants.UNKNOWN : ua;
    }

}
