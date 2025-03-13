package com.song.deviceinfo.utils

import android.content.Context
import androidx.core.util.Pair
import com.bun.miitmdid.core.ErrorCode
import com.bun.miitmdid.core.MdidSdkHelper

/**
 * Created by chensongsong on 2020/9/22.
 */
class OaidUtils {
    fun getOaidsInfo(context: Context?, list: MutableList<Pair<String, String>>) {
        val code = MdidSdkHelper.InitSdk(context, true) { b, idSupplier ->
            LogUtils.d("MdidSdkHelper isSupported: $b")
            if (b) {
                list.add(Pair("OAID", idSupplier.oaid))
                list.add(Pair("VAID", idSupplier.vaid))
                list.add(Pair("AAID", idSupplier.aaid))
                idSupplier.shutDown()
            }
        }
        LogUtils.d("MdidSdkHelper Code: $code")
    }
    
    private fun descriptionCode(code: Int): String {
        return when (code) {
            ErrorCode.INIT_ERROR_DEVICE_NOSUPPORT -> "DEVICE_NOSUPPORT"
            ErrorCode.INIT_ERROR_LOAD_CONFIGFILE -> "LOAD_CONFIGFILE"
            ErrorCode.INIT_ERROR_MANUFACTURER_NOSUPPORT -> "MANUFACTURER_NOSUPPORT"
            ErrorCode.INIT_ERROR_RESULT_DELAY -> "RESULT_DELAY"
            ErrorCode.INIT_HELPER_CALL_ERROR -> "HELPER_CALL_ERROR"
            else -> "SUCCESS"
        }
    }
}
