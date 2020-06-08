package com.song.deviceinfo.model.beans;

import org.jetbrains.annotations.NotNull;

/**
 * Created by chensongsong on 2020/6/8.
 */
public class USBBean {

    public String path;

    public String num;

    public String manufacturerName;

    public String productName;

    public String version;

    public String serialNumber;

    public String vendorId;

    public String productId;

    @NotNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("UsbDevice[mName=");
        sb.append(this.path);
        sb.append(",mVendorId=");
        sb.append(this.vendorId);
        sb.append(",mProductId=");
        sb.append(this.productId);
        sb.append(",mManufacturerName=");
        sb.append(this.manufacturerName);
        sb.append(",mProductName=");
        sb.append(this.productName);
        sb.append(",mVersion=");
        sb.append(this.version);
        sb.append(",mSerialNumber=");
        sb.append(this.serialNumber);
        sb.append(",mConfigurations=[");
        sb = new StringBuilder(sb.toString());
        sb.append("]");
        return sb.toString();
    }

}
