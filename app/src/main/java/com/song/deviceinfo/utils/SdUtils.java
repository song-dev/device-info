package com.song.deviceinfo.utils;

import android.annotation.SuppressLint;
import android.app.usage.StorageStatsManager;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.text.format.Formatter;

import com.song.deviceinfo.model.beans.StorageBean;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

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
     * 获取 sd 卡存储信息
     *
     * @param context
     * @param bean
     */
    public static void getStoreInfo(Context context, StorageBean bean) {
        File card = Environment.getExternalStorageDirectory();
        bean.setStorePath(card.getAbsolutePath());
        long totalSpace = card.getTotalSpace();
        long freeSpace = card.getFreeSpace();
        long usableSpace = totalSpace - freeSpace;
        String total = Formatter.formatFileSize(context, totalSpace);
        String usable = Formatter.formatFileSize(context, usableSpace);
        String free = Formatter.formatFileSize(context, freeSpace);
        bean.setTotalStore(total);
        bean.setFreeStore(free);
        bean.setUsedStore(usable);
        int ratio = (int) ((usableSpace / (double) totalSpace) * 100);
        bean.setRatioStore(ratio);
        bean.setRomSize(getRealStorage(context));
    }

    /**
     * 获取 ROM 空间大小
     *
     * @param context
     * @return
     */
    private static String getRomTotal(Context context) {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return Formatter.formatFileSize(context, totalBlocks * blockSize);
    }

    @SuppressLint("DiscouragedPrivateApi")
    public static String getRealStorage(Context context) {
        long total = 0L;
        try {
            StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
            int version = Build.VERSION.SDK_INT;
            float unit = version >= Build.VERSION_CODES.O ? 1000 : 1024;
            if (version < Build.VERSION_CODES.M) {
                Method getVolumeList = StorageManager.class.getDeclaredMethod("getVolumeList");
                StorageVolume[] volumeList = (StorageVolume[]) getVolumeList.invoke(storageManager);
                if (volumeList != null) {
                    Method getPathFile = null;
                    for (StorageVolume volume : volumeList) {
                        if (getPathFile == null) {
                            getPathFile = volume.getClass().getDeclaredMethod("getPathFile");
                        }
                        File file = (File) getPathFile.invoke(volume);
                        total += file.getTotalSpace();
                    }
                }
            } else {
                @SuppressLint("PrivateApi") Method getVolumes = StorageManager.class.getDeclaredMethod("getVolumes");
                List<Object> getVolumeInfo = (List<Object>) getVolumes.invoke(storageManager);
                for (Object obj : getVolumeInfo) {
                    Field getType = obj.getClass().getField("type");
                    int type = getType.getInt(obj);
                    if (type == 1) {
                        long totalSize = 0L;
                        if (version >= Build.VERSION_CODES.O) {
                            Method getFsUuid = obj.getClass().getDeclaredMethod("getFsUuid");
                            String fsUuid = (String) getFsUuid.invoke(obj);
                            totalSize = getTotalSize(context, fsUuid);
                        } else if (version >= Build.VERSION_CODES.N_MR1) {
                            Method getPrimaryStorageSize = StorageManager.class.getMethod("getPrimaryStorageSize");
                            totalSize = (long) getPrimaryStorageSize.invoke(storageManager);
                        }
                        Method isMountedReadable = obj.getClass().getDeclaredMethod("isMountedReadable");
                        boolean readable = (boolean) isMountedReadable.invoke(obj);
                        if (readable) {
                            Method file = obj.getClass().getDeclaredMethod("getPath");
                            File f = (File) file.invoke(obj);
                            if (totalSize == 0) {
                                totalSize = f.getTotalSpace();
                            }
                            total += totalSize;
                        }
                    } else if (type == 0) {
                        Method isMountedReadable = obj.getClass().getDeclaredMethod("isMountedReadable");
                        boolean readable = (boolean) isMountedReadable.invoke(obj);
                        if (readable) {
                            Method file = obj.getClass().getDeclaredMethod("getPath");
                            File f = (File) file.invoke(obj);
                            total += f.getTotalSpace();
                        }
                    }
                }
            }
            return getUnit(total, unit);
        } catch (Exception ignore) {

        }
        return null;
    }

    private static String[] units = {"B", "KB", "MB", "GB", "TB"};

    /**
     * 进制转换
     */
    private static String getUnit(float size, float base) {
        int index = 0;
        while (size > base && index < 4) {
            size = size / base;
            index++;
        }
        return String.format(Locale.getDefault(), "%.2f %s ", size, units[index]);
    }

    /**
     * API 26 android O
     * 获取总共容量大小，包括系统大小
     */
    @SuppressLint("NewApi")
    private static long getTotalSize(Context context, String fsUuid) {
        try {
            UUID id;
            if (fsUuid == null) {
                id = StorageManager.UUID_DEFAULT;
            } else {
                id = UUID.fromString(fsUuid);
            }
            StorageStatsManager stats = context.getSystemService(StorageStatsManager.class);
            return stats.getTotalBytes(id);
        } catch (NoSuchFieldError | NoClassDefFoundError | NullPointerException | IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

}
