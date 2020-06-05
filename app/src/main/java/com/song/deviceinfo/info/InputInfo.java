package com.song.deviceinfo.info;

import android.os.Build;
import android.text.TextUtils;
import android.view.InputDevice;

import com.song.deviceinfo.model.beans.InputBean;
import com.song.deviceinfo.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chensongsong on 2020/6/4.
 */
public class InputInfo {

    private static List<InputBean> inputList = new ArrayList<>();

    public static List<InputBean> getInputInfo() {
        if (inputList.isEmpty()) {
            // maybe exec getevent -pl
            String devices = FileUtils.readFile("/proc/bus/input/devices");
            if (!TextUtils.isEmpty(devices)) {
                readDevices(devices);
            } else {
                getDevicesByInterface();
            }
        }
        return inputList;
    }

    private static void getDevicesByInterface() {
        int[] ids = InputDevice.getDeviceIds();
        for (int i = 0; i < ids.length; i++) {
            InputDevice device = InputDevice.getDevice(ids[i]);
            if (device != null) {
                String name = device.getName();
                if (name != null) {
                    InputBean inputBean = new InputBean();
                    inputBean.setName(device.getName());
                    inputBean.setHandlers(device.getDescriptor());
                    StringBuilder sb = new StringBuilder();
                    sb.append("id: ");
                    sb.append(device.getId());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        sb.append(" vendor: ");
                        sb.append(device.getVendorId());
                        sb.append(" product: ");
                        sb.append(device.getProductId());
                    }
                    inputBean.setAttribute(sb.toString());
                    inputList.add(inputBean);
                }
            }
        }
    }

    public static boolean isMtk() {
        int[] ids = InputDevice.getDeviceIds();
        for (int id : ids) {
            InputDevice inputDevice = InputDevice.getDevice(id);
            if (inputDevice != null) {
                String name = inputDevice.getName();
                if (name != null && name.equals("mtk-tpd")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 从文件读取配置
     *
     * @param devices
     */
    private static void readDevices(String devices) {
        for (String device : devices.split("\n\n")) {
            if (device != null && !device.isEmpty()) {
                Bean bean = new Bean(device);
                String name = bean.getName();
                if (name != null && !name.isEmpty()) {
                    InputBean inputBean = new InputBean();
                    inputBean.setName(bean.name);
                    inputBean.setSys("sysfs: " + bean.sys);
                    inputBean.setHandlers("handlers: " + bean.handlers);
                    StringBuffer buffer = new StringBuffer();
                    buffer.append("bus: ");
                    buffer.append(bean.bus);
                    buffer.append(" vendor: ");
                    buffer.append(bean.vendor);
                    buffer.append(" product: ");
                    buffer.append(bean.product);
                    buffer.append(" ver: ");
                    buffer.append(bean.version);
                    inputBean.setAttribute(buffer.toString());
                    inputList.add(inputBean);
                }
            }
        }
    }


    public static class Bean {
        private String bus;

        private String vendor;

        private String product;

        private String version;

        private String name;

        private String phys;

        private String sys;

        private String handlers;

        public String getName() {
            return name;
        }

        private Bean(String device) {
            for (String line : device.split("\n")) {
                if (!line.isEmpty())
                    if (line.startsWith("I:")) {
                        this.bus = parseAttribute(line, "Bus");
                        this.vendor = parseAttribute(line, "Vendor");
                        this.product = parseAttribute(line, "Product");
                        this.version = parseAttribute(line, "Version");
                    } else if (line.startsWith("N:")) {
                        this.name = parseValue(line, "Name");
                        if (this.name != null) {
                            this.name = this.name.replace("\"", "");
                        }
                    } else if (line.startsWith("P:")) {
                        this.phys = parseValue(line, "Phys");
                    } else if (line.startsWith("S:")) {
                        this.sys = parseValue(line, "Sysfs");
                    } else if (line.startsWith("H:")) {
                        this.handlers = parseValue(line, "Handlers");
                    }
            }
        }

        private static String parseValue(String data, String key) {
            StringBuffer sb = new StringBuffer();
            sb.append(key);
            sb.append("=");
            int i = data.indexOf(sb.toString());
            return (i >= 0) ? data.substring(i + key.length() + 1) : null;
        }

        private static String parseAttribute(String data, String key) {
            StringBuffer sb = new StringBuffer();
            sb.append(key);
            sb.append("=");
            int i = data.indexOf(sb.toString());
            if (i >= 0) {
                int j = key.length() + i + 1;
                i = data.indexOf(" ", i);
                return (i >= 0) ? data.substring(j, i) : data.substring(j);
            }
            return null;
        }

    }

}
