package com.song.deviceinfo.info;

import android.content.Context;
import android.text.TextUtils;

import com.song.deviceinfo.R;
import com.song.deviceinfo.model.beans.USBBean;
import com.song.deviceinfo.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.core.util.Pair;

/**
 * Created by chensongsong on 2020/6/8.
 */
public class USBInfo {

    /**
     * 获取 USB 设备信息
     *
     * @param context
     * @return
     */
    public static List<Pair<String, String>> getUSBInfo(Context context) {
        List<Pair<String, String>> list = new ArrayList<>();
        List<USBBean> info = getInfo();
        if (!info.isEmpty()) {
            for (int i = 0; i < info.size(); i++) {
                USBBean bean = info.get(i);
                list.add(new Pair<>(context.getString(R.string.usb_vendor), bean.manufacturerName));
                list.add(new Pair<>(context.getString(R.string.usb_model), bean.productName));
                list.add(new Pair<>(context.getString(R.string.usb_device_id), bean.vendorId + ":" + bean.productId));
                if (!TextUtils.isEmpty(bean.serialNumber)) {
                    list.add(new Pair<>(context.getString(R.string.usb_serial), bean.serialNumber));
                }
                list.add(new Pair<>(context.getString(R.string.usb_number), bean.num));
                list.add(new Pair<>(context.getString(R.string.usb_path), bean.path));
                list.add(new Pair<>(context.getString(R.string.usb_version), bean.version));
                if (i != (info.size() - 1)) {
                    list.add(new Pair<>("", ""));
                }
            }
        } else {
            list.add(new Pair<>(context.getString(R.string.usb_devices), context.getString(R.string.usb_not_found)));
        }
        return list;
    }

    public static boolean isUSBDrivers() {
        File[] files = (new File("/sys/bus/usb/drivers/usb/")).listFiles();
        if (files == null) {
            return false;
        }
        for (File file : files) {
            String name = file.getName();
            if (name.startsWith("usb") || name.contains("-")) {
                return true;
            }
        }
        return false;
    }

    private static List<USBBean> getInfo() {
        File file = new File("/sys/bus/usb/drivers/usb/");
        List<USBBean> list = new ArrayList<>();
        File[] files = file.listFiles();
        if (files == null) {
            return list;
        }
        for (int i = 0; i < files.length; i++) {
            String name = files[i].getName();
            if (name.startsWith("usb") || name.contains("-")) {
                StringBuilder sb = new StringBuilder();
                sb.append("/sys/bus/usb/drivers/usb/");
                sb.append(name);
                sb.append("/");
                USBBean bean = new USBBean();
                parseUSB(sb.toString(), bean);
                list.add(bean);
            }
        }
        return list;
    }

    private static void parseUSB(String path, USBBean bean) {
        StringBuilder sb = new StringBuilder();
        sb.append(path);
        sb.append("manufacturer");
        bean.manufacturerName = FileUtils.readFile(sb.toString());
        sb = new StringBuilder();
        sb.append(path);
        sb.append("product");
        bean.productName = FileUtils.readFile(sb.toString());
        sb = new StringBuilder();
        sb.append(path);
        sb.append("serial");
        bean.serialNumber = FileUtils.readFile(sb.toString());
        sb = new StringBuilder();
        sb.append(path);
        sb.append("idProduct");
        bean.productId = FileUtils.readFile(sb.toString());
        sb = new StringBuilder();
        sb.append(path);
        sb.append("idVendor");
        bean.vendorId = FileUtils.readFile(sb.toString());
        sb = new StringBuilder();
        sb.append(path);
        sb.append("version");
        bean.version = FileUtils.readFile(sb.toString());
        sb = new StringBuilder();
        sb.append(path);
        sb.append("busnum");
        String busNum = FileUtils.readFile(sb.toString());
        sb = new StringBuilder();
        sb.append(path);
        sb.append("devnum");
        String devNum = FileUtils.readFile(sb.toString());
        if (busNum != null) {
            if (devNum != null) {
                devNum = parsePath(devNum, "0", 3);
                StringBuilder builder = new StringBuilder();
                builder.append(busNum);
                builder.append(devNum);
                bean.num = builder.toString();
                busNum = parsePath(busNum, "0", 3);
            }
        }
        StringBuilder builder = new StringBuilder();
        builder.append("/dev/bus/usb/");
        builder.append(busNum);
        builder.append("/");
        builder.append(devNum);
        bean.path = builder.toString();
    }

    private static String parsePath(String numStr, String str, int size) {
        StringBuilder sb = new StringBuilder();
        for (size -= numStr.length(); size > 0; size--) {
            sb.append(str);
        }
        sb.append(numStr);
        return sb.toString();
    }

}
