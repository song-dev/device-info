package com.song.deviceinfo.info;


import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
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
import android.util.Log;

import com.song.deviceinfo.utils.Constants;
import com.song.deviceinfo.utils.NetWorkUtils;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import androidx.core.util.Pair;

/**
 * Created by chensongsong on 2020/5/25.
 */
public class NetWorkInfo {

    private static final String WIFI = "WIFI";
    private static final String TAG = NetWorkInfo.class.getSimpleName();

    /**
     * @param context
     * @return
     */
    public static List<Pair<String, String>> getNetInfo(Context context) {
        List<Pair<String, String>> list = new ArrayList<>();
        if (NetWorkUtils.isWifi(context)) {
            // wifi
            setWifiInfo(context, list);
        } else {
            getMobileDbm(context, list);
        }
        return list;
    }

    /**
     * mobile
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    private static void getMobileDbm(Context context, List<Pair<String, String>> list) {
        int dbm = -1;
        int level = 0;
        try {
            list.add(new Pair<String, String>("IPv4", getNetIPV4()));
            list.add(new Pair<String, String>("IPv6", getNetIP()));
            list.add(new Pair<String, String>("Mac", getMac(context)));
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            List<CellInfo> cellInfoList;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (tm == null) {
                    return;
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
            list.add(new Pair<String, String>("dbm", dbm + ""));
            list.add(new Pair<String, String>("Level", level + ""));
        } catch (Exception e) {
            Log.i(TAG, e.toString());
        }
    }

    private static String getNetIPV4() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address && !inetAddress.isLinkLocalAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            Log.i(TAG, e.toString());
        }
        return null;
    }

    private static String getNetIP() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet6Address && !inetAddress.isLinkLocalAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            Log.i(TAG, e.toString());
        }
        return null;
    }

    /**
     * Anything worse than or equal to this will show 0 bars.
     */
    private static final int MIN_RSSI = -100;

    /**
     * Anything better than or equal to this will show the max bars.
     */
    private static final int MAX_RSSI = -55;

    private static int calculateSignalLevel(int rssi) {

        if (rssi <= MIN_RSSI) {
            return 0;
        } else if (rssi >= MAX_RSSI) {
            return 4;
        } else {
            float inputRange = (MAX_RSSI - MIN_RSSI);
            float outputRange = (4);
            return (int) ((float) (rssi - MIN_RSSI) * outputRange / inputRange);
        }
    }

    /**
     * 获取WifiInfo
     *
     * @param mContext
     * @return
     */
    @SuppressLint("MissingPermission")
    private static WifiInfo getWifiInfo(Context mContext) {
        WifiManager mWifiManager = (WifiManager) mContext.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (mWifiManager != null) {
            return mWifiManager.getConnectionInfo();
        }
        return null;
    }

    /**
     * 是否使用代理上网
     *
     * @param context
     * @return
     */
    private static void isWifiProxy(Context context, List<Pair<String, String>> list) {
        // 是否大于等于4.0
        final boolean isIcsOrLater = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
        String proxyAddress;
        int proxyPort;
        if (isIcsOrLater) {
            proxyAddress = System.getProperty("http.proxyHost");
            String portStr = System.getProperty("http.proxyPort");
            proxyPort = Integer.parseInt((portStr != null ? portStr : "-1"));
        } else {
            proxyAddress = android.net.Proxy.getHost(context);
            proxyPort = android.net.Proxy.getPort(context);
        }
        if ((!TextUtils.isEmpty(proxyAddress)) && (proxyPort != -1)) {
            list.add(new Pair<String, String>("Proxy", "是"));
            list.add(new Pair<String, String>("Proxy Address", proxyAddress));
            list.add(new Pair<String, String>("Proxy Port", proxyPort + ""));
        } else {
            list.add(new Pair<String, String>("Proxy", "否"));
        }


    }

    /**
     * wifi
     *
     * @param mContext
     * @return
     */
    private static void setWifiInfo(Context mContext, List<Pair<String, String>> list) {
        try {
            WifiInfo mWifiInfo = getWifiInfo(mContext);
            int ip = mWifiInfo.getIpAddress();
            String strIp = "" + (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "." + ((ip >> 24) & 0xFF);
            list.add(new Pair<String, String>("IPv4", strIp));
            list.add(new Pair<String, String>("IPv6", getNetIP()));
            list.add(new Pair<String, String>("LinkSpeed", mWifiInfo.getLinkSpeed() + "Mbps"));
            list.add(new Pair<String, String>("BSSID", mWifiInfo.getBSSID()));
            list.add(new Pair<String, String>("SSID", mWifiInfo.getSSID().replace("\"", "")));
            list.add(new Pair<String, String>("MAC", getMac(mContext)));
            list.add(new Pair<String, String>("ID", mWifiInfo.getNetworkId() + ""));
            int rssi = mWifiInfo.getRssi();
            list.add(new Pair<String, String>("RSSI", rssi + ""));
            list.add(new Pair<String, String>("Level", calculateSignalLevel(rssi) + ""));
            isWifiProxy(mContext, list);
            list.add(new Pair<String, String>("SupplicantState", mWifiInfo.getSupplicantState().name()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * >=22的sdk则进行如下算法 mac
     *
     * @return
     */
    private static String getMacForBuild() {
        try {
            for (
                    Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                    networkInterfaces.hasMoreElements(); ) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                if ("wlan0".equals(networkInterface.getName())) {
                    byte[] hardwareAddress = networkInterface.getHardwareAddress();
                    if (hardwareAddress == null || hardwareAddress.length == 0) {
                        continue;
                    }
                    StringBuilder buf = new StringBuilder();
                    for (byte b : hardwareAddress) {
                        buf.append(String.format("%02X:", b));
                    }
                    if (buf.length() > 0) {
                        buf.deleteCharAt(buf.length() - 1);
                    }
                    return buf.toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Constants.UNKNOWN;
    }


    /**
     * get macAddress
     *
     * @param mContext
     * @return
     */
    @SuppressLint("HardwareIds")
    public static String getMac(Context mContext) {
        if (Build.VERSION.SDK_INT >= 23) {
            return getMacForBuild();
        } else {
            try {
                return getWifiInfo(mContext).getMacAddress();
            } catch (Exception e) {
                return Constants.UNKNOWN;
            }

        }
    }
}
