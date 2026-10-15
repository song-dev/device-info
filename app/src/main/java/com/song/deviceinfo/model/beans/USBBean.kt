package com.song.deviceinfo.model.beans

import org.jetbrains.annotations.NotNull

/**
 * Created by chensongsong on 2020/6/8.
 */
class USBBean {

    var path: String? = null

    var num: String? = null

    var manufacturerName: String? = null

    var productName: String? = null

    var version: String? = null

    var serialNumber: String? = null

    var vendorId: String? = null

    var productId: String? = null

    @NotNull
    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("UsbDevice[mName=")
        sb.append(this.path)
        sb.append(",mVendorId=")
        sb.append(this.vendorId)
        sb.append(",mProductId=")
        sb.append(this.productId)
        sb.append(",mManufacturerName=")
        sb.append(this.manufacturerName)
        sb.append(",mProductName=")
        sb.append(this.productName)
        sb.append(",mVersion=")
        sb.append(this.version)
        sb.append(",mSerialNumber=")
        sb.append(this.serialNumber)
        sb.append(",mConfigurations=[")
        sb.append("]")
        return sb.toString()
    }
}
