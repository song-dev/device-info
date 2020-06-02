package com.song.deviceinfo.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.text.format.Formatter;

import com.song.deviceinfo.model.beans.StorageBean;

/**
 * Created by chensongsong on 2020/6/2.
 */
public class MemoryUtils {

    /**
     * 读取内存信息
     *
     * @return
     */
    public static void getMemoryInfo(Context context, StorageBean bean) {
        try {
            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
            manager.getMemoryInfo(info);
            long totalMem = info.totalMem;
            long availMem = info.availMem;
            long usedMem = totalMem - availMem;
            String total = Formatter.formatFileSize(context, totalMem);
            String usable = Formatter.formatFileSize(context, usedMem);
            String free = Formatter.formatFileSize(context, availMem);
            bean.setTotalMemory(total);
            bean.setFreeMemory(free);
            bean.setUsedMemory(usable);
            int ratio = (int) ((availMem / (double) totalMem) * 100);
            bean.setRatioMemory(ratio);
            double v = totalMem / 1024 / 1024 / 1024.0;
            String ram;
            if (v <= 1) {
                ram = "1 GB";
            } else if (v <= 2) {
                ram = "2 GB";
            } else if (v <= 4) {
                ram = "4 GB";
            } else if (v <= 6) {
                ram = "6 GB";
            } else if (v <= 8) {
                ram = "8 GB";
            } else if (v <= 12) {
                ram = "12 GB";
            } else {
                ram = "16 GB";
            }
            bean.setMemInfo(ram);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
