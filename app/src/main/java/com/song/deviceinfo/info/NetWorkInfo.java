package com.song.deviceinfo.info;


import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;

import com.song.deviceinfo.R;
import com.song.deviceinfo.utils.GatewayUtils;
import com.song.deviceinfo.utils.NetWorkUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.core.util.Pair;

/**
 * Created by chensongsong on 2020/5/25.
 */
public class NetWorkInfo {

    /**
     * @param context
     * @return
     */
    public static List<Pair<String, String>> getNetWorkInfo(Context context) {
        List<Pair<String, String>> list = new ArrayList<>();
        if (NetWorkUtils.isWifi(context)) {
            setWifiInfo(context, list);
        } else {
            getMobileInfo(context, list);
        }
        return list;
    }

    /**
     * wifi
     *
     * @param context
     * @return
     */
    private static void setWifiInfo(Context context, List<Pair<String, String>> list) {
        try {
            Map<String, String> ips = GatewayUtils.getIp(context);
            if (ips.containsKey("en0")) {
                list.add(new Pair<String, String>(context.getString(R.string.net_ipv4), ips.get("en0")));
                list.add(new Pair<String, String>(context.getString(R.string.net_ipv6), GatewayUtils.getHostIpv6(ips.get("network_name"))));
            } else if (ips.containsKey("vpn")) {
                list.add(new Pair<String, String>(context.getString(R.string.net_ipv4), ips.get("vpn")));
            }
            list.add(new Pair<String, String>(context.getString(R.string.net_bssid), GatewayUtils.getBssid(context)));
            list.add(new Pair<String, String>(context.getString(R.string.net_ssid), GatewayUtils.getSsid(context)));
            list.add(new Pair<String, String>(context.getString(R.string.net_mac), GatewayUtils.getMacAddress(context)));
            GatewayUtils.getProxyInfo(context, list);
            WifiInfo wifiInfo = GatewayUtils.getWifiInfo(context);
            if (wifiInfo != null) {
                list.add(new Pair<String, String>(context.getString(R.string.net_link_speed), wifiInfo.getLinkSpeed() + " Mbps"));
                list.add(new Pair<String, String>(context.getString(R.string.net_id), wifiInfo.getNetworkId() + ""));
                int rssi = wifiInfo.getRssi();
                list.add(new Pair<String, String>(context.getString(R.string.net_rssi), rssi + " dBm"));
                list.add(new Pair<String, String>(context.getString(R.string.net_level), calculateSignalLevel(rssi) + ""));
                list.add(new Pair<String, String>(context.getString(R.string.net_state), wifiInfo.getSupplicantState().name()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * mobile
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    private static void getMobileInfo(Context context, List<Pair<String, String>> list) {

        try {
            Map<String, String> ips = GatewayUtils.getIp(context);
            if (ips.containsKey("en0")) {
                list.add(new Pair<String, String>(context.getString(R.string.net_ipv4), ips.get("en0")));
                list.add(new Pair<String, String>(context.getString(R.string.net_ipv6), GatewayUtils.getHostIpv6(ips.get("network_name"))));
            } else if (ips.containsKey("vpn")) {
                list.add(new Pair<String, String>(context.getString(R.string.net_ipv4), ips.get("vpn")));
            }
            list.add(new Pair<String, String>(context.getString(R.string.net_mac), GatewayUtils.getMacAddress(context)));
            Pair<Integer, Integer> signal = GatewayUtils.getMobileSignal(context);
            list.add(new Pair<String, String>(context.getString(R.string.net_rssi), signal.first + " dBm"));
            list.add(new Pair<String, String>(context.getString(R.string.net_level), signal.second + ""));
            GatewayUtils.getProxyInfo(context, list);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static final int MIN_RSSI = -100;
    private static final int MAX_RSSI = -55;

    /**
     * 根据 rssi 计算 level
     *
     * @param rssi
     * @return
     */
    private static int calculateSignalLevel(int rssi) {

        if (rssi <= MIN_RSSI) {
            return 0;
        } else if (rssi >= MAX_RSSI) {
            return 4;
        } else {
            float inputRange = (MAX_RSSI - MIN_RSSI);
            float outputRange = 4;
            return (int) ((float) (rssi - MIN_RSSI) * outputRange / inputRange);
        }
    }

}
