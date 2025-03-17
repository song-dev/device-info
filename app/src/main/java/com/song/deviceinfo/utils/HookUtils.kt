package com.song.deviceinfo.utils

import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Process
import java.io.BufferedReader
import java.io.FileReader
import java.util.Locale

/**
 * Created by chensongsong on 2020/7/16.
 */
object HookUtils {
    @JvmStatic
    fun checkRunningProcesses(context: Context): Boolean {
        var returnValue = false
        // Get currently running application processes
        val activityManager = (context.getSystemService(Context.ACTIVITY_SERVICE)) as ActivityManager
        
        var list: List<ActivityManager.RunningServiceInfo>? = null
        list = activityManager.getRunningServices(300)
        if (list != null) {
            var tempName: String
            for (i in list.indices) {
                tempName = list[i].process
                if (tempName.contains("fridaserver")) {
                    returnValue = true
                }
            }
        }
        return returnValue
    }
    
    /**
     * 检查包名是否存在
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun chargeXposedPackage(context: Context): String? {
        val packageManager = context.applicationContext.packageManager
        val appliacationInfoList = packageManager.getInstalledApplications(PackageManager.GET_META_DATA) ?: return null
        for (item in appliacationInfoList) {
            if ("de.robv.android.xposed.installer" == item.packageName) {
                return item.packageName
            }
            if ("com.saurik.substrate" == item.packageName) {
                return item.packageName
            }
        }
        return null
    }
    
    /**
     * 检测调用栈中的可疑方法
     */
    @JvmStatic
    fun chargeXposedHookMethod(): String? {
        try {
            throw Exception("Deteck hook")
        } catch (e: Exception) {
            var zygoteInitCallCount = 0
            for (item in e.stackTrace) {
                // 检测"com.android.internal.os.ZygoteInit"是否出现两次，如果出现两次，则表明Substrate框架已经安装
                if ("com.android.internal.os.ZygoteInit" == item.className) {
                    zygoteInitCallCount++
                    if (zygoteInitCallCount == 2) {
//                        Log.i(TAG, "Substrate is active on the device.");
                        return "com.saurik.substrate"
                    }
                }
                
                if ("com.saurik.substrate.MS$2" == item.className && "invoke" == item.methodName) {
//                    Log.i(TAG, "A method on the stack trace has been hooked using Substrate.");
                    return "com.saurik.substrate"
                }
                
                if ("de.robv.android.xposed.XposedBridge" == item.className
                    && "main" == item.methodName
                ) {
//                    Log.i(TAG, "Xposed is active on the device.");
                    return "de.robv.android.xposed.XposedBridge"
                }
                if ("de.robv.android.xposed.XposedBridge" == item.className
                    && "handleHookedMethod" == item.methodName
                ) {
//                    Log.i(TAG, "A method on the stack trace has been hooked using Xposed.");
                    return "de.robv.android.xposed.XposedBridge"
                }
            }
            return null
        }
    }
    
    /**
     * 检测内存中可疑的jars
     */
    @JvmStatic
    fun chargeXposedJars(): String? {
        val libraries: MutableSet<String> = HashSet()
        val mapsFilename = "/proc/" + Process.myPid() + "/maps"
        try {
            val reader = BufferedReader(FileReader(mapsFilename))
            var line: String
            while ((reader.readLine().also { line = it }) != null) {
                LogUtils.d("maps line: $line")
                if (line.lowercase(Locale.getDefault()).contains("frida")) {
                    return "frida"
                }
                if (line.endsWith(".so") || line.endsWith(".jar")) {
                    val n = line.lastIndexOf(" ")
                    libraries.add(line.substring(n + 1))
                }
            }
            for (library in libraries) {
                if (library.startsWith("/system/framework/ed") || library.contains("Xposed")) {
                    LogUtils.d("libraries line: $library")
                }
                if (library.contains("com.saurik.substrate")) {
//                    Log.i(TAG, "Substrate shared object found: " + library);
                    return "com.saurik.substrate"
                }
                if (library.contains("XposedBridge.jar") || library.contains("edxp.jar")) {
//                    Log.i(TAG, "Xposed JAR found: " + library);
                    return "XposedBridge.jar"
                }
            }
            reader.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
    
    @JvmStatic
    fun classCheck(): Boolean {
        try {
            val classLoader = ClassLoader.getSystemClassLoader()
            classLoader.loadClass("de.robv.android.xposed.XposedHelpers").newInstance()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
}
