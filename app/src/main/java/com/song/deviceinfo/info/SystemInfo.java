package com.song.deviceinfo.info;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

import com.song.deviceinfo.R;
import com.song.deviceinfo.utils.CommandUtils;
import com.song.deviceinfo.utils.DensityUtils;
import com.song.deviceinfo.utils.SocUtils;
import com.song.deviceinfo.utils.SystemConfigUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.core.util.Pair;

/**
 * Created by chensongsong on 2020/5/27.
 */
public class SystemInfo {

    public static List<Pair<String, String>> getSystemInfo(Activity activity) {
        ArrayList<Pair<String, String>> list = new ArrayList<>();
        Context context = activity.getApplicationContext();
        list.add(new Pair<>(context.getString(R.string.system_manufacture), Build.MANUFACTURER));
        list.add(new Pair<>(context.getString(R.string.system_model), Build.MODEL));
        list.add(new Pair<>(context.getString(R.string.system_brand), Build.BRAND));
        list.add(new Pair<>(context.getString(R.string.system_release), Build.VERSION.RELEASE));
        list.add(new Pair<>(context.getString(R.string.system_api), Build.VERSION.SDK_INT + ""));
        list.add(new Pair<>(context.getString(R.string.system_code_name), Build.VERSION.CODENAME));
        list.add(new Pair<>(context.getString(R.string.system_density),
                DensityUtils.getDensityDpi(context) + " (" + DensityUtils.getDensityId(context) + ")"));
        list.add(new Pair<>(context.getString(R.string.system_refresh_rate), DensityUtils.getRefreshRate(activity) + " Hz"));
        list.add(new Pair<>(context.getString(R.string.system_device), Build.DEVICE));
        list.add(new Pair<>(context.getString(R.string.system_product), Build.PRODUCT));
        list.add(new Pair<>(context.getString(R.string.system_board), Build.BOARD));
        list.add(new Pair<>(context.getString(R.string.system_platform), SocUtils.getSocInfo()));
        list.add(new Pair<>(context.getString(R.string.system_build), Build.ID));
        list.add(new Pair<>(context.getString(R.string.system_vm), System.getProperty("java.vm.version")));
        list.add(new Pair<>(context.getString(R.string.system_security), CommandUtils.getProperty("ro.build.version.security_patch")));
        list.add(new Pair<>(context.getString(R.string.system_baseband), CommandUtils.getProperty("gsm.version.baseband")));
        list.add(new Pair<>(context.getString(R.string.system_build_type), Build.TYPE));
        list.add(new Pair<>(context.getString(R.string.system_tags), Build.TAGS));
        list.add(new Pair<>(context.getString(R.string.system_incremental), CommandUtils.getProperty("ro.build.version.incremental")));
        list.add(new Pair<>(context.getString(R.string.system_description), CommandUtils.getProperty("ro.build.description")));
        list.add(new Pair<>(context.getString(R.string.system_fingerprint), Build.FINGERPRINT));
        list.add(new Pair<>(context.getString(R.string.system_device_features), "68"));
        list.add(new Pair<>(context.getString(R.string.system_builder), "builder@" + CommandUtils.getProperty("ro.build.host")));
        list.add(new Pair<>(context.getString(R.string.system_language), SystemConfigUtils.getCurrentLanguage(context)));
        list.add(new Pair<>(context.getString(R.string.system_timezone), SystemConfigUtils.getCurrentTimeZone()));
        list.add(new Pair<>(context.getString(R.string.system_uptime), SystemConfigUtils.getSystemUpdate()));

        return list;
    }


}
