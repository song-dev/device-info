package com.song.deviceinfo.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;

import com.song.deviceinfo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.core.app.ActivityCompat;
import androidx.core.util.Pair;

/**
 * Created by chensongsong on 2020/4/29.
 */
public class GatewayUtils {

    /**
     * 获取内网 IPv6
     *
     * @param name
     * @return
     */
    public static String getHostIpv6(String name) {

        String hostIp = "";
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if ((ia instanceof Inet6Address) && TextUtils.equals(name, ni.getName()) && !ia.isLoopbackAddress()) {
                        String address = ia.getHostAddress().toLowerCase();
                        if (address.indexOf('%') > -1) {
                            address = address.substring(0, address.indexOf('%'));
                        }
                        hostIp = address;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hostIp;

    }

    /**
     * 获取 ipv4 地址
     *
     * @return
     */
    public static Map<String, String> getIp(Context context) {

        Map<String, String> map = new HashMap<>();
        try {
            boolean vpn = isVpn(context);
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;
                    }
                    if (vpn) {
                        // vpn ip
                        if (!ia.isLoopbackAddress() && !ia.isSiteLocalAddress()) {
                            map.put("vpn", TextUtils.isEmpty(ia.getHostAddress()) ? Constants.UNKNOWN : ia.getHostAddress());
                        } else if (!ia.isLoopbackAddress() && ia.isSiteLocalAddress()) {
                            map.put("en0", TextUtils.isEmpty(ia.getHostAddress()) ? Constants.UNKNOWN : ia.getHostAddress());
                            map.put("network_name", TextUtils.isEmpty(ni.getName()) ? Constants.UNKNOWN : ni.getName());
                        }
                    } else {
                        // vpn 关闭，数据流量
                        if (!ia.isLoopbackAddress()) {
                            map.put("en0", TextUtils.isEmpty(ia.getHostAddress()) ? Constants.UNKNOWN : ia.getHostAddress());
                            map.put("network_name", TextUtils.isEmpty(ni.getName()) ? Constants.UNKNOWN : ni.getName());
                            // 内网 ipv6
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;

    }

    /**
     * 获取代理信息
     *
     * @param context
     * @param list
     * @return
     */
    public static void getProxyInfo(Context context, List<Pair<String, String>> list) {

        String proxyAddress = System.getProperty("http.proxyHost");
        String port = System.getProperty("http.proxyPort");
        int proxyPort = Integer.parseInt((port != null ? port : "-1"));
        boolean isProxy = (!TextUtils.isEmpty(proxyAddress) && (proxyPort != -1));
        try {
            if (isVpn(context)) {
                list.add(new Pair<>(context.getString(R.string.net_proxy), "vpn"));
            } else if (isProxy) {
                list.add(new Pair<>(context.getString(R.string.net_proxy), "proxy"));
                list.add(new Pair<>(context.getString(R.string.net_proxy_host), proxyAddress));
                list.add(new Pair<>(context.getString(R.string.net_proxy_port), port));
            } else {
                list.add(new Pair<>(context.getString(R.string.net_proxy), "否"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static boolean isVpn(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getNetworkInfo(17);
            return networkInfo.isConnected();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取mac地址
     *
     * @param context
     * @return
     */
    @SuppressLint("HardwareIds")
    public static String getMacAddress(Context context) {
        String macAddress = Constants.UNKNOWN;
        WifiInfo wifiInfo = getWifiInfo(context);
        if (wifiInfo != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                macAddress = getMac();
            } else {
                macAddress = wifiInfo.getMacAddress();
            }
        }
        return macAddress;
    }

    /**
     * 通过网卡获取 Mac
     *
     * @return
     */
    private static String getMac() {

        String mac = Constants.UNKNOWN;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                if ("wlan0".equals(ni.getName())) {
                    byte[] hardwareAddress = ni.getHardwareAddress();
                    if (hardwareAddress == null || hardwareAddress.length == 0) {
                        continue;
                    }
                    StringBuilder sb = new StringBuilder();
                    for (byte b : hardwareAddress) {
                        sb.append(String.format("%02X:", b));
                    }
                    if (sb.length() > 0) {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                    return sb.toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mac;

    }

    /**
     * 拿到bssid
     *
     * @param context
     * @return
     */
    public static String getBssid(Context context) {
        String macAddress = Constants.UNKNOWN;
        WifiInfo wifiInfo = getWifiInfo(context);
        if (wifiInfo != null) {
            String bssid = wifiInfo.getBSSID();
            if (TextUtils.isEmpty(bssid)) {
                macAddress = Constants.UNKNOWN;
            } else {
                macAddress = bssid;
            }
        }
        return macAddress;
    }

    /**
     * 获取广播 ID
     *
     * @param context
     * @return
     */
    public static String getSsid(Context context) {
        String ssid = Constants.UNKNOWN;
        WifiInfo wifiInfo = getWifiInfo(context);
        if (wifiInfo != null) {
            ssid = wifiInfo.getSSID().replace("\"", "");
        }
        return ssid;
    }

    /**
     * 获取WifiInfo
     *
     * @param context
     * @return
     */
    public static WifiInfo getWifiInfo(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            return wifiManager.getConnectionInfo();
        }
        return null;
    }

    @SuppressLint("MissingPermission")
    public static Pair<Integer, Integer> getMobileSignal(Context context) {
        int dbm = -1;
        int level = 0;
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            List<CellInfo> cellInfoList;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (tm == null) {
                    return new Pair<>(dbm, level);
                }
                cellInfoList = tm.getAllCellInfo();
                if (null != cellInfoList) {
                    for (CellInfo cellInfo : cellInfoList) {
                        if (cellInfo instanceof CellInfoGsm) {
                            CellSignalStrengthGsm cellSignalStrengthGsm = ((CellInfoGsm) cellInfo).getCellSignalStrength();
                            dbm = cellSignalStrengthGsm.getDbm();
                            level = cellSignalStrengthGsm.getLevel();
                        } else if (cellInfo instanceof CellInfoCdma) {
                            CellSignalStrengthCdma cellSignalStrengthCdma =
                                    ((CellInfoCdma) cellInfo).getCellSignalStrength();
                            dbm = cellSignalStrengthCdma.getDbm();
                            level = cellSignalStrengthCdma.getLevel();
                        } else if (cellInfo instanceof CellInfoLte) {
                            CellSignalStrengthLte cellSignalStrengthLte = ((CellInfoLte) cellInfo).getCellSignalStrength();
                            dbm = cellSignalStrengthLte.getDbm();
                            level = cellSignalStrengthLte.getLevel();

                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                            if (cellInfo instanceof CellInfoWcdma) {
                                CellSignalStrengthWcdma cellSignalStrengthWcdma =
                                        ((CellInfoWcdma) cellInfo).getCellSignalStrength();
                                dbm = cellSignalStrengthWcdma.getDbm();
                                level = cellSignalStrengthWcdma.getLevel();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Pair<>(dbm, level);
    }


    private static float checkBattery(Context context) {
        //通过粘性广播读取电量
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent intentBattery = context.registerReceiver(null, intentFilter);//注意，粘性广播不需要广播接收器
        if (intentBattery != null) {
            //获取当前电量
            int batteryLevel = intentBattery.getIntExtra("level", 0);
            //电量的总刻度
            int batterySum = intentBattery.getIntExtra("scale", 100);
            float rotatio = 100 * (float) batteryLevel / (float) batterySum;
            return rotatio;
        }
        return 0;
    }

    /**
     * 获取电量 by
     *
     * @param context
     * @return
     */
    private static JSONObject getBattroy(Context context) {
        JSONObject jsonObject = new JSONObject();
        Intent batteryStatus = context.registerReceiver(null,
                new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        if (batteryStatus != null) {
            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            double batteryLevel = -1;
            if (level != -1 && scale != -1) {
                batteryLevel = (double) level / scale;
            }
            // unknown=1, charging=2, discharging=3, not charging=4, full=5
            int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            // ac=1, usb=2, wireless=4
            int plugState = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            // unknown=1, good=2, overheat=3, dead=4, over voltage=5, unspecified failure=6, cold=7
            int health = batteryStatus.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);
            try {
                jsonObject.put("br", batteryLevel + "");
                jsonObject.put("bs", status + "");
                jsonObject.put("plugState", plugState + "");
                jsonObject.put("health", health + "");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    private static String getIMSI(Context context) {

        String imsi = null;
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            imsi = tm.getSubscriberId();
        } catch (Exception e) {
            // e.printStackTrace();
            imsi = "$unknown";
        }
        return imsi;

    }

    /**
     * 运行商信息 carrier
     *
     * @param context
     * @return
     */
    private static int getOperators(Context context) {

        int OperatorsName = 0;
        String imsi = getIMSI(context);
        if (!TextUtils.isEmpty(imsi) && !TextUtils.equals(imsi, "$unknown")) {
            // IMSI号前面3位460是国家，紧接着后面2位00 运营商代码
            if (imsi.startsWith("46000") || imsi.startsWith("46002") || imsi.startsWith("46004") || imsi.startsWith("46007")) {
                OperatorsName = 1;
            } else if (imsi.startsWith("46001") || imsi.startsWith("46006") || imsi.startsWith("46009")) {
                OperatorsName = 2;
            } else if (imsi.startsWith("46003") || imsi.startsWith("46005") || imsi.startsWith("46011")) {
                OperatorsName = 3;
            }
        }
        return OperatorsName;

    }

    /**
     * 获取mems
     *
     * @param context
     * @return
     */
    private static String getTotalMemory(Context context) {
        String str1 = "/proc/meminfo";
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try {
            FileReader localFileReader;
            localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            initial_memory = Long.valueOf(arrayOfString[1]) * 1024;
            localBufferedReader.close();
        } catch (Exception ignored) {
            return "$unknown";
        }
        return Formatter.formatFileSize(context, initial_memory);
    }

}
