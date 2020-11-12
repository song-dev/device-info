package com.song.deviceinfo.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.song.deviceinfo.model.beans.SimBean;

import java.util.ArrayList;
import java.util.List;

import androidx.core.util.Pair;

/**
 * Created by chensongsong on 2020/8/3.
 */
public class DeviceUtils {

    public static String getAndroidId(Context context) {
        try {
            return Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Constants.UNKNOWN;
    }

    @SuppressLint("MissingPermission")
    public static String getIMEI(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Constants.UNKNOWN;
    }

    @SuppressLint("MissingPermission")
    public static void getDeviceInfo(Context context, List<Pair<String, String>> list) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                list.add(new Pair<>("IMEI2", tm.getImei(1)));
                // TODO 另外方式 CommandUtils.getProperty("persist.sys.meid")
                list.add(new Pair<>("MEID", tm.getMeid()));
                list.add(new Pair<>("MEID2", tm.getMeid(1)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            list.add(new Pair<>("IMSI", tm.getSubscriberId()));
            list.add(new Pair<>("SERIAL", getSerial()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getSerial() {
        try {
            String serial = Build.SERIAL;
            if (TextUtils.isEmpty(serial)) {
                serial = CommandUtils.getProperty("no.such.thing");
            }
            if (TextUtils.isEmpty(serial)) {
                serial = CommandUtils.getProperty("ro.serialno");
            }
            if (TextUtils.isEmpty(serial)) {
                serial = CommandUtils.getProperty("ro.boot.serialno");
            }
            if (TextUtils.isEmpty(serial)) {
                serial = Constants.UNKNOWN;
            }
            return serial;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Constants.UNKNOWN;
    }

    @SuppressLint("MissingPermission")
    public static String getIccId(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getSimSerialNumber();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Constants.UNKNOWN;
    }

    @SuppressLint("MissingPermission")
    public static void getSimInfo(Context context, List<Pair<String, String>> list) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            list.add(new Pair<>("SIM ISO", tm.getSimCountryIso()));
            list.add(new Pair<>("SIM OP ID", tm.getSimOperator()));
            list.add(new Pair<>("SIM OP NAME", tm.getSimOperatorName()));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                list.add(new Pair<>("SIM Id", tm.getSimCarrierId() + ""));
                list.add(new Pair<>("SIM IdName", tm.getSimCarrierIdName().toString()));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                list.add(new Pair<>("SIM SpecificId", tm.getSimSpecificCarrierId() + ""));
                list.add(new Pair<>("SIM SpecificIdName", tm.getSimSpecificCarrierIdName().toString()));
                list.add(new Pair<>("SIM SpecificIdFromMM", tm.getCarrierIdFromSimMccMnc() + ""));
            }
            list.add(new Pair<>("SIM STATE", tm.getSimState() + ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("MissingPermission")
    public static void getOtherInfo(Context context, List<Pair<String, String>> list) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                list.add(new Pair<>("NET Specifier", tm.getNetworkSpecifier()));
            }
            list.add(new Pair<>("NET ISO", tm.getNetworkCountryIso()));
            list.add(new Pair<>("NET OP", tm.getNetworkOperator()));
            list.add(new Pair<>("NET OP NAME", tm.getNetworkOperatorName()));
            list.add(new Pair<>("NET TYPE", tm.getNetworkType() + ""));
            list.add(new Pair<>("Device Soft Version", tm.getDeviceSoftwareVersion()));
            list.add(new Pair<>("LINE NUMBER", tm.getLine1Number()));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                list.add(new Pair<>("MAN CODE", tm.getManufacturerCode()));
                list.add(new Pair<>("Allocation Code", tm.getTypeAllocationCode()));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                list.add(new Pair<>("MMS UA", tm.getMmsUserAgent()));
                list.add(new Pair<>("MMS UA URL", tm.getMmsUAProfUrl()));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                list.add(new Pair<>("NAI", tm.getNai()));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                list.add(new Pair<>("DATA NET TYPE", tm.getDataNetworkType() + ""));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                list.add(new Pair<>("Phone Count", tm.getPhoneCount() + ""));
            }
            List<SimBean> simBeans = querySimInfo(context);
            for (SimBean ben : simBeans) {
                int simId = ben.getSimId();
                list.add(new Pair<>("SIM " + simId + " ID", ben.getId() + ""));
                list.add(new Pair<>("SIM " + simId + " ICCID", ben.getIccId()));
                list.add(new Pair<>("SIM " + simId + " CarrierName", ben.getCarrierName()));
                list.add(new Pair<>("SIM " + simId + " DisplayName", ben.getDisplayName()));
                list.add(new Pair<>("SIM " + simId + " Number", ben.getNumber()));
                list.add(new Pair<>("SIM " + simId + " MCC", ben.getMcc()));
                list.add(new Pair<>("SIM " + simId + " MNC", ben.getMnc()));
            }
            getBuildInfo(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询数据库 SIM 信息 (同样需要 READ_PHONE_STATUS 权限)
     *
     * @param context
     * @return
     */
    private static List<SimBean> querySimInfo(Context context) {
        List<SimBean> list = new ArrayList<>();
        try {
            Uri uri = Uri.parse("content://telephony/siminfo"); //访问raw_contacts表
            ContentResolver resolver = context.getContentResolver();
            Cursor cursor = resolver.query(uri, new String[]{"_id", "icc_id", "sim_id", "display_name",
                    "carrier_name", "name_source", "color", "number", "display_number_format",
                    "data_roaming", "mcc", "mnc"}, "sim_id>=0", null, "sim_id");
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id")));
                    int simId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("sim_id")));
                    String iccId = cursor.getString(cursor.getColumnIndex("icc_id"));
                    String carrierName = cursor.getString(cursor.getColumnIndex("carrier_name"));
                    String displayName = cursor.getString(cursor.getColumnIndex("display_name"));
                    String number = cursor.getString(cursor.getColumnIndex("number"));
                    String mcc = cursor.getString(cursor.getColumnIndex("mcc"));
                    String mnc = cursor.getString(cursor.getColumnIndex("mnc"));
                    SimBean info = new SimBean(id, simId, iccId, carrierName, displayName, number, mcc, mnc);
                    list.add(info);
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private static void getBuildInfo(List<Pair<String, String>> list) {
        String[] array = CommandUtils.exec("getprop");
        for (String line : array) {
            if (!TextUtils.isEmpty(line)
                    && (line.contains("imei")
                    || line.contains("iccid")
                    || line.contains("imsi")
                    || line.contains("meid")
                    || line.contains("serialno")
            )) {
                String[] split = line.split(":");
                if (split.length == 2) {
                    try {
                        if (!"[]".equals(split[1].trim())) {
                            list.add(new Pair<>(split[0].trim(), split[1].trim()));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

}
