package com.song.deviceinfo.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.TextUtils
import androidx.core.util.Pair
import com.song.deviceinfo.model.beans.SimBean

/**
 * Created by chensongsong on 2020/8/3.
 */
object DeviceUtils {
    @JvmStatic
    fun getAndroidId(context: Context): String {
        try {
            return Settings.System.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Constants.UNKNOWN
    }
    
    @JvmStatic
    @SuppressLint("MissingPermission")
    fun getIMEI(context: Context): String {
        try {
            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            return tm.deviceId
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Constants.UNKNOWN
    }
    
    @SuppressLint("MissingPermission")
    fun getDeviceInfo(context: Context, list: MutableList<Pair<String, String>>) {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                list.add(Pair("IMEI2", tm.getImei(1)))
                // TODO 另外方式 CommandUtils.getProperty("persist.sys.meid")
                list.add(Pair("MEID", tm.meid))
                list.add(Pair("MEID2", tm.getMeid(1)))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            list.add(Pair("IMSI", tm.subscriberId))
            list.add(Pair("SERIAL", serial))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    private val serial: String
        get() {
            try {
                var serial = Build.SERIAL
                if (TextUtils.isEmpty(serial)) {
                    serial = CommandUtils.getProperty("no.such.thing")
                }
                if (TextUtils.isEmpty(serial)) {
                    serial = CommandUtils.getProperty("ro.serialno")
                }
                if (TextUtils.isEmpty(serial)) {
                    serial = CommandUtils.getProperty("ro.boot.serialno")
                }
                if (TextUtils.isEmpty(serial)) {
                    serial = Constants.UNKNOWN
                }
                return serial!!
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return Constants.UNKNOWN
        }
    
    @JvmStatic
    @SuppressLint("MissingPermission")
    fun getIccId(context: Context): String {
        try {
            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            return tm.simSerialNumber
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Constants.UNKNOWN
    }
    
    @SuppressLint("MissingPermission")
    fun getSimInfo(context: Context, list: MutableList<Pair<String, String>>) {
        try {
            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            list.add(Pair("SIM ISO", tm.simCountryIso))
            list.add(Pair("SIM OP ID", tm.simOperator))
            list.add(Pair("SIM OP NAME", tm.simOperatorName))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                list.add(Pair("SIM Id", tm.simCarrierId.toString() + ""))
                list.add(Pair("SIM IdName", tm.simCarrierIdName.toString()))
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                list.add(Pair("SIM SpecificId", tm.simSpecificCarrierId.toString() + ""))
                list.add(Pair("SIM SpecificIdName", tm.simSpecificCarrierIdName.toString()))
                list.add(Pair("SIM SpecificIdFromMM", tm.carrierIdFromSimMccMnc.toString() + ""))
            }
            list.add(Pair("SIM STATE", tm.simState.toString() + ""))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    @SuppressLint("MissingPermission")
    fun getOtherInfo(context: Context, list: MutableList<Pair<String, String>>) {
        try {
            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                list.add(Pair("NET Specifier", tm.networkSpecifier))
            }
            list.add(Pair("NET ISO", tm.networkCountryIso))
            list.add(Pair("NET OP", tm.networkOperator))
            list.add(Pair("NET OP NAME", tm.networkOperatorName))
            list.add(Pair("NET TYPE", tm.networkType.toString() + ""))
            list.add(Pair("Device Soft Version", tm.deviceSoftwareVersion))
            list.add(Pair("LINE NUMBER", tm.line1Number))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                list.add(Pair("MAN CODE", tm.manufacturerCode))
                list.add(Pair("Allocation Code", tm.typeAllocationCode))
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                list.add(Pair("MMS UA", tm.mmsUserAgent))
                list.add(Pair("MMS UA URL", tm.mmsUAProfUrl))
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                list.add(Pair("NAI", tm.nai))
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                list.add(Pair("DATA NET TYPE", tm.dataNetworkType.toString() + ""))
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                list.add(Pair("Phone Count", tm.phoneCount.toString() + ""))
            }
            val simBeans = querySimInfo(context)
            for (ben in simBeans) {
                val simId = ben.simId
                list.add(Pair("SIM $simId ID", ben.id.toString() + ""))
                list.add(Pair("SIM $simId ICCID", ben.iccId))
                list.add(Pair("SIM $simId CarrierName", ben.carrierName))
                list.add(Pair("SIM $simId DisplayName", ben.displayName))
                list.add(Pair("SIM $simId Number", ben.number))
                list.add(Pair("SIM $simId MCC", ben.mcc))
                list.add(Pair("SIM $simId MNC", ben.mnc))
            }
            getBuildInfo(list)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    /**
     * 查询数据库 SIM 信息 (同样需要 READ_PHONE_STATUS 权限)
     *
     * @param context
     * @return
     */
    private fun querySimInfo(context: Context): List<SimBean> {
        val list: MutableList<SimBean> = ArrayList()
        try {
            val uri = Uri.parse("content://telephony/siminfo") //访问raw_contacts表
            val resolver = context.contentResolver
            val cursor = resolver.query(uri, arrayOf("_id", "icc_id", "sim_id", "display_name",
                    "carrier_name", "name_source", "color", "number", "display_number_format",
                    "data_roaming", "mcc", "mnc"), "sim_id>=0", null, "sim_id")
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val id = cursor.getString(cursor.getColumnIndex("_id")).toInt()
                    val simId = cursor.getString(cursor.getColumnIndex("sim_id")).toInt()
                    val iccId = cursor.getString(cursor.getColumnIndex("icc_id"))
                    val carrierName = cursor.getString(cursor.getColumnIndex("carrier_name"))
                    val displayName = cursor.getString(cursor.getColumnIndex("display_name"))
                    val number = cursor.getString(cursor.getColumnIndex("number"))
                    val mcc = cursor.getString(cursor.getColumnIndex("mcc"))
                    val mnc = cursor.getString(cursor.getColumnIndex("mnc"))
                    val info = SimBean(id, simId, iccId, carrierName, displayName, number, mcc, mnc)
                    list.add(info)
                }
                cursor.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }
    
    private fun getBuildInfo(list: MutableList<Pair<String, String>>) {
        val array = CommandUtils.exec("getprop")
        for (line in array) {
            if (!TextUtils.isEmpty(line)
                && (line!!.contains("imei")
                        || line.contains("iccid")
                        || line.contains("imsi")
                        || line.contains("meid")
                        || line.contains("serialno")
                        )
            ) {
                val split = line.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                if (split.size == 2) {
                    try {
                        if ("[]" != split[1].trim { it <= ' ' }) {
                            list.add(Pair(split[0].trim { it <= ' ' }, split[1].trim { it <= ' ' }))
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}
