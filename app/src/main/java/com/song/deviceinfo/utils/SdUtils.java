package com.song.deviceinfo.utils;

import android.content.Context;
import android.os.Environment;
import android.text.format.Formatter;

import com.song.deviceinfo.model.beans.StorageBean;

import java.io.File;

/**
 * Created by chensongsong on 2020/6/1.
 */
public class SdUtils {

    public static boolean isMounted() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public static String getDirPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 获取存储信息
     *
     * @param context
     * @param bean
     */
    public static void getStoreInfo(Context context, StorageBean bean) {
        File card = Environment.getExternalStorageDirectory();
        bean.setStorePath(card.getAbsolutePath());
        long usableSpace = card.getUsableSpace();
        long totalSpace = card.getTotalSpace();
        long freeSpace = card.getFreeSpace();
        String total = Formatter.formatFileSize(context, freeSpace);
        String usable = Formatter.formatFileSize(context, usableSpace);
        String free = Formatter.formatFileSize(context, freeSpace);
        bean.setTotalStore(total);
        bean.setFreeStore(free);
        bean.setUsedStore(usable);
        bean.setRatioStore((int) (usableSpace / totalSpace));
    }

}
