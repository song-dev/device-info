package com.song.deviceinfo.info;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.song.deviceinfo.utils.CommandUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.core.util.Pair;

/**
 * Created by chensongsong on 2020/7/9.
 */
public class VirtualAppInfo {

    /**
     * 获取应用列表
     *
     * @param context
     * @return
     */
    public static List<Pair<String, String>> getVirtualAppInfo(Context context) {
        List<Pair<String, String>> list = new ArrayList<>();
        list.add(new Pair<>("FilesDir", context.getFilesDir().getAbsolutePath()));
        list.add(new Pair<>("Permission", moreOpenCheck(context) + ""));
        list.add(new Pair<>("Binder", getSystemServer() + ""));
        list.add(new Pair<>("Process", processCheck() + ""));
        list.add(new Pair<>("PackageNum", packageCheck(context) + ""));
        return list;
    }

    /**
     * 进程检测，若出现同一个 uid 下出现的进程名对应 /data/data/pkg 私有目录，超出 1 个则为多开
     * 需要排除当前进程名存在多个情况
     *
     * @return
     * @deprecated 当前方案在 6.0 以上机型不可用，因为只能获取当前 uid 进程列表
     */
    private static boolean processCheck() {
        String filter = CommandUtils.getUidStrFormat();
        if (TextUtils.isEmpty(filter)) {
            return false;
        }
        String result = CommandUtils.execute("ps");
        if (result == null || result.isEmpty()) {
            return false;
        }
        String[] lines = result.split("\n");
        if (lines.length <= 0) {
            return false;
        }
        int exitDirCount = 0;
        try {
            for (int i = 0; i < lines.length; i++) {
                if (lines[i].contains(filter)) {
                    int pkgStartIndex = lines[i].lastIndexOf(" ");
                    String processName = lines[i].substring(pkgStartIndex <= 0
                            ? 0 : pkgStartIndex + 1, lines[i].length());
                    File dataFile = new File(String.format("/data/data/%s",
                            processName, Locale.CHINA));
                    if (dataFile.exists()) {
                        exitDirCount++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exitDirCount > 1;
    }

    /**
     * 若 applist 存在两个当前包名则为多开
     *
     * @param context
     * @return
     * @deprecated 大部分多开软件已经绕过
     */
    private static boolean packageCheck(Context context) {
        try {
            if (context == null) {
                return false;
            }
            int count = 0;
            String packageName = context.getPackageName();
            PackageManager pm = context.getPackageManager();
            List<PackageInfo> pkgs = pm.getInstalledPackages(0);
            for (PackageInfo info : pkgs) {
                if (packageName.equals(info.packageName)) {
                    count++;
                }
            }
            return count > 1;
        } catch (Exception ignore) {
        }
        return false;
    }

    /**
     * 获取系统 binder 服务数量，若保持在 140 左右则为多开
     *
     * @return
     */
    private static boolean getSystemServer() {
        return false;
    }

    /**
     * 0. 多开检测 false
     * 1. 多开检测 true
     * -1. 检测失败（$unknown）
     * 检测多开, 若可访问规定目录则为正常，否则为多开环境
     *
     * @return
     */
    public static native int moreOpenCheck(Context context);

}
