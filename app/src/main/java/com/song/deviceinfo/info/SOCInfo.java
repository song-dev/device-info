package com.song.deviceinfo.info;

import android.content.Context;
import android.os.Build;

import com.song.deviceinfo.utils.CommandUtils;
import com.song.deviceinfo.utils.SocUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
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
        list.add(new Pair<>("CPU", CommandUtils.getProperty("ro.board.platform")));
        list.add(new Pair<>("CPU", CommandUtils.getProperty("ro.build.product")));
        list.add(new Pair<>("CPU", CommandUtils.getProperty("ro.product.board")));
        list.add(new Pair<>("CPU", CommandUtils.getProperty("ro.vendor.product.device")));
        list.add(new Pair<>("CPU", CommandUtils.getProperty("ro.vendor.product.name")));
        list.add(new Pair<>("Cores", getCores() + ""));
        list.add(new Pair<>("Machine", CommandUtils.execute("uname -m")));
        list.add(new Pair<>("ABI", Build.CPU_ABI));
        SocUtils.getGPUInfo(context, list);

        return list;
    }

    private static int getCores() {
        int cores = 0;
        try {
            cores = Objects.requireNonNull(new File("/sys/devices/system/cpu/").listFiles(CPU_FILTER)).length;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cores;
    }

    private static final FileFilter CPU_FILTER = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            return Pattern.matches("cpu[0-9]", pathname.getName());
        }
    };

}
