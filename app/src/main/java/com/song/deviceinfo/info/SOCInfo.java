package com.song.deviceinfo.info;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.song.deviceinfo.utils.CommandUtils;
import com.song.deviceinfo.utils.DecimalUtils;
import com.song.deviceinfo.utils.FileUtils;
import com.song.deviceinfo.utils.LogUtils;
import com.song.deviceinfo.utils.SocUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import androidx.core.util.Pair;

/**
 * Created by chensongsong on 2020/6/8.
 */
public class SOCInfo {

    public static List<Pair<String, String>> getSOCInfo(Context context) {
        List<Pair<String, String>> list = new ArrayList<>();
        SocUtils.setCpuInfo(list);
        setFrequency(list);
        list.add(new Pair<>("Machine", CommandUtils.execute("uname -m")));
        list.add(new Pair<>("ABI", Build.CPU_ABI));
        list.add(new Pair<>("Thermal", getCpuTemp() + "℃"));
        list.add(new Pair<>("CPU", CommandUtils.getProperty("ro.board.platform")));
        SocUtils.getGPUInfo(context, list);
        return list;
    }

    private static final FileFilter CPU_FILTER = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            return Pattern.matches("cpu[0-9]", pathname.getName());
        }
    };

    private static String getCpuTemp() {
        String temp = null;
        try {
            FileReader fr = new FileReader("/sys/class/thermal/thermal_zone9/subsystem/thermal_zone9/temp");
            BufferedReader br = new BufferedReader(fr);
            temp = br.readLine();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return TextUtils.isEmpty(temp) ? null : temp.length() >= 5 ? (Integer.parseInt(temp) / 1000) + "" : temp;
    }

    @SuppressLint("DefaultLocale")
    private static void setFrequency(List<Pair<String, String>> list) {
        try {
            int cores = Objects.requireNonNull(new File("/sys/devices/system/cpu/").listFiles(CPU_FILTER)).length;
            list.add(new Pair<>("Cores", cores + ""));
            if (cores > 0) {
                ArrayList<Integer> min = new ArrayList<>();
                ArrayList<Integer> max = new ArrayList<>();
                for (int i = 0; i < cores; i++) {
                    min.add(Integer.parseInt(FileUtils.readFile(String.format("/sys/devices/system/cpu/cpu%d/cpufreq/cpuinfo_min_freq", i))));
                    max.add(Integer.parseInt(FileUtils.readFile(String.format("/sys/devices/system/cpu/cpu%d/cpufreq/cpuinfo_max_freq", i))));
                }
                Collections.sort(min);
                Collections.sort(max);
                LogUtils.e(max.toString());
                if (max.size() > 0) {
                    list.add(new Pair<>("Clock speed", min.get(0) + " - " + max.get(max.size() - 1) + " MHz"));
                    Map<Integer, Integer> map = new HashMap<>();
                    for (int temp : max) {
                        Integer count = map.get(temp);
                        map.put(temp, (count == null) ? 1 : count + 1);
                    }
                    LogUtils.e(map.toString());
                    StringBuffer sb = new StringBuffer();
                    for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                        sb.append(entry.getValue())
                                .append(" x ")
                                .append(DecimalUtils.round(entry.getKey() / 1000.0 / 1000.0, 2))
                                .append(" GHz")
                                .append('\n');
                    }
                    list.add(new Pair<>("Clusters", sb.deleteCharAt(sb.length() - 1).toString()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
