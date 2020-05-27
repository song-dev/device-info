package com.song.deviceinfo.info;

import android.text.TextUtils;

import com.song.deviceinfo.utils.FileUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import androidx.core.util.Pair;

public class ThermalInfo {

    public static List<Pair<String, String>> getThermalInfo() {
        List<Pair<String, String>> list = new ArrayList<>();
        File[] files = filter("/sys/class/thermal/", new ThermalFilter("thermal_zone"));
        if (files != null && files.length > 0) {
            for (File file : files) {
                String type = FileUtils.readFile(new File(file, "type"));
                if (!TextUtils.isEmpty(type)) {
                    String temp = FileUtils.readFile(new File(file, "temp"));
                    if (!TextUtils.isEmpty(temp)) {
                        list.add(new Pair<>(type.trim(), temp.trim()));
                    }
                }
            }
        }
        return list;
    }

    private static File[] filter(String path, FilenameFilter filter) {
        File file = new File(path);
        if (file.exists()) {
            return file.listFiles(filter);
        }
        return null;
    }

    private static class ThermalFilter implements FilenameFilter {

        private String condition;

        public ThermalFilter(String condition) {
            this.condition = condition;
        }

        @Override
        public boolean accept(File dir, String name) {
            return name.startsWith(condition);
        }
    }
}
