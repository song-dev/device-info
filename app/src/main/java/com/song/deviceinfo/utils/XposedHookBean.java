package com.song.deviceinfo.utils;

/**
 * Hook Bean Xposed
 *
 * @author gunaonian
 * @date 2019-03-21
 */
public class XposedHookBean {
    private boolean xposedApp = false;
    private boolean xposedImei = false;
    private boolean xposedSerial = false;
    private boolean xposedSsid = false;
    private boolean xposedMac = false;
    private boolean xposedAddress = false;
    private boolean xposedAndroidId = false;
    private boolean xposedImsi = false;
    private boolean xposedLatitude = false;
    private boolean xposedLongitude = false;

    public XposedHookBean() {
    }

    public boolean isXposedApp() {
        return xposedApp;
    }

    public void setXposedApp(boolean xposedApp) {
        this.xposedApp = xposedApp;
    }

    public boolean isXposedImei() {
        return xposedImei;
    }

    public void setXposedImei(boolean xposedImei) {
        this.xposedImei = xposedImei;
    }

    public boolean isXposedSerial() {
        return xposedSerial;
    }

    public void setXposedSerial(boolean xposedSerial) {
        this.xposedSerial = xposedSerial;
    }

    public boolean isXposedSsid() {
        return xposedSsid;
    }

    public void setXposedSsid(boolean xposedSsid) {
        this.xposedSsid = xposedSsid;
    }

    public boolean isXposedMac() {
        return xposedMac;
    }

    public void setXposedMac(boolean xposedMac) {
        this.xposedMac = xposedMac;
    }

    public boolean isXposedAddress() {
        return xposedAddress;
    }

    public void setXposedAddress(boolean xposedAddress) {
        this.xposedAddress = xposedAddress;
    }

    public boolean isXposedAndroidId() {
        return xposedAndroidId;
    }

    public void setXposedAndroidId(boolean xposedAndroidId) {
        this.xposedAndroidId = xposedAndroidId;
    }

    public boolean isXposedImsi() {
        return xposedImsi;
    }

    public void setXposedImsi(boolean xposedImsi) {
        this.xposedImsi = xposedImsi;
    }

    public boolean isXposedLatitude() {
        return xposedLatitude;
    }

    public void setXposedLatitude(boolean xposedLatitude) {
        this.xposedLatitude = xposedLatitude;
    }

    public boolean isXposedLongitude() {
        return xposedLongitude;
    }

    public void setXposedLongitude(boolean xposedLongitude) {
        this.xposedLongitude = xposedLongitude;
    }
}
