package com.song.deviceinfo.utils;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.os.BatteryManager;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * 模拟器检测
 * Created by chensongsong on 2020/7/9.
 */
public class EmulatorUtils {

    /**
     * 获取 su 版本
     *
     * @return
     */
    public static String getSuVersion() {
        return CommandUtils.execute("su -v");
    }

    /**
     * 读取声卡型号
     *
     * @return
     */
    public static String getSound() {
        String sound = CommandUtils.execute("cat /proc/asound/card0/id");
        if (TextUtils.isEmpty(sound)) {
            sound = FileUtils.readFile("cat /proc/asound/card0/id");
        }
        return sound;
    }

    /**
     * 桌面应用
     *
     * @param context
     * @return
     */
    public static String getLauncherPackageName(Context context) {
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        final ResolveInfo res = context.getPackageManager().resolveActivity(intent, 0);
        if (res == null) {
            return "$unknown";
        }
        if (res.activityInfo == null) {
            return "$unknown";
        }
        return res.activityInfo.packageName;
    }

    private static JSONObject brandJson = null;

    /**
     * @return 品牌信息列表
     */
    public static String getBrandInfo() {

        if (brandJson != null && brandJson.length() > 0) {
            return brandJson.toString();
        }

        if (FileUtils.exists("/system/app")) {
            File file = new File("/system/app");
            String[] list = file.list();
            if (list != null && list.length > 0) {
                JSONArray hw = new JSONArray();
                JSONArray oppo = new JSONArray();
                JSONArray vivo = new JSONArray();
                JSONArray xm = new JSONArray();
                JSONArray op = new JSONArray();
                JSONArray smartisan = new JSONArray();
                JSONArray samsung = new JSONArray();
                JSONArray lenovo = new JSONArray();
                JSONArray zte = new JSONArray();
                JSONArray mz = new JSONArray();

                // 读取值
                for (String s : list) {
                    s = s.toLowerCase();
                    if (((s.startsWith("hw") || s.contains("huawei")) && !s.equals("hw"))) {
                        hw.put(s);
                    } else if (s.contains("miui") || s.contains("xiaomi")) {
                        xm.put(s);
                    } else if (s.contains("oppo")) {
                        oppo.put(s);
                    } else if (s.contains("vivo")) {
                        vivo.put(s);
                    } else if (s.contains("samsung")) {
                        samsung.put(s);
                    } else if (s.startsWith("op")) {
                        op.put(s);
                    } else if (s.contains("smartisan")) {
                        smartisan.put(s);
                    } else if (s.contains("lenovo")) {
                        lenovo.put(s);
                    } else if (s.startsWith("zte")) {
                        zte.put(s);
                    } else if (s.startsWith("mz")) {
                        mz.put(s);
                    }
                }

                if (FileUtils.exists("/system/emui")) {
                    hw.put("/system/emui");
                }

                JSONObject jsonObject = new JSONObject();
                if (hw.length() > 0) {
                    try {
                        jsonObject.put("huawei", hw);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (xm.length() > 0) {
                    try {
                        jsonObject.put("xiaomi", xm);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (oppo.length() > 0) {
                    try {
                        jsonObject.put("oppo", oppo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (vivo.length() > 0) {
                    try {
                        jsonObject.put("vivo", vivo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (samsung.length() > 0) {
                    try {
                        jsonObject.put("samsung", samsung);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (op.length() > 0) {
                    try {
                        jsonObject.put("oneplus", op);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (smartisan.length() > 0) {
                    try {
                        jsonObject.put("smartisan", smartisan);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (lenovo.length() > 0) {
                    try {
                        jsonObject.put("lenovo", lenovo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (zte.length() > 0) {
                    try {
                        jsonObject.put("zte", zte);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (mz.length() > 0) {
                    try {
                        jsonObject.put("meizu", mz);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                brandJson = jsonObject;
                return brandJson.toString();
            }
        }
        return null;
    }

    public static String getBatteryInfo(Context context) {
        Intent batteryStatus = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        if (batteryStatus != null) {
            StringBuffer sb = new StringBuffer();
            // 电量
            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            sb.append("Level: ");
            sb.append(level);
            // unknown=1, charging=2, discharging=3, not charging=4, full=5
            int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            sb.append("    Status: ");
            sb.append(status);
            return sb.toString();
        }
        return null;
    }

}
