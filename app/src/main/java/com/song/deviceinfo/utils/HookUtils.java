package com.song.deviceinfo.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by chensongsong on 2020/7/16.
 */
public class HookUtils {

    public static boolean checkRunningProcesses(Context context) {
        boolean returnValue = false;
        // Get currently running application processes
        ActivityManager activityManager = (ActivityManager) (context.getSystemService(Context.ACTIVITY_SERVICE));

        List<ActivityManager.RunningServiceInfo> list = null;
        if (activityManager != null) {
            list = activityManager.getRunningServices(300);
        }
        if (list != null) {
            String tempName;
            for (int i = 0; i < list.size(); ++i) {
                tempName = list.get(i).process;
                if (tempName.contains("fridaserver")) {
                    returnValue = true;
                }
            }
        }
        return returnValue;
    }

    /**
     * 检查包名是否存在
     *
     * @param context
     * @return
     */
    public static String chargeXposedPackage(Context context) {
        PackageManager packageManager = context.getApplicationContext().getPackageManager();
        List<ApplicationInfo> appliacationInfoList = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        if (appliacationInfoList == null) {
            return null;
        }
        for (ApplicationInfo item : appliacationInfoList) {
            if ("de.robv.android.xposed.installer".equals(item.packageName)) {
                return item.packageName;
            }
            if ("com.saurik.substrate".equals(item.packageName)) {
                return item.packageName;
            }
        }
        return null;
    }

    /**
     * 检测调用栈中的可疑方法
     */
    public static String chargeXposedHookMethod() {
        try {
            throw new Exception("Deteck hook");
        } catch (Exception e) {
            int zygoteInitCallCount = 0;
            for (StackTraceElement item : e.getStackTrace()) {
                // 检测"com.android.internal.os.ZygoteInit"是否出现两次，如果出现两次，则表明Substrate框架已经安装
                if ("com.android.internal.os.ZygoteInit".equals(item.getClassName())) {
                    zygoteInitCallCount++;
                    if (zygoteInitCallCount == 2) {
//                        Log.i(TAG, "Substrate is active on the device.");
                        return "com.saurik.substrate";
                    }
                }

                if ("com.saurik.substrate.MS$2".equals(item.getClassName()) && "invoke".equals(item.getMethodName())) {
//                    Log.i(TAG, "A method on the stack trace has been hooked using Substrate.");
                    return "com.saurik.substrate";
                }

                if ("de.robv.android.xposed.XposedBridge".equals(item.getClassName())
                        && "main".equals(item.getMethodName())) {
//                    Log.i(TAG, "Xposed is active on the device.");
                    return "de.robv.android.xposed.XposedBridge";
                }
                if ("de.robv.android.xposed.XposedBridge".equals(item.getClassName())
                        && "handleHookedMethod".equals(item.getMethodName())) {
//                    Log.i(TAG, "A method on the stack trace has been hooked using Xposed.");
                    return "de.robv.android.xposed.XposedBridge";
                }
            }
            return null;
        }
    }

    /**
     * 检测内存中可疑的jars
     */
    public static String chargeXposedJars() {
        Set<String> libraries = new HashSet<String>();
        String mapsFilename = "/proc/" + android.os.Process.myPid() + "/maps";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(mapsFilename));
            String line;
            while ((line = reader.readLine()) != null) {
                LogUtils.d("maps line: " + line);
                if (line.toLowerCase().contains("frida")) {
                    return "frida";
                }
                if (line.endsWith(".so") || line.endsWith(".jar")) {
                    int n = line.lastIndexOf(" ");
                    libraries.add(line.substring(n + 1));
                }
            }
            for (String library : libraries) {
                if (library.startsWith("/system/framework/ed") || library.contains("Xposed")) {
                    LogUtils.d("libraries line: " + library);
                }
                if (library.contains("com.saurik.substrate")) {
//                    Log.i(TAG, "Substrate shared object found: " + library);
                    return "com.saurik.substrate";
                }
                if (library.contains("XposedBridge.jar") || library.contains("edxp.jar")) {
//                    Log.i(TAG, "Xposed JAR found: " + library);
                    return "XposedBridge.jar";
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean classCheck() {
        try {
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            classLoader.loadClass("de.robv.android.xposed.XposedHelpers").newInstance();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
