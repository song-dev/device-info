package com.song.deviceinfo.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.location.Criteria;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.provider.Settings;

/**
 * Created by chensongsong on 2020/7/14.
 */
public class DebugUtils {

    /**
     * 是否开启debug模式
     *
     * @param context
     * @return
     */
    public static boolean isOpenDebug(Context context) {
        try {
            return (Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.ADB_ENABLED, 0) > 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * APP 是否是 debug 版本
     *
     * @param context
     * @return
     */
    public static boolean isDebugVersion(Context context) {
        try {
            return (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 是否正在调试
     *
     * @return
     */
    public static boolean isDebugConnected() {
        try {
            return android.os.Debug.isDebuggerConnected();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 读取当前 USB 调试状态
     *
     * @return
     */
    public static String getUsbDebugStatus() {
        return CommandUtils.execute("getprop init.svc.adbd");
    }

    /**
     * 判断是否打开了允许虚拟位置
     *
     * @param context
     * @return
     */
    public static boolean isAllowMockLocation(Context context) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            try {
                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                String providerStr = LocationManager.GPS_PROVIDER;
                LocationProvider provider = locationManager.getProvider(providerStr);
                if (provider != null) {
                    locationManager.addTestProvider(
                            provider.getName()
                            , provider.requiresNetwork()
                            , provider.requiresSatellite()
                            , provider.requiresCell()
                            , provider.hasMonetaryCost()
                            , provider.supportsAltitude()
                            , provider.supportsSpeed()
                            , provider.supportsBearing()
                            , provider.getPowerRequirement()
                            , provider.getAccuracy());
                } else {
                    locationManager.addTestProvider(
                            providerStr
                            , true, true, false, false, true, true, true
                            , Criteria.POWER_HIGH, Criteria.ACCURACY_FINE);
                }
                locationManager.setTestProviderEnabled(providerStr, true);
                locationManager.setTestProviderStatus(providerStr, LocationProvider.AVAILABLE, null, System.currentTimeMillis());
                // 模拟位置可用
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            try {
                return Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION, 0) != 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static native int getTracerPid();

}